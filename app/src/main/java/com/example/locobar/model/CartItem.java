package com.example.locobar.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

    public CartItem(String productName, double price, int quantity, String imageURI, ImageView imageView){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageURI = imageURI;
        this.imageView  = imageView;
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

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public static Bitmap getImageBitmap(String uri){
        Bitmap bm = null;
        try{
            InputStream ins = new URL(uri).openStream();
            bm = BitmapFactory.decodeStream(ins);
        }catch(NullPointerException exception){
            throw exception;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bm;
    }


    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return
                "productName='" + productName +
                "\nprice=" + price +
                "\nquantity=" + quantity +
                "\nimageView=" + imageView ;
    }
}
