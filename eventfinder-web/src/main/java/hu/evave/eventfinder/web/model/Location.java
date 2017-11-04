package hu.evave.eventfinder.web.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import hu.evave.eventfinder.web.model.user.User;

@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String country;
	private String city;

	@Column(name = "zip_code")
	private String zipCode;

	private String address;

	@Column(precision = 11, scale = 8)
	private BigDecimal lat;

	@Column(precision = 11, scale = 8)
	private BigDecimal lon;

	@ManyToMany(mappedBy = "savedLocations", fetch = FetchType.LAZY)
	private List<User> subscribedUsers = new ArrayList<>();

	public Location() { 
	}

	public Location(String name, String country, String city, String zipCode, String address, BigDecimal lat, BigDecimal lon) {
		this.name = name;
		this.country = country;
		this.city = city;
		this.zipCode = zipCode;
		this.address = address;
		this.lat = lat;
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public Long getId() {
		return id;
	}

	public List<User> getSubscribedUsers() {
		return subscribedUsers;
	}

}
