package eg.iti.losh.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Spalsh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        final Thread newthread =new Thread(){


            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent myintent;
                    /// to get the clss name not hte thread do't use this
                    myintent = new Intent(getApplicationContext(),AppIntroClass.class);
                    startActivity(myintent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        newthread.start();
    }
}
