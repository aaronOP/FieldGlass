package com.example.fieldglass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

public class About extends AppCompatActivity {

    //firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference eventRef = db.collection("Dates").document("Big code demo");

    FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private StorageReference storageReference;


    //Nav Bar
    public void homeActivity(View view){
        startActivity(new Intent(About.this, MainHome.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
    public void mapsActivity(View view){
        startActivity(new Intent(About.this, Dashboard.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    //WebLinks
    public void WebClicked(View view) {
        Intent ExternalIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fieldglass.farm/"));
        startActivity(ExternalIntent);
    }


    //declare button for logout
    Button button;
    //Show name
    TextView TVNameEvent;


    //Test click
    public void cardviewClicked (View view){

        Toast.makeText(About.this, "Test clicked!",
                Toast.LENGTH_LONG).show();
    }

    //To profile page
    public void profileClicked(View view ) {
        Intent intent = new Intent(About.this, Profile.class);
        startActivity(intent);
        Toast.makeText(About.this, "Profile Clicked",
                Toast.LENGTH_SHORT).show();
    }
    //To prices page
    public void pricesClicked(View view ) {
        Intent intent = new Intent(About.this, Prices.class);
        startActivity(intent);
        Toast.makeText(About.this, "Prices Clicked",
                Toast.LENGTH_SHORT).show();
    }

    //To weather page
    public void weatherClicked(View view ) {
        Intent intent = new Intent(About.this, WeatherApp.class);
        startActivity(intent);
        Toast.makeText(About.this, "WeatherApp Clicked",
                Toast.LENGTH_SHORT).show();
    }

    //To weather page
    public void calcClicked(View view ) {
        Intent intent = new Intent(About.this, Calculator.class);
        startActivity(intent);
        Toast.makeText(About.this, "Calculator Clicked",
                Toast.LENGTH_SHORT).show();
    }

    //To Machine page
    public void machineClicked(View view ) {

        if (global.user_role.equals("Manager")) {

            Intent intent = new Intent(About.this, Machine.class);
            startActivity(intent);
            Toast.makeText(About.this, "Machines Clicked",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(About.this, "Managers Access only!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //To calendar page
    public void CalendarClicked(View view ) {
        Intent intent = new Intent(About.this, Calendar.class);
        startActivity(intent);
        Toast.makeText(About.this, "Calendar Clicked",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //username
        TVNameEvent = findViewById(R.id.TVNameEvent);
        TVNameEvent.setText(global.Dispalyname + "'s" + " Events");

        //Get user city
        db.collection("Users")
                .whereEqualTo("userId", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                // retrieve data and store as strings
                                String dbName = document.getString("username");
                                String dbAddress = document.getString("address");
                                String dbCity = document.getString("city");
                                String dbPost = document.getString("post");
                                String dbPhone =document.getString("phone");
                                String dbEmail = document.getString("email");
                                String dbRole = document.getString("role");

                                //Save Doc id
                                global.userDocID = document.getId();
                                global.user_role = dbRole;
                                global.city = dbCity;
                                global.username = dbName;
                            }
                        } else {
                        }
                    }
                });

        //find logout button
        button = (Button) findViewById(R.id.LogoutBtn);
        //capture button click to trigger log out
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log out
                if (user !=null && firebaseAuth!= null ) {
                    firebaseAuth.signOut();
                    //Wipe global variable
                    global.loggedInID = null;

                    startActivity(new Intent(About.this,
                            Primary.class));
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}