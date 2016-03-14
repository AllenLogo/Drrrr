/**
 * 作者：李鹏飞
 * 时间：2016-2-25
 * 登陆请求处理
 * 1、数据库记录用户名、IP地址、登陆时间
 * 2、用户User类放入session
 */

package controller;


import javax.servlet.http.HttpServletRequest;
import login.dao.LoginServiceDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import user.User;


@Controller
@Scope("prototype")
public class LoginController {

	//登陆服务
	@Autowired
	private LoginServiceDao loginService;
	
	@RequestMapping( value="/login",method = RequestMethod.POST )
	public String test( @RequestParam("name") String name, HttpServletRequest request){
		
		//数据库记录登录用户信息
		User user = loginService.login(name, request);
		//用户User类存入session
		request.getSession().setAttribute("user", user);
		return "redirect:hall.jsp";
	}
	
	@RequestMapping( value="/login",method = RequestMethod.GET )
	public String test(){
		return "redirect:index.jsp";
	}
	
	
}
