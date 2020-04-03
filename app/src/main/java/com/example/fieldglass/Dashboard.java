package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    //Nav Bar
    public void homeActivity(View view){
        startActivity(new Intent(Dashboard.this, MainHome.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
    public void infoActivity(View view){
        startActivity(new Intent(Dashboard.this, About.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);





    }
}


