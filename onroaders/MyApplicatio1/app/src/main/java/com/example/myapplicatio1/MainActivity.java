package com.example.myapplicatio1;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2500;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable()

        {
            @Override
            public void run()
            {
                Intent homeintent=new Intent(MainActivity.this,LOGIN.class);
                startActivity(homeintent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}

