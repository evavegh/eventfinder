package hu.evave.eventfinder.settings.eventfilter.model;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;

public enum Distance {
    _5KM(5),
    _10KM(10),
    _25KM(25),
    _50KM(50),
    _100KM(100),
    _250KM(250),
    NATIONAL(0);

    private static final int METERS_IN_KILOMETERS = 1000;

    private int maxDistanceInKilometers;

    Distance(int maxDistanceInKilometers) {
        this.maxDistanceInKilometers = maxDistanceInKilometers;
    }

    public int getMaxDistanceInKilometers() {
        return maxDistanceInKilometers;
    }

    public int getMaxDistanceInMeters() {
        return maxDistanceInKilometers * METERS_IN_KILOMETERS;
    }

    @Override
    public String toString() {
        if (this == NATIONAL) {
            return App.getContext().getString(R.string.search_filter_national);
        }

        return maxDistanceInKilometers + " km";
    }
}
