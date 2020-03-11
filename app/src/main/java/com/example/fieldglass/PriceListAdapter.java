package com.example.fieldglass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PriceListAdapter extends ArrayAdapter<Price> {

    private static final String TAG = "PriceListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition;

    //Default constructor for price list adapter
    //Holds variables in a view
    static class ViewHolder {
        TextView service;
        TextView cost;
        TextView per;
    }

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

        //Create view result for showing  the animation
        final View result;

        //view holder object
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            //Set text views
            holder.service = (TextView) convertView.findViewById(R.id.textView1);
            holder.cost = (TextView) convertView.findViewById(R.id.textView2);
            holder.per = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder =  (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Declare animation
        //if position is greater than last position, then use animation laod down, if not use load up.
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        //Set text to parameters
        holder.service.setText(price.getService());
        holder.cost.setText(price.getCost());
        holder.per.setText(price.getPer());


        return  convertView;

    }
}