package hu.evave.eventfinder.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	public List<Event> getEventsByKeyword(String keyword) {
		if (keyword != null && !keyword.isEmpty()) {
			return eventRepository.findByNameContaining(keyword);
		}

		return null;
	}

}
