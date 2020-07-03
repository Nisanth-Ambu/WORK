package com.example.myapplicatio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminpanal extends AppCompatActivity {
    public Button BL;
    public Button be;
    public Button bv;
    public Button vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanal);

        BL = (Button) findViewById(R.id.buttonv);
        BL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoverify();
            }
        });
        be = (Button) findViewById(R.id.buttonm);
        be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoviewmechanic();
            }
        });
        bv = (Button) findViewById(R.id.buttonvu);
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoviewuser();
            }
        });
        vf = (Button) findViewById(R.id.buttonvf);
        vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoviewfeedback();
            }
        });
    }


    private void movetoverify() {
        Intent inn = new Intent(adminpanal.this, verify.class);
        startActivity(inn);
    }

    private void movetoviewmechanic() {
        Intent inn = new Intent(adminpanal.this, viewMechanic.class);
        startActivity(inn);


    }

    private void movetoviewuser() {
        Intent inn = new Intent(adminpanal.this, viewuser.class);
        startActivity(inn);

    }
    private void movetoviewfeedback() {
        Intent inn = new Intent(adminpanal.this, ViewFeedback.class);
        startActivity(inn);
    }
}