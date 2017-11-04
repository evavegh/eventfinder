package hu.evave.eventfinder.web.model.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Location;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String email;
	private String password;

	@ElementCollection
	@Cascade(value = { CascadeType.ALL })
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Set<Role> roles;

	@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "member_since")
	private Date memberSince;

	@ManyToMany
	@Cascade(value = { CascadeType.ALL })
	@JoinTable(name = "user_saved_event", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
	private List<Event> savedEvents = new ArrayList<>();

	@ManyToMany
	@Cascade(value = { CascadeType.ALL })
	@JoinTable(name = "user_saved_location", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<Location> savedLocations = new ArrayList<>();

	@ManyToMany
	@Cascade(value = { CascadeType.ALL })
	@JoinTable(name = "user_saved_advertiser", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "advertiser_id"))
	private List<User> savedAdvertisers = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "settings_id")
	@Cascade(value = { CascadeType.ALL })
	private UserSettings settings = new UserSettings();

	@ManyToMany(mappedBy = "savedAdvertisers", fetch = FetchType.LAZY)
	private List<User> subscribedUsers = new ArrayList<>();

	public User() {
		if (memberSince == null) {
			memberSince = Calendar.getInstance().getTime();
		}
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

		if (memberSince == null) {
			memberSince = Calendar.getInstance().getTime();
		}
	}

	public void addRole(Role role) {
		if (roles == null) {
			roles = new HashSet<>();
		}
		roles.add(role);
	}

	public void subscribeEvent(Event event) {
		if (!savedEvents.contains(event)) {
			savedEvents.add(event);
		}
	}

	public void unsubscribeEvent(Event event) {
		savedEvents.remove(event);
	}

	public void subscribeLocation(Location location) {
		if (!savedLocations.contains(location)) {
			savedLocations.add(location);
		}
	}

	public void unsubscribeLocation(Location location) {
		savedLocations.remove(location);
	}

	public void subscribeAdvertiser(User advertiser) {
		if (!savedAdvertisers.contains(advertiser)) {
			savedAdvertisers.add(advertiser);
		}
	}

	public void unsubscribeAdvertiser(User advertiser) {
		savedAdvertisers.remove(advertiser);
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

	public Long getId() {
		return id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Event> getSavedEvents() {
		return savedEvents;
	}

	public UserSettings getSettings() {
		return settings;
	}

	public void setSettings(UserSettings settings) {
		this.settings = settings;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public List<Location> getSavedLocations() {
		return savedLocations;
	}

	public List<User> getSavedAdvertisers() {
		return savedAdvertisers;
	}

	public List<User> getSubscribedUsers() {
		return subscribedUsers;
	}

}
