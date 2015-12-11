package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class indexFilter extends  OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("过滤请求");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header =null!=request &&null!=request.getHeader("X-Requested-With")?request.getHeader("X-Requested-With"):null;
        List<String> urllist = new ArrayList<String>();
        urllist.add("/login");
        urllist.add("/index.jsp");
        if("XMLHttpRequest".equals(header) && !urllist.contains(request.getServletPath())){
        	System.out.println(request.getServletPath()+"拦截");
        	servletResponse.sendRedirect("");
        }
        else {
        	System.out.println(request.getServletPath()+"不拦截");
            filterChain.doFilter(servletRequest,servletResponse);
        }
	}

}
