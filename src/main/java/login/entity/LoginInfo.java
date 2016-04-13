package login.entity;

import java.sql.Timestamp;

public class LoginInfo
{
  private Integer id;
  private String name;
  private String ip;
  private Timestamp logintime;

  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
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
  public Timestamp getLogintime() {
    return this.logintime;
  }
  public void setLogintime(Timestamp logintime) {
    this.logintime = logintime;
  }
}