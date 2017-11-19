package hu.evave.eventfinder.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.main.activity.MainActivity;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.EventType;
import hu.evave.eventfinder.model.EventTypeMapping;
import hu.evave.eventfinder.model.dao.DaoSession;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RestClientHandler {

    private static final String BASE_URL = "http://192.168.43.216:8080/eventfinder/rest/";
    private static DaoSession session;

    private Runnable finishedCallback;
    private Context context;

    private ProgressDialog dialog;

    public String url;

    public RestClientHandler(Runnable finishedCallback, Context context) {
        this.finishedCallback = finishedCallback;
        this.context = context;
    }

    interface RestService {
        @GET("events/search")
        Call<EventResponse[]> search(@Query("keyword") String keyword, @Query("location") String location, @Query("types") String types);
    }


    public void run() {
        dialog = ProgressDialog.show(context, context.getString(R.string.event_refresh), context.getString(R.string.please_wait));
        session = App.getDaoSession();

        EventfulTask task = new EventfulTask();
        task.execute();
    }

    private final class EventfulTask extends AsyncTask<Void, Void, Void> {

        private List<EventResponse> searchResponse;
        private EventResponse[] eventsArray;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                final OkHttpClient client = new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

                RestService service = retrofit.create(RestService.class);
                String types = "";
                for (EventType type : EventType.values()) {
                    types += type.name();
                    types += "-";
                }
                types = types.substring(0, types.length() - 1);
                Call<EventResponse[]> call = service.search(null, "Magyarország", types);

                url = call.request().url().toString();


                call.enqueue(new Callback<EventResponse[]>() {
                    @Override
                    public void onResponse(Call<EventResponse[]> call, Response<EventResponse[]> response) {
                        //Dismiss Dialog
                        dialog.dismiss();

                        //dialog = ProgressDialog.show(context, "siker", "siker");

                        if (response.isSuccessful()) {
                            /**
                             * Got Successfully
                             */
                            //dialog = ProgressDialog.show(context, "siker2", "siker2");
                            eventsArray = response.body();
                            saveEvents(eventsArray);
                            dialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<EventResponse[]> call, Throwable t) {
                        dialog.dismiss();
                        //dialog = ProgressDialog.show(context, "failure", "failure");
                        Log.e("failure", String.valueOf(t.getMessage()));
                        Log.e("failure", String.valueOf(t.getCause()));
                        Log.e("failure", String.valueOf(t.getStackTrace()));
                    }


                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //dialog.dismiss();
            //dialog = ProgressDialog.show(context, "vége", "vége");
            //dialog = ProgressDialog.show(context, "url", url);

            finishedCallback.run();
        }

    }

    private static void saveEvents(EventResponse[] events) {
        for (EventResponse event : events) {
            saveEvents(event);
        }
    }

    private static void saveEvents(EventResponse event) {

        boolean isSaved = false;

        Event result = event.toEvent();

        List<Event> events = session.getEventDao().loadAll();
        if(events.contains(result)) {
            int index = events.indexOf(result);
            isSaved = events.get(index).getIsSaved();
        }

        result.setIsSaved(isSaved);
        session.insertOrReplace(result.getLocation());
        session.insertOrReplace(result);

        for (EventTypeMapping mapping : result.getTypes()) {
            session.insertOrReplace(mapping);
        }
    }

}

