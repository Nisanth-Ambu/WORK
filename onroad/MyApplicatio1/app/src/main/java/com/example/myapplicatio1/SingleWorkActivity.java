package com.example.myapplicatio1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SingleWorkActivity extends AppCompatActivity {

    static String Name;
    static String Locations;
    static String Ph;
    static String jid;
    static String uid;
    static String wid;
    static String date;
    static String type;
    static  String ur;
    static  String Status;
    TextView txtname,txtcontact,txtaddress;
    ImageView vCall,vLoca;
    Button btaccept,btdecline,btcancel;
    FirebaseDatabase rdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_work);
        txtname=findViewById(R.id.wtxtname);
        txtcontact=findViewById(R.id.wtxtcontact);
        txtaddress=findViewById(R.id.wtxtaddress);
        btaccept=findViewById(R.id.wkaccept);
        btcancel=findViewById(R.id.urcancel);
        btdecline=findViewById(R.id.wkdecline);
        vCall=findViewById(R.id.wcall);
        vLoca=findViewById(R.id.wloc);
        rdb=FirebaseDatabase.getInstance();
        vCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +Ph));
                startActivity(intent);
            }
        });
        vLoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Locations.contains("null"))
                {

                }
                else
                {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Locations + "&mode=b");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });
        btaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptDeal();
            }
        });
        btdecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeclineDeal();
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelDeal();
            }
        });
        insertAll();
    }
    public SingleWorkActivity(){}
    public SingleWorkActivity(String ur,String Name,String Locations,String Ph,String jid,String uid,String wid,String date,String type,String status)
    {
        this.ur=ur;
        this.Name=Name;
        this.Locations=Locations;
        this.Ph=Ph;
        this.jid=jid;
        this.uid=uid;
        this.wid=wid;
        this.date=date;
        this.type=type;
        this.Status=status;
    }
    private void insertAll()
    {
txtname.setText(Name);
txtcontact.setText(Ph);
if(type.equals("Other"))
{
 txtaddress.setText(Locations);
 vLoca.setVisibility(View.INVISIBLE);
}
else
{
    vLoca.setVisibility(View.VISIBLE);
}
if(Status.equals("N/A"))
{
    if(ur.equals("MECH"))
    {
        btdecline.setVisibility(View.VISIBLE);
        btaccept.setVisibility(View.VISIBLE);
        btcancel.setVisibility(View.INVISIBLE);
    }
    else
    {
        btdecline.setVisibility(View.INVISIBLE);
        btaccept.setVisibility(View.INVISIBLE);
        btcancel.setVisibility(View.VISIBLE);
    }
}

    }
    public void acceptDeal()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("status","ACCEPTED");
        rdb.getReference().child("BOOK").child(date).child(uid).child(wid).child(jid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SingleWorkActivity.this, "Accepted work", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SingleWorkActivity.this, "Someting went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void DeclineDeal()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("status","DECLINED");
        rdb.getReference().child("BOOK").child(date).child(uid).child(wid).child(jid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    onBackPressed();
                    Toast.makeText(SingleWorkActivity.this, "Declined work", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SingleWorkActivity.this, "Someting went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void CancelDeal()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("status","CANCELED");
        rdb.getReference().child("BOOK").child(date).child(uid).child(wid).child(jid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    onBackPressed();
                    Toast.makeText(SingleWorkActivity.this, "Declined work", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SingleWorkActivity.this, "Someting went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
