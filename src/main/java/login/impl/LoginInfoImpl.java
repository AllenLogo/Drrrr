/**
 * 作者：李鹏飞
 * 时间：2016-2-25
 * 数据库处理
 */
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


public class LoginInfoImpl extends JdbcDaoSupport implements LoginInfoDao  {
	
	private Logger log = Logger.getLogger(LoginInfoImpl.class);
	
	public List<LoginInfo> getAllLoingInfo() {
		String sql = "select id,name,ip,logintime from logininfo;";
		log.info("SQL语句："+sql);
		return getJdbcTemplate().query(sql, new RowMapper<LoginInfo>() {  
            public LoginInfo mapRow(ResultSet rs, int num) throws SQLException {  
                LoginInfo logininfo = new LoginInfo();  
                logininfo.setId(rs.getInt("id"));
                logininfo.setName(rs.getNString("name"));
                logininfo.setIp(rs.getNString("ip"));
                logininfo.setLogintime(rs.getTimestamp("logintime"));
                return logininfo;  
            }  
        });
	}

	public List<LoginInfo> getLoingInfo(String name, String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LoginInfo> getLoingInfo(String name, String ip,
			Timestamp logintime) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LoginInfo> getLoingInfoByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LoginInfo> getLoingInfoByIp(String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LoginInfo> getLoingInfoByLoginTime(Timestamp logintime) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertLoginInfo(User user) {
		StringBuilder sql = new StringBuilder("insert into logininfo(name,ip) values(?,?);");
		log.info("SQL语句："+sql.toString()+"--参数1："+user.getName()+"--参数2："+user.getIp());
		getJdbcTemplate().update(sql.toString(), user.getName(),user.getIp());
	}

}
