package eg.iti.losh.splash;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class AppIntroClass extends AppIntro {
private  static int runbefore=0;
    Intent mainIntent;
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        runbefore=1;
        mainIntent =new Intent(getApplicationContext(),LoginActivity.class);
        mainIntent.putExtra("firstrun","1");
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        runbefore=1;
        mainIntent =new Intent(getApplicationContext(),MainActivity.class);
        mainIntent.putExtra("firstrun","1");
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onSlideChanged(Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //cheack if its the first time or not
//        if(runbefore==1){
//            mainIntent =new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(mainIntent);
//            finish();
//        }
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_app_intro); don't use it
        //slid 1
        addSlide(AppIntroFragment.newInstance("Title 1", "this sild 1","Description","Type face", R.mipmap.ic_launcher,R.color.white));
        //slid 2
        addSlide(AppIntroFragment.newInstance("Title 2", "this sild 2", R.mipmap.ic_launcher,R.color.slid2));
        //slid 3
        addSlide(AppIntroFragment.newInstance("Title 3", "this sild 3", R.mipmap.ic_launcher,R.color.slide3));
      setBarColor(Color.parseColor("#FF4081"));


       setSeparatorColor(Color.parseColor("#FF4081"));
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setFadeAnimation();
    }
}
