package hu.evave.eventfinder.calendar.adapter;

import android.content.Context;
import android.location.Location;

import java.util.Date;
import java.util.List;

import hu.evave.eventfinder.adapter.EventListAdapter;
import hu.evave.eventfinder.function.BiFunction;
import hu.evave.eventfinder.model.Event;

public class CalendarEventListAdapter extends EventListAdapter {

    private final BiFunction<Location, Date, List<Event>> provider;

    public CalendarEventListAdapter(Context context, Location location, BiFunction<Location, Date, List<Event>> provider) {
        super(context, location);

        this.provider = provider;
    }

    public void refreshEvents(Date date) {
        setEvents(provider.apply(location, date));
    }

}
