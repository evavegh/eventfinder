package hu.evave.eventfinder.web.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_settings")
public class UserSettings {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email_notification_enabled")
	private boolean emailNotificationEnabled = true;

	@Column(name = "android_notification_enabled")
	private boolean androidNotificationEnabled = true;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
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

}
