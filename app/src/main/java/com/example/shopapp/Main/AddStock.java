package com.example.shopapp.Main;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.DAO.StockDAO;
import com.example.shopapp.Entity.Stock;
import com.example.shopapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class AddStock extends AppCompatActivity {
    public Button addStock1, mButtonChooseImage;
    public EditText Title, Manufact, Price, Category, Quantity;
    public String Title1, Manufact1, Category1, Quantity1, Price1;
    //public int Price1;

    public Uri mImageUri = null;
    public StorageTask mUploadTask;
    public StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_stock_layout);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");



        inputEditText();
        inputStock();
    }

    private void inputEditText() {
        Title = findViewById(R.id.name);
        Manufact = findViewById(R.id.Manufacturer);
        Price = findViewById(R.id.password4);
        Category = findViewById(R.id.Category);
        Quantity = findViewById(R.id.Quantity);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonChooseImage.setOnClickListener(v -> openFileChooser());
    }

    private void inputStock() {



        addStock1 = findViewById(R.id.addStock);
        addStock1.setOnClickListener(v -> {
            Title1 = Title.getText().toString();
            Manufact1 = Manufact.getText().toString();
            Price1 = Price.getText().toString();
            Category1 = Category.getText().toString();
            Quantity1 = Quantity.getText().toString();

            if (mImageUri != null) {

                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            String id = null;

                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                Stock item1 = new Stock(id, Title1, Manufact1, Price1, Category1, imageUrl, Quantity1); // Data here
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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}