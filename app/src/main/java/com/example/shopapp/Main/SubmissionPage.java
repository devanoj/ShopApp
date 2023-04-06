package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.R;
import com.example.shopapp.SharedPrefHashMap.DataHolder;

import java.util.HashMap;
import java.util.Map;


public class SubmissionPage extends AppCompatActivity {
    Button qButton, bButton;
    EditText qEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submission_layout);

        qButton = findViewById(R.id.buttonQ);
        bButton = findViewById(R.id.buttonB);
        qEditText = findViewById(R.id.Quantity1);
        String myValue = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myValue = bundle.getString("Item");
        }

        String finalMyValue = myValue;
        qButton.setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(finalMyValue, "5");
            DataHolder.saveHashMap(getApplicationContext(), hashMap);
            printHashMap();
        });

        backButton();
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
