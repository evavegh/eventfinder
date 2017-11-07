package hu.evave.eventfinder.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.model.Event;

public class EventListAdapter extends RecyclerView.Adapter<EventHolder> {

    protected Location location;
    protected List<Event> events = new ArrayList<>();
    protected Context context;

    public EventListAdapter(Context context, Location location) {
        this.location = location;
        this.context = context;
    }

    protected final void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event, parent, false);
        return new EventHolder(context, view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        Event event = events.get(position);
        holder.bindEvent(event, location);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}
