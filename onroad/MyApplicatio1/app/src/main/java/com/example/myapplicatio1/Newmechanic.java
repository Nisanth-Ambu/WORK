package com.example.myapplicatio1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Newmechanic extends AppCompatActivity {
    public Button bp;
    Button pc;
    FirebaseFirestore fs;
    int b=0;
EditText txtname,txtaddress,txtloca,txtexp,txtph,txtcrntwrk,txtpass,txtconpass;
String name=null,address=null,loca=null,exp=null,mob=null,crntwrk=null,pass=null,conpass=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmechanic);
        bp=(Button)findViewById(R.id.button4);
        fs=FirebaseFirestore.getInstance();
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoregister();
            }
        });
        txtname=findViewById(R.id.editText11);
        txtaddress=findViewById(R.id.editText12);
        txtloca=findViewById(R.id.editText13);
        txtexp=findViewById(R.id.editText14);
        txtph=findViewById(R.id.editText15);
        txtcrntwrk=findViewById(R.id.editText16);
        txtpass=findViewById(R.id.editText19);
        txtconpass=findViewById(R.id.editText20);
    }
    private void movetoregister()
    {
             b=0;
        name = txtname.getText().toString().trim();
        address = txtaddress.getText().toString().trim();
        loca = txtloca.getText().toString().trim();
        exp = txtexp.getText().toString().trim();
        mob = txtph.getText().toString().trim();
        crntwrk = txtcrntwrk.getText().toString().trim();
        pass = txtpass.getText().toString().trim();
        conpass = txtconpass.getText().toString().trim();
        if (name.length() == 0) {
            b = 1;
            txtname.setError("fill name correctly");

        } else {
            b = 0;
            txtname.setError(null);
        }
        if (address.length() == 0) {
            b = 1;
            txtname.setError("fill address correctly");

        } else {
            b = 0;
            txtname.setError(null);
        }
        if (loca.length() == 0) {
            b = 1;
            txtname.setError("give proper location");
        } else {
            b = 0;
            txtname.setError(null);
        }
        if (exp.length() == 0)
        {
            b = 1;
            txtname.setError("give experience");
    }
        else
        {
            b=0;
            txtname.setError(null);
        }

        if (mob.length()==0)
        {
            b=1;
            txtname.setError("give 10digit mobile number");
        }
        else
        {
            b=0;
            txtph.setError(null);
            fs.collection("WORKERS").whereEqualTo("PH",mob).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               for(DocumentChange ds:queryDocumentSnapshots.getDocumentChanges())
               {
                   if(ds.getType()==DocumentChange.Type.ADDED)
                   {
                    if(ds.getDocument().exists())
                    {
                       b=1;
                       txtph.setError("Already exist");
                    }

                   }
               }
                }
            });

        }
        if (crntwrk.length()==0)
        {
            b=1;
            txtname.setError("currently working");
        }
        else
        {
            b=0;
            txtname.setError(null);
        }
        if (pass.length()==0)
        {
            b=1;
            txtname.setError("give proper password");
        }
        else
        {
            b=0;
            txtname.setError(null);
        }
        if (conpass.length()==0 || !conpass.equals(pass))
        {
            b=1;
            txtname.setError("confirm password");
        }
        else
        {
            b=0;
            txtname.setError(null);
        }

        if(b==0)
        {
            Map<String,Object> map=new HashMap<>();
            map.put("NAME",name);
            map.put("ADDRESS",address);
            map.put("LOCATION",loca);
            map.put("EXP",exp);
            map.put("PH",mob);
            map.put("CURRENTLY_WORK",crntwrk);
            map.put("PASSWORD",pass);
            fs.collection("WORKERS").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful())
               {
                   Toast.makeText(Newmechanic.this, "Svaed sucessfully", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(getApplicationContext(),LOGIN.class));
                   finish();
               }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
    }
        else
        {

        }
        Intent intent=new Intent(Newmechanic.this,LOGIN.class);
        startActivity(intent);

    }
}


