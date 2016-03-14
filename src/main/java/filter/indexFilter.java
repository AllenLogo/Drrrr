/**
 * 作者：李鹏飞
 * 时间：2016-2-25
 * 过滤器
 * 路径：/index.jsp
 * 作用:过滤三类请求
 * 1、第一次登录，请求不转发
 * 2、大厅界面意外退出，请求转发：hall.jsp
 * 3、聊天室界面意外退出，请求转发：/room/{roomname}
 */
package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import user.User;


public class indexFilter extends  OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		
		//获取用户基本信息
		User user = (User) servletRequest.getSession().getAttribute("user");
        
        //验证用户是否登录
        if( user != null && user.isLogin() ){
        	
        	if( user.HallorRoom() ){
        		servletResponse.sendRedirect("room/"+user.getRoom().getRoomName());
        	}else{
        		servletResponse.sendRedirect("hall.jsp");
        	}
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
        
	}

}
