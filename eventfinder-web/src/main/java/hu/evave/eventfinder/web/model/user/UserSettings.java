package hu.evave.eventfinder.web.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_settings")
public class UserSettings {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "email_notification_enabled")
	private boolean emailNotificationEnabled = true;

	@Column(name = "android_notification_enabled")
	private boolean androidNotificationEnabled = true;

	@Column(name = "event_notification_enabled")
	private boolean eventNotificationEnabled = true;

	@Column(name = "advertiser_notification_enabled")
	private boolean advertiserNotificationEnabled = true;

	@Column(name = "location_notification_enabled")
	private boolean locationNotificationEnabled = true;

	public UserSettings() {
	}

	public boolean isAndroidNotificationEnabled() {
		return androidNotificationEnabled;
	}

	public void setAndroidNotificationEnabled(boolean androidNotificationEnabled) {
		this.androidNotificationEnabled = androidNotificationEnabled;
	}

	public boolean isEmailNotificationEnabled() {
		return emailNotificationEnabled;
	}

	public void setEmailNotificationEnabled(boolean emailNotificationEnabled) {
		this.emailNotificationEnabled = emailNotificationEnabled;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
