package hu.evave.eventfinder.settings.eventfilter.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.settings.theme.ThemeConfigurer;

public class EventFilterSettingsDialog extends DialogFragment {

    private OnDataChangeListener mListener;
    private EventFilterSettingsFragment fragment;

    public EventFilterSettingsDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ThemeConfigurer.configureTheme(this);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.settings_filter);
        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_filter_settings_dialog, container, false);

        fragment = (EventFilterSettingsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.frgm_event_filter);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.frgm_event_filter));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    @OnClick(R.id.btnOk)
    public void okClick() {

        if (!fragment.isAnyTypeSelected()) {
            Toast.makeText(getContext(), R.string.alert_empty_type, Toast.LENGTH_SHORT).show();
            return;
        }

        fragment.saveSettings();
        dismiss();


        if (mListener != null) {
            mListener.onDataChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataChangeListener) {
            mListener = (OnDataChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDataChangeListener {
        void onDataChanged();
    }
}
