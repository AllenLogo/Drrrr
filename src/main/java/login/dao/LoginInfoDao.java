package login.dao;

import java.sql.Timestamp;
import java.util.List;
import login.entity.LoginInfo;
import user.User;

public abstract interface LoginInfoDao
{
  public abstract List<LoginInfo> getAllLoingInfo();

  public abstract List<LoginInfo> getLoingInfo(String paramString1, String paramString2);

  public abstract List<LoginInfo> getLoingInfo(String paramString1, String paramString2, Timestamp paramTimestamp);

  public abstract List<LoginInfo> getLoingInfoByName(String paramString);

  public abstract List<LoginInfo> getLoingInfoByIp(String paramString);

  public abstract List<LoginInfo> getLoingInfoByLoginTime(Timestamp paramTimestamp);

  public abstract void insertLoginInfo(User paramUser);

  public abstract boolean selectAdmin(String paramString1, String paramString2);
}