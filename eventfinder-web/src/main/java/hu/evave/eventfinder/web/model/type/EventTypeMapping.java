package hu.evave.eventfinder.web.model.type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hu.evave.eventfinder.web.model.Event;

@Entity
@Table(name = "event_type_mapping")
public class EventTypeMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	@Enumerated(EnumType.STRING)
	private EventType type;

	public EventTypeMapping() {
	}

	public EventTypeMapping(Event event, EventType type) {
		this.event = event;
		this.type = type;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

}
