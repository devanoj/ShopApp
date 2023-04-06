package com.example.shopapp.Main;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.R;
import com.example.shopapp.SharedPrefHashMap.DataHolder;

import java.util.HashMap;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    TextView Price1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out_layout);

        Price1 = findViewById(R.id.Price);

        printHashMap();
    }

    private void printHashMap() {
        HashMap<String, String> hashMap = DataHolder.getHashMap(this);
        if (hashMap != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append("Name: ").append(key).append(", Price: ").append(value).append("\n");
            }
            Price1.setText(sb.toString()); // Set the concatenated string as the text of the TextView
        }
    }
}
