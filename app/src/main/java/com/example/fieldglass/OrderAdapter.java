package com.example.fieldglass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class OrderAdapter extends FirestoreRecyclerAdapter<Archive, OrderAdapter.OrderHolder> {
    private OnItemClickListener listener;

    public OrderAdapter(@NonNull FirestoreRecyclerOptions<Archive> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHolder orderHolder, int i, @NonNull Archive archive) {
        //what to put in card view
        orderHolder.TVService.setText(archive.getService());
        orderHolder.TVAcre.setText((archive.getAcre()));
        orderHolder.TVLocation.setText(archive.getLocation());
        orderHolder.TVDate.setText(archive.getDate());
        orderHolder.TVComment.setText(archive.getComment());
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,
                parent, false);
        return new OrderHolder(v);
    }

    //Delete record and update firestore
    public void deleteOrder(int i) {
        getSnapshots().getSnapshot(i).getReference().delete();
    }
    //gets data from source into view
    class OrderHolder extends RecyclerView.ViewHolder {
        //create variables for text views
        TextView TVService;
        TextView TVAcre;
        TextView TVLocation;
        TextView TVDate;
        TextView TVComment;

        public OrderHolder(View itemView) {
            super(itemView);
            //assign variables
            TVService = itemView.findViewById(R.id.TVService);
            TVAcre = itemView.findViewById(R.id.TVAcre);
            TVLocation = itemView.findViewById(R.id.TVLocation);
            TVDate = itemView.findViewById(R.id.TVDate);
            TVComment = itemView.findViewById(R.id.TVComment);

            //Onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    //prevents index -1 crash
                    if (i != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(i), i);

                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
