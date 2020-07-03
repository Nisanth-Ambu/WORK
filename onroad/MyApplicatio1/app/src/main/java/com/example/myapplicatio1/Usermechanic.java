package com.example.myapplicatio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Usermechanic extends AppCompatActivity {
    public Button b12;
    public Button b13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermechanic);

        b12=(Button)findViewById(R.id.button12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user();
            }
        });
        b13=(Button)findViewById(R.id.button13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mechanic();
            }
        });
    }
    private void mechanic()
    {
        Intent intent=new Intent(Usermechanic.this,Newmechanic.class);
        startActivity(intent);

    }
    private void user()
    {
        Intent innn=new Intent(Usermechanic.this,Newuser.class);
        startActivity(innn);
    }
}


