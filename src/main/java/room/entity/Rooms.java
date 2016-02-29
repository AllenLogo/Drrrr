package room.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Rooms {

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
	
	public String getAdminRoom(){
		JSONArray res_data = JSONArray.fromObject("[]");
		for(Room room : roomList ){
				JSONObject jo = JSONObject.fromObject("{}");
				jo.accumulate("roomname", room.getRoomName());
				jo.accumulate("roomhost", room.getHost());
				jo.accumulate("roompwd", room.getPwd());
				jo.accumulate("roomcount", room.getCount());
				jo.accumulate("roomnumber", room.getNumber());
				res_data.add(jo.toString());
		}
		return res_data.toString();
	}
	
	public boolean insertRooms(Room room){
		for(Room r : roomList){
			if(r.getRoomName().equals(room.getRoomName())){
				return false;
			}
		}
		roomList.add(room);
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
