package com.example.splashscreenaskit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 *This screen shows the logo and the name of our app when the app is first opened
 */

public class SplashScreen extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                Intent logIntent = new Intent( SplashScreen.this, Login.class );
                startActivity( logIntent);  // go to login screen
                finish();
            }

        }, SPLASH_TIME_OUT);
    }
}
