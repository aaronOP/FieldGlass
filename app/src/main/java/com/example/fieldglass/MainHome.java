package com.example.fieldglass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        FloatingActionButton fab = findViewById(R.id.fab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , New_Service.class));

                Toast.makeText(MainHome.this, "Clicked to add Service", Toast.LENGTH_SHORT).show();
            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext()
                                , MapsActivity.class));
                        overridePendingTransition(0, 0 );
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext()
                                , About.class));
                        overridePendingTransition(0, 0 );
                        return true;
                }
                return false;
            }
        });
    }
}

