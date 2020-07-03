package com.example.myapplicatio1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Searchmech extends AppCompatActivity {

    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    ArrayList<BookItem> emp;
    FirebaseDatabase rdb;
    String Dates,Uid,Wid,Jid;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmech);
        recyclerView=findViewById(R.id.uhistoryrecycle);
        rdb=FirebaseDatabase.getInstance();
        sharedPreferences=getSharedPreferences("Temp",MODE_PRIVATE);
        Uid=sharedPreferences.getString("id",null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emp=new ArrayList<>();
        bookAdapter=new BookAdapter(emp);
        recyclerView.setAdapter(bookAdapter);
        getAllnnedehistory();
bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String Name=emp.get(position).getUname();
                String Locations=emp.get(position).getLocation();
                String Ph=emp.get(position).getUcontact();
                String jid=emp.get(position).getJid();
                String uid=emp.get(position).getUid();
                String wid=emp.get(position).getWid();
                String date=emp.get(position).getDdate();
                String type=emp.get(position).getLoctype();
                String status=emp.get(position).getStatus();
                new SingleWorkActivity("USR",Name,Locations,Ph,jid,uid,wid,date,type,status);
                startActivity(new Intent(getApplicationContext(),SingleWorkActivity.class));

            }

            @Override
            public void onCallClick(int position) {
                String contact=emp.get(position).getUcontact().trim();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +contact));
                startActivity(intent);
            }

            @Override
            public void onLocateClick(int position) {
                String type=emp.get(position).getLoctype().trim();
                if(type.equals("Current"))
                {
                    String location=emp.get(position).getLocation();
                    Log.e("eeee","location :"+location);
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location + "&mode=b");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);

                }
            }
        });
    }
    public void getAllnnedehistory()
    {
        rdb.getReference().child("BOOK").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emp.clear();
                for(DataSnapshot dbDate : snapshot.getChildren())
                {
                    Dates =dbDate.getKey();
                    for (DataSnapshot dbWid : dbDate.child(Uid).getChildren())
                    {
                        Wid=dbWid.getKey();
                        for(DataSnapshot dbid : dbWid.getChildren())
                        {
                            if(dbid.exists()) {
                                Jid = dbid.getKey();
                                BookItem bk=dbid.getValue(BookItem.class);
                                String Uname=bk.getUname();
                                String Wname=bk.getWname();
                                String Ucontact=bk.getUcontact();
                                String Wcontact=bk.getWcontact();
                                String Loctype=bk.getLoctype();
                                String Location=bk.getLocation();
                                String status=bk.getStatus();

                                    emp.add(new BookItem(Dates, Uid, Wid, Jid, Wname, Uname, Wcontact, Ucontact, Loctype, Location, status));

                            }
                        }
                    }
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
