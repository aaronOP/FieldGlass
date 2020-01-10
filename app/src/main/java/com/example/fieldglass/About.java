package com.example.fieldglass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class About extends AppCompatActivity {
    //declare update form details
    String FarmName, Address;
    int Phone;

    //Declare edit text fields
    EditText FarmNameInput;
    EditText AddressInput;
    EditText PhoneInput;

   //Declare reset and Save buttons
    Button saveBtn;
    Button resetBtn;

    //declare button for logout
    Button button;

    //Fire base auth
    FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //initilise edit text fields
        FarmNameInput = (EditText) findViewById(R.id.FarmName);
        AddressInput = (EditText) findViewById(R.id.Address);
        PhoneInput = (EditText) findViewById(R.id.Phone);

        resetBtn = (Button) findViewById(R.id.ResetBtn);
        saveBtn = (Button) findViewById(R.id.SaveBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address = AddressInput.getText().toString();
                FarmName = FarmNameInput.getText().toString();
                Phone = Integer.valueOf(PhoneInput.getText().toString());
            }
        });


        //find logout button
        button = (Button) findViewById(R.id.LogoutBtn);

        //capture button click to trigger log out
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log out
                if (user !=null && firebaseAuth!= null ) {
                    firebaseAuth.signOut();

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