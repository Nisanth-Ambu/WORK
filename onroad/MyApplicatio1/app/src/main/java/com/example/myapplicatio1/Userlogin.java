package com.example.myapplicatio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Userlogin extends AppCompatActivity {
    public Button sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        sm = (Button) findViewById(R.id.button10);
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetosearchmech();
            }
        });
    }
    private void movetosearchmech() {
        Intent in = new Intent(Userlogin.this, Searchmech.class);
        startActivity(in);

    }

}
