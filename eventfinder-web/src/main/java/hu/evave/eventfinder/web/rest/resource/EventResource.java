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

	private UserResource createdBy;

	public EventResource() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EventType> getTypes() {
		return types;
	}

	public void setTypes(List<EventType> types) {
		this.types = types;
	}

	public LocationResource getLocation() {
		return location;
	}

	public void setLocation(LocationResource location) {
		this.location = location;
	}

	public Date getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(Date startsAt) {
		this.startsAt = startsAt;
	}

	public Date getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(Date endsAt) {
		this.endsAt = endsAt;
	}

	public List<PriceResource> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceResource> prices) {
		this.prices = prices;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getFbUrl() {
		return fbUrl;
	}

	public void setFbUrl(String fbUrl) {
		this.fbUrl = fbUrl;
	}

	public UserResource getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserResource createdBy) {
		this.createdBy = createdBy;
	}

}
