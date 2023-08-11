package com.example.locobar.service;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.locobar.model.CartItem;

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

    public void getAllItems(final GridViewAdapter adapter) {
        firebase.collection("genstand")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<CartItem> listOfItems = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("navn");
                        double pris = document.getDouble("pris");

                        CartItem item = new CartItem(name, pris);
                        listOfItems.add(item);
                    }
                    adapter.addAll(listOfItems);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {

                });
    }
}
