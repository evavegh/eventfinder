package hu.evave.eventfinder.web.service.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import hu.evave.eventfinder.web.exception.AuthorizationException;
import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.user.Role;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.model.user.UserSettings;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.repository.LocationRepository;
import hu.evave.eventfinder.web.repository.UserRepository;
import hu.evave.eventfinder.web.service.EmailService;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder encoder;
	private EmailService emailService;
	private UserValidator userValidator;
	public EventRepository eventRepository;
	public LocationRepository locationRepository;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder encoder, EmailService emailService, UserValidator userValidator,
			EventRepository eventRepository, LocationRepository locationRepository) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.emailService = emailService;
		this.userValidator = userValidator;
		this.eventRepository = eventRepository;
		this.locationRepository = locationRepository;
	}

	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User getUserByName(String name) {
		return userRepository.findByName(name);
	}

	public void sendRegistrationEmail(User user) {
		emailService.prepareAndSend(user.getEmail(),
				"Dear User!\n\nYour registration was successful.\nYour new account name: " + user.getName() + "\n\nThank you for choosing us!\nEventFinder");
	}

	public User getUserById(long id) {
		return userRepository.findOne(id);
	}

	public User createNewEmptyUser() {
		User user = new User();
		user.setId(-1L);
		user.setPassword("password");
		user.setMemberSince(new Date());
		user.addRole(Role.ADMIN);

		save(user);
		return user;
	}

	public void createNewUser(User user, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		user.addRole(Role.REGISTERED);
		user.setSettings(new UserSettings());
		user.setMemberSince(new Date());

		save(user);
		sendRegistrationEmail(user);
	}

	@GetMapping("currentUser")
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String name = authentication.getName();
			return getUserByName(name);
		}
		return null;
	}

	public User createNewAdminUser() {
		User user = new User();
		user.setPassword(null);
		user.addRole(Role.ADMIN);
		return user;
	}

	private User getCurrentAuthenticatedUser() throws AuthorizationException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new AuthorizationException();
		}

		return getUserByName(authentication.getName());
	}

	public void setEventSubscription(long id, boolean subscribe) throws AuthorizationException {
		User user = getCurrentAuthenticatedUser();

		Event event = eventRepository.getOne(id);
		if (subscribe) {
			user.subscribeEvent(event);
		} else {
			user.unsubscribeEvent(event);
		}
		save(user);
	}

	public void setAdvertiserSubscription(long id, boolean subscribe) throws AuthorizationException {
		User user = getCurrentAuthenticatedUser();

		User advertiser = getUserById(id);
		if (subscribe) {
			user.subscribeAdvertiser(advertiser);
		} else {
			user.unsubscribeAdvertiser(advertiser);
		}
		save(user);
	}

	public void setLocationSubscription(long id, boolean subscribe) throws AuthorizationException {
		User user = getCurrentAuthenticatedUser();

		Location location = locationRepository.findOne(id);
		if (subscribe) {
			user.subscribeLocation(location);
		} else {
			user.unsubscribeLocation(location);
		}
		save(user);
	}

}
