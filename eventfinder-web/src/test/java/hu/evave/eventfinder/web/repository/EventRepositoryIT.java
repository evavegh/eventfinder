package hu.evave.eventfinder.web.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.type.EventType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryIT {

	@Autowired
	EventRepository eventRepository;

	private Event event = new Event();

	@Before
	@Transactional
	public void setUp() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		event.setStartsAt(calendar.getTime());
		event.setName("Példa név");
		eventRepository.save(event);
	}

	@Test
	public void testFindByKeywordFullMatch() {
		List<Event> events = eventRepository.findByTypeLocationKeyword(null, null, "Példa név");
		Assert.assertFalse(events.isEmpty());
	}

	@Test
	public void testFindByKeywordPartialMatch() {
		List<Event> events = eventRepository.findByTypeLocationKeyword(null, null, "név");
		Assert.assertFalse(events.isEmpty());
	}

	@Test
	public void testFindByLocation() {
		Location location = new Location(null, null, "Budapest", null, null, null, null);
		event.setLocation(location);
		List<Event> events = eventRepository.findByTypeLocationKeyword(null, location, null);
		Assert.assertFalse(events.isEmpty());
	}

	@Test
	public void testFindByLocationAndKeywordMatching() {
		Location location = new Location(null, null, "Budapest", null, null, null, null);
		event.setLocation(location);
		List<Event> events = eventRepository.findByTypeLocationKeyword(null, location, "Példa");
		Assert.assertFalse(events.isEmpty());
	}

	@Test
	public void testFindByLocationAndKeywordNotMatching() {
		Location location = new Location(null, null, "Budapest", null, null, null, null);
		event.setLocation(location);
		List<Event> events = eventRepository.findByTypeLocationKeyword(null, location, "xyz");
		Assert.assertTrue(events.isEmpty());
	}

	@Test
	public void testFindByLocationNotMatchingAndKeyword() {
		Location location = new Location(null, null, "Budapest", null, null, null, null);
		event.setLocation(location);
		eventRepository.save(event);
		List<Event> events = eventRepository.findByTypeLocationKeyword(null, new Location(), "név");
		Assert.assertFalse(events.isEmpty());
	}

	@Test
	public void testFindByOneTypeMatching() {
		List<EventType> types = new ArrayList<>();
		types.add(EventType.GASTRO);
		types.add(EventType.KIDS);

		List<EventType> eventTypes = new ArrayList<>();
		eventTypes.add(EventType.GASTRO);
		eventTypes.add(EventType.CINEMA);
		event.setTypes(eventTypes);
		eventRepository.save(event);

		List<Event> events = eventRepository.findByTypeLocationKeywordQueryDsl(types, null, null);
		Assert.assertFalse(events.isEmpty());
	}

	@Test
	public void testFindByTypeNotMatching() {
		List<EventType> types = new ArrayList<>();
		types.add(EventType.GASTRO);
		types.add(EventType.KIDS);

		List<EventType> eventTypes = new ArrayList<>();
		eventTypes.add(EventType.THEATRE);
		eventTypes.add(EventType.CINEMA);
		event.setTypes(eventTypes);
		eventRepository.save(event);

		List<Event> events = eventRepository.findByTypeLocationKeywordQueryDsl(types, null, null);
		Assert.assertTrue(events.isEmpty());
	}

}
