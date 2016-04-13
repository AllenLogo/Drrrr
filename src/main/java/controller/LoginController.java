package controller;

import javax.servlet.http.HttpServletRequest;
import login.dao.LoginServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import user.User;

@Controller
@Scope("prototype")
public class LoginController
{

  @Autowired
  private LoginServiceDao loginService;

  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String test(@RequestParam("name") String name, HttpServletRequest request)
  {
    User user = this.loginService.login(name, request);

    request.getSession().setAttribute("user", user);
    return "redirect:hall.jsp";
  }

  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String test() {
    return "redirect:index.jsp";
  }
}