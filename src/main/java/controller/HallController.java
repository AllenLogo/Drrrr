package controller;

import java.util.HashMap;
import java.util.Map;

import hall.entity.Hall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import room.entity.Room;
import room.entity.Rooms;
import tools.JsonTool;
import tools.Styles;
import tools.StringTool;;

@Controller
public class HallController {

	private Logger log = Logger.getLogger(HallController.class);
	@Autowired
	private Styles styleList;

	@ResponseBody
	@RequestMapping(value = "/createroom",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public String  createroom(@RequestParam(name="roomname") String roomname,
							   @RequestParam(name="member") int member,
							   @RequestParam(name="pwd") String pwd,
							   HttpServletRequest request){
		
		String name = request.getSession().getAttribute("name")==null? null:(String) request.getSession().getAttribute("name");
		String ip = request.getSession().getAttribute("ip")==null? null:(String) request.getSession().getAttribute("ip");
		
		Room room = new Room(roomname,name,member,pwd);
		Map<String,String> res_map = new HashMap<String,String>();

		if( Rooms.getInstance().insertRooms(room) ){
			res_map.put("type", "02");
			res_map.put("room", room.getHall_Room_info());
			Hall.getInstance().sendMessage(JsonTool.buildStrig(res_map));
			log.info("[Name："+name+"，IP："+ip+",事件：创建房间："+roomname+"]");
			res_map.clear();
			res_map.put("type", "success");
		}else{
			res_map.put("type", "error");
			res_map.put("content", "\"聊天室名称重复\"");
		}
		return JsonTool.buildMessage(res_map).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/addroom/{roomname}",produces = "text/html;charset=UTF-8",method = {RequestMethod.POST})
	public String addroom(@PathVariable("roomname") String roomname,
			HttpServletResponse response,
			String pwd,
			HttpSession session){
		String name = StringTool.getMyURLEncoder(roomname);
		Room room = Rooms.getInstance().findRoomByName(name);
		Map<String,String> res_map = new HashMap<String,String>();
		//判断聊天室是否存在，聊天室密码是否正确
		if( room != null ){
			if( room.checkPwd(pwd) ){
				if( room.isCount() ){
					if( room.findMember(name) ){
						res_map.put("type", "success");
						Rooms.getInstance().insertRooms(room);
					}else{
						res_map.put("type", "error");
						res_map.put("content", "\"聊天室已有成员取名："+name+"\"");
					}
				}else{
					res_map.put("type", "error");
					res_map.put("content", "\"聊天室人满\"");
				}
			}else{
				res_map.put("type", "error");
				res_map.put("content", "\"聊天室密码错误\"");
			}
		}else{
			res_map.put("type", "error");
			res_map.put("content", "\"聊天室不存在\"");
		}
		return JsonTool.buildMessage(res_map).toString();
	}
	
	
	@RequestMapping(value="/room/{roomname}",produces = "text/html;charset=UTF-8",method = RequestMethod.GET)
	public String room(@PathVariable("roomname") String roomname,HttpSession session){  
		String name = StringTool.getMyURLEncoder(roomname);
		Room room = (Room) session.getAttribute("room");
		if(room == null || room.getRoomName().equals(name)){
			room = Rooms.getInstance().findRoomByName(name);
		}
		if(room != null && room.isOpen() && room.isCount()){
			session.setAttribute("room", room);
			session.setAttribute("style", styleList.getRandomStyle());
			return "room";
		}else{
			return "redirect:/hall.jsp";
		}
	}
	
}


