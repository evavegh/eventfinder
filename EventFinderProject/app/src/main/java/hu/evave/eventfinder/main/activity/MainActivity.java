package hu.evave.eventfinder.main.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.activity.EventFinderActivity;
import hu.evave.eventfinder.calendar.fragment.CalendarFragment;
import hu.evave.eventfinder.main.view.EventView;
import hu.evave.eventfinder.main.view.NullEventView;
import hu.evave.eventfinder.map.fragment.MapFragment;
import hu.evave.eventfinder.notification.NotificationPublisher;
import hu.evave.eventfinder.rest.RestClientHandler;
import hu.evave.eventfinder.search.fragment.EventListFragment;
import hu.evave.eventfinder.settings.application.activity.ApplicationSettingsActivity;
import hu.evave.eventfinder.settings.eventfilter.fragment.EventFilterSettingsDialog;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;
import hu.evave.eventfinder.settings.init.fragment.InitFragment;
import hu.evave.eventfinder.settings.theme.preferences.ThemePreferences;

public class MainActivity extends EventFinderActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        InitFragment.OnInitListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        EventFilterSettingsDialog.OnDataChangeListener,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final String TAG_INIT_DIALOG = "Init";
    public static final String TAG_FILTER_SETTINGS_DIALOG = "filter_settings";

    private GoogleApiClient mGoogleApiClient;
    private Location lastLocation;
    private EventFilterPreferences eventFilterPreferences;
    private EventView eventView = new NullEventView();
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        connectToGooglePlayServices();

        ThemePreferences themePreferences = new ThemePreferences(this);
        themePreferences.registerChangeListener(this);

        eventFilterPreferences = new EventFilterPreferences(this);

        loadEvents();
    }

    public void loadEvents() {
        if (isNetworkAvailable()) {
            new RestClientHandler(new Runnable() {
                @Override
                public void run() {
                    eventsRefreshed();
                }
            }, this).run();
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.drawer_layout), R.string.no_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadEvents();
                        }
                    });
            snackbar.show();
            eventsRefreshed();

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void eventsRefreshed() {
        if (!eventFilterPreferences.isInitialized()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_content, InitFragment.newInstance());
            ft.commit();
        } else {
            replaceFragment(new EventListFragment(), R.string.nav_events);
        }
    }

    @Override
    public void onInitComplete() {
        replaceFragment(new EventListFragment(), R.string.nav_events);
    }

    private void connectToGooglePlayServices() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            updateLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateLocation();
        }
    }

    private void updateLocation() {
        try {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();

        initNotificationScheduler();

        super.onStart();
    }

    private void initNotificationScheduler() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.SECOND, 10);
        cal.set(Calendar.MILLISECOND, 0);
        Long time = cal.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intentAlarm = new Intent(this, NotificationPublisher.class);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 60000, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT));
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    public Location getLastLocation() {
        if (lastLocation != null) {
            return lastLocation;
        }

        Location result = new Location((String) null);
        result.setLatitude(47.526115);
        result.setLongitude(19.074145);
        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(isTaskRoot());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            FragmentManager fm = getSupportFragmentManager();

            DialogFragment settingsDialog = new EventFilterSettingsDialog();
            settingsDialog.show(fm, TAG_FILTER_SETTINGS_DIALOG);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_events) {
            replaceFragment(new EventListFragment(), R.string.nav_events);
        } else if (id == R.id.nav_map) {
            replaceFragment(new MapFragment(), R.string.nav_map);
        } else if (id == R.id.nav_calendar) {
            replaceFragment(new CalendarFragment(), R.string.nav_calendar);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, ApplicationSettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment, int titleId) {

        if (fragment instanceof EventView) {
            eventView = (EventView) fragment;
        } else {
            eventView = new NullEventView();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack(null)
                .commit();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(titleId);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!key.equals(ThemePreferences.KEY_THEME)) {
            return;
        }

        finish();
        final Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDataChanged() {
        eventView.refresh();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        eventView.search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onClose() {
        eventView.refresh();
        return false;
    }
}
