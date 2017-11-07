package hu.evave.eventfinder.app;

import android.app.Application;
import android.content.Context;

import hu.evave.eventfinder.model.dao.Dao;
import hu.evave.eventfinder.model.dao.DaoSession;

public class App extends Application {

    private static Context context;
    private static Dao dao;

    public App() {
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dao = new Dao(this);
    }

    public static Context getContext() {
        return context;
    }

    public static DaoSession getDaoSession() {
        return dao.getDaoSession();
    }
}
