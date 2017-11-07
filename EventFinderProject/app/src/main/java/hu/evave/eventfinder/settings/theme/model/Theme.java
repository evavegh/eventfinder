package hu.evave.eventfinder.settings.theme.model;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;


public enum Theme {
    LIGHT(R.style.AppTheme, android.R.style.Theme_Material_Light_Dialog_Alert, com.caldroid.R.style.CaldroidDefault, R.string.theme_light),
    DARK(R.style.AppTheme_Dark, android.R.style.Theme_Material_Dialog_Alert, com.caldroid.R.style.CaldroidDefaultDark, R.string.theme_dark);

    private int appThemeResource;
    private int dialogThemeResource;
    private int textResource;
    private int calendarThemeResource;

    Theme(int appThemeResource, int dialogThemeResource, int calendarThemeResource, int textResource) {
        this.appThemeResource = appThemeResource;
        this.dialogThemeResource = dialogThemeResource;
        this.calendarThemeResource = calendarThemeResource;
        this.textResource = textResource;
    }

    public int getAppThemeResource() {
        return appThemeResource;
    }

    public int getCalendarThemeResource() {
        return calendarThemeResource;
    }

    public int getDialogThemeResource() {
        return dialogThemeResource;
    }

    @Override
    public String toString() {
        return App.getContext().getString(textResource);
    }
}
