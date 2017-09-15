package hu.evave.eventfinder.web.rest.resource;

import java.math.BigDecimal;

import hu.evave.eventfinder.web.model.Location;

public class LocationResource {

	private Long id;

	private String name;
	private String country;
	private String city;

	private String zipCode;

	private String address;

	private BigDecimal lat;

	private BigDecimal lon;

	public LocationResource(Location location) {
		id = location.getId();
		name = location.getName();
		country = location.getCountry();
		city = location.getCity();
		zipCode = location.getZipCode();
		address = location.getAddress();
		lat = location.getLat();
		lon = location.getLon();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getAddress() {
		return address;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public BigDecimal getLon() {
		return lon;
	}

}
