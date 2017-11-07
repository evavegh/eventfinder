package hu.evave.eventfinder.map.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.base.Function;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.details.activity.EventDetailsActivity;
import hu.evave.eventfinder.main.view.EventView;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.provider.event.EventProviderByCategory;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;

public class MapFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.InfoWindowAdapter,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        EventView {

    private GoogleMap map;
    private Map<Marker, Event> eventMapping = new HashMap<>();
    private View infoWindowView = null;
    private LocationManager locationManager;
    private Function<Location, List<Event>> eventSupplier;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        SupportMapFragment mapFragment = new SupportMapFragment();
        fragmentTransaction.add(R.id.mapLayout, mapFragment);
        fragmentTransaction.commit();

        mapFragment.getMapAsync(this);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        initCamera();

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setCompassEnabled(false);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);

        map.setOnMarkerClickListener(this);
        map.setInfoWindowAdapter(this);
        map.setOnInfoWindowClickListener(this);

        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            // nothing to do
        }

        eventSupplier = new EventProviderByCategory(new EventFilterPreferences(getContext()));
        addEventMarkers();
    }

    private void initCamera() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Criteria crit = new Criteria();
        Location loc = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, false));

        if (loc != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 10));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .zoom(10)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void addEventMarkers() {

        eventMapping.clear();

        List<Event> events = eventSupplier.apply(null);
        for (Event event : events) {
            addEventMarker(event);
        }
    }

    private void addEventMarker(Event event) {
        LatLng position = event.getLocation().toLatLng();
        MarkerOptions options =
                new MarkerOptions()
                        .position(position)
                        .title(event.getName());
        Marker marker = map.addMarker(options);
        eventMapping.put(marker, event);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Event event = eventMapping.get(marker);

        if (infoWindowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            infoWindowView = inflater.inflate(R.layout.info_window_event, null);
        }

        populateInfoWindowDetails(event, infoWindowView);

        return infoWindowView;
    }

    private void populateInfoWindowDetails(Event event, View view) {
        TextView txtName = ButterKnife.findById(view, R.id.txtName);
        TextView txtLocation = ButterKnife.findById(view, R.id.txtLocation);
        TextView txtStart = ButterKnife.findById(view, R.id.txtStart);
        TextView txtEndLabel = ButterKnife.findById(view, R.id.txtEndLabel);
        TextView txtEnd = ButterKnife.findById(view, R.id.txtEnd);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());

        txtName.setText(event.getName());
        txtLocation.setText(event.getLocation().toString());

        Date startsAt = event.getStartsAt();
        txtStart.setText(dateFormat.format(startsAt) + ' ' + timeFormat.format(startsAt));

        Date endsAt = event.getEndsAt();
        if (endsAt == null) {
            txtEndLabel.setVisibility(View.INVISIBLE);
            txtEnd.setVisibility(View.INVISIBLE);
        } else {
            txtEnd.setText(dateFormat.format(endsAt) + ' ' + timeFormat.format(endsAt));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Event event = eventMapping.get(marker);
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.EXTRA_EVENT_ID, event.getId());
        startActivity(intent);
    }

    @Override
    public void refresh() {
        map.clear();
        addEventMarkers();
    }

    @Override
    public void search(String phrase) {
        //TODO
    }
}
