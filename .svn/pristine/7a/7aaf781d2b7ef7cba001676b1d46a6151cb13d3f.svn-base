package login.dao;

import java.sql.Timestamp;
import java.util.List;

import login.entity.LoginInfo;


public interface LoginInfoDao {
	
	public List<LoginInfo> getAllLoingInfo();
	
	public List<LoginInfo> getLoingInfo(String name,String ip);
	public List<LoginInfo> getLoingInfo(String name,String ip,Timestamp logintime);
	
	public List<LoginInfo> getLoingInfoByName(String name);
	public List<LoginInfo> getLoingInfoByIp(String ip);
	public List<LoginInfo> getLoingInfoByLoginTime(Timestamp logintime);
	
	public boolean insertLoginInfo(String name,String ip);
}
