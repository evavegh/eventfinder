
package hu.evave.eventfinder.provider.event;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.function.BiFunction;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.model.EventTypeMapping;
import hu.evave.eventfinder.model.dao.DaoSession;
import hu.evave.eventfinder.model.dao.EventDao;
import hu.evave.eventfinder.model.dao.EventTypeMappingDao;
import hu.evave.eventfinder.model.dao.LocationDao;
import hu.evave.eventfinder.settings.eventfilter.model.Distance;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;


public class EventProviderByDate implements BiFunction<Location, Date, List<Event>> {

    private EventFilterPreferences preferences;
    private Context context;

    public EventProviderByDate(EventFilterPreferences preferences, Context context) {
        this.preferences = preferences;
        this.context = context;
    }

    @Override
    public List<Event> apply(Location location, Date date) {
        if (date == null) return new ArrayList<>();
        final List<EventType> selectedTypes = preferences.getSelectedTypes();
        final DaoSession session = App.getDaoSession();
        QueryBuilder<Event> event = session.queryBuilder(Event.class);
        event.distinct();

        Join<Event, EventTypeMapping> eventType = event.join(EventTypeMapping.class, EventTypeMappingDao.Properties.EventId);

        List<WhereCondition> typeConditions = new ArrayList<>();
        for (EventType type : selectedTypes) {
            typeConditions.add(EventTypeMappingDao.Properties.Type.eq(type.name()));
        }

        WhereCondition typeCondition;
        if (typeConditions.size() == 1) {
            typeCondition = typeConditions.get(0);
        } else {
            WhereCondition[] remainingConditions = new WhereCondition[typeConditions.size() - 2];
            typeConditions.subList(2, typeConditions.size()).toArray(remainingConditions);
            typeCondition = eventType.or(typeConditions.get(0), typeConditions.get(1), remainingConditions);
        }

        eventType.where(typeCondition);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dayStart = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date dayEnd = cal.getTime();

        WhereCondition startsToday = event.and(EventDao.Properties.StartsAt.ge(dayStart), EventDao.Properties.StartsAt.lt(dayEnd));
        WhereCondition endsToday = event.and(EventDao.Properties.EndsAt.isNotNull(), EventDao.Properties.EndsAt.ge(dayStart), EventDao.Properties.EndsAt.lt(dayEnd));
        WhereCondition onToday = event.and(EventDao.Properties.EndsAt.isNotNull(), EventDao.Properties.StartsAt.lt(dayStart), EventDao.Properties.EndsAt.ge(dayEnd));

        event.where(event.or(startsToday, endsToday, onToday));

        Distance distance = preferences.getDistance();

        if (distance == Distance.NATIONAL && location != null) {
            Join<Event, hu.evave.eventfinder.model.Location> loc = event.join(EventDao.Properties.LocationId, hu.evave.eventfinder.model.Location.class);

            Geocoder gcd = new Geocoder(context);
            String countryCode = null;
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                countryCode = addresses.get(0).getCountryCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            loc.where(LocationDao.Properties.CountryCode.eq(countryCode));
        }

        List<Event> events = event.list();

        if (distance != Distance.NATIONAL) {
            final float maxDistance = preferences.getDistance().getMaxDistanceInMeters();
            for (Iterator<Event> i = events.iterator(); i.hasNext(); ) {
                Event e = i.next();
                Location eventLocation = e.getLocation().toAndroidLocation();
                float dist = location.distanceTo(eventLocation);

                if (dist > maxDistance) {
                    i.remove();
                }
            }
        } else {


            for (Iterator<Event> i = events.iterator(); i.hasNext(); ) {
                Event e = i.next();

            }
        }

        return events;
    }

}
