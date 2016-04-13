package login.interceptor;

import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor
  implements HandlerInterceptor
{
  public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
    throws Exception
  {
  }

  public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
    throws Exception
  {
    System.out.println("退出拦截");
  }

  public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2)
    throws Exception
  {
    System.out.println("/login请求拦截");
    return true;
  }
}