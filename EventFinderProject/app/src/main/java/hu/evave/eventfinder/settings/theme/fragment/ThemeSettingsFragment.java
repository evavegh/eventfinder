package hu.evave.eventfinder.settings.theme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.settings.theme.model.Theme;
import hu.evave.eventfinder.settings.theme.preferences.ThemePreferences;

public class ThemeSettingsFragment extends Fragment {

    @BindView(R.id.spnTheme)
    Spinner spnTheme;

    private ThemePreferences preferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        preferences = new ThemePreferences(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme_settings, container, false);
        ButterKnife.bind(this, view);
        initThemes();
        return view;
    }

    private void initThemes() {
        ArrayAdapter<Theme> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Theme.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTheme.setAdapter(adapter);

        spnTheme.setSelection(preferences.getTheme().ordinal());
    }

    private Theme getTheme() {
        return (Theme) spnTheme.getSelectedItem();
    }

    public void saveSettings() {
        preferences.setTheme(getTheme());
    }

}
