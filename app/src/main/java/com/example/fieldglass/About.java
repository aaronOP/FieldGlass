package com.example.fieldglass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fieldglass.models.EventModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

public class About extends AppCompatActivity {

    //Recyclerview
    private RecyclerView mfirestoreEvent;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    //firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        //Recycler view for events
        firebaseAuth = FirebaseAuth.getInstance();
        mfirestoreEvent = findViewById(R.id.firestoreEvent);

        //Query
        Query query = db.collection("Dates");

        //Recycler options

        FirestoreRecyclerOptions<EventModel> options = new FirestoreRecyclerOptions.Builder<EventModel>()
                .setQuery(query, EventModel.class)
                .build();

        //Recycler adapter
        adapter = new FirestoreRecyclerAdapter<EventModel, EventViewHolder>(options) {
            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
                return new EventViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i, @NonNull EventModel eventModel) {
                eventViewHolder.list_date.setText(eventModel.getDate());
                eventViewHolder.list_event.setText(eventModel.getEvent());
                eventViewHolder.list_location.setText(eventModel.getLocation());
                eventViewHolder.list_time.setText(eventModel.getTime());
            }
        };

        //Viewholder class
        //firestore list
        mfirestoreEvent.setHasFixedSize(true);
        mfirestoreEvent.setLayoutManager(new LinearLayoutManager(this));
        mfirestoreEvent.setAdapter(adapter);
    }

    private class EventViewHolder extends RecyclerView.ViewHolder {


        private TextView list_date;
        private TextView list_location;
        private TextView list_time;
        private TextView list_event;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            //get from item view
            list_event = itemView.findViewById(R.id.list_event);
            list_date = itemView.findViewById(R.id.list_date);
            list_location = itemView.findViewById(R.id.list_location);
            list_time = itemView.findViewById(R.id.list_time);
        }
    }

    @Override
    protected void  onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}


