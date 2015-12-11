package controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import login.dao.LoginServiceDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Scope("prototype")
public class LoginController {
	
	//日志
	private Logger log = Logger.getLogger(LoginController.class);
	
	//登陆服务
	@Autowired
	private LoginServiceDao loginService;
	
	@RequestMapping( value="/login",method = RequestMethod.POST )
	public String test( @RequestParam("name") String name, HttpServletRequest request){
		List<String> list = loginService.login(name, request);
		if( list.get(0).equals("1") ){
			log.info(name+"成功登陆");
			request.getSession().setAttribute("name", list.get(1));
			request.getSession().setAttribute("ip", list.get(2));
			return "redirect:hall.jsp";
		}else{
			request.getSession().setAttribute("login_error", list.get(1));
			log.info(name+"登陆失败");
			return "redirect:index.jsp";
		}
	}
	
	@RequestMapping( value="/login",method = RequestMethod.GET )
	public String test(){
		return "redirect:index.jsp";
	}
	
	
}
