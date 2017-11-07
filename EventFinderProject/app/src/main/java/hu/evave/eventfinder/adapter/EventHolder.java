package hu.evave.eventfinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.details.activity.EventDetailsActivity;
import hu.evave.eventfinder.model.Event;

public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int METERS_PER_KILOMETERS = 1000;

    private final TextView txtName;
    private final TextView txtDistance;

    private Event event;
    private Context context;

    public EventHolder(Context context, View itemView) {

        super(itemView);

        this.context = context;

        this.txtName = (TextView) itemView.findViewById(R.id.txtName);
        this.txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);

        itemView.setOnClickListener(this);
    }

    public void bindEvent(Event event, Location location) {
        this.event = event;
        txtName.setText(event.getName());

        if (location == null) {
            txtDistance.setText(R.string.not_available);
        } else {
            final double distanceInMeters = location.distanceTo(event.getLocation().toAndroidLocation());
            final double distanceInKilometers = distanceInMeters / METERS_PER_KILOMETERS;
            txtDistance.setText(numberFormat().format(distanceInKilometers) + " km");
        }
    }

    private NumberFormat numberFormat() {
        final NumberFormat result = NumberFormat.getNumberInstance();
        result.setMinimumFractionDigits(1);
        result.setMaximumFractionDigits(1);
        return result;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.EXTRA_EVENT_ID, event.getId());
        context.startActivity(intent);
    }

}
