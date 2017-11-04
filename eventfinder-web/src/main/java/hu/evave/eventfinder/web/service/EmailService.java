package hu.evave.eventfinder.web.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	JavaMailSender mailSender;

	public void prepareAndSend(String recipient, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(recipient);
			helper.setText(text);
			helper.setSubject("Eventfinder notification");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		mailSender.send(message);
	}

}
