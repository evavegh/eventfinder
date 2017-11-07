
package hu.evave.eventfinder.provider.event;

import android.location.Location;

import com.google.common.base.Function;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.model.EventTypeMapping;
import hu.evave.eventfinder.model.dao.DaoSession;
import hu.evave.eventfinder.model.dao.EventDao;
import hu.evave.eventfinder.model.dao.EventTypeMappingDao;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;


public class EventProviderByCategory implements Function<Location, List<Event>> {

    private EventFilterPreferences preferences;

    public EventProviderByCategory(EventFilterPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public List<Event> apply(Location location) {
        final Date now = new Date();
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

        WhereCondition hasEnd = event.and(EventDao.Properties.EndsAt.isNotNull(), EventDao.Properties.EndsAt.gt(now));
        WhereCondition hasNoEnd = event.and(EventDao.Properties.EndsAt.isNull(), EventDao.Properties.StartsAt.gt(now));

        event.where(event.or(hasEnd, hasNoEnd));

        return event.list();
    }

}
