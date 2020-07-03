package com.example.myapplicatio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mechanic extends AppCompatActivity {
    public Button bc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);
        bc = (Button) findViewById(R.id.button14);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetovm();
            }
        });
    }

    private void movetovm() {
        Intent inn = new Intent(Mechanic.this, Viewrequest.class);
        startActivity(inn);

    }
}
