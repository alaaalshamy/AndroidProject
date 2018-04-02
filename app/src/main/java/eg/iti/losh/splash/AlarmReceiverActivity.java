package eg.iti.losh.splash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

public class AlarmReceiverActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    LatLng start;
    LatLng end;
    String S;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        Intent intent=getIntent();
        if(intent!=null) {
            alertDialog.setTitle(intent.getStringExtra("alarmName"));
            alertDialog.setMessage(intent.getStringExtra("alarmStart")+" to "+intent.getStringExtra("alarmEnd"));
            double startLatitude = Double.parseDouble(intent.getStringExtra("alarmStartLat"));
            double startLongitude = Double.parseDouble(intent.getStringExtra("alarmStartLong"));
            final double endLatitude = Double.parseDouble(intent.getStringExtra("alarmEndLat"));
            final double endLongitude = Double.parseDouble(intent.getStringExtra("alarmEndLong"));
            Log.i("start", startLatitude+"#"+startLongitude+"#"+endLatitude+"#"+endLongitude);
            S="google.navigation:q="+endLatitude+","+endLongitude;
            alertDialog.setButton("Start", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mMediaPlayer.stop();
                    Uri gmmintentUri=Uri.parse(S);
                    Intent mapIntent=new Intent(Intent.ACTION_VIEW,gmmintentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    finish();

                }

            });
            alertDialog.setButton3("Mute", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mMediaPlayer.stop();
                    finish();

                }
            });
            alertDialog.show();


            playSound(this, getAlarmUri());
        }
        else {

        }
    }

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
}
