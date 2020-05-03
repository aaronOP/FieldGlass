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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Order extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("orders");
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toast.makeText(Order.this,"Swipe to Delete, tap to update",Toast.LENGTH_LONG).show();
        //Adapter
        startOrderRecycler();

        //Floating action button to close Calculator
        FloatingActionButton fab = findViewById(R.id.fabOrd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext()
                        , About.class));
                Toast.makeText(Order.this, "Return to About", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startOrderRecycler(){
        Query query = orderRef.orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Archive> options = new FirestoreRecyclerOptions.Builder<Archive>()
                .setQuery(query, Archive.class)
                .build();

        adapter = new OrderAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.OrderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //Delete record

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteOrder(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                Archive archive = documentSnapshot.toObject(Archive.class);
                String id = documentSnapshot.getId();
                //String path = documentSnapshot.getReference().getPath();
                Toast.makeText(Order.this, "Index " + i + "ID " + id, Toast.LENGTH_SHORT).show();

                //Pass the ID
                Intent intent = new Intent(Order.this, Order_Update.class);
                intent.putExtra("DocuId", id);
            //("Doc ID", id);
                startActivity(intent);
            }
        });

    }
    //for performance starts stops based on foreground or background
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
