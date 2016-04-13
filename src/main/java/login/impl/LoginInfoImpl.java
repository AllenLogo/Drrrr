package login.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import login.dao.LoginInfoDao;
import login.entity.LoginInfo;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import user.User;

public class LoginInfoImpl extends JdbcDaoSupport
  implements LoginInfoDao
{
  private Logger log = Logger.getLogger(LoginInfoImpl.class);

  public List<LoginInfo> getAllLoingInfo() {
    String sql = "select id,name,ip,logintime from logininfo;";
    this.log.info("SQL语句：" + sql);
    return getJdbcTemplate().query(sql, new RowMapper() {
      public LoginInfo mapRow(ResultSet rs, int num) throws SQLException {
        LoginInfo logininfo = new LoginInfo();
        logininfo.setId(Integer.valueOf(rs.getInt("id")));
        logininfo.setName(rs.getNString("name"));
        logininfo.setIp(rs.getNString("ip"));
        logininfo.setLogintime(rs.getTimestamp("logintime"));
        return logininfo;
      }
    });
  }

  public List<LoginInfo> getLoingInfo(String name, String ip)
  {
    return null;
  }

  public List<LoginInfo> getLoingInfo(String name, String ip, Timestamp logintime)
  {
    return null;
  }

  public List<LoginInfo> getLoingInfoByName(String name)
  {
    return null;
  }

  public List<LoginInfo> getLoingInfoByIp(String ip)
  {
    return null;
  }

  public List<LoginInfo> getLoingInfoByLoginTime(Timestamp logintime)
  {
    return null;
  }

  public void insertLoginInfo(User user)
  {
    StringBuilder sql = new StringBuilder("insert into logininfo(name,ip) values(?,?);");
    this.log.info("SQL语句：" + sql.toString() + "--参数1：" + user.getName() + "--参数2：" + user.getIp());
    getJdbcTemplate().update(sql.toString(), new Object[] { user.getName(), user.getIp() });
  }

  public boolean selectAdmin(String name, String pwd)
  {
    StringBuilder sql = new StringBuilder("SELECT count(id) as number FROM manager where name = ? and pwd = ?;");
    this.log.info("SQL语句：" + sql.toString() + "--参数1：" + name + "--参数2：" + pwd);
    int number = ((Integer)getJdbcTemplate().queryForObject(sql.toString(), new Object[] { name, pwd }, Integer.class)).intValue();
    if (number == 1) {
      return true;
    }
    return false;
  }
}