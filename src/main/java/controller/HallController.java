package controller;

import java.io.UnsupportedEncodingException;

import hall.entity.Hall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import room.entity.Room;
import room.entity.Rooms;

@Controller
public class HallController {

	private Logger log = Logger.getLogger(HallController.class);

	@ResponseBody
	@RequestMapping(value = "/createroom",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String  createroom(@RequestParam(name="roomname") String roomname,
							   @RequestParam(name="member") int member,
							   @RequestParam(name="pwd") String pwd,HttpServletRequest request){
		
		String name = request.getSession().getAttribute("name")==null? null:(String) request.getSession().getAttribute("name");
		String ip = request.getSession().getAttribute("ip")==null? null:(String) request.getSession().getAttribute("ip");
		
		Room room = new Room();
		
		room.setRoomName(roomname);
		room.setHost(name);	
		room.setCount(member);
		if( !pwd.equals("") ){
			room.setPwd(pwd);
		}
		

		if( Rooms.getInstance().insertRooms(room) ){
			JSONObject json = JSONObject.fromObject("{}");
	    	json.accumulate("type", 2);
	    	json.accumulate("room", "[{\"roomname\":\""+roomname+"\",\"host\":\""+name+"\",\"pwd\":\""+room.getPwd()+"\"}]");
			Hall.getInstance().sendMessage(json.toString());
			log.info("[Name："+name+"，IP："+ip+",事件：创建房间："+roomname+"]");
			json = JSONObject.fromObject("{}");
			json.accumulate("type", "success");
			return json.toString();
		}else{
			JSONObject json = JSONObject.fromObject("{}");
			json.accumulate("type", "error");
			json.accumulate("content", "\"聊天室名称重复\"");
			return json.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addroom/{roomname}",produces = "text/html;charset=UTF-8",method = {RequestMethod.POST})
	public String addroom(@PathVariable("roomname") String roomname,
			HttpServletResponse response,String pwd){
		String name = getMyURLEncoder(roomname);
		Room room = Rooms.getInstance().findRoomByName(name);
		if( room != null && room.getPwd(pwd) ){
			JSONObject json = JSONObject.fromObject("{}");
			json.accumulate("type", "success");
			return json.toString();
		}else{
			JSONObject json = JSONObject.fromObject("{}");
			json.accumulate("type", "error");
			json.accumulate("content", "\"聊天室密码错误\"");
			return json.toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/room/{roomname}",produces = "text/html;charset=UTF-8",method = RequestMethod.GET)
	public String room(@PathVariable("roomname") String roomname){  
		
		String name = getMyURLEncoder(roomname);
		return name;
	}
	
	private String getMyURLEncoder(String str){
		try {
			return java.net.URLDecoder.decode(str,"utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	@SuppressWarnings("unused")
	private String setMyURLEncoder(String str,String type){
		try {
			return java.net.URLEncoder.encode(str, type);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
}


