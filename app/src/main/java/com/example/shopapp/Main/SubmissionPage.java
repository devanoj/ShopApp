package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.DAO.CommentDAO;
import com.example.shopapp.Entity.Comment;
import com.example.shopapp.R;
import com.example.shopapp.SharedPrefHashMap.DataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;


public class SubmissionPage extends AppCompatActivity {
    Button qButton, bButton, pButton;
    EditText qEditText, cEditText;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submission_layout);

        qButton = findViewById(R.id.buttonQ);
        bButton = findViewById(R.id.buttonB);
        pButton = findViewById(R.id.postB);

        cEditText = findViewById(R.id.Comment);
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
        postComment();
    }

    private void postComment() {
        String myValue = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myValue = bundle.getString("Item");
        }

        String finalMyValue = myValue;


        pButton.setOnClickListener(v->{
            String comment = cEditText.getText().toString();
            Comment c1 = new Comment(null, finalMyValue, comment, currentUser.getUid());
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
