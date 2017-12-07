package hu.evave.eventfinder.web.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.evave.eventfinder.web.mapper.EventFinderMapper;
import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.type.EventType;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.request.EventRestRequest;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.rest.resource.LocationResource;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = { "http://localhost:4200" })
public class EventRestController {

	private EventRepository eventRepository;
	private EventFinderMapper mapper;

	@Autowired
	public EventRestController(EventRepository eventRepository, EventFinderMapper mapper) {
		this.eventRepository = eventRepository;
		this.mapper = mapper;
	}

	@GetMapping(value = "/events/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<EventResource> findByTypeLocationKeyword(EventRestRequest request) {
		List<Event> events = eventRepository.findAll();
		return events.stream()
				.map(e -> mapper.toResource(e))
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String[] findTypes() {
		String[] types = new String[EventType.values().length];
		for (int i = 0; i < EventType.values().length; i++) {
			types[i] = EventType.values()[i].name();
		}
		return types;
	}

	@GetMapping("details")
	public EventResource getEventById(@RequestParam("id") String id) {
		return mapper.toResource(eventRepository.findOne(Long.parseLong(id)));
	}

	@GetMapping("details/{eventId}")
	public EventResource details(@PathVariable("eventId") String id) {
		return mapper.toResource(eventRepository.findOne(Long.parseLong(id)));

	}

	@GetMapping(value = "/locationByEventId/{eventId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LocationResource findLocationByEventId(@PathVariable("eventId") long id) {
		return mapper.toResource(eventRepository.findOne(id).getLocation());
	}

}
