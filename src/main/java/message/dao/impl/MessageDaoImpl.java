package message.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import message.dao.MessageDao;

public class MessageDaoImpl extends JdbcDaoSupport implements MessageDao {

	public void save(String user, String room, String content) {
		StringBuilder sql = new StringBuilder("insert into message(user,room,content) values(?,?,?);");
	    getJdbcTemplate().update(sql.toString(), new Object[] {user, room, content});
	}

}
