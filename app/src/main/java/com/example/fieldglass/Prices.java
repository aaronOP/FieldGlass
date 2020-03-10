package com.example.fieldglass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class Prices extends AppCompatActivity {

    private static final String TAG = "Prices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        Log.d(TAG,"onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listview);

        //Create the price objects
        Price mowing = new Price("Mowing", "£13","Per Acre");
        Price tedding = new Price("Tedding", "£7","Per Acre");
        Price raking = new Price("Raking", "£10","Per Acre");
        Price bale = new Price("Small-Baling","£0.70","Per Bale");
        Price baleing = new Price("Round-Baling","£4.50","Per Bale");
        Price combi = new Price("Bale & Wrap","£7.50","Per Bale");

        Price stack = new Price("Stack","£30","Per Hour");
        Price collect = new Price("Collect", "£30", "Per Hour");
        Price transport = new Price("Transport", "£40", "Per Hour");
        Price _________ = new Price("", "","");

        Price excavator = new Price("Excavator Hire","£33","Per Hour");
        Price dumper = new Price("Dumper Hire", "£25","Per Hour");
        Price tractorhire = new Price("Tractor Hire"," £25", "Per Hour");

        //Add the prices to an array list
        ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(mowing);
        priceList.add(tedding);
        priceList.add(raking);
        priceList.add(bale);
        priceList.add(baleing);
        priceList.add(combi);
        priceList.add(_________);
        priceList.add(stack);
        priceList.add(collect);
        priceList.add(transport);
        priceList.add(_________);
        priceList.add(excavator);
        priceList.add(dumper);
        priceList.add(tractorhire);

        //create Price list adapter
        PriceListAdapter adapter = new PriceListAdapter(this, R.layout.adapter_view_layout, priceList);
        mListView.setAdapter(adapter);
    }
}
