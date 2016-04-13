package controller;

import hall.entity.Hall;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import room.entity.Room;
import room.entity.Rooms;
import tools.JsonTool;
import tools.StringTool;
import tools.Styles;
import user.User;

@Controller
public class HallController
{
  private Logger log = Logger.getLogger(HallController.class);

  @Autowired
  private Styles styleList;

  @ResponseBody
  @RequestMapping(value={"/createroom"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String createroom(@RequestParam(name="roomname") String roomname, @RequestParam(name="member") int member, @RequestParam(name="pwd") String pwd, HttpServletRequest request) { User user = (User)request.getSession().getAttribute("user");
    if ((user != null) && (user.isLogin()))
    {
      Room room = new Room(roomname, user.getName(), member, pwd);

      if (Rooms.getInstance().insertRooms(room)) {
        room.addUser(user);
        user.setRoom(room);
        Hall.getInstance().sendMessage(JsonTool.getMessage(new String[] { "type", "02", "room", room.getHall_Room_info() }));
        this.log.info(user.info() + "事件：创建房间：" + roomname + "成功]");
        return JsonTool.getMessage(new String[] { "type", "success" });
      }
      this.log.info(user.info() + "事件：创建房间：" + roomname + "失败]");
      return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室名重复\"" });
    }

    return JsonTool.getMessage(new String[] { "type", "error", "content", "\"请登录\"" }); }

  @ResponseBody
  @RequestMapping(value={"/addroom/{roomname}"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String addroom(@PathVariable("roomname") String roomname, String pwd, HttpSession session)
  {
    User user = (User)session.getAttribute("user");
    if ((user != null) && (user.isLogin())) {
      String name = StringTool.getMyURLEncoder(roomname);
      Room room = Rooms.getInstance().findRoomByName(name);
      if (room == null) {
        return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室不存在\"" });
      }
      if (!room.checkPwd(pwd)) {
        return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室密码错误\"" });
      }
      if (!room.isCount()) {
        return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室人满\"" });
      }
      if (room.findMember(user.getName())) {
        return JsonTool.getMessage(new String[] { "type", "error", "content", "\"聊天室已有成员取名：" + user.getName() + "\"" });
      }
      room.addUser(user);
      user.setRoom(room);
      return JsonTool.getMessage(new String[] { "type", "success" });
    }
    return JsonTool.getMessage(new String[] { "type", "error", "content", "\"未登录\"" });
  }

  @RequestMapping(value={"/room/{roomname}"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String room(@PathVariable("roomname") String roomname, HttpSession session)
  {
    String name = StringTool.getMyURLEncoder(roomname);
    User user = (User)session.getAttribute("user");
    Room room = null;
    if ((user == null) || (!user.isLogin())) {
      return "redirect:/index.jsp";
    }
    room = user.getRoom();
    if ((room == null) || (room.getRoomName().equals(name))) {
      room = Rooms.getInstance().findRoomByName(name);
    }
    if ((room != null) && (room.isOpen()) && (room.isCount())) {
      room.addUser(user);
      user.setRoom(room);
      user.setStyle(this.styleList.getRandomStyle());
      return "room";
    }
    return "redirect:/hall.jsp";
  }

  @ResponseBody
  @RequestMapping(value={"/outroom"}, produces={"text/html;charset=UTF-8"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String outroom(HttpSession session) {
    User user = (User)session.getAttribute("user");
    if ((user != null) && (user.isLogin()) && (user.HallorRoom())) {
      Room room = user.getRoom();
      room.remvoeUser(user);
      user.setRoom(null);
      user.setStyle("");
      return "";
    }
    return JsonTool.getMessage(new String[] { "type", "error", "content", "\"未登录\"" });
  }
}