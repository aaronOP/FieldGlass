package com.example.fieldglass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MachineAdapter extends FirestoreRecyclerAdapter<MachineItem, MachineAdapter.MachineHolder> {

    public MachineAdapter(@NonNull FirestoreRecyclerOptions<MachineItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MachineHolder machineHolder, int i, @NonNull MachineItem machineItem) {
        machineHolder.TVMake.setText(machineItem.getMake());
        machineHolder.TVModel.setText(machineItem.getModel());
        machineHolder.TVIssue.setText(machineItem.getIssue());
        machineHolder.TVComment.setText(machineItem.getComment());
        machineHolder.TVType.setText(machineItem.getType());
        machineHolder.TVYear.setText(machineItem.getYear());
    }

    @NonNull
    @Override
    public MachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View m = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_machine,
                parent, false);
        return new MachineHolder(m);
    }

    //Delete machine method
    public void deletecard(int i){
        getSnapshots().getSnapshot(i).getReference().delete();
    }
    //get data into view

    class MachineHolder extends RecyclerView.ViewHolder {
        //declare variables
        TextView TVMake;
        TextView TVModel;
        TextView TVIssue;
        TextView TVComment;
        TextView TVType;
        TextView TVYear;

        public MachineHolder(View itemView) {
            super(itemView);
            //Assign variables
            TVMake = itemView.findViewById(R.id.TVMake);
            TVModel= itemView.findViewById(R.id.TVModel);
            TVIssue =itemView.findViewById(R.id.TVIssue);
            TVComment = itemView.findViewById(R.id.TVComment);
            TVType = itemView.findViewById(R.id.TVType);
            TVYear = itemView.findViewById(R.id.TVYear);
        }
    }

}
