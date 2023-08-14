package com.example.locobar.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locobar.R;
import com.example.locobar.model.CartItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import javax.annotation.Nullable;

public class ImageAdapter extends ArrayAdapter<CartItem> {

    private Context context;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference = storage.getReference();
    private List<CartItem> items;


    public ImageAdapter(List<CartItem> items, Context context) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CartItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        CartItem currentItem = getItem(position);
        ImageView itemImageView = convertView.findViewById(R.id.item_image_view);
        loadImageFromStorage(currentItem.getImageURI(), itemImageView);
        TextView textView = convertView.findViewById(R.id.item_name);
        textView.setText(items.get(position).getProductName());
        TextView priceView = convertView.findViewById(R.id.item_price);
        priceView.setText("" + (int) items.get(position).getPrice());


        return convertView;

    }

    private void loadImageFromStorage(String imagePath, ImageView imageView){
        StorageReference imageRef = reference.child("/images/" + imagePath);
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);
        }).addOnFailureListener(e -> Log.e("Firebase", "Error fetching image", e));
    }
}



