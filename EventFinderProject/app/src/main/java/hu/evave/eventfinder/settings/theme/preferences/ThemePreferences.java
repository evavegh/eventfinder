package hu.evave.eventfinder.settings.theme.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import hu.evave.eventfinder.settings.theme.model.Theme;


public class ThemePreferences {
    private static final String PREFERENCE_THEME = "theme";
    public static final String KEY_THEME = "theme";

    private SharedPreferences preferences;

    public ThemePreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCE_THEME, Context.MODE_PRIVATE);
    }

    public Theme getTheme() {
        String themeName = preferences.getString(KEY_THEME, Theme.LIGHT.name());
        return Theme.valueOf(themeName);
    }

    public void setTheme(Theme theme) {
        preferences
                .edit()
                .putString(KEY_THEME, theme.name())
                .apply();
    }

    public void registerChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }
}
