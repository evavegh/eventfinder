package hu.evave.eventfinder.web.rest.request;

import java.util.ArrayList;
import java.util.List;

import hu.evave.eventfinder.web.model.type.EventType;

public class EventRestRequest {

	private String types;
	private String keyword;
	private String country;

	public void setTypes(String types) {
		this.types = types;
	}

	public List<EventType> getTypes() {
		List<EventType> result = new ArrayList<>();
		if (types != null && !types.isEmpty()) {
			String[] eventTypes = types.split("-");
			for (String type : eventTypes) {
				result.add(EventType.valueOf(type.toUpperCase()));
			}
		}

		return result;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
