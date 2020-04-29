package com.example.fieldglass;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Calendar extends AppCompatActivity {

    //Fire store connection
    //set database and invoke
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;

    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

    CalendarView calendarView;
    TextView CalDate;
    EditText CalEvent;
    EditText CalLocation;
    EditText CalTime;
    TimePickerDialog timePickerDialog;
    String AmPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        CalDate = findViewById(R.id.CalDate);
        CalTime = findViewById(R.id.CalTime);

        //OnChange listener for detecting change in date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Store date as string
                String date = (dayOfMonth + 1) + "-" + month + "-" + year;
                CalDate.setText(date);
            }
        });

        //on click listener for date picker
        CalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(Calendar.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm;
                        if (hourOfDay >= 12 ) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        CalTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);

                    }
                }, 0, 0, false);

                timePickerDialog.show();
            }
        });

        //Get text input
        CalEvent = findViewById(R.id.CalEvent);
        CalLocation =findViewById(R.id.CalLocation);

        //initialize fire base auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Save button Click
        Button saveevent = findViewById(R.id.CalButton);

        saveevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data to firestore
                if (!TextUtils.isEmpty(CalEvent.getText().toString())
                  && !TextUtils.isEmpty(CalDate.getText().toString())
                  && !TextUtils.isEmpty(CalLocation.getText().toString())
                  && !TextUtils.isEmpty(CalTime.getText().toString())){

                    //.trim to remove trailing or leading spaces
                    String event = CalEvent.getText().toString().trim();
                    String date = CalDate.getText().toString().trim();
                    String location= CalLocation.getText().toString().trim();
                    String time = CalTime.getText().toString().trim();

                    //Map to Database
                    Map<String, Object> Dates = new HashMap<>();
                    Dates.put("event", CalEvent.getText().toString().trim());
                    Dates.put("date", CalDate.getText().toString().trim());
                    Dates.put("location", CalLocation.getText().toString().trim());
                    Dates.put("time", CalTime.getText().toString().trim());

                    //Assign collection name
                    db.collection("Dates")
                            .add(Dates)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Calendar.this, "Event Saved", Toast.LENGTH_SHORT).show();
                                                                    }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    CalEvent.requestFocus();
                                    Toast.makeText(Calendar.this, "Failed to add event", Toast.LENGTH_SHORT).show();
                                }
                            });

                    //return user to about page
                    startActivity(new Intent(getApplicationContext()
                            ,About.class));
                    return;
                }

                else{
                    //Check to ensure inputs are not empty
                    Toast.makeText(Calendar.this, "Please Complete all fields", Toast.LENGTH_SHORT).show();
                }
              }
        });

        //Floating action button to close calendar
        FloatingActionButton fab = findViewById(R.id.fabC_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , About.class));
                Toast.makeText(Calendar.this, "Return to About", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


