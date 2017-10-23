package hu.evave.eventfinder.web.rest.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.price.Price;
import hu.evave.eventfinder.web.model.type.EventType;

public class EventResource {

	private Long id;

	private String name;

	private List<EventType> types = new ArrayList<>();

	private LocationResource location;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startsAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endsAt;

	private List<PriceResource> prices = new ArrayList<>();

	private String summary;
	private String description;
	private String webUrl;
	private String fbUrl;

	public EventResource(Event event) {
		id = event.getId();
		name = event.getName();

		for (EventType type : event.getTypes()) {
			types.add(type);
		}
		
		for(Price price : event.getPrices()) {
			prices.add(new PriceResource(price));
		}

		location = new LocationResource(event.getLocation());
		startsAt = event.getStartsAt();
		endsAt = event.getEndsAt();

		// prices.add(new PriceResource(event.getNormalPrice()));
		// prices.add(new PriceResource(event.getStudentPrice()));
		// prices.add(new PriceResource(event.getRetiredPrice()));

		summary = event.getSummary();
		description = event.getDescription();
		webUrl = event.getWebUrl();
		fbUrl = event.getFbUrl();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<EventType> getTypes() {
		return types;
	}

	public LocationResource getLocation() {
		return location;
	}

	public Date getStartsAt() {
		return startsAt;
	}

	public Date getEndsAt() {
		return endsAt;
	}

	public List<PriceResource> getPrices() {
		return prices;
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public String getFbUrl() {
		return fbUrl;
	}
	
	public static List<EventResource> eventListToEventResourceList(List<Event> events) {

		List<EventResource> result = new ArrayList<>();

		for (Event event : events) {
			result.add(new EventResource(event));
		}

		return result;
	}

}
