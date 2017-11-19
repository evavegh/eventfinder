package hu.evave.eventfinder.web.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.type.EventType;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.request.EventRestRequest;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.rest.resource.LocationResource;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = {"http://localhost:4200"})
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

		return EventResource.eventListToEventResourceList(events);
	}
	
	@RequestMapping(
			value = "/types",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String[] findTypes() {	
		String[] types = new String[EventType.values().length];
		for(int i = 0; i < EventType.values().length; i++) {
			types[i] = EventType.values()[i].name();
		}
		return types;
	}
	
	@RequestMapping(value = "details", method = RequestMethod.GET)
	public ResponseEntity<EventResource> getEventById(@RequestParam("id") String id) {
	   EventResource event = new EventResource(eventRepository.findOne(Long.parseLong(id)));
	   return new ResponseEntity<EventResource>(event, HttpStatus.OK);
	} 
	
	@RequestMapping(value = "details/{eventId}", method = RequestMethod.GET)
	public EventResource details(@PathVariable("eventId") String id, Map<String, Object> model) {	
		
		EventResource event = new EventResource(eventRepository.findOne(Long.parseLong(id)));
		model.put("event", event);
		return event;

	}
	
	@GetMapping(value = "/locationByEventId/{eventId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LocationResource findLocationByEventId(@PathVariable("eventId") long id) {
		return new LocationResource(eventRepository.findOne(id).getLocation());
	}

}
