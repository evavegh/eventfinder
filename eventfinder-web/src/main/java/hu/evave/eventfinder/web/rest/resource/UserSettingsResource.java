package hu.evave.eventfinder.web.rest.resource;

import hu.evave.eventfinder.web.model.user.UserSettings;

public class UserSettingsResource {

	private Long id;

	private boolean emailNotificationEnabled = true;

	private boolean androidNotificationEnabled = true;

	private boolean eventNotificationEnabled = true;

	private boolean advertiserNotificationEnabled = true;

	private boolean locationNotificationEnabled = true;

	public UserSettingsResource(UserSettings settings) {
		this.id = settings.getId();
		this.emailNotificationEnabled = settings.isEmailNotificationEnabled();
		this.androidNotificationEnabled = settings.isAndroidNotificationEnabled();
		this.eventNotificationEnabled = settings.isEventNotificationEnabled();
		this.advertiserNotificationEnabled = settings.isAdvertiserNotificationEnabled();
		this.locationNotificationEnabled = settings.isLocationNotificationEnabled();
	}

	public boolean isEmailNotificationEnabled() {
		return emailNotificationEnabled;
	}

	public boolean isAndroidNotificationEnabled() {
		return androidNotificationEnabled;
	}

	public boolean isEventNotificationEnabled() {
		return eventNotificationEnabled;
	}

	public boolean isAdvertiserNotificationEnabled() {
		return advertiserNotificationEnabled;
	}

	public boolean isLocationNotificationEnabled() {
		return locationNotificationEnabled;
	}

	public Long getId() {
		return id;
	}

}
