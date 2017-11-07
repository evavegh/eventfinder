package hu.evave.eventfinder.web.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.model.RestSession;
import hu.evave.eventfinder.web.model.user.Role;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.repository.RestSessionRepository;
import hu.evave.eventfinder.web.rest.resource.UserResource;
import hu.evave.eventfinder.web.service.user.UserService;

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class SecurityRestController {

	private UserService userService;
	private RestSessionRepository restSessionRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityRestController(UserService userService, RestSessionRepository restSessionRepository, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.restSessionRepository = restSessionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("currentUser")
	public UserResource getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String name = authentication.getName();
			return UserResource.fromUser(userService.getUserByName(name));
		}
		return null;
	}

	@GetMapping(value = "/registration")
	public UserResource registration(Map<String, Object> model) {
		User user = new User();
		user.setPassword(null);
		user.addRole(Role.ADMIN);
		model.put("userForm", user);
		return UserResource.fromUser(user);
	}

	@PostMapping("login")
	public String login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		User user = userService.getUserByName(userName);
		if (user == null) {
			return null;
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			return null;
		}

		RestSession session = new RestSession();
		session.setUser(user);
		restSessionRepository.save(session);

		return session.getId();
	}

	@PostMapping("logout")
	public void logout(@RequestParam("token") String token) {
		RestSession session = restSessionRepository.findOne(token);
		if (session != null) {
			session.invalidate();
			restSessionRepository.save(session);
		}
	}

}
