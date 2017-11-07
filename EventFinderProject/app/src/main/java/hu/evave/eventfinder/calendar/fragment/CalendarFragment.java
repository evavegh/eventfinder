package hu.evave.eventfinder.calendar.fragment;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.calendar.adapter.CalendarEventListAdapter;
import hu.evave.eventfinder.function.BiFunction;
import hu.evave.eventfinder.main.view.EventView;
import hu.evave.eventfinder.main.activity.MainActivity;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.provider.event.EventProviderByDate;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;
import hu.evave.eventfinder.settings.theme.ThemeConfigurer;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements EventView {

    @BindView(R.id.CalendarRecyclerView)
    RecyclerView recyclerView;

    private CaldroidFragment caldroid;
    private MainActivity activity;
    private CalendarEventListAdapter adapter;
    private Date lastSelectedDay = new Date();

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, view);

        initCalendar();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frm_calendar, caldroid);
        ft.commit();

        initList();

        return view;
    }

    private void initCalendar() {

        caldroid = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
        args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, false);
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
        args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
        args.putInt(CaldroidFragment._MIN_DATE_TIME, (int) System.currentTimeMillis());
        caldroid.setArguments(args);

        caldroid.setMinDate(new Date());
        caldroid.refreshView();

        ThemeConfigurer.configureTheme(this);

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                caldroid.clearSelectedDates();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                Date today = calendar.getTime();
                if(date.before(today)) {
                    lastSelectedDay = null;
                } else {
                    lastSelectedDay = date;
                    caldroid.setSelectedDate(date);
                    caldroid.refreshView();
                }

                refresh();
            }
        };

        ArrayList<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 9, 15);
        dates.add(calendar.getTime());
        caldroid.setDisableDates(dates);

        caldroid.setCaldroidListener(listener);
    }

    private void initList() {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final EventFilterPreferences preferences = new EventFilterPreferences(getContext());
        final BiFunction<Location, Date, List<Event>> provider = new EventProviderByDate(preferences, getContext());
        adapter = new CalendarEventListAdapter(getContext(), activity.getLastLocation(), provider);
        recyclerView.setAdapter(adapter);

        showEvents(new Date());
    }

    @Override
    public void refresh() {
        showEvents(lastSelectedDay);
    }

    @Override
    public void search(String phrase) {
        //TODO
    }

    private void showEvents(Date date) {
        adapter.refreshEvents(date);
    }

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof MainActivity)) {
            throw new IllegalStateException("Fragment only can be attached to MainActivity");
        }

        activity = (MainActivity) context;

        setHasOptionsMenu(true);

        super.onAttach(context);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void setStyle(int themeResource) {
        Bundle args = caldroid.getArguments();
        args.putInt(CaldroidFragment.THEME_RESOURCE, themeResource);
        caldroid.setArguments(args);

    }


}
