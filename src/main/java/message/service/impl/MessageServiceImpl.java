package message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import message.dao.MessageDao;
import message.service.MessageService;

public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messgeDao;
	
	public void saveMessage(String user, String room, String content) {
		messgeDao.save(user, room, content);
	}

}
