package com.example.adminpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash_screen extends AppCompatActivity {
    Animation logo_flipin;
    ImageView Splash_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(splash_screen.this, R.color.black));

        logo_flipin = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        Splash_logo = findViewById(R.id.splash_logo);
        Splash_logo.setAnimation(logo_flipin);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),admin_login.class);
                startActivity(i);

            }
        },3000);
    }
}