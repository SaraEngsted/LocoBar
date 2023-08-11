package com.example.locobar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.locobar.service.FirebaseService;
import com.example.locobar.service.GridViewAdapter;

import java.util.ArrayList;

public class Basket extends AppCompatActivity {
    private GridViewAdapter adapter;
    private final FirebaseService firebaseService = new FirebaseService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        GridView gridView = findViewById(R.id.gridView);
        adapter = new GridViewAdapter(this, R.layout.activity_basket, new ArrayList<>());
        firebaseService.getAllItems(adapter);
        gridView.setAdapter(adapter);

    }

    public void back(View view) {
        finish();
    }

}