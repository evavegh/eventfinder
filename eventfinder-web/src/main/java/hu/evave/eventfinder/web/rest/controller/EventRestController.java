package hu.evave.eventfinder.web.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.request.EventRestRequest;
import hu.evave.eventfinder.web.rest.resource.EventResource;

@RestController
@RequestMapping("/rest")
public class EventRestController {

	@Autowired
	EventRepository eventRepository;

	@RequestMapping(
			value = "/events/search",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResource> findByTypeLocationKeyword(EventRestRequest request) {

//		Location location = new Location();
//		location.setCountry(request.getCountry());
//
//		List<Event> events = eventRepository.findByTypeLocationKeywordQueryDsl(request.getTypes(), location,
//				request.getKeyword());
		
		List<Event> events = eventRepository.findAll();

		return eventListToEventResourceList(events);
	}

	public List<EventResource> eventListToEventResourceList(List<Event> events) {

		List<EventResource> result = new ArrayList<>();

		for (Event event : events) {
			result.add(new EventResource(event));
		}

		return result;
	}

}
