package com.example.fieldglass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class About extends AppCompatActivity {


    //declare button for logout
    Button button;

    //Fire base auth
    FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    public void cardviewClicked (View view){
        Toast.makeText(About.this, "Test clicked!",
                Toast.LENGTH_LONG).show();
    }


    public void profileClicked(View view ) {
        Intent intent = new Intent(About.this, Profile.class);
        startActivity(intent);
    }

    public void pricesClicked(View view ) {
        Intent intent = new Intent(About.this, Prices.class);
        startActivity(intent);
        Toast.makeText(About.this, "Prices Clicked",
                Toast.LENGTH_SHORT).show();
    }

    public void weatherClicked(View view ) {
        Intent intent = new Intent(About.this, WeatherApp.class);
        startActivity(intent);
        Toast.makeText(About.this, "WeatherApp Clicked",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        //find logout button
        button = (Button) findViewById(R.id.LogoutBtn);
        //capture button click to trigger log out
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log out
                if (user !=null && firebaseAuth!= null ) {
                    firebaseAuth.signOut();
                    //Wipe global variable
                    global.loggedInID = null;

                    startActivity(new Intent(About.this,
                            Primary.class));
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set about selected
        bottomNavigationView.setSelectedItemId(R.id.about);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext()
                                , Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.about:
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}