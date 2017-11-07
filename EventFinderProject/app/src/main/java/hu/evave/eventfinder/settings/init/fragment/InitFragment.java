package hu.evave.eventfinder.settings.init.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.settings.eventfilter.fragment.EventFilterSettingsFragment;

public class InitFragment extends Fragment {

    private EventFilterSettingsFragment eventFilterSettingsFragment;

    private OnInitListener mListener;

    public InitFragment() {
    }

    public static InitFragment newInstance() {
        InitFragment fragment = new InitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init, container, false);

        eventFilterSettingsFragment = new EventFilterSettingsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frm_init, eventFilterSettingsFragment);
        fragmentTransaction.commit();

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnOk)
    public void okClick() {
        if (!eventFilterSettingsFragment.isAnyTypeSelected()) {
            Toast.makeText(getContext(), R.string.alert_empty_type, Toast.LENGTH_SHORT).show();
            return;
        }

        eventFilterSettingsFragment.saveSettings();
        mListener.onInitComplete();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInitListener) {
            mListener = (OnInitListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInitListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnInitListener {
        void onInitComplete();
    }
}
