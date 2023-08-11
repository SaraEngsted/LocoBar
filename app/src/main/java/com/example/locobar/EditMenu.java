package com.example.locobar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.locobar.model.CartItem;
import com.example.locobar.service.FirebaseService;

public class EditMenu extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;
    private Button buttonAdd;
    private final FirebaseService firebaseService = new FirebaseService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.EditTextIPrice);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

    }

    private void addItem() {
        String productName = editTextName.getText().toString().trim();
        double price = Double.parseDouble(editTextPrice.getText().toString().trim());
        CartItem itemToAdd = new CartItem(productName, price);
        firebaseService.addItem(itemToAdd);
    }
}