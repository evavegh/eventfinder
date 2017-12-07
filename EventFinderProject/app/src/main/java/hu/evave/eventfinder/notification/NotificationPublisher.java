package hu.evave.eventfinder.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hu.evave.eventfinder.R;
import hu.evave.eventfinder.app.App;
import hu.evave.eventfinder.model.Event;
import hu.evave.eventfinder.model.dao.EventDao;

public class NotificationPublisher extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        List<Event> events = getEvents();

        for (Event event : events) {
            String message = event.getName() + " is starting in 5 hours";
            Notification notification = getNotification(message, context);
            int id = event.hashCode();
            notificationManager.notify(id, notification);
        }

    }

    private List<Event> getEvents() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 5);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date startFrom = cal.getTime();

        cal.add(Calendar.HOUR, 1);
        cal.add(Calendar.MILLISECOND, -1);

        Date startTo = cal.getTime();

        QueryBuilder<Event> qb = App.getDaoSession().getEventDao().queryBuilder();
        qb.where(qb.and(EventDao.Properties.IsSaved.eq(true), EventDao.Properties.StartsAt.between(startFrom, startTo)));
        return qb.list();
    }

    private Notification getNotification(String content, Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Event notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }
}
