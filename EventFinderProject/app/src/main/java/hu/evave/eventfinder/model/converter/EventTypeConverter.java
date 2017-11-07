package hu.evave.eventfinder.model.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import hu.evave.eventfinder.model.EventType;


public class EventTypeConverter implements PropertyConverter<EventType, String> {

    @Override
    public EventType convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return EventType.OTHER;
        }

        return EventType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(EventType entityProperty) {
        if (entityProperty == null) {
            return EventType.OTHER.name();
        }

        return entityProperty.name();
    }

}
