package login.impl;

import javax.servlet.http.HttpServletRequest;
import login.dao.LoginInfoDao;
import login.dao.LoginServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import user.User;

public class LoginServiceImpl
  implements LoginServiceDao
{

  @Autowired
  private LoginInfoDao logininfoDao;

  public User login(String name, HttpServletRequest request)
  {
    User user = new User(name, getIpAddr(request), "login");
    this.logininfoDao.insertLoginInfo(user);
    return user;
  }

  public String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("PRoxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  public boolean login(String name, String pwd)
  {
    return this.logininfoDao.selectAdmin(name, pwd);
  }
}