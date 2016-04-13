package user;

import room.entity.Room;

public class User
{
  private String name;
  private String ip;
  private String state;
  private Room room;
  private String style;

  public User()
  {
  }

  public User(String name, String ip, String state)
  {
    setIp(ip);
    setName(name);
    setState(state);
  }

  public boolean isLogin()
  {
    return (this.name != null) && (this.name != "") && 
      (this.ip != null) && (this.ip != "") && 
      (this.state == "login");
  }

  public boolean HallorRoom()
  {
    return (this.room != null) && (this.style != null) && (this.style != "");
  }

  public String info()
  {
    return String.format("[name:%s,ip:%s]", new Object[] { this.name, this.ip });
  }

  public void Dectory()
  {
    setIp(null);
    setName(null);
    setRoom(null);
    setState(null);
    setStyle(null);
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getIp() {
    return this.ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public Room getRoom() {
    return this.room;
  }
  public void setRoom(Room room) {
    this.room = room;
  }
  public String getStyle() {
    return this.style;
  }
  public void setStyle(String style) {
    this.style = style;
  }
}