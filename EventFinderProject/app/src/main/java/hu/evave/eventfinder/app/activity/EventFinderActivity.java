package hu.evave.eventfinder.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import hu.evave.eventfinder.settings.theme.ThemeConfigurer;

public abstract class EventFinderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeConfigurer.configureTheme(this);
        super.onCreate(savedInstanceState);
    }
}
