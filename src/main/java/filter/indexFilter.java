package filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;


public class indexFilter extends  OncePerRequestFilter {

	//日志
	private Logger log = Logger.getLogger(indexFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        //session存在登录信息，跳转大厅页面；否则跳转登录页面
        String name = session.getAttribute("name") != null ? (String)session.getAttribute("name"):null;
        String ip = session.getAttribute("ip") != null ? (String)session.getAttribute("ip"):null;
        String state = session.getAttribute("state") != null ? (String)session.getAttribute("state"):null;
        if( name != null && name != "" &&
        	ip != null && ip != "" &&
        	state == "1"){
        	//session存在聊天室信息
        	String room = session.getAttribute("room") != null?(String) session.getAttribute("room"):null;
        	if( room != null ){
        		log.info(name+"重新登录系统进入房间："+room);
        	}else{
        		log.info(name+"重新登录系统进入大厅");
        		servletResponse.sendRedirect("hall.jsp");
        	}
        }else{
        	//设置session过期时间
        	session.setMaxInactiveInterval(30*60);
            filterChain.doFilter(servletRequest,servletResponse);
        }
        
	}

}
