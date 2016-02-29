/**
 * 作者：李鹏飞
 * 时间：2026-2-26
 * 聊天室实体类
 */
package room.entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.JsonTool;
import user.User;

public class Room {
	private Logger log = Logger.getLogger(Room.class);
	
	private String roomName;					//聊天室名称
	private String host;						//聊天室主人
	private int count;							//聊天室限制人数
	private int number;						//聊天室实际人数
	private String pwd;							//聊天室密码
	private List<WebSocketSession> sessions;	//聊天室websocket链表
	private Set<String> user;					//聊天室成员链表
	private boolean roomState=false;			//聊天室状态标志
	
	/**
	 * 构造函数
	 */
	public Room(String roomName,String host,int count,String pwd){
		this.setRoomName(roomName);
		this.setHost(host);
		this.setCount(count);
		this.setPwd(pwd);
		this.number=0;
		this.sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());
		this.user = new HashSet<String>();
		this.OpenRoom();
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 * 
	 * 用户加入
	 * 人数增加
	 * 日志记录
	 * 
	 */
	public boolean insertMember(WebSocketSession session){
		if( !this.isOpen() ){return false;}
		
		if( session.isOpen() && !sessions.contains(session)  ){
			this.sessions.add(session);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean addUser(String name){
		if(!this.isOpen()){return false;}
		if(this.number >= count) {return false;}
		if(!this.user.contains(name)){
			this.user.add(name);
			this.number++;
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 
	 * @param session
	 * @return
	 * 
	 * 用户退出
	 * 人数减少
	 * 主人销毁
	 * 日志记录
	 */
	public boolean removeMember(WebSocketSession session){
		if( !this.isOpen() ){return false;}

		if( this.sessions.contains(session) ){
			this.sessions.remove(session);
			return true;
		}else{
			return false;
		}
	}
	
	public void remvoeUser(String name){
		if(!this.isOpen()){return ;}
		if(this.number >= count) {return ;}
		if(this.user.contains(name)){
			this.user.remove(name);
			this.number--;
		}
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * 
	 * 用户查找
	 * 
	 */
	public boolean findMember(String name){
		if( !this.isOpen() ){return false;}
		for( WebSocketSession m : sessions ){
			User user = (User) m.getAttributes().get("user");
			if( user.getName().equals(name) )
				return true;
				
		}
		return false;
	}
	
	public void sendMessage(String message){
		if( !this.isOpen() ){return ;}
		TextMessage Textmsg = new TextMessage(message.getBytes());
		for( WebSocketSession session : sessions ){
			if( session.isOpen() ){
				try {
					session.sendMessage(Textmsg);
					log.info("[服务器发送消息成功："+message+"]");
				} catch (IOException e) {
					log.error("[服务器发送消息失败："+message+"]");
				}
			}
		}
	}

	public String getRoom_info(){
		if( !this.isOpen() ){return "";}
		Map<String,String> map = new HashMap<String,String>();
		map.put("room_name", this.roomName);
		map.put("room_number", ""+this.number);
		map.put("room_count", ""+this.count);
		map.put("room_list", getMember_map());
		
		String content = JsonTool.buildStrig(map);
		map.clear();
		map.put("type", "3");
		map.put("content", content);
		return JsonTool.buildStrig(map);
	}
	
	//{"host":{'name':'lpf','style':'zaika'},"number":{'name':'allen','style':'zaika'}}
	private String getMember_map(){
		if( !this.isOpen() ){return "";}
		Map<String,String> maps = new HashMap<String,String>();
		Map<String,String> map = new HashMap<String,String>();
		User user;
		for( WebSocketSession session : sessions ){
			user =(User) session.getAttributes().get("user");
			map.put("name", user.getName());
			map.put("style", user.getStyle());
			maps.put(user.getName(), JsonTool.buildStrig(map));
			map.clear();
		}
		return JsonTool.buildStrig(maps);
	}
	
	public String getAdminRoom(){
		User user;
		JSONArray res_data = JSONArray.fromObject("[]");
		for(WebSocketSession session : sessions ){
				JSONObject jo = JSONObject.fromObject("{}");
				user = (User) session.getAttributes().get("user");
				jo.accumulate("username", user.getName());
				jo.accumulate("ip", user.getIp());
				if( user.getName().equals(this.host) ){
					jo.accumulate("roommember", "主人");
				}else{
					jo.accumulate("roommember", "成员");
				}
				res_data.add(jo.toString());
		}
		return res_data.toString();
	}
	
	/**
	 * @return 新增聊天室，大厅更新聊天室列表所需信息
	 */
	public String getHall_Room_info(){
		if( !this.isOpen() ){return "{}";}
		return JsonTool.getMessage("roomname",roomName,"roomhost",host,"roomnumber",number+"","roomcount",count+"","roompwd",getPwd()+"");
	}
	
	/**
	 * 聊天室销毁
	 * 内部调用
	 * @param name
	 */
	private void dectoryRoom(){
		if( !this.isOpen() ){return ;}
		this.sessions.clear();
		this.CloseRoom();
		Rooms.getInstance().removeRooms(this);
	}
	
	/**
	 * 聊天室销毁
	 * 主人外部调用
	 * @param name
	 */
	public void dectoryRoom(String name){
		if( !this.isOpen() ){return ;}
		if( !this.host.equals(name)){return ;}
		this.dectoryRoom();
	}
	
	public boolean closeMember(String name){
		if( !this.isOpen() ){return false;}
		for( WebSocketSession m : sessions ){
			User user = (User) m.getAttributes().get("user");
			if( user.getName().equals(name) ){
				try {
					m.close();
				} catch (IOException e) {
					log.info(e.getMessage());
				}
				return true;
			}
				
		}
		return false;
	}
	
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getPwd() {
		return (this.pwd != null && this.pwd != "")?true:false;
	}
	
	public boolean checkPwd(String pwd) {
		return this.pwd.equals(pwd);
	}

	private void setPwd(String pwd) {
		if(pwd == null || "".equals(pwd)){
			this.pwd = "";
		}else{
			this.pwd = pwd;
		}
	}

	public int getNumber() {
		return number;
	}
	
	public boolean isCount() {
		return count >= number?true:false;
	}

	private void CloseRoom() {
		this.roomState = false;
	}

	private void OpenRoom() {
		this.roomState = true;
	}
	
	public boolean isOpen(){
		return this.roomState;
	}
}
