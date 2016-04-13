package hall.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;
import room.entity.Room;
import user.User;

public class HallFilter extends OncePerRequestFilter
{
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException
  {
    User user = (User)request.getSession().getAttribute("user");
    if ((user != null) && (user.isLogin())) {
      if (user.HallorRoom())
        response.sendRedirect("room/" + user.getRoom().getRoomName());
      else
        filterChain.doFilter(request, response);
    }
    else
      response.sendRedirect("index.jsp");
  }
}