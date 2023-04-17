package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.DAO.CommentDAO;
import com.example.shopapp.Entity.Comment;
import com.example.shopapp.R;
import com.example.shopapp.SharedPrefHashMap.DataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class AddToCart extends AppCompatActivity {
    Button qButton, bButton, pButton, cButton;
    EditText qEditText, cEditText;
    RatingBar ratingBar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Stock");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtocart_layout);

        qButton = findViewById(R.id.buttonQ);
        bButton = findViewById(R.id.buttonB);
        pButton = findViewById(R.id.postB);
        cButton = findViewById(R.id.checkOut);

        cEditText = findViewById(R.id.Comment);
        qEditText = findViewById(R.id.Quantity1);

        ratingBar = findViewById(R.id.ratingBar);

        saveDataHashMap();
        backButton();
        postComment();
        goToCheckout();
    }

    private void goToCheckout() {
        cButton.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), CheckOut.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void saveDataHashMap() {
        String myValue = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myValue = bundle.getString("Item");
        }

        final String finalMyValue = myValue; // Declare as final

        qButton.setOnClickListener(v -> {
            HashMap<String, String> hashMap = DataHolder.getHashMap(getApplicationContext()); // Retrieve existing data from SharedPreferences
            if (hashMap == null) {
                hashMap = new HashMap<>();
            }

            hashMap.put(finalMyValue, qEditText.getText().toString()); // Update the HashMap with new data

            HashMap<String, String> finalHashMap = hashMap;
            dr.child(finalMyValue).child("quantity").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String text = qEditText.getText().toString();
                    Toast.makeText(AddToCart.this, "Text: " + text, Toast.LENGTH_SHORT).show();
                    if (!TextUtils.isEmpty(text)) { // Add null check
                        int number = Integer.parseInt(text);
                        String q = snapshot.getValue(String.class);
                        Toast.makeText(AddToCart.this, "Text 1: " + q, Toast.LENGTH_SHORT).show(); //null
                        if (q != null) { // Add null check
                            int intValue = Integer.parseInt(q);
                            if (number < intValue) {
                                DataHolder.saveHashMap(getApplicationContext(), finalHashMap); // Save the updated HashMap to SharedPreferences
                                printHashMap();
                            } else {
                                return; // Just Crashes here move to something else
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddToCart.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void postComment() {
        String myValue = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myValue = bundle.getString("Item");
        }
        String finalMyValue = myValue;

        String[] stringRating = {null}; // Use an array to hold the mutable value

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            stringRating[0] = String.valueOf(rating); // Set the value in the array
        });

        pButton.setOnClickListener(v -> {
            String comment = cEditText.getText().toString();
            Comment c1 = new Comment(null, finalMyValue, comment, currentUser.getUid(), stringRating[0]); // Get the value from the array
            CommentDAO aDAO = new CommentDAO(c1);
        });
    }


    private void backButton() {
        bButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), Find.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void printHashMap() {
        HashMap<String, String> hashMap = DataHolder.getHashMap(this);
        if (hashMap != null) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.d("HashMap", "Key: " + key + ", Value: " + value);
            }
        }
    }
}
