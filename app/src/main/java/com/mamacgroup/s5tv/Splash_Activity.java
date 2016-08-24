package com.mamacgroup.s5tv;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by sriven on 8/9/2016.
 */
public class Splash_Activity extends Activity {
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView=(ImageView)findViewById(R.id.splash);
        imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                intent.putExtra("goto", getIntent().getStringExtra("goto"));
                intent.putExtra("data", getIntent().getStringExtra("data"));
                startActivity(intent);
                finish();

            }
        }, 2000);
    }

}



