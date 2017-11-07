package hu.evave.eventfinder.model;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;


public enum PriceType {
    NORMAL(R.string.price_normal),
    STUDENT(R.string.price_student),
    ADULT(R.string.price_adult),
    RETIRED(R.string.price_retired);

    PriceType(int stringResource) {
        this.stringResource = stringResource;
    }

    private int stringResource;

    @Override
    public String toString() {
        return App.getContext().getString(stringResource);
    }
}
