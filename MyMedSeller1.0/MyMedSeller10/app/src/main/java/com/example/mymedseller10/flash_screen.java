package com.example.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class flash_screen extends AppCompatActivity {

    static String number;
    FirebaseFirestore f;
    static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int n=0;

    private int    timeoutMillis       = 5000;

    /** The time when this {@link AppCompatActivity} was created. */

    private long                startTimeMillis     = 0;

    /** The code used when requesting permissions */

    private static final int    PERMISSIONS_REQUEST = 1234;


    public int getTimeoutMillis() {
        return timeoutMillis;
    }





    public String[] getRequiredPermissions() {
        String[] permissions = null;
        try {
            permissions = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (permissions == null) {
            return new String[0];
        } else {
            return permissions.clone();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flash_screen);
        f= FirebaseFirestore.getInstance();
        Context context;
        sharedPreferences=getSharedPreferences("ACC",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        startTimeMillis = System.currentTimeMillis();
//        startNextActivity();

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            startNextActivity();
        }




    }



/////////////////////////////////////



    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            checkPermissions();
        }
    }

    private void startNextActivity() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

            }
        });
        long delayMillis = getTimeoutMillis() - (System.currentTimeMillis() - startTimeMillis);
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             check();
                finish();
            }
        }, delayMillis);
    }

    private void checkPermissions() {
        String[] ungrantedPermissions = requiredPermissionsStillNeeded();
        if (ungrantedPermissions.length == 0) {
            startNextActivity();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(ungrantedPermissions, PERMISSIONS_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private String[] requiredPermissionsStillNeeded() {

        Set<String> permissions = new HashSet<String>();
        for (String permission : getRequiredPermissions()) {
            permissions.add(permission);
        }
        for (Iterator<String> i = permissions.iterator(); i.hasNext();) {
            String permission = i.next();
            if(permission.equals("android.permission.FOREGROUND_SERVICE"))
            {
                i.remove();
            }
            else if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d(MyMedHomeActivity.class.getSimpleName(),
                        "Permission: " + permission + " already granted.");
                i.remove();
            } else {
                Log.d(MyMedHomeActivity.class.getSimpleName(),
                        "Permission: " + permission + " not yet granted.");
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }

    ///////////////////////////////////



    void check() {
Log.d("jhjhj","jhgkfhjjklk");

        String nm=null;nm = sharedPreferences.getString("USERNAME", null);
        String ps=null;ps=sharedPreferences.getString("PASSWORD", null);
        Log.d("jhjhj","/nm="+nm+"/ps="+ps);
        try{
            if (nm!=null && ps!=null) {


                f.collection("name").whereEqualTo("USERNAME",nm).whereEqualTo("PASSWORD",ps).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange d : queryDocumentSnapshots.getDocumentChanges()) {
                            if (d.getType() == DocumentChange.Type.ADDED) {
                                if (d.getDocument().exists()) {
                                    n = 1;

                                }

                            }
                        }   f.terminate();
                        if (n == 1) {


                            Toast.makeText(getApplicationContext(), "Sucessfully logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MyMedHomeActivity.class));

                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                });

            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }
        catch(NullPointerException e) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
    }
}
