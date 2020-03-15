package com.example.fieldglass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Comment extends AppCompatActivity {

    EditText editText_Note;
    String Note;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        //Floating action button to close new service
        FloatingActionButton fab = findViewById(R.id.fab_btn);
        editText_Note = findViewById(R.id.editText_Note);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , New_Service.class));
                Toast.makeText(Comment.this, "Note Saved", Toast.LENGTH_SHORT).show();
            }

        });


    }
}