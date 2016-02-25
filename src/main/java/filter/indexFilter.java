package filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import room.entity.Room;


public class indexFilter extends  OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		//获取对象
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        //获取用户基本信息
        String name = session.getAttribute("name") != null ? (String)session.getAttribute("name"):null;
        String ip = session.getAttribute("ip") != null ? (String)session.getAttribute("ip"):null;
        String state = session.getAttribute("state") != null ? (String)session.getAttribute("state"):null;
        
        //验证用户是否登录
        if( name != null && name != "" &&
        	ip != null && ip != "" &&
        	state == "1"  ){
 
        	//验证用户是否在聊天室
        	Room room = session.getAttribute("room") != null?(Room) session.getAttribute("room"):null;
        	String style  = session.getAttribute("style") != null?(String)session.getAttribute("style"):null;
        	
        	if( room != null && room.isOpen() && style != null && style !=""){
        		
        		servletResponse.sendRedirect("room/"+room.getRoomName());
        	
        	}else{
        		
        		servletResponse.sendRedirect("hall.jsp");
        		
        	}
        }else{
        	//设置session过期时间
        	session.setMaxInactiveInterval(30*60);
            filterChain.doFilter(servletRequest,servletResponse);
        }
        
	}

}
