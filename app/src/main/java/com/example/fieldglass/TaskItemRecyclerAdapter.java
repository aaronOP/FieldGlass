package com.example.fieldglass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskItemRecyclerAdapter extends RecyclerView.Adapter<TaskItemRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<TaskItem> TaskItemList;

    public  TaskItemRecyclerAdapter(Context context, List<TaskItem> taskItemList) {

        this.context=context;
        TaskItemList=taskItemList;

    }

    @NonNull
    @Override
    public TaskItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.task_item, viewGroup, false );
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskItemRecyclerAdapter.ViewHolder viewHolder, int position) {
       TaskItem taskItem = TaskItemList.get(position);

       viewHolder.textservice.setText(taskItem.getService());
       viewHolder.textacre.setText(taskItem.getAcre());
       viewHolder.textdate.setText(taskItem.getDate());

       //Clickable recycler view

        //Get doc ID
        final String documentID = TaskItemList.get(position).docID;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, documentID, Toast.LENGTH_SHORT).show();

                //Store Doc ID
                global.orderDocID = documentID;

                //Move order to new activity
                TaskItemList.clear();
                Intent intent = new Intent(context, Order.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return TaskItemList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textservice;
        public TextView textdate;
        public TextView textacre;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            //get context
            context= context;

            textservice = itemView.findViewById(R.id.text_view_service);
            textacre = itemView.findViewById(R.id.text_view_acre);
            textdate = itemView.findViewById(R.id.text_view_date);
                    }

    }

}
