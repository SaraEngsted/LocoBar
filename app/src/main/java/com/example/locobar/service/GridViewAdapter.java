package com.example.locobar.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.locobar.R;
import com.example.locobar.model.CartItem;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<CartItem> {
    private Context mContext;
    private int mResource;

    public GridViewAdapter(Context context, int resource, List<CartItem> items) {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        CartItem item = getItem(position);

        if (item != null) {
            TextView navnTextView = view.findViewById(R.id.navnTextView);
            TextView prisTextView = view.findViewById(R.id.prisTextView);

            navnTextView.setText(item.getProductName());
            prisTextView.setText(String.valueOf(item.getPrice()));
        }

        return view;
    }
}