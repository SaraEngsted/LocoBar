package com.example.locobar.service;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.locobar.model.CartItem;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class FirebaseService {

    FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference = storage.getReference();

    public FirebaseService () {}
    public CompletableFuture<Void> addItem(CartItem cartItem, Uri imageUriFromPhone){
        CompletableFuture<Void> future = new CompletableFuture<>();
        Map<String, Object> data = new HashMap<>();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/" + imageUriFromPhone.getLastPathSegment());

        imageRef.putFile(imageUriFromPhone)
                .addOnSuccessListener(taskSnapshot ->
                {

                    Task<Uri> downloadImageTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    downloadImageTask.addOnCompleteListener(t -> {
                       if(t.isSuccessful()){
                           Uri imageUri = t.getResult();
                           data.put("navn", cartItem.getProductName());
                           data.put("pris", cartItem.getPrice());
                           data.put("imageUri", imageUri.toString());
                           data.put("quantity", 0.0);

                           firebase.collection("genstand")
                                   .add(data)
                                   .addOnFailureListener(e -> future.completeExceptionally(e))
                                   .addOnSuccessListener(docRef -> future.complete(null));
                       }
                       if(!t.isSuccessful()){
                           System.out.println("Something went wrong");
                       }
                    });



                    //storeImageUrlInDatabase(imageUrl);
                })
                .addOnFailureListener(exception -> {
                    System.out.println("Something went wrong" + exception);
                    // Handle errors
                });

        return future;
    }


public Task<List<CartItem>> getAllItems2(){
    ArrayList<CartItem> listofitems = new ArrayList<>();

    return firebase.collection("genstand")
            .get()
            .continueWith(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("navn");
                        double pris = document.getDouble("pris");
                        String imageURI = document.getString("imageUri");
                        String imageID = extractImageIdFromUrl(imageURI);

                        CartItem item = new CartItem(name, pris, 0, imageID);
                        listofitems.add(item);
                    }
                    return listofitems;
                } else {
                    throw task.getException();
                }
            });
}

    public String extractImageIdFromUrl(String imageUrl) {
        int startIndex = imageUrl.indexOf("images%2F") + "images%2F".length();
        int endIndex = imageUrl.indexOf("?alt=");

        if (startIndex != -1 && endIndex != -1) {
            return imageUrl.substring(startIndex, endIndex);
        }

        return null; // Return null if the format of the URL is not as expected
    }

}
