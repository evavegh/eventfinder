package hu.evave.eventfinder.web.web.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import hu.evave.eventfinder.web.repository.EventRepository;
import hu.evave.eventfinder.web.rest.resource.EventResource;

@RestController
public class EventController {
	
	@Autowired
	EventRepository eventRepository;

//	public List<EventResource> allevents() {
//
//	}

}
