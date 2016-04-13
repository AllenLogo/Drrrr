package room.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.JsonTool;
import user.User;

public class Room
{
  private Logger log = Logger.getLogger(Room.class);
  private String roomName;
  private String host;
  private int count;
  private int number;
  private String pwd;
  private List<WebSocketSession> sessions;
  private List<User> users;
  private boolean roomState = false;

  public Room(String roomName, String host, int count, String pwd)
  {
    setRoomName(roomName);
    setHost(host);
    setCount(count);
    setPwd(pwd);
    this.number = 0;
    this.sessions = Collections.synchronizedList(new ArrayList());
    this.users = Collections.synchronizedList(new ArrayList());
    OpenRoom();
  }

  public boolean insertMember(WebSocketSession session)
  {
    if (!isOpen()) return false;

    if ((session.isOpen()) && (!this.sessions.contains(session))) {
      this.sessions.add(session);
      return true;
    }
    return false;
  }

  public boolean addUser(User user)
  {
    if (!isOpen()) return false;
    if (this.number >= this.count) return false;
    if (!this.users.contains(user)) {
      this.users.add(user);
      this.number += 1;
      return true;
    }
    return false;
  }

  public boolean removeMember(WebSocketSession session)
  {
    if (!isOpen()) return false;

    if (this.sessions.contains(session)) {
      this.sessions.remove(session);
      return true;
    }
    return false;
  }

  public void remvoeUser(User user)
  {
    if (!isOpen()) return;
    if (this.number >= this.count) return;
    if (this.users.contains(user)) {
      this.users.remove(user);
      this.number -= 1;
    }
  }

  public boolean findMember(String name)
  {
    if (!isOpen()) return false;
    for (WebSocketSession m : this.sessions) {
      User user = (User)m.getAttributes().get("user");
      if (user.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public void sendMessage(String message) {
    if (!isOpen()) return;
    TextMessage Textmsg = new TextMessage(message.getBytes());
    for (WebSocketSession session : this.sessions)
      if (session.isOpen())
        try {
          session.sendMessage(Textmsg);
          this.log.info("[服务器发送消息成功：" + message + "]");
        } catch (IOException e) {
          this.log.error("[服务器发送消息失败：" + message + "]");
        }
  }

  public String getRoom_info()
  {
    if (!isOpen()) return "";
    Map map = new HashMap();
    map.put("room_name", this.roomName);
    map.put("room_number", this.number);
    map.put("room_count", this.count);
    map.put("room_list", getMember_map());

    String content = JsonTool.buildStrig(map);
    map.clear();
    map.put("type", "3");
    map.put("content", content);
    return JsonTool.buildStrig(map);
  }

  private String getMember_map()
  {
    if (!isOpen()) return "";
    Map maps = new HashMap();
    Map map = new HashMap();
    for (User user : this.users) {
      map.put("name", user.getName());
      map.put("style", user.getStyle());
      maps.put(user.getName(), JsonTool.buildStrig(map));
      map.clear();
    }
    return JsonTool.buildStrig(maps);
  }

  public String getAdminRoom()
  {
    JSONArray res_data = JSONArray.fromObject("[]");
    for (WebSocketSession session : this.sessions) {
      JSONObject jo = JSONObject.fromObject("{}");
      User user = (User)session.getAttributes().get("user");
      jo.accumulate("username", user.getName());
      jo.accumulate("ip", user.getIp());
      if (user.getName().equals(this.host))
        jo.accumulate("roommember", "主人");
      else {
        jo.accumulate("roommember", "成员");
      }
      res_data.add(jo.toString());
    }
    return res_data.toString();
  }

  public String getHall_Room_info()
  {
    if (!isOpen()) return "{}";
    return JsonTool.getMessage(new String[] { "roomname", this.roomName, "roomhost", this.host, "roomnumber", this.number+"", "roomcount", this.count+"", "roompwd", getPwd()+"" });
  }

  private void dectoryRoom()
  {
    if (!isOpen()) return;
    this.sessions.clear();
    CloseRoom();
    Rooms.getInstance().removeRooms(this);
  }

  public void dectoryRoom(String name)
  {
    if (!isOpen()) return;
    if (!this.host.equals(name)) return;
    dectoryRoom();
  }

  public boolean closeMember(String name) {
    if (!isOpen()) return false;
    for (WebSocketSession m : this.sessions) {
      User user = (User)m.getAttributes().get("user");
      if (user.getName().equals(name)) {
        try {
          TextMessage Textmsg = new TextMessage(JsonTool.getMessage(new String[] { "type", "4", "content", "你被管理员请出聊天室" }).getBytes());
          m.sendMessage(Textmsg);
          m.close();
          user.Dectory();
          remvoeUser(user);
        } catch (IOException e) {
          this.log.info(e.getMessage());
        }
        return true;
      }
    }

    return false;
  }

  public String getRoomName()
  {
    return this.roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public boolean getPwd() {
    return (this.pwd != null) && (this.pwd != "");
  }

  public boolean checkPwd(String pwd) {
    return this.pwd.equals(pwd);
  }

  private void setPwd(String pwd) {
    if ((pwd == null) || ("".equals(pwd)))
      this.pwd = "";
    else
      this.pwd = pwd;
  }

  public int getNumber()
  {
    return this.number;
  }

  public boolean isCount() {
    return this.count >= this.number;
  }

  private void CloseRoom() {
    this.roomState = false;
  }

  private void OpenRoom() {
    this.roomState = true;
  }

  public boolean isOpen() {
    return this.roomState;
  }
}