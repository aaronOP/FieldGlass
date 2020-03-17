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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//new firebase import
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class New_Service extends AppCompatActivity {

    //set firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //end

    private static final String TAG = "New_Service";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView Service;
    private TextView Client;
    private TextView Machines;
    private TextView Location;
    private TextView Date;
    private EditText Comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__service);

        //declare  them
        //.settext of global variable.

        //Floating action button to close new service
        FloatingActionButton fab = findViewById(R.id.fab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , MainHome.class));
                //Save to Database

                Map<String, Object> orders = new HashMap<>();
                orders.put("service", global.user_Service);
                orders.put("client", global.user_Client);
                orders.put("machines", global.user_Machines);
                orders.put("location", global.user_Location);
                orders.put("Date", global.user_Date);
                orders.put("Time", global.user_Time);
                orders.put("comment",global.user_Comment);

                if (global.user_Service == null){
                    //Check to ensure inputs are not empty
                    Toast.makeText(New_Service.this, "Please Add a Service", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (global.user_Client == null){
                    return;
                }
                else if (global.user_Machines == null){
                    return;
                }
                else if (global.user_Location == null){
                    return;
                }
                else if (global.user_Date == null){
                    return;
                }
                else if (global.user_Time == null){
                    return;
                }
                else {
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
                }

            }
        });

        //Click tvtask to open Select_Service




        mDisplayDate = (TextView) findViewById(R.id.tvDate);
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

