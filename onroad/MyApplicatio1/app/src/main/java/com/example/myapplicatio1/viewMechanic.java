package com.example.myapplicatio1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class viewMechanic extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    private static String Name;
    private static String Location;
    private static String Ph;
    private static String mid;
    private GoogleApiClient mGoogleApiClient;
    ImageView call;
    RadioButton cl,Al;
    EditText Addresser;
    Button bt;
    ProgressBar progs;
    String txtRadio="n";
    FirebaseDatabase rdb;
    SharedPreferences sharedPreferences;
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    protected String latitude, longitude;
TextView nametxt,locatontxt,phtxt;
String location=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mechanic);
nametxt=findViewById(R.id.txtnamer);
rdb=FirebaseDatabase.getInstance();
progs=findViewById(R.id.progs);
call=findViewById(R.id.call);
        sharedPreferences=getSharedPreferences("Temp",MODE_PRIVATE);
Addresser=findViewById(R.id.txtotherloc);
cl=findViewById(R.id.rcurrent);
Al=findViewById(R.id.rother);
bt=findViewById(R.id.Bookbt);
locatontxt=findViewById(R.id.txtlocer);
phtxt=findViewById(R.id.txtph);
insertneeded();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        BookMech();
    }
});
call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        caller();
    }
});
    }

public void insertDatas(  String Name, String Location, String Ph,String mid)
{
   this.mid=mid;
   this.Name=Name;
   this.Location=Location;
   this.Ph=Ph;
}
public void insertneeded()
{
    nametxt.setText("Name : "+Name);
    locatontxt.setText("Location"+Location);
    phtxt.setText("Contact : "+Ph);
}
public void RadioSelect(View view)
{
    if(view.getId()==cl.getId())
    {
        if(cl.isChecked())
        {
            txtRadio="Current";
            Addresser.setVisibility(View.INVISIBLE);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGPS();
            } else {
                getLocation();
            }
            Log.d("kkkk","lat : "+latitude+"/log : "+longitude);
         /*   Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

          */
        }
    }
    else
    {
        if(Al.isChecked())
        {
            Addresser.setVisibility(View.VISIBLE);
            txtRadio="Other";
        }
    }
}

///////////////////////////////////////////////////////

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                viewMechanic.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                viewMechanic.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

///////////////////////////////////////////////////////
private void BookMech()
{bt.setEnabled(false);
progs.setVisibility(View.VISIBLE);
    if(txtRadio.equals("Current"))
    {
        location=latitude+","+longitude;
        BookAllter();
    }
    else if(txtRadio.equals("Other"))
    {
        location=Addresser.getText().toString().trim();
        BookAllter();
    }
    else
    {
        bt.setEnabled(true);
        progs.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Please select Location Type", Toast.LENGTH_SHORT).show();
    }
}
public void BookAllter() {
        if(isNetworkAvailable())
    {
    Calendar cal = Calendar.getInstance();
    String date = cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR);
    String uid = sharedPreferences.getString("id", null);
    String uname = sharedPreferences.getString("name", null);
    String ucontact = sharedPreferences.getString("mobile", null);
    BookItem bk = new BookItem();
    bk.setUname(uname);
    bk.setWname(Name);
    bk.setUcontact(ucontact);
    bk.setWcontact(Ph);
    bk.setLoctype(txtRadio);
    bk.setLocation(location);
    bk.setStatus("N/A");
    rdb.getReference().child("BOOK").child(date).child(uid).child(mid).push().setValue(bk).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful())
{
    Toast.makeText(viewMechanic.this, "Saved Sucessfully", Toast.LENGTH_SHORT).show();
    bt.setEnabled(true);
    progs.setVisibility(View.INVISIBLE);
}
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            bt.setEnabled(true);
            progs.setVisibility(View.INVISIBLE);
        }
    });
}
        else {
            bt.setEnabled(true);
            progs.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Please enable network", Toast.LENGTH_SHORT).show();
        }
}
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void caller()
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +Ph));
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
latitude=""+location.getLatitude();
longitude=""+location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
