package com.mynewuber.project.uber.UberApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mynewuber.project.uber.UberApp.services.EmailSenderService;

@SpringBootTest
class UberAppApplicationTests {
	
	@Autowired
	private EmailSenderService emailSenderService;
	

	@Test
	void contextLoads() {
		emailSenderService.sendEmail("gauravke2002@gmail.com", "this is testing email", "Body of the mail");
	}

}
