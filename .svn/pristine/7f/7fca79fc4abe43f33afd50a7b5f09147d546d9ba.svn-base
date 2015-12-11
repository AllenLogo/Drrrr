package login.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
	
	private static List<String> userList = Collections.synchronizedList(new ArrayList<String>());
	
	static class UserHolder {	  
		 static User user = new User();  
	}
	
	public static User getInstance() {    
	        return UserHolder.user;  
	}  
	
	public List<String> getUsers(){
		return userList;
	}
	
	public boolean insertUser(String name){
		if( findUser(name) ){
			userList.add(name);
			return true;
		}else
			return false;
	}
	
	public boolean removeUser(String name){
		if( findUser(name) ){
			userList.remove(name);
			return true;
		}else
			return false;
	}
	
	public boolean findUser(String name){
		for(String user : userList){
			if( user.equals(name) )
				return false;
		}
		return true;
	}
}
