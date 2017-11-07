package hu.evave.eventfinder.settings.application.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.activity.EventFinderActivity;
import hu.evave.eventfinder.settings.application.fragment.ApplicationSettingsFragment;

public class ApplicationSettingsActivity extends EventFinderActivity {

    private ApplicationSettingsFragment applicationSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_settings);

        ButterKnife.bind(this);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        applicationSettings = (ApplicationSettingsFragment) getSupportFragmentManager().findFragmentById(R.id.frgApplicationSettings);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.nav_settings);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        try {
            applicationSettings.save();
            super.onBackPressed();
        } catch (IllegalStateException e) {
            // nincs teendő, már toastoltunk
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    applicationSettings.save();
                    finish();
                    return true;
                } catch (IllegalStateException e) {
                    // nincs teendő, már toastoltunk
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
