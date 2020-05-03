package com.example.fieldglass;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Order_Update extends AppCompatActivity {
    //set firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("orders");
    //end

    //Declare Firebase instance variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "New_Service";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText tvTask;
    private TextView tvClient;
    private EditText tvAddress;
    private EditText tvDate;
    private EditText Comment;
    private EditText acreNum;

    private Button btnMinus;
    private Button btnAddition;
    private String AcreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__update);

        //plus minus button for acre
        //Acre Counter
        btnAddition = findViewById(R.id.btnAddition);
        btnMinus = findViewById(R.id.btnMinus);
        acreNum = findViewById(R.id.acreNum);

        btnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_acre = Integer.parseInt(acreNum.getText().toString().trim());
                new_acre +=1;
                acreNum.setText(String.valueOf(new_acre));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_acre = Integer.parseInt(acreNum.getText().toString().trim());
                new_acre -=1;
                acreNum.setText(String.valueOf(new_acre));
            }
        });

        mDisplayDate = findViewById(R.id.tvDate);
        //create calender object to get current day, month, year
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                int year = cal.get(java.util.Calendar.YEAR);
                int month = cal.get(java.util.Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Order_Update.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //Initialise onDate set listenerObject
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"OnDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year );
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        //retrieve doc id
        Intent intent = getIntent();

        String docID = intent.getStringExtra("Doc ID");

        //show doc ID
        Toast.makeText(Order_Update.this,"OrderUpdate ID " + docID, Toast.LENGTH_SHORT).show();

        //Get Document data
        DocumentReference docRef = db.collection("orders")
                .document(global.ODocID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //retrieve and store as string
                    String dbTask = document.getString("service");
                    String dbClient = document.getString("client");
                    String dbDate = document.getString("date");
                    String dbLocation = document.getString("location");
                    String dbComment =document.getString("comment");
                    String dbAcre = document.getString("acre");

                    //show in text fields
                    tvTask.setText(dbTask);
                    tvClient.setText(dbClient);
                    tvAddress.setText(dbLocation);
                    tvDate.setText(dbDate);
                    Comment.setText(dbComment);
                    acreNum.setText(dbAcre);


                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //Find
        tvTask = findViewById(R.id.tvTask);
        tvClient = findViewById(R.id.tvClient);
        tvAddress = findViewById(R.id.tvAddress);
        tvDate = findViewById(R.id.tvDate);
        Comment = findViewById(R.id.Comment);
        acreNum = findViewById(R.id.acreNum);

        //Floating action button to close new service
        FloatingActionButton fab = findViewById(R.id.fab_update);
        fab.setOnClickListener(new View.OnClickListener() {

            //save on close
            @Override
            public void onClick(View v) {
                if (tvTask.getText().toString().trim().equals("")||
                        (tvDate.getText().toString().trim().equals(""))||
                        (acreNum.getText().toString().trim().equals(""))){

                    //Check to ensure inputs are not empty
                    Toast.makeText(Order_Update.this, "Please Complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String task = tvTask.getText().toString().trim();
                    String client = tvClient.getText().toString().trim();
                    String address = tvAddress.getText().toString().trim();
                    String date = tvDate.getText().toString().trim();
                    String comment = Comment.getText().toString().trim();
                    String acre = acreNum.getText().toString().trim();

                    //Save updated values
                    Map<String, Object> orders = new HashMap<>();
                    orders.put("service", tvTask.getText().toString().trim());
                    orders.put("client", tvClient.getText().toString().trim());
                    orders.put("clientId", user.getUid());
                    orders.put("location", tvAddress.getText().toString().trim());
                    orders.put("date", tvDate.getText().toString().trim());
                    orders.put("comment",Comment.getText().toString().trim());
                    orders.put("acre", acreNum.getText().toString().trim());

                    Toast.makeText(Order_Update.this, "Service Accepted", Toast.LENGTH_SHORT).show();

                    db.collection("orders").document(global.ODocID)
                            .set(orders)//merge options
                            .addOnSuccessListener((new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    //Back to about page
                                    Toast.makeText(Order_Update.this, "Details Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Order_Update.this, About.class));
                                }
                            }))
                            //onFailure Listener
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Order_Update.this, "This didn't worked, Update Failed", Toast.LENGTH_SHORT).show();

                                }
                            });
                }


            }
        });}}