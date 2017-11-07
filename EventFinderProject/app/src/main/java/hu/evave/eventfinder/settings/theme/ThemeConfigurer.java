package hu.evave.eventfinder.settings.theme;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.calendar.fragment.CalendarFragment;
import hu.evave.eventfinder.settings.theme.model.Theme;
import hu.evave.eventfinder.settings.theme.preferences.ThemePreferences;


public abstract class ThemeConfigurer {

    private ThemeConfigurer() {
    }

    public static void configureTheme(AppCompatActivity activity) {
        Theme theme = getCurrentTheme(activity);
        activity.setTheme(theme.getAppThemeResource());
    }

    private static Theme getCurrentTheme(Context context) {
        ThemePreferences preferences = new ThemePreferences(context);
        return preferences.getTheme();
    }

    public static void configureTheme(DialogFragment dialogFragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Theme theme = getCurrentTheme(dialogFragment.getContext());
            dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, theme.getDialogThemeResource());
        }
    }

    public static void configureTheme(CalendarFragment calendarFragment) {
        calendarFragment.setStyle(R.style.CalendarStyle);
    }

}
