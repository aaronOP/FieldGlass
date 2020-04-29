package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainHome extends AppCompatActivity {
    //set firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Declare Firebase instance variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private CollectionReference collectionReference = db.collection("Users");
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private TaskItemRecyclerAdapter taskItemRecyclerAdapter;
    private List<TaskItem> TaskItemList;
    FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

    //Nav Bar
    public void infoActivity(View view){
        startActivity(new Intent(MainHome.this, About.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
    public void mapsActivity(View view){
        startActivity(new Intent(MainHome.this, Dashboard.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        //Recycler View
        TaskItemList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));

        //Firebase User
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        //Check User Role
        db.collection("Users")
                .whereEqualTo("userId", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                String role = document.getString("role");
                                    global.user_role = role;
                                    //for user roles, in the on create set above line to visible for managers.
                                Toast.makeText(MainHome.this, "User Role = " + global.user_role, Toast.LENGTH_SHORT).show();
                            }
                        } else {

                        }
                    }
                });

        //Create Service button
        FloatingActionButton fab = findViewById(R.id.fab_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHome.this, New_Service.class));

                Toast.makeText(MainHome.this, "Clicked to add Service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        db.collection("orders")
                .whereEqualTo("clientId", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                String docID= document.getId();
                                TaskItem taskItem = document.toObject(TaskItem.class);
                                        //.(docID);
                                //Toast for docID
                                //Toast.makeText(MainHome.this, docID, Toast.LENGTH_SHORT).show();
                                TaskItemList.add(taskItem);
                            }

                            taskItemRecyclerAdapter = new TaskItemRecyclerAdapter(getApplicationContext(), TaskItemList);
                            recyclerView.setAdapter(taskItemRecyclerAdapter);
                            taskItemRecyclerAdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                });

    }
    }