package hu.evave.eventfinder.eventful;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false, name = "search")
public class SearchResponse {

    @ElementList(name = "events")
    List<EventResponse> events;

    public List<EventResponse> getEvents() {
        return events;
    }

    public void setEvents(List<EventResponse> events) {
        this.events = events;
    }

}