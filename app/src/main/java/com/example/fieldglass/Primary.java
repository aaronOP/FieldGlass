package com.example.fieldglass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Primary extends AppCompatActivity {

    //Declare Firebase instance variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;


    //set firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        getStartedButton = findViewById(R.id.startButton);

        mAuth = FirebaseAuth.getInstance();

        //Check if current user session running
        try{
            FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
            global.loggedInID = user.getUid();
        }

        catch (Exception e){
            //No user session on firebase, user must sign in
        }

        if (global.loggedInID  == null){
            //No User Logged In
        }
        else{
            startActivity(new Intent(Primary.this,
                    MainHome.class));
        }

        getStartedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //takes to login page
                startActivity(new Intent(Primary.this,
                        Login.class));

                }
            }
        );
    }
}
