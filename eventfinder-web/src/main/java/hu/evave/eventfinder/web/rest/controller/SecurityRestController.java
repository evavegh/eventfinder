package hu.evave.eventfinder.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.mapper.EventFinderMapper;
import hu.evave.eventfinder.web.rest.resource.UserResource;
import hu.evave.eventfinder.web.service.user.UserService;

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class SecurityRestController {

	private UserService userService;
	private EventFinderMapper mapper;

	@Autowired
	public SecurityRestController(UserService userService, EventFinderMapper mapper) {
		this.userService = userService;
		this.mapper = mapper;
	}

	@GetMapping("currentUser")
	public UserResource getCurrentUser() {
		return mapper.toResource(userService.getCurrentUser());
	}

	@GetMapping(value = "/registration")
	public UserResource registration() {
		return mapper.toResource(userService.createNewAdminUser());
	}

}
