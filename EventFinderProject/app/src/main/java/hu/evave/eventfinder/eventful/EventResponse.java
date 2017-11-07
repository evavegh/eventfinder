package hu.evave.eventfinder.eventful;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.model.EventTypeMapping;

@Root(strict = false, name = "event")
public class EventResponse {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Attribute(name = "id")
    String id;

    @Element(name = "title", required = false)
    String title;

    @Element(name = "latitude", required = false)
    BigDecimal latitude;

    @Element(name = "longitude", required = false)
    BigDecimal longitude;

    @Element(name = "city_name", required = false)
    String cityName;

    @Element(name = "postal_code", required = false)
    String postalCode;

    @Element(name = "country_name", required = false)
    String countryName;

    @Element(name = "venue_address", required = false)
    String venueAddress;

    @Element(name = "description", required = false)
    String description;

    @Element(name = "start_time", required = false)
    String startTime;

    @Element(name = "stop_time", required = false)
    String stopTime;

    @Element(name = "url", required = false)
    String url;

    @Element(name = "venue_name", required = false)
    String venueName;

    @Element(name = "venue_id", required = false)
    String venueId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }


    public Event toEvent(EventType type) {
        Event result = new Event();

        result.setId(getId());
        result.setName(getTitle());
        result.setDescription(getDescription());
        result.setWebUrl(getUrl());

        hu.evave.eventfinder.model.Location location = new hu.evave.eventfinder.model.Location();
        location.setId(getVenueId());
        location.setLat(getLatitude());
        location.setLon(getLongitude());
        location.setName(getVenueName());
        location.setCountry(getCountryName());
        location.setCity(getCityName());
        location.setZipCode(getPostalCode());
        location.setAddress(getVenueAddress());

        result.setLocation(location);

        result.setStartsAt(parseDate(getStartTime()));

        result.setEndsAt(parseDate(getStopTime()));

        EventTypeMapping typeMapping = new EventTypeMapping();
        typeMapping.setType(type);
        typeMapping.setEventId(result.getId());
        result.addType(typeMapping);

        return result;
    }

    private static Date parseDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}