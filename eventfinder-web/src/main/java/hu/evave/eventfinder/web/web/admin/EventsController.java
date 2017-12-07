package hu.evave.eventfinder.web.web.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hu.evave.eventfinder.web.mapper.EventFinderMapper;
import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.price.Currency;
import hu.evave.eventfinder.web.model.type.EventType;
import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.resource.EventResource;
import hu.evave.eventfinder.web.service.EventService;

@Controller
public class EventsController {

	private EventRepository eventRepository;
	private EventService eventService;
	private EventFinderMapper eventMapper;

	@Autowired
	public EventsController(EventRepository eventRepository, EventService eventService, EventFinderMapper eventMapper) {
		this.eventRepository = eventRepository;
		this.eventService = eventService;
		this.eventMapper = eventMapper;
	}

	@RequestMapping("/events")
	public String listEvents(Map<String, Object> model) {
		model.put("events", eventRepository.findAll());
		return "events";

	}

	@RequestMapping(value = "/events/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<EventResource> listEventsJson() {
		return eventRepository.findAll().stream().map(e -> eventMapper.toResource(e)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/myevents/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<EventResource> listMyEventsJson() {
		List<Event> events = eventService.getMyEvents();
		return events.stream().map(e -> eventMapper.toResource(e)).collect(Collectors.toList());
	}

	@RequestMapping("/myevents")
	public String listMyEvents(Map<String, Object> model) {
		List<Event> events = eventService.getMyEvents();
		model.put("events", events);
		return "myevents";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody List<Event> delete(@RequestBody Event eventToDelete) {
		eventService.deleteEvent(eventToDelete);
		return eventRepository.findAll();

	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createNewEvent(Map<String, Object> model) {
		Event event = eventService.createEmptyEvent();
		model.put("event", event);
		model.put("allTypes", EventType.values());
		model.put("currencies", Currency.values());
		return "edit";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createNewEvent(@ModelAttribute("event") Event event) {
		eventService.createNewEvent(event);
		return "redirect:/myevents";

	}

	@RequestMapping(value = "/edit/{eventId}", method = RequestMethod.GET)
	public String edit(@PathVariable("eventId") long id, Map<String, Object> model) {
		model.put("event", eventRepository.findOne(id));
		model.put("allTypes", EventType.values());
		model.put("currencies", Currency.values());
		return "edit";

	}

	@RequestMapping(value = "/edit/{eventId}", method = RequestMethod.POST)
	public String edit(@PathVariable("eventId") long id, @ModelAttribute("event") Event event) {
		eventService.editEvent(event);
		return "redirect:/myevents";
	}

}
