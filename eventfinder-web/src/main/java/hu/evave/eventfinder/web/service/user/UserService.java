package hu.evave.eventfinder.web.service.user;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	JavaMailSender mailSender;

	public UserService() {
	}

	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User login(String email, String password) {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			return null;
		}
		if (!user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}

	public void changePassword(User user) {
		if (user == null) {
			return;
		}
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User getUserByName(String name) {
		return userRepository.findByName(name);
	}

	public void sendEmail(User user) {
		prepareAndSend(user.getEmail(), "Dear User!\n\nYour registration was successful.\nYour new account name: " + user.getName() + "\n\nThank you for choosing us!\nEventFinder");
	}

	private void prepareAndSend(String recipient, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(recipient);
			helper.setText(text);
			helper.setSubject("Registration successful");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		mailSender.send(message);
	}

}
