package com.example.locobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Frontpage extends AppCompatActivity {

    private Button button;
    private Button button2;
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

    public void openBasket(View view){
        Intent intent3 = new Intent(Frontpage.this, Basket.class);
        startActivity(intent3);
    }
}