package com.example.android.test1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (HomeActivity.isLogged) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }
                } else {
                    if (ChooseActivity.email.isEmpty()){

                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                            finish();
                            return ;
                        }
                    }
                    Intent intent = new Intent(SplashActivity.this, ChooseActivity.class);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, secondsDelayed * 1000);

    }
}
