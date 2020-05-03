package com.example.fieldglass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Machine extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference machineRef = db.collection("Machines");
    private MachineAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);

        //Instructions
        Toast.makeText(Machine.this, "Swipe to Delete!", Toast.LENGTH_SHORT).show();

        StartRecyclerView();

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

    private void StartRecyclerView(){
        Query query = machineRef.orderBy("make",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MachineItem> options = new FirestoreRecyclerOptions.Builder<MachineItem>()
                .setQuery(query,MachineItem.class)
                .build();

        //adapter variable

        adapter = new MachineAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.MachineRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deletecard(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
