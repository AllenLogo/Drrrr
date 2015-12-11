package login.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface LoginServiceDao {

	public String getAllLoingInfo();	
	public List<String> login(String name,HttpServletRequest request);
}
