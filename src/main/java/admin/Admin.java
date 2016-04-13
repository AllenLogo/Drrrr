package admin;

public class Admin
{
  private String username;
  private String pwd;
  private String state;

  public Admin()
  {
  }

  public Admin(String username, String pwd, String state)
  {
    setPwd(pwd);
    setState(state);
    setUsername(username);
  }

  public boolean isAdmin()
  {
    return (this.username != null) && (this.username != "") && 
      (this.pwd != null) && (this.pwd != "") && 
      (this.state == "admin");
  }

  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPwd() {
    return this.pwd;
  }
  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
  public String getState() {
    return this.state;
  }
  public void setState(String state) {
    this.state = state;
  }
}