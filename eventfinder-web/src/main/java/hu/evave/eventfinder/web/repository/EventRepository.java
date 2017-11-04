package hu.evave.eventfinder.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.type.EventType;
import hu.evave.eventfinder.web.model.user.User;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

	List<Event> findByTypes(List<EventType> type);

	List<Event> findByStartsAtAfter(Date date);

	List<Event> findByEndsAtBefore(Date date);

	List<Event> findByStartsAtBetween(Date starts, Date ends);

	List<Event> findByLocationCountry(String country);

	List<Event> findByLocationCity(String city);

	List<Event> findByNameContaining(String phrase);

	List<Event> findByCreatedBy(User user);
	
	int countByCreatedBy(User id);
	
}
