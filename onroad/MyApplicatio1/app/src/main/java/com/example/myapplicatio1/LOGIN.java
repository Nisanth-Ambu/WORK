package com.example.myapplicatio1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LOGIN extends AppCompatActivity {
    public Button BL;
EditText txtph,txtpass;
  TextView reg;
  RadioButton rusr,rmech;
  String txtRadio=null;
  FirebaseFirestore fs;
  SharedPreferences  sharedPreferences;
  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
reg=findViewById(R.id.textView2);
txtph=findViewById(R.id.editText);
txtpass=findViewById(R.id.editText2);
fs=FirebaseFirestore.getInstance();
sharedPreferences=getSharedPreferences("Temp",MODE_PRIVATE);
editor=sharedPreferences.edit();
rusr=findViewById(R.id.ruser);
txtRadio="N";
rmech=findViewById(R.id.rmech);
        BL = (Button) findViewById(R.id.button);
        BL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoregister(v);

            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movetologin();
            }
        });
    }
public void radioder(View v)
{
    Log.e("check","radio :"+v.getId());
   if(v.getId()==rusr.getId())
   { Log.e("check","radio :"+v.getId());
       if(rusr.isChecked())
       {
          txtRadio="USR";
       }
   }
   else
   {
       if(rmech.isChecked())
       {
txtRadio="MECH";
       }
   }
    Log.e("check","radio :"+txtRadio);
}
    private void movetoregister(View view) {
        int k=0;
        String user=txtph.getText().toString().trim();
        String pass=txtpass.getText().toString().trim();

        if(user.length()==0)
        {
            txtph.setError("fill column");
            k=1;
        }
        else
        {txtph.setError(null);
            k=0;
        }
        if(pass.length()==0)
        {
          k=1;
        }
        else
        {
            txtph.setError(null);
            k=0;
        }

        if(k==1)
        {

        }
        else if(txtRadio.equals("N"))
        {
            Snackbar.make(view,"Please select account type",700).show();
        }
        else if(txtRadio.equals("USR"))
        {
            fs.collection("ACCOUNT").whereEqualTo("CONTACT",user).whereEqualTo("PASSWORD",pass).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               for(DocumentChange ds:queryDocumentSnapshots.getDocumentChanges())
               {
                   if(ds.getType()==DocumentChange.Type.ADDED)
                   {
                       if(ds.getDocument().exists())
                       {
                           editor.putString("id",ds.getDocument().getId());
                           editor.putString("name",ds.getDocument().get("NAME").toString());
                           editor.putString("mobile",ds.getDocument().get("CONTACT").toString());
                           editor.putString("password",ds.getDocument().get("PASSWORD").toString());
                           editor.apply();
                           Intent in = new Intent(LOGIN.this, navigationd.class);
                           startActivity(in);
                           finish();
                       }

                   }

               }
                }
            });

        }
        else if(txtRadio.equals("MECH"))
        {

            fs.collection("WORKERS").whereEqualTo("PH",user).whereEqualTo("PASSWORD",pass).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(DocumentChange ds:queryDocumentSnapshots.getDocumentChanges())
                    {
                        if(ds.getType()==DocumentChange.Type.ADDED)
                        {
                            if(ds.getDocument().exists())
                            {
                                editor.putString("id",ds.getDocument().getId());
                                editor.putString("name",ds.getDocument().get("NAME").toString());
                                editor.putString("mobile",ds.getDocument().get("PH").toString());
                                editor.putString("password",ds.getDocument().get("PASSWORD").toString());
                                editor.apply();
                                Intent inn = new Intent(LOGIN.this,Mechnavigation.class);
                                startActivity(inn);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LOGIN.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                }
            });

        }



    }

    private void movetologin() {
        startActivity(new Intent(getApplicationContext(),Usermechanic.class));
    }

}



