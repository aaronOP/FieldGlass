package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class Profile extends AppCompatActivity {
//    initialise fire store
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //collection reference
    private CollectionReference collectionReference = db.collection("Users");

//    declare Widgets
    private EditText nameInput;
    private EditText addressInput;
    private EditText townInput;
    private EditText postInput;
    private EditText phoneInput;
    private EditText emailInput;
    private EditText passInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Get UserID
        final String userid = user.getUid();

        //get widgets
        nameInput = findViewById(R.id.nameInput);
        addressInput = findViewById(R.id.addressInput);
        townInput = findViewById(R.id.townInput);
        postInput = findViewById(R.id.postInput);
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);

//        load data into form

        db.collection("Users")
                .whereEqualTo("userId", userid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                // retrieve data and store as strings
                                String dbName = document.getString("username");
                                String dbAddress = document.getString("address");
                                String dbTown = document.getString("city");
                                String dbPost = document.getString("post");
                                String dbPhone =document.getString("phone");
                                String dbEmail = document.getString("email");
                                String dbRole = document.getString("role");

                                //Show in edit text fields
                                nameInput.setText(dbName);
                                addressInput.setText(dbAddress);
                                townInput.setText(dbTown);
                                postInput.setText(dbPost);
                                phoneInput.setText(dbPhone);
                                emailInput.setText(dbEmail);

                                //Save Doc id
                                global.userDocID = document.getId();
                                global.user_role = dbRole;

                                global.city = townInput.getText().toString().trim();
                            }
                        } else {
                            Toast.makeText(Profile.this,"Error getting documents", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //find button
        Button save = findViewById(R.id.btnSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //get values
        String username = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String city = townInput.getText().toString().trim();
        String post = postInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        //save to fire store
        Map<String, Object> Users = new HashMap<>();
        Users.put("username", username);
        Users.put("address", address);
        Users.put("city", city);
        Users.put("post", post);
        Users.put("phone", phone);
        Users.put("email",email);
        Users.put("userId", userid);
        Users.put("role", global.user_role);


            db.collection("Users").document(global.userDocID)
            .set(Users)//merge options
            .addOnSuccessListener((new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    //Back to profile page
                    Toast.makeText(Profile.this, "Details Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Profile.this, About.class));
                }
            }))
                //onFailure Listener
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "This didn't Quite work, Update Failed", Toast.LENGTH_SHORT).show();

            }
        });

        }
        });
    }

}

