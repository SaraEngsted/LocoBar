package com.example.locobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.locobar.model.CartItem;
import com.example.locobar.service.FirebaseService;

public class EditMenu extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPrice;
    private Button buttonAdd;

    private String imageURI;
    private final FirebaseService firebaseService = new FirebaseService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.EditTextIPrice);
        buttonAdd = findViewById(R.id.buttonAdd);

        Button button = (Button) findViewById(R.id.uploadImage);
        button.setOnClickListener(view -> uploadImage());

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
        CartItem itemToAdd = new CartItem(productName, price, imageURI);
        firebaseService.addItem(itemToAdd);
    }

    //kalder uploadImage
    private void uploadImage () {
        Log.i("firebase123", "upload image function called ");

        openGallery();
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            firebaseService.uploadImageToFirebaseStorage(selectedImageUri);
        }
    }

}