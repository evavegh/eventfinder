package hu.evave.eventfinder.eventful;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.model.EventTypeMapping;
import hu.evave.eventfinder.model.dao.DaoSession;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class EventfulHandler {


    private static final String EVDB_API_KEY = "8msh7SGRjsM8nn5W";
    private static final String EVDB_USER = "35df9ab0d1e704b55255";
    private static final String EVDB_PASSWORD = "ff5de20e6da9a2546424";
    private static final String EVDB_BASE_URL = "http://api.eventful.com/rest/";
    private static DaoSession session;

    private Runnable finishedCallback;
    private Context context;

    private ProgressDialog dialog;

    public EventfulHandler(Runnable finishedCallback, Context context) {
        this.finishedCallback = finishedCallback;
        this.context = context;
    }

    interface EventfulService {
        @GET("events/search?app_key=" + EVDB_API_KEY + "&user_key=" + EVDB_USER + "&password=" + EVDB_PASSWORD)
        Call<SearchResponse> search(@Query("location") String location, @Query("category") String category, @Query("page_size") int pageSize, @Query("page_number") int pageNumber, @Query("sort_order") String order, @Query("date") String date);
    }


    public void run() {
        dialog = ProgressDialog.show(context, context.getString(R.string.event_refresh), context.getString(R.string.please_wait));
        session = App.getDaoSession();

        EventfulTask task = new EventfulTask();
        task.execute();
    }

    private final class EventfulTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(EVDB_BASE_URL)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build();

                EventfulService service = retrofit.create(EventfulService.class);
                Call<SearchResponse> search;

                for (EventType type : EventType.values()) {
                    String typeName = type.getEventfulId();
                    search = service.search("Hungary", typeName, 10, 1, "date", "Future");
                    SearchResponse searchResponse = search.execute().body();
                    List<EventResponse> events = searchResponse.getEvents();
                    saveEvents(events, type);
                }

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            finishedCallback.run();
        }

    }

    private static void saveEvents(List<EventResponse> events, EventType type) {
        for (EventResponse event : events) {
            System.out.println(event.toString());
            Log.e("EZAZ", event.toString());

            saveEvents(event, type);
        }
    }

    private static void saveEvents(EventResponse event, EventType type) {

        Event result = event.toEvent(type);

        session.insertOrReplace(result.getLocation());
        session.insertOrReplace(result);

        for (EventTypeMapping mapping : result.getTypes()) {
            session.insertOrReplace(mapping);
        }
    }

}
