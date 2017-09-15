package hu.evave.eventfinder.web.repository;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.type.EventType;

public interface EventRepositoryCustom extends QueryDslPredicateExecutor<Event> {

	List<Event> findByTypeLocationKeyword(List<EventType> types, Location location, String phrase);
	
	List<Event> findByTypeLocationKeywordQueryDsl(List<EventType> types, Location location, String phrase);

}
