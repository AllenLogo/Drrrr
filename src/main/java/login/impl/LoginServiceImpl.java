package login.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


import login.dao.LoginInfoDao;
import login.dao.LoginServiceDao;
public class LoginServiceImpl implements LoginServiceDao {
	
	@Autowired
	private LoginInfoDao logininfoDao;
	
	//登陆流程
	public List<String> login(String name, HttpServletRequest request) {
		List<String> result = new ArrayList<String>();
		//第一步：获取IP地址
		String ip = getIpAddr(request);
		//第二步：用户名、IP存入数据库
		result.add(name);
		result.add(ip);
		logininfoDao.insertLoginInfo(name, ip);
		//第三步：返回
		return result;
	}
	
	public String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("PRoxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}
}
