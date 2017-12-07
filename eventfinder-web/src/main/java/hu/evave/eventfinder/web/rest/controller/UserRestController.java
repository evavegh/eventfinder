package hu.evave.eventfinder.web.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.exception.AuthorizationException;
import hu.evave.eventfinder.web.mapper.EventFinderMapper;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.rest.resource.UserResource;
import hu.evave.eventfinder.web.service.user.UserService;

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserRestController {

	private UserService userService;
	private EventRepository eventRepository;
	private EventFinderMapper mapper;

	@Autowired
	public UserRestController(UserService userService, EventRepository eventRepository, EventFinderMapper mapper) {
		this.userService = userService;
		this.eventRepository = eventRepository;
		this.mapper = mapper;
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 401
	void onAuthenticationError(AuthorizationException e) {
	}

	@PostMapping("/subscribe_event")
	public void subscribeEvent(@RequestBody long id, @RequestParam("token") String token) throws AuthorizationException {
		userService.setEventSubscription(id, true);
	}

	@PostMapping("/unsubscribe_event")
	public void unsubscribeEvent(@RequestBody long id) throws AuthorizationException {
		userService.setEventSubscription(id, false);
	}

	@PostMapping("/subscribe_advertiser")
	public void subscribeAdvertiser(@RequestBody long id) throws AuthorizationException {
		userService.setAdvertiserSubscription(id, true);
	}

	@PostMapping("/unsubscribe_advertiser")
	public void unsubscribeAdvertiser(@RequestBody long id) throws AuthorizationException {
		userService.setAdvertiserSubscription(id, false);
	}

	@PostMapping("/subscribe_location")
	public void subscribeLocation(@RequestBody long id) throws AuthorizationException {
		userService.setLocationSubscription(id, true);
	}

	@PostMapping("/unsubscribe_location")
	public void unsubscribeLocation(@RequestBody long id) throws AuthorizationException {
		userService.setLocationSubscription(id, false);
	}

	@GetMapping(value = "/subscribedEvents", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResource> findSubscribedEvents() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());

			return user.getSavedEvents().stream().map(e -> mapper.toResource(e)).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@GetMapping(value = "/userById/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public UserResource findUserById(@PathVariable("userId") long id) {
		UserResource user = mapper.toResource(userService.getUserById(id));
		user.setEventCount(eventRepository.countByCreatedBy(userService.getUserById(id)));
		return user;
	}

}
