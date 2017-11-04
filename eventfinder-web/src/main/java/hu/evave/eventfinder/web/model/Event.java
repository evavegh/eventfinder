package hu.evave.eventfinder.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import hu.evave.eventfinder.web.model.price.Price;
import hu.evave.eventfinder.web.model.price.PriceType;
import hu.evave.eventfinder.web.model.type.EventType;
import hu.evave.eventfinder.web.model.user.User;

@Entity
@Table(name = "event")
@Where(clause = "starts_at is not null")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@ElementCollection
	@CollectionTable(name = "event_type", joinColumns = @JoinColumn(name = "event_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private List<EventType> types;

	@ManyToOne(cascade = { CascadeType.ALL })
	private Location location;

	@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "starts_at")
	private Date startsAt;

	@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ends_at")
	private Date endsAt;

	@OneToMany
	@JoinColumn(name = "event_id")
	private List<Price> prices;
	
	@OneToOne(cascade = { CascadeType.ALL })
	private Price normalPrice;	
	
	@OneToOne(cascade = { CascadeType.ALL })
	private Price studentPrice;
	
	@OneToOne(cascade = { CascadeType.ALL })
	private Price retiredPrice;

	@Lob
	private String summary;

	@Lob
	private String description;
	private String webUrl;
	private String fbUrl;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@ManyToMany(mappedBy = "savedEvents", fetch = FetchType.LAZY)
	private List<User> subscribedUsers = new ArrayList<>();

	public Event() {
	}

	public Event(String name, List<EventType> types, Location location, Date startsAt, Date endsAt, List<Price> prices,
			String summary, String description, String webUrl, String fbUrl, User createdBy) {
		this.name = name;
		this.types = types;
		this.location = location;
		this.startsAt = startsAt;
		this.endsAt = endsAt;
		this.prices = prices;
		this.summary = summary;
		this.description = description;
		this.webUrl = webUrl;
		this.fbUrl = fbUrl;
		this.createdBy = createdBy;
		
		normalPrice = new Price();
		studentPrice = new Price();
		retiredPrice = new Price();
		
		normalPrice.setType(PriceType.NORMAL);
		studentPrice.setType(PriceType.STUDENT);
		retiredPrice.setType(PriceType.RETIRED);
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
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

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Price getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(Price normalPrice) {
		this.normalPrice = normalPrice;
	}

	public Price getStudentPrice() {
		return studentPrice;
	}

	public void setStudentPrice(Price studentPrice) {
		this.studentPrice = studentPrice;
	}

	public Price getRetiredPrice() {
		return retiredPrice;
	}

	public void setRetiredPrice(Price retiredPrice) {
		this.retiredPrice = retiredPrice;
	}
	
	public List<User> getSubscribedUsers() {
		return Collections.unmodifiableList(subscribedUsers);
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", types=" + types + ", location=" + location + ", startsAt="
				+ startsAt + ", endsAt=" + endsAt + ", prices=" + prices + ", summary=" + summary + ", description="
				+ description + ", webUrl=" + webUrl + ", fbUrl=" + fbUrl + "]";
	}

}
