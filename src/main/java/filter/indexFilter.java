package filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;
import room.entity.Room;
import user.User;

public class indexFilter extends OncePerRequestFilter
{
  protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
    throws ServletException, IOException
  {
    User user = (User)servletRequest.getSession().getAttribute("user");

    if ((user != null) && (user.isLogin()))
    {
      if (user.HallorRoom())
        servletResponse.sendRedirect("room/" + user.getRoom().getRoomName());
      else
        servletResponse.sendRedirect("hall.jsp");
    }
    else
      filterChain.doFilter(servletRequest, servletResponse);
  }
}