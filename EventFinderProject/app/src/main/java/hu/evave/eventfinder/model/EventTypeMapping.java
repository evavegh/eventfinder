package hu.evave.eventfinder.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import hu.evave.eventfinder.model.converter.EventTypeConverter;


@Entity(nameInDb = "event_type_mapping")
public class EventTypeMapping {

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;

    @Property(nameInDb = "event_id")
    private String eventId;

    @Property(nameInDb = "type")
    @Convert(columnType = String.class, converter = EventTypeConverter.class)
    @NotNull
    private EventType type;

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
        this.type = type;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


    @Generated(hash = 467334338)
    public EventTypeMapping(Long id, String eventId, @NotNull EventType type) {
        this.id = id;
        this.eventId = eventId;
        this.type = type;
    }

    @Keep
    public EventTypeMapping() {
    }
}
