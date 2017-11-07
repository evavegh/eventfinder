package hu.evave.eventfinder.settings.eventfilter.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.settings.eventfilter.model.Distance;

public class EventFilterPreferences {
    private static final String PREFERENCE_FILTER = "event_filter";
    private static final String KEY_INIT = "init";
    private static final String KEY_PREFIX_TYPE = "type_";
    private static final String KEY_DISTANCE = "distance";

    private SharedPreferences preferences;

    public EventFilterPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCE_FILTER, Context.MODE_PRIVATE);
    }

    public boolean isInitialized() {
        return preferences.getBoolean(KEY_INIT, false);
    }

    public boolean isTypeSelected(EventType type) {
        return preferences.getBoolean(KEY_PREFIX_TYPE + type.name(), true);
    }

    public void setSelectedTypes(Map<EventType, Boolean> settings) {
        SharedPreferences.Editor editor = preferences.edit();

        for (EventType type : settings.keySet()) {
            boolean selected = settings.get(type);
            editor.putBoolean(KEY_PREFIX_TYPE + type.name(), selected);
        }

        editor.apply();
    }

    public List<EventType> getSelectedTypes() {
        List<EventType> result = new ArrayList<>();

        for (EventType type : EventType.values()) {
            if (isTypeSelected(type)) {
                result.add(type);
            }
        }

        return Collections.unmodifiableList(result);
    }

    public Distance getDistance() {
        return Distance.valueOf(preferences.getString(KEY_DISTANCE, Distance._10KM.name()));
    }

    public void setDistance(Distance distance) {
        preferences
                .edit()
                .putString(KEY_DISTANCE, distance.name())
                .apply();
    }

    public void setInitialized() {
        preferences
                .edit()
                .putBoolean(KEY_INIT, true)
                .apply();
    }

}
