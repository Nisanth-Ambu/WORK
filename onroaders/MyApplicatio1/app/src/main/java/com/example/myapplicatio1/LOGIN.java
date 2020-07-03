package com.example.myapplicatio1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
  RadioGroup rkd;
  String txtRadio=null;
  FirebaseFirestore fs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
reg=findViewById(R.id.textView2);
txtph=findViewById(R.id.editText);
txtpass=findViewById(R.id.editText2);
fs=FirebaseFirestore.getInstance();
rusr=findViewById(R.id.ruser);
rmech=findViewById(R.id.rmech);
        BL = (Button) findViewById(R.id.button);
        BL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetologin();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movetoregister(view);
            }
        });
    }
public void radioder(View v)
{
   if(v.getId()==rusr.getId())
   {
       if(rusr.isChecked())
       {
          txtRadio="USR";
       }
   }
   else if(v.getId()==rmech.getId())
   {
       if(rmech.isChecked())
       {
txtRadio="MECH";
       }
   }
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
        else if(txtRadio.equals(null))
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

                       }

                   }

               }
                }
            });





        }
        else if(txtRadio.equals("MECH"))
        {
            Intent inn = new Intent(LOGIN.this,Usermechanic.class);
            startActivity(inn);
        }



    }



    private void movetologin() {


        Intent in = new Intent(LOGIN.this, navigationd.class);
        startActivity(in);

    }

}



