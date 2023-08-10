package com.example.locobar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locobar.model.Cart;

public class Payment extends AppCompatActivity {

    private Cart cart;

    private Button finishPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        TextView totalPriceTextView = findViewById(R.id.total_price_text_view);
        totalPriceTextView.setText("Total: " + totalPrice +" kr");

        cart = new Cart();

        finishPaymentButton = findViewById(R.id.finish_payment_button);
        finishPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completPayment();

            }
        });
    }
    private void completPayment(){
        showConfirmationDialog();
    }

    private void showConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bekræftrlse");
        builder.setMessage("Betalingen er fuldført. tak for dit køb");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Payment.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}