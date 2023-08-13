package com.example.locobar.model;

import android.widget.ImageView;

public class CartItem {

    private  String productName;
    private double price;
    private int quantity;
    private ImageView imageView;

    private String imageURI;



    public CartItem(String productName, double price, int quantity, String imageURI){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageURI = imageURI;
    }

    public CartItem(String productName, double price) {
        this.productName = productName;
        this.price = price;

    }


    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public String getProductName(){
        return productName;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", imageView=" + imageView +
                '}';
    }
}
