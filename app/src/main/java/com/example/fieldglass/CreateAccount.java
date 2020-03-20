package com.example.fieldglass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import utilities.DetailsApi;

public class CreateAccount extends AppCompatActivity {
    private Button loginButton;
    private Button createAccButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Fire store connection
    //set database and invoke
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //add users to collection in fire store
    private CollectionReference collectionReference = db.collection("Users");
    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private EditText userNameEditText;

    //    new details
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText postEditText;
    private EditText phoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //assign
        setContentView(R.layout.activity_create_account);

        //initialize fire base auth
        firebaseAuth = FirebaseAuth.getInstance();

        createAccButton = findViewById(R.id.create_acc_button);
        progressBar = findViewById(R.id.create_acc_Progress);
        passwordEditText = findViewById(R.id.password_acc);
        emailEditText = findViewById(R.id.email_account);
        userNameEditText = findViewById(R.id.username_acc);
//        new details
        addressEditText = findViewById(R.id.Address_account);
        cityEditText = findViewById(R.id.city_account);
        postEditText = findViewById(R.id.post_account);
        phoneEditText = findViewById(R.id.phone_account);

//create auth listen to pick up change in user login activity

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser !=null) {
                    //user is already logged in
                }else {
                    //no users logged in
            }
        }
    };
        //on click convert text to string
    createAccButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(emailEditText.getText().toString())
                && !TextUtils.isEmpty(passwordEditText.getText().toString())
                && !TextUtils.isEmpty(userNameEditText.getText().toString())
//                    new details
                && !TextUtils.isEmpty(addressEditText.getText().toString())
                && !TextUtils.isEmpty(cityEditText.getText().toString())
                && !TextUtils.isEmpty(postEditText.getText().toString())
                && !TextUtils.isEmpty(phoneEditText.getText().toString())){

                //.trim to remove trailing or leading spaces
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String username = userNameEditText.getText().toString().trim();
//                new details
                String address = addressEditText.getText().toString().trim();
                String city = cityEditText.getText().toString().trim();
                String post = postEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                createUserEmailAccount(email, password, username, address, city, post, phone);
            }else{//prevent empty entry
                Toast.makeText(CreateAccount.this,
                        "Empty Fields Not Allowed",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    });
    //Login Button to return to login page
        Button button = (Button) findViewById(R.id.login2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, Login.class));
            }
        });

    }

    private void createUserEmailAccount(final String email, String password, final String username, final String address, final String city, final String post, final String phone){
        if (!TextUtils.isEmpty(email)
            &&!TextUtils.isEmpty(password)
            &&!TextUtils.isEmpty(username)
            &&!TextUtils.isEmpty(address)
            &&!TextUtils.isEmpty(city)
            &&!TextUtils.isEmpty(post)
            &&!TextUtils.isEmpty(phone)) {

            progressBar.setVisibility(View.VISIBLE);
            //create record
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //take user to home ActivityMain
                                currentUser = firebaseAuth.getCurrentUser();
                                //assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                //user map allows creation of user in user collection create user map to create  profile content
                                //by here user is created hence user ID can be gathered

                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);
                                userObj.put("address", address);
                                userObj.put("city", city );
                                userObj.put("email", email);
                                userObj.put("post", post);
                                userObj.put("phone", phone);
                                userObj.put("Role", "User");

                                //save to fire store db
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    String name = task.getResult()
                                                                            .getString("username");

                                                                    DetailsApi detailsApi = DetailsApi.getInstance(); //global api
                                                                    detailsApi.setUsername(name);
                                                                    detailsApi.setUserId(currentUserId);

                                                                    //if successful navigate to main landing page "MainActivity"
                                                                    Intent intent = new Intent(CreateAccount.this,
                                                                            MainHome.class);
                                                                    intent.putExtra("username", name);
                                                                    intent.putExtra("USerId", currentUserId);
                                                                    startActivity(intent);

                                                                }else {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CreateAccount.this, "Try Again", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateAccount.this, "Users Exists", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

        }else {

        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        // on start to check if user is signed in
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);

    }
}