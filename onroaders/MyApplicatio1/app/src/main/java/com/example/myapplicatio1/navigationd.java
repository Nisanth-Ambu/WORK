package com.example.myapplicatio1;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;

public class navigationd extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    Eadapter eadapter;
    ArrayList<ExampleItem> emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationd);
        recyclerView = findViewById(R.id.find_mech);
        drawerLayout = findViewById(R.id.Drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
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

        //navigationView = findViewById(R.id.navigation_view);
        //View naview = navigationView.inflateHeaderView(R.layout.header);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(navigationd.this, viewMechanic.class);
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
    for(int n=0;n<10;n++)
    {
        emp.add(new ExampleItem(""+n,""+n,""+n));

    }
    eadapter.notifyDataSetChanged();
}

}



