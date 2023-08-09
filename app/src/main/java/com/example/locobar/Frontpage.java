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
        button.setOnClickListener(view -> openActivity2());

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(view -> openActivity3());
    }

    public void openActivity2(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent2 = new Intent(this, EditMenu.class);
        startActivity(intent2);
    }

    public void openActivity4(View view){
        Intent intent3 = new Intent(Menu.this, Basket.class);
        startActivity(intent3);
    }
}