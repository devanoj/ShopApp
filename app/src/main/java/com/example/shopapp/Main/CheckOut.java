package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.R;
import com.example.shopapp.SharedPrefHashMap.DataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    TextView Price1;
    Button Back, Buy;
    DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Stock");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out_layout);

        Price1 = findViewById(R.id.Price);
        Back = findViewById(R.id.Back);
        Buy = findViewById(R.id.Buy);

        printHashMap();
        buy();
        back();
    }

    private void back() {
        Back.setOnClickListener(v->{
                Bundle bundle1 = new Bundle();
                Intent intent1 = new Intent(CheckOut.this, Find.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
        });
    }

    private void buy() {
        Buy.setOnClickListener(v->{
            adjustStockLevels();


            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(CheckOut.this, Find.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }

    private void adjustStockLevels() {
        HashMap<String, String> hashMap = DataHolder.getHashMap(this);
        if (hashMap != null) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                int intValue = Integer.parseInt(value);

                dr.child(key).child("quantity").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String q = snapshot.getValue(String.class);
                        int qLevel = Integer.parseInt(q);
                        int finalValue = qLevel - intValue;
                        String sfinalStockValue = String.valueOf(finalValue);

                        dr.child(key).child("quantity").setValue(sfinalStockValue)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(CheckOut.this, "Stock Changed", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(CheckOut.this, "Stock Not Changed", Toast.LENGTH_SHORT).show();
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    private void printHashMap() {
        HashMap<String, String> hashMap = DataHolder.getHashMap(this);
        if (hashMap != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append("Stock ID: ").append(key).append(", Quantity: ").append(value).append("\n");
            }
            Price1.setText(sb.toString()); // Set the concatenated string as the text of the TextView
        }
    }
}
