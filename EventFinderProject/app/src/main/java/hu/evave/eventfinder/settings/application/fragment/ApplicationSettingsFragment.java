package hu.evave.eventfinder.settings.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.settings.eventfilter.fragment.EventFilterSettingsFragment;
import hu.evave.eventfinder.settings.theme.fragment.ThemeSettingsFragment;

public class ApplicationSettingsFragment extends Fragment {

    private EventFilterSettingsFragment eventFilterSettingsFragment;
    private ThemeSettingsFragment themeSettingsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application_settings, container, false);
        ButterKnife.bind(this, view);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        eventFilterSettingsFragment = new EventFilterSettingsFragment();
        themeSettingsFragment = new ThemeSettingsFragment();

        fragmentManager.beginTransaction()
                .add(R.id.frmFilterSettings, eventFilterSettingsFragment)
                .add(R.id.frmThemeSettings, themeSettingsFragment)
                .commit();
        return view;
    }

    public void save() {
        if (!eventFilterSettingsFragment.isAnyTypeSelected()) {
            Toast.makeText(getContext(), R.string.alert_empty_type, Toast.LENGTH_SHORT).show();
            throw new IllegalStateException("No event types selected.");
        }

        eventFilterSettingsFragment.saveSettings();
        themeSettingsFragment.saveSettings();

    }

}
