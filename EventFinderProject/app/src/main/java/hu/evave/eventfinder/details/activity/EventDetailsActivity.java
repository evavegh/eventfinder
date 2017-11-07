package hu.evave.eventfinder.details.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.app.activity.EventFinderActivity;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.Price;

public class EventDetailsActivity extends EventFinderActivity {

    public static final String EXTRA_EVENT_ID = "eventId";

    private Event event;

    private Menu menu;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtLocation)
    TextView txtLocation;

    @BindView(R.id.txtStart)
    TextView txtStart;

    @BindView(R.id.txtEndLabel)
    TextView txtEndLabel;
    @BindView(R.id.txtEnd)
    TextView txtEnd;

    @BindView(R.id.txtFee)
    TextView txtFee;

    @BindView(R.id.txtFeeLabel)
    TextView txtFeeLabel;

    @BindView(R.id.txtSummary)
    TextView txtSummary;

    @BindView(R.id.txtDescription)
    WebView txtDescription;

    @BindView(R.id.txtWebUrl)
    TextView txtWebUrl;

    @BindView(R.id.txtFbUrl)
    TextView txtFbUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        ButterKnife.bind(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String eventId = intent.getStringExtra(EXTRA_EVENT_ID);
        event = App.getDaoSession().getEventDao().load(eventId);
        actionBar.setTitle(event.getName());

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);

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


        List<Price> prices = event.getPrices();
        if (prices == null || prices.isEmpty()) {
            txtFeeLabel.setVisibility(View.INVISIBLE);
            txtFee.setVisibility(View.INVISIBLE);
        } else {
            StringBuilder priceText = new StringBuilder();
            for (Price price : prices) {
                priceText.append(price);
                priceText.append('\n');
            }
            priceText.setLength(priceText.length() - 1);

            txtFee.setText(priceText.toString());
        }

        txtSummary.setText(event.getSummary());
        txtDescription.getSettings().setDefaultTextEncodingName("utf-8");
        txtDescription.loadData(event.getDescription(), "text/html; charset=utf-8", "utf-8");

        String webUrl = event.getWebUrl();
        if (webUrl == null) {
            txtWebUrl.setVisibility(View.INVISIBLE);
        } else {
            txtWebUrl.setText(webUrl);
        }

        String fbUrl = event.getFbUrl();
        if (fbUrl == null) {
            txtFbUrl.setVisibility(View.INVISIBLE);
        } else {
            txtFbUrl.setText(fbUrl);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);
                finish();
                return true;
            case R.id.action_save: {
                event.setIsSaved(true);
                App.getDaoSession().getEventDao().save(event);
                menu.findItem(R.id.action_save).setVisible(false);
                menu.findItem(R.id.action_forget).setVisible(true);
                return true;
            }
            case R.id.action_forget: {
                event.setIsSaved(false);
                App.getDaoSession().getEventDao().save(event);
                menu.findItem(R.id.action_save).setVisible(true);
                menu.findItem(R.id.action_forget).setVisible(false);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.save, menu);

        initSaveButtons();

        return super.onCreateOptionsMenu(menu);
    }

    private void initSaveButtons() {
        boolean isSaved = event.getIsSaved();

        menu.findItem(R.id.action_save).setVisible(!isSaved);
        menu.findItem(R.id.action_forget).setVisible(isSaved);
    }
}
