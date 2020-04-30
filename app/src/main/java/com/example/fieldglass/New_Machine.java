package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class New_Machine extends AppCompatActivity {
    //set firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //end

    //Declare Firebase instance variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

    private EditText TextViewMan;
    private EditText TextViewMod;
    private EditText TextViewYear;
    private EditText TVCom;
    //Radio buttons
    RadioGroup RGTypeGroup;
    RadioButton RBVehicle;
    RadioButton RBImplement;
    RadioButton RBTrailer;

    RadioButton radioButton;
    RadioButton radioProblem;

    RadioGroup RGProbGroup;
    RadioButton RBEngine;
    RadioButton RBHydraulics;
    RadioButton RBCooling;
    RadioButton RBBody;
    RadioButton RBInterior;
    RadioButton RBElectrical;
    RadioButton RBOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__machine);

        //Find in XML
        TextViewMan = findViewById(R.id.TextViewMan);
        TextViewMod = findViewById(R.id.TextViewMod);
        TextViewYear = findViewById(R.id.TextViewYear);
        TVCom = findViewById(R.id.TVCom);

        //Radio buttons
        RGTypeGroup = findViewById(R.id.RGTypeGroup);
        RBVehicle = findViewById(R.id.RBVehicle);
        RBImplement = findViewById(R.id.RBImplement);
        RBTrailer = findViewById(R.id.RBTrailer);

        RGProbGroup = findViewById(R.id.RGProbGroup);
        RBEngine = findViewById(R.id.RBEngine);
        RBHydraulics = findViewById(R.id.RBHydraulics);
        RBCooling = findViewById(R.id.RBCooling);
        RBBody = findViewById(R.id.RBBody);
        RBInterior = findViewById(R.id.RBInterior);
        RBElectrical = findViewById(R.id.RBElectrical);
        RBOther = findViewById(R.id.RBOther);

        //Floating action button to save and close new machine
        FloatingActionButton fab = findViewById(R.id.fab_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if fields are valid
                if (TextViewMan.getText().toString().trim().equals("")|| (TextViewMod.getText().toString().trim().equals(""))|| (TextViewYear.getText().toString().trim().equals(""))){
                    //if empty show error message
                    Toast.makeText(New_Machine.this, "Please complete vehicle fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                //Get radio button for type
                int RadioId = RGTypeGroup.getCheckedRadioButtonId();
                //Get radio button using Id
                radioButton = (findViewById(RadioId));

                //Get radio button for issue
                int RadioProblemId = RGProbGroup.getCheckedRadioButtonId();
                //Get radio button using Id
                radioProblem = (findViewById(RadioProblemId));

                //Toast.makeText(New_Machine.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

                //Save to Database
                Map<String, Object> Machine = new HashMap<>();
                Machine.put("make", TextViewMan.getText().toString().trim());
                Machine.put("model", TextViewMod.getText().toString().trim());
                Machine.put("year", TextViewYear.getText().toString().trim());
                Machine.put("comment", TVCom.getText().toString().trim());
                Machine.put("type", radioButton.getText().toString().trim());
                Machine.put("issue", radioProblem.getText().toString().trim());


             db.collection("Machines")
                     .add(Machine)
                     .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(New_Machine.this, "Machine log saved", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(New_Machine.this, "Save failed", Toast.LENGTH_SHORT).show();
                    //Log.w(TAG, "Error adding document", e);
                }
            });

            startActivity(new Intent(getApplicationContext()
                        , Machine.class));
        }
    }

    });
    }
}





