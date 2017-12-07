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

	public UserResource() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<EventResource> getSavedEvents() {
		return savedEvents;
	}

	public void setSavedEvents(List<EventResource> savedEvents) {
		this.savedEvents = savedEvents;
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

	public int getEventCount() {
		return eventCount;
	}

	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}

	public UserSettingsResource getSettings() {
		return settings;
	}

	public void setSettings(UserSettingsResource settings) {
		this.settings = settings;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	

}
