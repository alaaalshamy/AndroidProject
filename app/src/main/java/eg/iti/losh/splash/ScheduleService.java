package eg.iti.losh.splash;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by adel on 29/03/18.
 */

public class ScheduleService extends Service {
    /**
     * Class for clients to access
     */
    static Trip tripdata;
    public class ServiceBinder extends Binder {
        ScheduleService getService() {
            return ScheduleService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ScheduleService", "Received start id " + startId + ": " + intent);

        // We want this service to continue running until it is explicitly stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients. See
    private final IBinder mBinder = new ServiceBinder();
    /**
     * Show an alarm for a certain date when the alarm is called it will pop up a notification
     */
    public void setAlarm(Trip trip) {
        // This starts a new thread to set the alarm
        // You want to push off your tasks onto a new thread to free up the UI to carry on responding
        tripdata=trip;
        new AlarmTask(this, trip.getStart_date()).run();
    }
    public class AlarmTask implements Runnable{
        // The date selected for the alarm
        private final Calendar date;
        // The android system alarm manager
        private final AlarmManager am;
        // Your context to retrieve the alarm manager from
        private final Context context;

        public AlarmTask(Context context, Calendar date) {
            this.context = context;
            this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            this.date = date;
        }

        @Override
        public void run() {
            // Request to start are service when the alarm date is upon us
            // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
            Intent intent = new Intent(context, NotifyService.class);
            intent.putExtra(NotifyService.INTENT_NOTIFY, true);
            intent.putExtra("tripName",tripdata.getName());
            intent.putExtra("tripStart",tripdata.getStart_point());
            intent.putExtra("tripEnd",tripdata.getEnd_point());
            intent.putExtra("Start_Lat",String.valueOf(tripdata.getStart_Lat()));
            intent.putExtra("Start_Long",String.valueOf(tripdata.getStart_Long()));
            intent.putExtra("End_Lat",String.valueOf(tripdata.getEnd_Lat()));
            intent.putExtra("End_Long",String.valueOf(tripdata.getEnd_Long()));
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
            am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
        }
    }

}
