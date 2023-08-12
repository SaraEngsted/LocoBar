package com.example.locobar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locobar.model.Cart;
import com.example.locobar.model.CartItem;
import com.example.locobar.service.FirebaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.List;

public class Menu extends AppCompatActivity {
    private static final String TAG = "Menu";

    private Cart cart;
    private CartItem cartItem;
    private List<CartItem> cartItems = new ArrayList<>();
    private ListView viewList;
    private ArrayAdapter<CartItem> adapter;

    private final FirebaseService firebaseService = new FirebaseService();

    private void removeCartItem(CartItem item){
        cart.removeFromCart(item);
        Toast.makeText(Menu.this,"Varen er fjernet fra kurven", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        viewList = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.myrow, R.id.myRowTextView, cartItems);
        viewList.setAdapter(adapter);
        firebaseService.getAllItems(adapter);
        cart = new Cart();
        ImageButton cartButton = findViewById(R.id.cart_button);
        Button btnPayNow = findViewById(R.id.btnPayNow);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCartContent();
                Log.d(TAG, "Besked");

            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPaymentPage();
            }
        });
    }

    public void cart_button(View view) {
        // Handle click event for cart_button
        // Implement the desired functionality here
        Toast.makeText(this, "Knap trykket!", Toast.LENGTH_SHORT).show();
        // Eksempel: Vis en toast-meddelelse, når knappen trykkes
    }

    private void openPaymentPage() {
        double totalPrice = cart.calculateTotalPrice();

        Intent intent = new Intent(this, Payment.class);
        intent.putExtra("totalPrice", totalPrice);
        startActivity(intent);
    }


    private void showRemoveItemDialog(final List<CartItem> cartItems){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vælg vare");

        String[] productNames = new String[cartItems.size()];
        for (int i = 0; i < cartItems.size(); i++){
            productNames[i] = cartItems.get(i).getProductName();
        }
        builder.setItems(productNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                CartItem selectedCartItem = cartItems.get(which);
                removeCartItem(selectedCartItem);

            }
        });
        builder.setNegativeButton("Annuller", null);
        builder.create().show();
    }

    public void showCartContent(){
        List<CartItem> cartItems = cart.getCartItems();
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        double totalPrice = 0;


        for (CartItem item : cartItems){
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView productTextView = new TextView(this);
            productTextView.setText("Produktnavn: " + item.getProductName());
            itemLayout.addView(productTextView);

            TextView priceTextView = new TextView(this);
            priceTextView.setText("Pris: " + item.getPrice() + " kr");
            itemLayout.addView(priceTextView);

            TextView quantityTexView = new TextView(this);
            quantityTexView.setText(",  Antal: " + item.getQuantity() + " stk");
            itemLayout.addView(quantityTexView);

            Button removeButton = new Button(this);
            removeButton.setText("Fjern");
            final CartItem finalItem = item;
            removeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    removeCartItem(finalItem);
                    layout.removeView(itemLayout);

                }
            });
            itemLayout.addView(removeButton);
            layout.addView(itemLayout);
            totalPrice += item.getPrice() * item.getQuantity();

        }


        TextView totalPriceTextView = new TextView(this);
        totalPriceTextView.setText("Samlet pris: " + totalPrice + "kr");
        layout.addView(totalPriceTextView);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Indkøbskurv  \nI aften skal der drikkes");
        builder.setView(layout);

        builder.setNegativeButton("Fjern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                showRemoveItemDialog(cartItems);

            }
        });
        builder.setPositiveButton("Luk", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        Log.d(TAG, "Besked");


        Log.d(TAG, "Besked");

    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        List<CartItem>cartItems = cart.getCartItems();

        final ImageView selectedImageView = (ImageView) view;
        final Object tag = selectedImageView.getTag();
        String productName = "";


        if (tag != null){
            productName = tag.toString();
        }

        final String finalProductName = productName;
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_add:
                        // Her kan du tilføje koden til at håndtere klik på "Tilføj" menuelementet
                        showQuantityDialog(finalProductName);
                        return true;
                    case R.id.menu_item_remove:
                        CartItem selectedCarItem = cart.findCartItemByName(finalProductName);

                        if (selectedCarItem != null) {
                            removeCartItem(selectedCarItem);
                            showCartContent();
                        }

                        return true;
                    // Håndter andre menuelementer her
                    default:
                        return false;
                }

            }
        });

        int totalQuantity = 0;
        double totalPrice = 0;

        if (!cartItems.isEmpty()) {
            totalQuantity = cartItems.size();
            totalPrice = cart.calculateTotalPrice();
        }

        String totalPriceText = String.format(Locale.getDefault(), "%.2f", totalPrice);

        // Tilføj samlede pris og antal som en del af popup-menuens titel
        String menuTitle = "Tilføj til kurv (" + totalQuantity + " varer, " + totalPriceText + " kr)";
        popupMenu.getMenu().findItem(R.id.menu_item_add).setTitle(menuTitle);

        popupMenu.show();
    }



    private void addToCart() {
    }

    private  void showQuantityDialog(String productName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tilføj antal");

        final EditText quantityEditText = new EditText(this);
        quantityEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(quantityEditText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String quantityString = quantityEditText.getText().toString();
                int quantity = Integer.parseInt(quantityString);


                double price = 15;

                CartItem item = new CartItem(productName, price, quantity);
                cart.addToCart(item);


                Toast.makeText(Menu.this, "Produkt tilføjet til kurven", Toast.LENGTH_SHORT).show();


            }
        });

        builder.create().show();

    }

    private void handleQuantity(int quantity){

        Toast.makeText(this, "Antal tilføjet: " + quantity, Toast.LENGTH_SHORT).show();

    }}

