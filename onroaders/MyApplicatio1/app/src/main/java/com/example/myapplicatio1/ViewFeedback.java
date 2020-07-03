package com.example.myapplicatio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ViewFeedback extends AppCompatActivity {
    public RadioButton ve;
    public RadioButton vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        ve = (RadioButton) findViewById(R.id.radioButton);
        ve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetob();
            }
        });
        vm = (RadioButton) findViewById(R.id.radioButton2);
        vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoc();
            }
        });
    }
        private void movetob(){
            Intent inn = new Intent(ViewFeedback.this, usersfeedback.class);
            startActivity(inn);

        }
    private void movetoc(){
        Intent inn = new Intent(ViewFeedback.this, Mechanicfeedback.class);
        startActivity(inn);

    }
    }


