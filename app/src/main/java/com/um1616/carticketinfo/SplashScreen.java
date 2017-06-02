package com.um1616.carticketinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.um1616.carticketinfo.activities.MainActivity;

/**
 * Created by Ba Nyar Naing on 31-Oct-16.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
