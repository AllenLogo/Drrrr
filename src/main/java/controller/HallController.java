package controller;

import hall.entity.Hall;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import room.entity.Room;
import room.entity.Rooms;

@Controller
public class HallController {

	private Logger log = Logger.getLogger(HallController.class);

	@RequestMapping(value = "/createroom",method = RequestMethod.POST)
	public String  createroom(@RequestParam(name="roomname") String roomname,HttpServletRequest request){
	
		String name = request.getSession().getAttribute("name")==null? null:(String) request.getSession().getAttribute("name");
		String ip = request.getSession().getAttribute("ip")==null? null:(String) request.getSession().getAttribute("ip");
		
		Room room = new Room();
		room.setRoomName(roomname);
		room.setHost(name);	

		if( Rooms.getInstance().insertRooms(room) ){
			JSONObject json = JSONObject.fromObject("{}");
	    	json.accumulate("type", 2);
	    	json.accumulate("room", "[{\"roomname\":\""+roomname+"\",\"host\":\""+name+"\"}]");
			Hall.getInstance().sendMessage(json.toString());
			log.info("[Name："+name+"，IP："+ip+",事件：创建房间："+roomname+"]");
			return "redirect:room_list.jsp";
		}else{
			request.getSession().setAttribute("room_create_error", "聊天室创建失败：名称重复。");
			return "redirect:hall.jsp";
		}
	}
	
	@RequestMapping(value = "/addroom/{roomname}",method = RequestMethod.POST)
	public String addroom(@PathVariable("roomname") String roomname){
		System.out.println(roomname);
		return null;
	}
	
	
}


