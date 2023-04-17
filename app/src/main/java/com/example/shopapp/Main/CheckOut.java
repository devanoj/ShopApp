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
import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    TextView Price1, totalPrice;
    Button Back, Buy, deleteCart, discount;
    EditText CodeDisocunt;
    //DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Stock");
    DatabaseReference dr;
    private static FirebaseDatabase firebaseDatabase;
    public static FirebaseDatabase getFirebaseDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out_layout);

        Price1 = findViewById(R.id.Price);
        Back = findViewById(R.id.Back);
        Buy = findViewById(R.id.Buy);
        deleteCart = findViewById(R.id.DeleteCart);
        totalPrice = findViewById(R.id.totalCart);
        CodeDisocunt = findViewById(R.id.discontCode);
        discount = findViewById(R.id.Discount);
        dr = getFirebaseDatabase().getReference("Stock");


        printHashMap();
        buy();
        back();
        deleteCart1();
        tp();
        discount1();
    }

    private void discount1() {
        discount.setOnClickListener(v->{
            String discountCode = CodeDisocunt.getText().toString().trim();
            if (!discountCode.isEmpty() && discountCode.equals("xxx")) {
                String finalPrice = totalPrice.getText().toString();
                double finalPriceInt = Double.parseDouble(finalPrice);
                double tenP = (finalPriceInt)-(finalPriceInt*0.1);
                String tenPer = String.valueOf(tenP);
                totalPrice.setText(tenPer);
                Toast.makeText(this, "Given Discount", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No Discount", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tp() {
        HashMap<String, String> hashMap = DataHolder.getHashMap(this);
        if (hashMap != null) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                double intValue = Double.parseDouble(value);
                //calculateTotal(key, intValue);
                Total(key, intValue);
                Log.w("TP11", key);
                Log.w("TP11", value);
            }
        }
    }

    private void Total(String k, double v) {
        dr.child(k).child("price").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String p = snapshot.getValue(String.class);
                double pItem = Double.parseDouble(p);
                double qtItem = pItem*v;
                double finalqtItem =+ qtItem;
                String myValue = String.valueOf(finalqtItem);
                totalPrice.setText(myValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteCart1() {
        deleteCart.setOnClickListener(v->{
            deleteHashMapData();

            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(CheckOut.this, Find.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
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
            deleteHashMapData();
            
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(CheckOut.this, Find.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }

    private void deleteHashMapData() {
        HashMap<String, String> emptyHashMap = new HashMap<>();

        // Save the empty HashMap to SharedPreferences
        DataHolder.saveHashMap(this, emptyHashMap);
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
                        //int qLevel = Integer.parseInt(q);
                        //int finalValue = qLevel - intValue;

                        double qLevel = Double.parseDouble(q);
                        double finalValue = qLevel - intValue;
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
