package com.example.locobar.service;


import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.locobar.model.CartItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FirebaseService {

    FirebaseFirestore firebase = FirebaseFirestore.getInstance();




    public void addItem(CartItem cartItem){

        Map<String, Object> data = new HashMap<>();
        data.put("navn", cartItem.getProductName());
        data.put("pris", cartItem.getPrice());
        firebase.collection("genstand")
                .add(data);
    }
//final GridViewAdapter adapter
    public void getAllItems(ArrayAdapter adapter) {
        firebase.collection("genstand")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<CartItem> listOfItems = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("navn");
                        double pris = document.getDouble("pris");
                        String imageURI = document.getString("imageURI");

                        CartItem item = new CartItem(name, pris, imageURI);
                        listOfItems.add(item);
                    }
                    adapter.addAll(listOfItems);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {

                });
    }

    public void uploadImageToFirebaseStorage(Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/" + imageUri.getLastPathSegment());

        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully, now store the image URL in the Firebase Realtime Database

                    //storeImageUrlInDatabase(imageUrl);
                    })
                .addOnFailureListener(exception -> {

                    // Handle errors
                });
    }

}
