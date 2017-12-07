package hu.evave.eventfinder.web.rest.resource;

import hu.evave.eventfinder.web.model.user.UserSettings;

public class UserSettingsResource {

	private Long id;

	private boolean emailNotificationEnabled = true;

	private boolean androidNotificationEnabled = true;

	private boolean eventNotificationEnabled = true;

	private boolean advertiserNotificationEnabled = true;

	private boolean locationNotificationEnabled = true;

	public UserSettingsResource() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEmailNotificationEnabled() {
		return emailNotificationEnabled;
	}

	public void setEmailNotificationEnabled(boolean emailNotificationEnabled) {
		this.emailNotificationEnabled = emailNotificationEnabled;
	}

	public boolean isAndroidNotificationEnabled() {
		return androidNotificationEnabled;
	}

	public void setAndroidNotificationEnabled(boolean androidNotificationEnabled) {
		this.androidNotificationEnabled = androidNotificationEnabled;
	}

	public boolean isEventNotificationEnabled() {
		return eventNotificationEnabled;
	}

	public void setEventNotificationEnabled(boolean eventNotificationEnabled) {
		this.eventNotificationEnabled = eventNotificationEnabled;
	}

	public boolean isAdvertiserNotificationEnabled() {
		return advertiserNotificationEnabled;
	}

	public void setAdvertiserNotificationEnabled(boolean advertiserNotificationEnabled) {
		this.advertiserNotificationEnabled = advertiserNotificationEnabled;
	}

	public boolean isLocationNotificationEnabled() {
		return locationNotificationEnabled;
	}

	public void setLocationNotificationEnabled(boolean locationNotificationEnabled) {
		this.locationNotificationEnabled = locationNotificationEnabled;
	}

	

}
