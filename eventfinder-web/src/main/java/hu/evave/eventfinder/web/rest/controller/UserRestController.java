package hu.evave.eventfinder.web.rest.controller;

import java.util.ArrayList;
import java.util.List;

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
import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.RestSession;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.repository.LocationRepository;
import hu.evave.eventfinder.web.repository.RestSessionRepository;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.rest.resource.UserResource;
import hu.evave.eventfinder.web.service.user.UserService;

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserRestController {

	private UserService userService;
	private EventRepository eventRepository;
	private LocationRepository locationRepository;
	private RestSessionRepository restSessionRepository;

	@Autowired
	public UserRestController(UserService userService, EventRepository eventRepository, LocationRepository locationRepository,
			RestSessionRepository restSessionRepository) {
		this.userService = userService;
		this.eventRepository = eventRepository;
		this.locationRepository = locationRepository;
		this.restSessionRepository = restSessionRepository;
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 401
	void onAuthenticationError(AuthorizationException e) {
	}

	@PostMapping("/subscribe_event")
	public void subscribeEvent(@RequestBody long id, @RequestParam("token") String token) throws AuthorizationException {
		User user = getCurrentUser(token);

		Event event = eventRepository.getOne(id);
		user.subscribeEvent(event);
		userService.save(user);
	}

	private User getCurrentUser(String token) throws AuthorizationException {
		if (token != null) {
			RestSession session = restSessionRepository.findOne(token);
			if (session == null || !session.isValid()) {
				throw new AuthorizationException();
			}

			session.extendValidity();
			restSessionRepository.save(session);
			return session.getUser();
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			throw new AuthorizationException();
		}

		return userService.getUserByName(authentication.getName());
	}

	@PostMapping("/unsubscribe_event")
	public void unsubscribeEvent(@RequestBody long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());
			Event event = eventRepository.getOne(id);
			user.unsubscribeEvent(event);
			userService.save(user);
		}
	}

	@PostMapping("/subscribe_advertiser")
	public void subscribeAdvertiser(@RequestBody long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());
			User advertiser = userService.getUserById(id);
			user.subscribeAdvertiser(advertiser);
			userService.save(user);
		}
	}

	@PostMapping("/unsubscribe_advertiser")
	public void unsubscribeAdvertiser(@RequestBody long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());
			User advertiser = userService.getUserById(id);
			user.unsubscribeAdvertiser(advertiser);
			userService.save(user);
		}
	}

	@PostMapping("/subscribe_location")
	public void subscribeLocation(@RequestBody long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());
			Location location = locationRepository.findOne(id);
			user.subscribeLocation(location);
			userService.save(user);
		}
	}

	@PostMapping("/unsubscribe_location")
	public void unsubscribeLocation(@RequestBody long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());
			Location location = locationRepository.findOne(id);
			user.unsubscribeLocation(location);
			userService.save(user);
		}
	}

	@GetMapping(value = "/subscribedEvents", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResource> findSubscribedEvents() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());

			return EventResource.eventListToEventResourceList(user.getSavedEvents());
		}
		return new ArrayList<>();
	}

	@GetMapping(value = "/userById/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public UserResource findUserById(@PathVariable("userId") long id) {
		UserResource user = UserResource.fromUser(userService.getUserById(id));
		user.setEventCount(eventRepository.countByCreatedBy(userService.getUserById(id)));
		return user;
	}

}
