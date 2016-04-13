package controller;

import admin.Admin;
import hall.entity.Hall;
import javax.servlet.http.HttpSession;
import login.dao.LoginServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import room.entity.Room;
import room.entity.Rooms;
import tools.JsonTool;

@Controller
@Scope("prototype")
public class AdminController
{

  @Autowired
  private LoginServiceDao loginService;

  @ResponseBody
  @RequestMapping(value={"/admin/login"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String adminlogin(@RequestParam("username") String username, @RequestParam("pwd") String pwd, HttpSession session)
  {
    if (this.loginService.login(username, pwd)) {
      session.setAttribute("admin", new Admin(username, pwd, "admin"));
      return JsonTool.getMessage(new String[] { "type", "success" });
    }
    return JsonTool.getMessage(new String[] { "type", "error", "content", "\"用户名或密码错误！\"" });
  }

  @ResponseBody
  @RequestMapping(value={"/admin/hall"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String admin_hall(HttpSession session)
  {
    Admin admin = (Admin)session.getAttribute("admin");
    if ((admin != null) && (admin.isAdmin())) {
      return Hall.getInstance().getHallUser();
    }
    return "[]";
  }

  @ResponseBody
  @RequestMapping(value={"/admin/rooms"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String admin_rooms(HttpSession session)
  {
    Admin admin = (Admin)session.getAttribute("admin");
    if ((admin != null) && (admin.isAdmin())) {
      return Rooms.getInstance().getAdminRoom();
    }
    return "[]";
  }

  @ResponseBody
  @RequestMapping(value={"/admin/room"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String admin_room(@RequestParam("roomname") String roomname, HttpSession session)
  {
    Room room = Rooms.getInstance().findRoomByName(roomname);
    if (room == null) {
      return "[]";
    }
    return room.getAdminRoom();
  }

  @ResponseBody
  @RequestMapping(value={"/admin/roommanager"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String admin_room_member(@RequestParam("roomname") String roomname, @RequestParam("username") String username, HttpSession session)
  {
    Room room = Rooms.getInstance().findRoomByName(roomname);
    if (room == null) {
      return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室不存在！\"" });
    }
    if (!room.isOpen()) {
      return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室已关闭！\"" });
    }
    if (!room.findMember(username)) {
      return JsonTool.getMessage(new String[] { "type", "error", "content", "\"用户" + username + "不存在！\"" });
    }
    if (room.closeMember(username)) {
      return JsonTool.getMessage(new String[] { "type", "success", "content", "\"用户" + username + "被管理员请出聊天室" + room.getRoomName() + "成功\"" });
    }
    return JsonTool.getMessage(new String[] { "type", "success", "content", "\"用户" + username + "被管理员请出聊天室" + room.getRoomName() + "失败\"" });
  }
}