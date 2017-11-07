package hu.evave.eventfinder.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hu.evave.eventfinder.model.dao.DaoSession;
import hu.evave.eventfinder.model.dao.EventDao;
import hu.evave.eventfinder.model.dao.EventTypeMappingDao;
import hu.evave.eventfinder.model.dao.LocationDao;
import hu.evave.eventfinder.model.dao.PriceDao;


@Entity(nameInDb = "event")
public class Event implements Comparable<Event> {

    @Id
    @Property(nameInDb = "id")
    private String id;

    @Property(nameInDb = "name")
    @NotNull
    private String name;

    @ToMany(referencedJoinProperty = "eventId")
    private List<EventTypeMapping> types;

    @ToOne(joinProperty = "locationId")
    @NotNull
    private Location location;

    @Property(nameInDb = "location_id")
    @NotNull
    private String locationId;

    @Property(nameInDb = "starts_at")
    @NotNull
    private Date startsAt;

    @Property(nameInDb = "ends_at")
    private Date endsAt;

    @ToMany(referencedJoinProperty = "eventId")
    private List<Price> prices;

    @Property(nameInDb = "summary")
    @NotNull
    private String summary;

    @Property(nameInDb = "description")
    @NotNull
    private String description;

    @Property(nameInDb = "web_url")
    private String webUrl;

    @Property(nameInDb = "fb_url")
    private String fbUrl;

    @Property(nameInDb = "is_saved")
    private boolean isSaved;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1542254534)
    private transient EventDao myDao;


    @Generated(hash = 116289101)
    public Event(String id, @NotNull String name, @NotNull String locationId,
            @NotNull Date startsAt, Date endsAt, @NotNull String summary,
            @NotNull String description, String webUrl, String fbUrl, boolean isSaved) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.summary = summary;
        this.description = description;
        this.webUrl = webUrl;
        this.fbUrl = fbUrl;
        this.isSaved = isSaved;
    }

    @Generated(hash = 344677835)
    public Event() {
    }

    @Generated(hash = 2092130019)
    private transient String location__resolvedKey;


    @Override
    @Keep
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return id != null ? id.equals(event.id) : event.id == null;

    }

    @Override
    @Keep
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */

    public String getFbUrl() {
        return this.fbUrl;
    }

    public void setFbUrl(String fbUrl) {
        this.fbUrl = fbUrl;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Keep
    public String getDescription() {
        if (description == null) {
            return "";
        }

        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Keep
    public String getSummary() {
        if (summary == null) {
            return "";
        }
        return this.summary;
    }

    @Keep
    public void setSummary(String summary) {
        if (summary != null) {
            this.summary = summary;
        } else {
            this.summary = "";
        }
    }

    public Date getEndsAt() {
        return this.endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public Date getStartsAt() {
        return this.startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
        if (startsAt == null) {
            this.startsAt = new Date();
        }
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Keep
    private List<EventTypeMapping> types() {
        if (types == null) {
            types = new ArrayList<>();
        }

        return types;
    }

    @Keep
    public void addType(EventTypeMapping mapping) {
        types().add(mapping);
    }


    public String getLocationId() {
        return this.locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Keep
    @Override
    public int compareTo(Event e) {
        return startsAt.compareTo(e.startsAt);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", types=" + types +
                ", location=" + location +
                ", locationId='" + locationId + '\'' +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", fbUrl='" + fbUrl + '\'' +
                '}';
    }

    public boolean getIsSaved() {
        return this.isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 983671041)
    public Location getLocation() {
        String __key = this.locationId;
        if (location__resolvedKey == null || location__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            Location locationNew = targetDao.load(__key);
            synchronized (this) {
                location = locationNew;
                location__resolvedKey = __key;
            }
        }
        return location;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2065453948)
    public void setLocation(@NotNull Location location) {
        if (location == null) {
            throw new DaoException(
                    "To-one property 'locationId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.location = location;
            locationId = location.getId();
            location__resolvedKey = locationId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 424626418)
    public List<EventTypeMapping> getTypes() {
        if (types == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EventTypeMappingDao targetDao = daoSession.getEventTypeMappingDao();
            List<EventTypeMapping> typesNew = targetDao._queryEvent_Types(id);
            synchronized (this) {
                if (types == null) {
                    types = typesNew;
                }
            }
        }
        return types;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1060082255)
    public synchronized void resetTypes() {
        types = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 946750141)
    public List<Price> getPrices() {
        if (prices == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PriceDao targetDao = daoSession.getPriceDao();
            List<Price> pricesNew = targetDao._queryEvent_Prices(id);
            synchronized (this) {
                if (prices == null) {
                    prices = pricesNew;
                }
            }
        }
        return prices;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1027193279)
    public synchronized void resetPrices() {
        prices = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1459865304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
    }

}
