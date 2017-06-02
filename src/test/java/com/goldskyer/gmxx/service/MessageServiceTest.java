package com.goldskyer.gmxx.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.goldskyer.gmxx.common.entities.MessageText;
import com.goldskyer.gmxx.common.service.MessageService;

public class MessageServiceTest extends BaseTest
{
	@Autowired
	private MessageService messageService;

	@Test
	@Rollback(false)
	public void testSend()
	{
		MessageText text = new MessageText();
		text.setText("很好");
		text.setTitle("我是标题");
		messageService.sendMessage(text, "111", "222");
	}

	@Test
	@Rollback(false)
	public void read()
	{
		messageService.readMessage("40289f7856517dc40156517dd8f90000", "222");
	}
}
