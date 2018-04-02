package eg.iti.losh.splash;

/**
 * Created by adel on 29/03/18.
 */
import android.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class NotifyService extends Service {
    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    // Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            showNotification(intent.getStringExtra("tripName"),intent.getStringExtra("tripStart"),intent.getStringExtra("tripEnd"),intent.getStringExtra("Start_Lat"),intent.getStringExtra("Start_Long"),intent.getStringExtra("End_Lat"),intent.getStringExtra("End_Long"));

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification(String Name, String StartPoint, String EndPoint, String Start_Lat,String Start_Long,String End_Lat,String End_Long) {
        // This is the 'title' of the notification
        CharSequence title = "Alarm!!";
        // This is the icon to use on the notification
        int icon = R.drawable.ic_dialog_alert;
        // This is the scrolling text of the notification
        CharSequence text = "Your notification time is upon us.";
        // What time to show on the notification
        long time = System.currentTimeMillis();
        // Send the notification to the system.
     //   mNM.notify(NOTIFICATION, notification);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setContentTitle(Name)
                .setContentText(StartPoint+"&"+EndPoint)
                .addAction(R.drawable.sym_action_chat, "click",
                null)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = mBuilder.getNotification();
        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(123, notification);
        //********************************************************************************
        Intent intent = new Intent(this, AlarmReceiverActivity.class);
        intent.putExtra("alarmStart",StartPoint);
        intent.putExtra("alarmEnd",EndPoint);
        intent.putExtra("alarmName",Name);
        intent.putExtra("alarmStartLat",Start_Lat);
        intent.putExtra("alarmStartLong",Start_Long);
        intent.putExtra("alarmEndLat",End_Lat);
        intent.putExtra("alarmEndLong",End_Long);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =
                (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, 0,
                pendingIntent);
        // Stop the service when we are finished
        stopSelf();
    }
}
