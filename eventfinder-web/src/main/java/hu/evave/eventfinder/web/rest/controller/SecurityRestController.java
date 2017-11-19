package hu.evave.eventfinder.web.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.model.user.Role;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.rest.resource.UserResource;
import hu.evave.eventfinder.web.service.user.UserService;

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class SecurityRestController {

	private UserService userService;

	@Autowired
	public SecurityRestController(UserService userService) {
		this.userService = userService;
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

}
