package com.example.shopapp.Main;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.DAO.StockDAO;
import com.example.shopapp.Entity.Stock;
import com.example.shopapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class AddStock extends AppCompatActivity {
    Button addStock1;

    Uri mImageUri = null;
    StorageTask mUploadTask;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_stock_layout);

        addStock1 = findViewById(R.id.addStock);
        inputStock();
    }

    private void inputStock() {
        addStock1.setOnClickListener(v -> {
            if (mImageUri != null) {

                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            String id = null;

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                Stock item1 = new Stock(); // Data here
                                StockDAO aDAO = new StockDAO(item1);
                                Toast.makeText(AddStock.this, "Entered Data", Toast.LENGTH_SHORT).show();
                            });
                        })
                        .addOnFailureListener(e -> Toast.makeText(AddStock.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(AddStock.this, "No file selected", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}