package com.example.locobar.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;


    public Cart(){
        cartItems = new ArrayList<>();
    }
    public void addToCart(String productName, double price, int quantity){
        CartItem item = new CartItem(productName, price, quantity);
        cartItems.add(item);
    }

    public CartItem findCartItemByName(String productName) {
        for (CartItem item : cartItems) {
            if (item.getProductName().equals(productName)) {
                return item;
            }
        }
        return null;
    }



    public void removeFromCart(CartItem item){
        cartItems.remove(item);
    }

    public  List<CartItem> getCartItems(){
        return cartItems;
    }

    public double calculateTotalPrice(){
        double total = 0;
        for (CartItem item : cartItems){
            total += item.getPrice() * item.getQuantity();
        }
        return  total;
    }

    public void addToCart(CartItem item) {
        cartItems.add(item);
    }
}
