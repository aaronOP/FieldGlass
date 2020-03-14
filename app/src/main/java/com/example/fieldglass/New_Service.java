package com.example.fieldglass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class New_Service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__service);

        FloatingActionButton fab = findViewById(R.id.fab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , MainHome.class));
                Toast.makeText(New_Service.this, "Clicked to cancel add Service", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
