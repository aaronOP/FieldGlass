package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Machine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);

        //Create Machine log button
        FloatingActionButton fab = findViewById(R.id.fabM_Btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Machine.this, New_Machine.class));

                Toast.makeText(Machine.this, "Clicked to add Service", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
