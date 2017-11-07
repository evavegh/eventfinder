package hu.evave.eventfinder.search.adapter;

import android.content.Context;
import android.location.Location;

import java.util.List;

import hu.evave.eventfinder.adapter.EventListAdapter;
import hu.evave.eventfinder.function.BiFunction;
import hu.evave.eventfinder.model.Event;


public class SearchEventListAdapter extends EventListAdapter {

    private static final int METERS_PER_KILOMETERS = 1000;

    private BiFunction<Location, String, List<Event>> provider;

    public SearchEventListAdapter(Context context, android.location.Location location, BiFunction<Location, String, List<Event>> provider) {
        super(context, location);
        this.provider = provider;

        refreshEvents();
    }

    public void refreshEvents() {
        setEvents(provider.apply(location, ""));
    }

    public void setPhrase(String phrase) {
        setEvents(provider.apply(location, phrase));
    }

}
