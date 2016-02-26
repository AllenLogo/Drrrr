package login.dao;


import javax.servlet.http.HttpServletRequest;

import user.User;


public interface LoginServiceDao {

	public User login(String name,HttpServletRequest request);
}
