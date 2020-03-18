package com.example.fieldglass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//new firebase import
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import org.w3c.dom.Text;

public class New_Service extends AppCompatActivity {

    //set firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
    private TextView tvTask;
    private TextView tvClient;
    private TextView tvAddress;
    private TextView tvDate;
    private EditText Comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__service);

        //Client
        db.collection("Users")
                .whereEqualTo("userId", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                String userName = document.getString("username");
                                tvClient.setText(userName);
                            }
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Find
        tvTask = findViewById(R.id.tvTask);
        tvClient = findViewById(R.id.tvClient);
        tvAddress = findViewById(R.id.tvAddress);
        tvDate = findViewById(R.id.tvDate);
        Comment = findViewById(R.id.Comment);

        //Display Selections
        if (global.baleType != "" && global.mow != "" && global.ted != "" && global.rake != "" && global.stack != "") {
            //Don't change TextView
            tvTask.setText("+ Add Service");
        }
        else{
            //Change TextView
            tvTask.setText(global.mow + "   " + global.ted + "   " + global.rake + "   "  + global.baleType + "   " + global.stack);
        }



        //Floating action button to close new service
        FloatingActionButton fab = findViewById(R.id.fab_btn);
        fab.setOnClickListener(new View.OnClickListener() {

            //save on close
            @Override
            public void onClick(View v) {
                if (tvTask.getText().toString().trim().equals("+ Add Service")|| (tvDate.getText().toString().trim().equals("+ Add Start Date"))){
                    //Check to ensure inputs are not empty
                    Toast.makeText(New_Service.this, "Please Complete all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(New_Service.this, "Service Accepted", Toast.LENGTH_SHORT).show();
                }

                //Save to Database
                Map<String, Object> orders = new HashMap<>();
                orders.put("service", tvTask.getText().toString().trim());
                orders.put("client", tvClient.getText().toString().trim());
                orders.put("location", tvAddress.getText().toString().trim());
                orders.put("date", tvDate.getText().toString().trim());
                orders.put("comment",Comment.getText().toString().trim());

                    db.collection("orders")
                            .add(orders)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });


                startActivity(new Intent(getApplicationContext()
                        , MainHome.class));
            }
        });

        mDisplayDate = findViewById(R.id.tvDate);
        //create calender object to get current day, month, year
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        New_Service.this, android.R.style.Theme_Holo_Dialog_MinWidth,
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
        TextView textView = findViewById(R.id.tvTask);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , Select_Service.class));

            }
        });
    }
}

