package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import utilities.DetailsApi;


public class Login extends AppCompatActivity {
    private Button loginButton;
    private Button createAccButton;
    private Button forgotPass;

    //username and password from xml
    private AutoCompleteTextView emailAddress;
    private EditText password;
    private ProgressBar progressBar;

    //Declare Firebase instance variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;


    //set firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //retrieve from .xml file
        progressBar = findViewById(R.id.login_Progress);
        firebaseAuth = firebaseAuth.getInstance();
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.email_sign_in_button);
        createAccButton = findViewById(R.id.create_acc_button_login);
        forgotPass = findViewById(R.id.forgotPass);

        //create account button activity
        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, CreateAccount.class));
            }
        });

        //login button activity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmailPasswordUser(emailAddress.getText().toString().trim(),
                        password.getText().toString().trim());
            }
        });

        //Reset Password button
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(Login.this, resetPassword.class)));
            }
        });
    }

    private void loginEmailPasswordUser(String email, String pwd) {

        //progressbar visible on click
        progressBar.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(email)
            && !TextUtils.isEmpty(pwd)) {
            firebaseAuth.signInWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            final String currentUserId = user.getUid();

                            collectionReference
                                    .whereEqualTo("userId", currentUserId)
                                    //capture entry
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                            @Nullable FirebaseFirestoreException e) {

                                            if (e !=null) {
                                            }
                                            //Global Api can be used to call userId + username
                                            assert queryDocumentSnapshots != null;
                                            if (!queryDocumentSnapshots.isEmpty()){

                                                //progressbar
                                                progressBar.setVisibility(View.INVISIBLE);
                                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                                    DetailsApi detailsApi = DetailsApi.getInstance();
                                                    detailsApi.setUsername(snapshot.getString("username"));
                                                    detailsApi.setUserId(snapshot.getString("userId"));

                                                    //Go to list activity
                                                    startActivity(new Intent(Login.this,
                                                            MainActivity.class));
                                                }

                                            }

                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressbar
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });

        }else{
            //progressbar
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Login.this,
                    "Please enter a username and password!",
            Toast.LENGTH_LONG)
            .show();
        }
    }
}
