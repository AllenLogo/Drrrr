/**
 * 作者：李鹏飞
 * 时间：2016-2-25
 * 登录信息数据库处理
 * 解析得到用户名、IP地址存入数据库
 */
package login.impl;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import user.User;
import login.dao.LoginInfoDao;
import login.dao.LoginServiceDao;

public class LoginServiceImpl implements LoginServiceDao {
	
	@Autowired
	private LoginInfoDao logininfoDao;
	
	/**
	 * 数据库存储用户登录信息
	 */
	public User login(String name, HttpServletRequest request) {
		User user = new User(name, getIpAddr(request), "login");
		logininfoDao.insertLoginInfo(user);
		return user;
	}
	
	/**
	 * 获取ip地址
	 * @param request
	 * @return
	 */
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

	/**
	 * 验证登录
	 */
	public boolean login(String name, String pwd) {
		return logininfoDao.selectAdmin(name, pwd);
	}
}
