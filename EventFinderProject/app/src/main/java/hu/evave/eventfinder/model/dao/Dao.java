package hu.evave.eventfinder.model.dao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;


public class Dao {
    private static final String DB_NAME = "events.db";

    private final DaoSession daoSession;

    public Dao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
