package com.example.fieldglass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PriceListAdapter extends ArrayAdapter<Price> {

    private static final String TAG = "PriceListAdapter";
    private Context mContext;
    int mResource;

    //Default constructor for price list adapter
    private static class ViewHolder {
        TextView service;
        TextView cost;
        TextView per;
    }

    /**
     * Default constructor for the PriceListAdapter
     * @param context
     * @param resource
     * @param objects
     */


    public PriceListAdapter(Context context, int resource, ArrayList<Price> objects) {
        super(context, resource,objects);
        mContext = context;
        mResource = resource;
}

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the services info
        String service = getItem(position).getService();
        String cost = getItem(position).getCost();
        String per = getItem(position).getPer();

        //Create the service object with the information
        Price price = new Price(service,cost,per);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //Set text views
        TextView tvService = convertView.findViewById(R.id.textView1);
        TextView tvCost =  convertView.findViewById(R.id.textView2);
        TextView tvPer = convertView.findViewById(R.id.textView3);

        tvService.setText(service);
        tvCost.setText(cost);
        tvPer.setText(per);

        return  convertView;

    }
}
