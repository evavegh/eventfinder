package hu.evave.eventfinder.web.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.user.User;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.service.user.UserService;

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserRestController {

	private UserService userService;
	private EventRepository eventRepository;

	@Autowired
	public UserRestController(UserService userService, EventRepository eventRepository) {
		this.userService = userService;
		this.eventRepository = eventRepository;
	}

	@PostMapping("/subscribe_event")
	public void subscribeEvent(@RequestBody long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());
			System.out.println(user.getPassword());
			Event event = eventRepository.getOne(id);
			user.subscribeEvent(event);
			userService.save(user);
			System.out.println(user.getPassword());
		}
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

	@GetMapping(value = "/subscribedEvents", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResource> findSubscribedEvents() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.getUserByName(authentication.getName());

			return EventResource.eventListToEventResourceList(user.getSavedEvents());
		}
		return new ArrayList<>();
	}

}
