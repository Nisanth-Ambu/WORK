package com.example.otpauthentication;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CallForBlood extends FragmentActivity {
private static String contact=null;
View view;
Button call,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
LayoutInflater inflater;
ViewGroup root;
view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_call_for_blood,null,false);
        setContentView(R.layout.activity_call_for_blood);
        call=findViewById(R.id.yess);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callmaker();
            }
        });
        cancel=findViewById(R.id.nooo);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notsent();
            }
        });
    }

    public void callperson(String contact) {
        this.contact = contact;

    }

    private void callmaker() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "Your Phone_number"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            Snackbar.make(view,"Permission is not allowed",Snackbar.LENGTH_SHORT ).show();
            return;
        }
        else {
            startActivity(intent);

        }
    }
    public void notsent()
    {
        super.onBackPressed();
    }
    }


