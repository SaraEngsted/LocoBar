package com.example.locobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Frontpage extends AppCompatActivity {

    private Button button;
    private Button button2;

    private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(view -> openMenu());

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(view -> openEditMenu());

    }

    public void openMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void openEditMenu() {
        Intent intent2 = new Intent(this, EditMenu.class);
        startActivity(intent2);
    }
}