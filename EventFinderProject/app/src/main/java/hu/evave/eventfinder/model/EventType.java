package hu.evave.eventfinder.model;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;


public enum EventType {
    AUTOMOTIVE(R.string.et_automotive, "technology"),
    FESTIVAL(R.string.et_festival, "festivals_parades"),
    GASTRO(R.string.et_gastro, "food"),
    KIDS(R.string.et_kids, "family_fun_kids"),
    TRADITION(R.string.et_tradition, "schools_alumni"),
    EXHIBITION(R.string.et_exhibiton, "art"),
    CULTURE(R.string.et_culture, "learning_education"),
    SIGHT(R.string.et_sight, "holiday"),
    CINEMA(R.string.et_cinema, "movies_film"),
    SPORT(R.string.et_sport, "sports"),
    OUTDOOR(R.string.et_outdoor, "outdoors_recreation"),
    THEATRE(R.string.et_theatre, "performing_arts"),
    MUSIC(R.string.et_music, "music"),
    MUSEUM(R.string.et_museum, "attractions"),
    CONCERT(R.string.et_concert, "singles_social"),
    OTHER(R.string.et_other, "other");

    EventType(int stringResource, String eventfulId) {
        this.stringResource = stringResource;
        this.eventfulId = eventfulId;
    }

    private int stringResource;
    private String eventfulId;

    public int getStringResource() {
        return stringResource;
    }

    public String getEventfulId() {
        return eventfulId;
    }

    public EventType getTypeByEventfulId(String id) {
        for (EventType type : EventType.values()) {
            if (type.eventfulId.equals(id))
                return type;
        }
        return EventType.OTHER;
    }

    @Override
    public String toString() {
        return App.getContext().getString(stringResource);
    }
}
