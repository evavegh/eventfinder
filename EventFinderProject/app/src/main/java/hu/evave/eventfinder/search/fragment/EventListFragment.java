package hu.evave.eventfinder.search.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.main.view.EventView;
import hu.evave.eventfinder.search.adapter.SearchEventListAdapter;
import hu.evave.eventfinder.main.activity.MainActivity;
import hu.evave.eventfinder.provider.event.EventProviderByDistanceAndCategory;
import hu.evave.eventfinder.settings.eventfilter.preferences.EventFilterPreferences;


public class EventListFragment extends Fragment implements EventView {

    private MainActivity activity;

    @BindView(R.id.ListRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefresh;

    private SearchEventListAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final EventFilterPreferences preferences = new EventFilterPreferences(getContext());

        adapter = new SearchEventListAdapter(getContext(), activity.getLastLocation(), new EventProviderByDistanceAndCategory(preferences, getContext()));
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        return view;
    }

    private void refreshItems() {
        activity.loadEvents();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void refresh() {
        adapter.refreshEvents();
    }

    @Override
    public void search(String phrase) {
        adapter.setPhrase(phrase);
    }
}
