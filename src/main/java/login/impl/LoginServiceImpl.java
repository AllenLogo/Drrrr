package login.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;

import login.dao.LoginInfoDao;
import login.dao.LoginServiceDao;
import login.entity.LoginInfo;
import login.entity.User;

public class LoginServiceImpl implements LoginServiceDao {
	
	@Autowired
	private LoginInfoDao logininfoDao;
	
	private Logger log = Logger.getLogger(LoginServiceImpl.class);

	
	public String getAllLoingInfo() {
		List<LoginInfo> list = logininfoDao.getAllLoingInfo();
		String json = JSONArray.fromObject(list).toString();
		log.info(json);
		return json;
	}
	
	//登陆流程
	public List<String> login(String name, HttpServletRequest request) {
		List<String> result = new ArrayList<String>();
		//第一步：获取IP地址
		String ip = getIpAddr(request);
		//第二步：判断用户名是否存在
		//是，返回错误信息
		//否，记录登录信息
		if( !User.getInstance().insertUser(name) ){
			result.add("0");
			result.add( "用户名以存在，请重新输入！");
		}else{
			result.add("1");
			result.add(name);
			result.add(ip);
			logininfoDao.insertLoginInfo(name, ip);
		}
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
