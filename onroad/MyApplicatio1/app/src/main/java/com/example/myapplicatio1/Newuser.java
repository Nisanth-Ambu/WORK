package com.example.myapplicatio1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Maps;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Newuser extends AppCompatActivity {
    public Button BLl;private EditText name,address,contact,location,password,confirmpassword;
    String name1,address1,contact1,location1,password1,confirmpassword1;
    FirebaseFirestore fb;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        name=findViewById(R.id.editText4);
        address=findViewById(R.id.editText5);
        contact=findViewById(R.id.editText7);
        location=findViewById(R.id.editText8);
        fb=FirebaseFirestore.getInstance();
        password=findViewById(R.id.editText9);
        confirmpassword=findViewById(R.id.editText10);
        BLl=(Button)findViewById(R.id.button3);
        BLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoregister(v);
            }
        });
    }
    private void movetoregister(View v) {
        view=v;
        name1 = name.getText().toString().trim();
        address1 = address.getText().toString().trim();
        contact1 = contact.getText().toString().trim();
        location1 = location.getText().toString().trim();
        password1 = password.getText().toString().trim();
        confirmpassword1 = confirmpassword.getText().toString().trim();
        if (name1.equals(" ") || name1.isEmpty())
        {
            name.setError("fill column correctly");
        }
        else if (address1.equals(" ") || address1.isEmpty())
        {
            address.setError("FIll the address correctly");
        }
        else if (contact1.equals(" ") || contact1.isEmpty())
        {
            contact.setError("eneter your contact correctly");
        }
       else if (location1.equals(" ") || location1.isEmpty())
        {
            location.setError("enter your location correctly");
        }
        else if (password1.equals(" ") || password1.isEmpty())
        {
            password.setError("enter a valid password ");
        }
       else if(!password1.equals(confirmpassword1))
       {

       }
       else
        {
            Map<String, Object> note = new HashMap<>();
              note.put("NAME",name1);
              note.put("ADDRESS",address1);
              note.put("CONTACT",contact1);
              note.put("LOCATION",location1);
              note.put("PASSWORD",password1);

              fb.collection("ACCOUNT").document(contact1).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      Snackbar.make(view,"saved sucessfully",Snackbar.LENGTH_SHORT).show();
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {

                  }
              });


        }



    }
}







