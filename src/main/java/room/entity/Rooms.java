package room.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

public class Rooms {

	private Logger log = Logger.getLogger(Rooms.class);
	private static List<Room> roomList = Collections.synchronizedList(new ArrayList<Room>());
	
	static class RoomHolder {	  
		 static Rooms user = new Rooms();  
	}
	
	public static Rooms getInstance() {    
	        return RoomHolder.user;  
	}  
	
	public List<Room> getRooms(){
		return roomList;
	}
	
	public String getHallRooms(){
		JSONArray result = JSONArray.fromObject("[]");
		for( Room room : roomList ){
			result.add(room.getHall_Room_info());
		}
		return result.toString();
	}
	
	public boolean insertRooms(Room room){
		for(Room r : roomList){
			if(r.getRoomName().equals(room.getRoomName())){
				log.info(room.getHost()+"创建房间："+room.getRoomName()+"失败。原因：房间名重复");
				return false;
			}
		}
		roomList.add(room);
		log.info(room.getHost()+"创建房间："+room.getRoomName()+"成功。");
		return true;
	}
	
	public boolean removeRooms(Room room){
		if( roomList.contains(room) ){
			roomList.remove(room);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean findRooms(Room room){
		if( roomList.contains(room) )
			return false;
		else
			return true;
	}
	
	public Room findRoomByName(String name){
		for( Room r : roomList ){
			if( name.equals(r.getRoomName()) ){
				return r;
			}
		}
		return null;
	}
	
}
