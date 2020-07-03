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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//import com.example.crs.ADMIN;
//import com.example.crs.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;

public class navigationd extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    Eadapter eadapter;
    ArrayList<ExampleItem> emp;
    FirebaseFirestore frb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationd);
        recyclerView = findViewById(R.id.find_mech);
        drawerLayout = findViewById(R.id.Drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        frb=FirebaseFirestore.getInstance();
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowTitleEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emp=new ArrayList<>();
        eadapter=new Eadapter(emp);
        insertData();
        recyclerView.setAdapter(eadapter);
        toggle = new ActionBarDrawerToggle(navigationd.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
eadapter.setOnItemClickListener(new Eadapter.OnItemClickListener() {
    @Override
    public void onItemClick(int position) {
        String Name=emp.get(position).getName();
        String Locations=emp.get(position).getLocation();
        String Ph=emp.get(position).getPh();
        String mid=emp.get(position).getMid();
        new viewMechanic().insertDatas(Name,Locations,Ph,mid);
        startActivity(new Intent(getApplicationContext(),viewMechanic.class));
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
                        Intent intent = new Intent(navigationd.this, Searchmech.class);
                        startActivity(intent);
                        return true;
                    case R.id.Profile:
                        Intent intent2 = new Intent(navigationd.this, profileview.class);
                        startActivity(intent2);
                        return true;
                    case R.id.Aboutus:
                        Intent intent3 = new Intent(navigationd.this, Aboutus.class);
                        startActivity(intent3);
                        return true;
                }
                return true;
            }
        });

    }
    public void insertData()
    {
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
                   emp.add(new ExampleItem(mid,name1,loca,ph1));
               }
           }
       }
            eadapter.notifyDataSetChanged();

        }
    });

    }

}



