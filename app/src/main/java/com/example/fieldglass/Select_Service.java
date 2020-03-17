package com.example.fieldglass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class Select_Service extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Declare buttons
    RadioButton rbMowing;
    RadioButton rbTedding;
    RadioButton rbRaking;
    RadioButton rbBaleSmall;
    RadioButton rbBaleLarge;
    RadioButton rbBaleWrap;
    RadioButton rbStack;

    RadioGroup radioGroupMow;
    RadioGroup radioGroupTed;
    RadioGroup radioGroupRake;
    RadioGroup radioGroupBale;
    RadioGroup radioGroupStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__service);

        rbMowing = findViewById(R.id.rbMowing);
        rbTedding =findViewById(R.id.rbTedding);
        rbRaking = findViewById(R.id.rbRaking);
        rbBaleSmall = findViewById(R.id.rbBaleSmall);
        rbBaleLarge = findViewById(R.id.rbBaleLarge);
        rbBaleWrap = findViewById(R.id.rbBaleWrap);
        rbStack = findViewById(R.id.rbStack);

        radioGroupMow = findViewById(R.id.radioGroupMow);
        radioGroupTed = findViewById(R.id.radioGroupTed);
        radioGroupRake = findViewById(R.id.radioGroupRake);
        radioGroupBale = findViewById(R.id.radioGroupBale);
        radioGroupStack = findViewById(R.id.radioGroupStack);



        //Floating action button to close new service
        FloatingActionButton fab = findViewById(R.id.saveFab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbBaleSmall.isChecked()){
                    global.baleType = "Small";
                }
                else if (rbBaleLarge.isChecked()){
                    global.baleType = "Large";
                }
                else {
                    global.baleType = "wrapped";
                }


                startActivity(new Intent(getApplicationContext()
                        , New_Service.class));


            }
        });
    }

}


