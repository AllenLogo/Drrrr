package login.dao;

import javax.servlet.http.HttpServletRequest;
import user.User;

public abstract interface LoginServiceDao
{
  public abstract User login(String paramString, HttpServletRequest paramHttpServletRequest);

  public abstract boolean login(String paramString1, String paramString2);
}