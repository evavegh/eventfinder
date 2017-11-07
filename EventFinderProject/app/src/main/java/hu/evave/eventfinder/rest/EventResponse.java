package hu.evave.eventfinder.rest;


import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.model.EventTypeMapping;

public class EventResponse {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("location")
    @Expose
    private LocationResponse location;
    @SerializedName("startsAt")
    @Expose
    private String startsAt;
    @SerializedName("endsAt")
    @Expose
    private String endsAt;
    @SerializedName("prices")
    @Expose
    private List<Object> prices = null;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("webUrl")
    @Expose
    private String webUrl;
    @SerializedName("fbUrl")
    @Expose
    private String fbUrl;

    /**
     * No args constructor for use in serialization
     */
    public EventResponse() {
    }

    /**
     * @param summary
     * @param startsAt
     * @param id
     * @param webUrl
     * @param fbUrl
     * @param location
     * @param description
     * @param endsAt
     * @param name
     * @param prices
     * @param types
     */
    public EventResponse(String id, String name, List<String> types, LocationResponse location, String startsAt, String endsAt, List<Object> prices, String summary, String description, String webUrl, String fbUrl) {
        super();
        this.id = id;
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    public List<Object> getPrices() {
        return prices;
    }

    public void setPrices(List<Object> prices) {
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

    public Event toEvent() {
        Event result = new Event();

        result.setId(getId());
        result.setName(getName());
        result.setDescription(getDescription());
        result.setWebUrl(getWebUrl());
        result.setFbUrl(getFbUrl());

        hu.evave.eventfinder.model.Location location = new hu.evave.eventfinder.model.Location();
        location.setId(getLocation().getId());
        location.setLat(getLocation().getLat());
        location.setLon(getLocation().getLon());
        location.setName(getLocation().getName());
        location.setCountry(getLocation().getCountry());
        location.setCity(getLocation().getCity());
        location.setZipCode(getLocation().getZipCode());
        location.setAddress(getLocation().getAddress());

        result.setLocation(location);

        result.setStartsAt(parseDate(getStartsAt()));

        result.setEndsAt(parseDate(getEndsAt()));

        for (String typeString : types) {
            EventType type = EventType.valueOf(typeString);
            EventTypeMapping typeMapping = new EventTypeMapping();
            typeMapping.setType(type);
            typeMapping.setEventId(result.getId());
            result.addType(typeMapping);
        }

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

    @Override
    public String toString() {
        return "EventResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", types=" + types +
                ", location=" + location +
                ", startsAt='" + startsAt + '\'' +
                ", endsAt='" + endsAt + '\'' +
                ", prices=" + prices +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", fbUrl='" + fbUrl + '\'' +
                '}';
    }
}
