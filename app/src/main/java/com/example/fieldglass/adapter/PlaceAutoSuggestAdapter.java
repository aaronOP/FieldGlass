package com.example.fieldglass.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

public class PlaceAutoSuggestAdapter extends ArrayAdapter implements Filterable {
    public PlaceAutoSuggestAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
}
