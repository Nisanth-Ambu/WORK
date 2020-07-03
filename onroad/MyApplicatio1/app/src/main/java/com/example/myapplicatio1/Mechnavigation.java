package com.example.myapplicatio1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Mechnavigation extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    BookAdapter eadapter;
    ArrayList<BookItem> emp;
    FirebaseFirestore frb;
    FirebaseDatabase rdb;
    String Dates,Uid,Wid,Jid;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechnavigation);
        recyclerView = findViewById(R.id.find_mech);
        drawerLayout = findViewById(R.id.Drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("Temp",MODE_PRIVATE);
        Wid=sharedPreferences.getString("id",null);
        rdb=FirebaseDatabase.getInstance();
        frb= FirebaseFirestore.getInstance();
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowTitleEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emp=new ArrayList<>();
        eadapter=new BookAdapter(emp);
        insertData();
        recyclerView.setAdapter(eadapter);
        toggle = new ActionBarDrawerToggle(Mechnavigation.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        eadapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
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
                new SingleWorkActivity("MECH",Name,Locations,Ph,jid,uid,wid,date,type,status);
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
        //navigationView = findViewById(R.id.navigation_view);
        //View naview = navigationView.inflateHeaderView(R.layout.header);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(getApplicationContext(), viewMechanic.class);
                        startActivity(intent);
                        return true;

                    case R.id.Profile:
                        Intent intent2 = new Intent(getApplicationContext(), profileview.class);
                        startActivity(intent2);
                        return true;
                    case R.id.Aboutus:
                        Intent intent3 = new Intent(getApplicationContext(), Aboutus.class);
                        startActivity(intent3);
                        return true;
                }
                return true;
            }
        });
    }
    public void insertData()
    {
        /*
        frb.collection("WORKERS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange ds:queryDocumentSnapshots.getDocumentChanges())
                {
                    if(ds.getType()==DocumentChange.Type.ADDED)
                    {
                        if(ds.getDocument().exists())
                        { String mid=ds.getDocument().getId();
                            String name1=ds.getDocument().get("NAME").toString();
                            String loca=ds.getDocument().get("LOCATION").toString();
                            String ph1=ds.getDocument().get("PH").toString();
                          //  emp.add(new ExampleItem(mid,name1,loca,ph1));
                        }
                    }
                }

                eadapter.notifyDataSetChanged();

            }
        });

         */
rdb.getReference().child("BOOK").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        emp.clear();
        for(DataSnapshot dbDate : snapshot.getChildren())
        {
            Dates =dbDate.getKey();
for (DataSnapshot dbUid : dbDate.getChildren())
{
    Uid=dbUid.getKey();
    for(DataSnapshot dbid : dbUid.child(Wid).getChildren())
    {
            if(dbid.exists()) {
                Jid = dbid.getKey();
                /*
                   bk.setUname(uname);
    bk.setWname(Name);
    bk.setUcontact(ucontact);
    bk.setWcontact(Ph);
    bk.setLoctype(txtRadio);
    bk.setLocation(location);
                 */
                BookItem bk=dbid.getValue(BookItem.class);
                String Uname=bk.getUname();
                String Wname=bk.getWname();
                String Ucontact=bk.getUcontact();
                String Wcontact=bk.getWcontact();
                String Loctype=bk.getLoctype();
                String Location=bk.getLocation();
                String status=bk.getStatus();
                if(status.equals("N/A")) {
                    emp.add(new BookItem(Dates, Uid, Wid, Jid, Uname, Wname, Ucontact, Wcontact, Loctype, Location, status));
                }
            }
    }
}
        }
        eadapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


/*

        for(int n=0;n<10;n++)
        {
            emp.add(new ExampleItem(""+n,""+n,""+n));

        }
        eadapter.notifyDataSetChanged();

 */
    }
}
