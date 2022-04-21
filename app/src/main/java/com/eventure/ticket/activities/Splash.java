package com.eventure.ticket.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.window.SplashScreen;

import com.eventure.ticket.R;

public class Splash extends AppCompatActivity {

    final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashScreenTimer();
    }

    private void splashScreenTimer() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            startActivity(new Intent(Splash.this, Login.class));
            finish();
        }, SPLASH_DELAY);
    }
}