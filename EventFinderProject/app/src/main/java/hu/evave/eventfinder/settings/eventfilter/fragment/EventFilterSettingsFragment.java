package hu.evave.eventfinder.settings.eventfilter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.settings.eventfilter.model.Distance;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;

public class EventFilterSettingsFragment extends Fragment {

    @BindView(R.id.lnlTypes)
    LinearLayout lnlTypes;

    @BindView(R.id.spnDistance)
    Spinner spnDistance;

    private Map<EventType, CheckBox> checkboxes = new HashMap<>();

    private EventFilterPreferences preferences;

    public EventFilterSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferences = new EventFilterPreferences(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_filter_settings, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        initTypes();
        initDistance();
    }

    private void initTypes() {
        lnlTypes.removeAllViews();
        checkboxes.clear();

        for (EventType type : EventType.values()) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(type.getStringResource());
            checkBox.setChecked(preferences.isTypeSelected(type));
            lnlTypes.addView(checkBox);

            checkboxes.put(type, checkBox);
        }
    }

    private void initDistance() {
        ArrayAdapter<Distance> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Distance.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDistance.setAdapter(adapter);

        spnDistance.setSelection(preferences.getDistance().ordinal());
    }

    public Map<EventType, Boolean> getTypeMapping() {
        Map<EventType, Boolean> result = new HashMap<>();

        for (EventType type : checkboxes.keySet()) {
            CheckBox checkBox = checkboxes.get(type);
            result.put(type, checkBox.isChecked());
        }

        return result;
    }

    public Distance getDistance() {
        return (Distance) spnDistance.getSelectedItem();
    }

    public boolean isAnyTypeSelected() {
        int selectedCount = 0;
        Map<EventType, Boolean> typeMapping = getTypeMapping();

        for (EventType type : typeMapping.keySet()) {
            boolean selected = typeMapping.get(type);
            if (selected) {
                selectedCount++;
            }
        }

        return selectedCount > 0;
    }

    public void saveSettings() {
        preferences.setSelectedTypes(getTypeMapping());
        preferences.setDistance(getDistance());
        preferences.setInitialized();
    }

}
