package hu.evave.eventfinder.web.rest.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.user.User;

public class UserResource {

	private long id;
	private String name;
	private String email;
	private String password;
	private List<String> roles;
	private List<EventResource> savedEvents;
	private List<UserResource> savedAdvertisers;
	private List<LocationResource> savedLocations;
	private int eventCount;
	private UserSettingsResource settings;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date memberSince;
	
	public static UserResource fromUser(User user) {
		UserResource result = new UserResource(user);
		
		for (Event event : user.getSavedEvents()) {
			result.savedEvents.add(new EventResource(event));
		}
		
		return result;
	}

	public UserResource() {
	}

	private UserResource(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles().stream().map(r -> r.name()).collect(Collectors.toList());
		this.memberSince = user.getMemberSince();
		this.settings = new UserSettingsResource(user.getSettings());

		this.savedEvents = new ArrayList<>();

		this.savedAdvertisers = new ArrayList<>();
		for (User advertiser : user.getSavedAdvertisers()) {
			this.savedAdvertisers.add(new UserResource(advertiser));
		}

		this.savedLocations = new ArrayList<>();
		for (Location location : user.getSavedLocations()) {
			this.savedLocations.add(new LocationResource(location));
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<EventResource> getSavedEvents() {
		return savedEvents;
	}

	public void setSavedEvents(List<EventResource> savedEvents) {
		this.savedEvents = savedEvents;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public int getEventCount() {
		return eventCount;
	}

	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}

	public List<UserResource> getSavedAdvertisers() {
		return savedAdvertisers;
	}

	public void setSavedAdvertisers(List<UserResource> savedAdvertisers) {
		this.savedAdvertisers = savedAdvertisers;
	}

	public List<LocationResource> getSavedLocations() {
		return savedLocations;
	}

	public void setSavedLocations(List<LocationResource> savedLocations) {
		this.savedLocations = savedLocations;
	}

	public UserSettingsResource getSettings() {
		return settings;
	}
	
	

}
