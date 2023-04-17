package com.example.shopapp.Main.AdminStock;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.DAO.StockDAO;
import com.example.shopapp.Entity.Stock;
import com.example.shopapp.Main.AddStock.AddStock;
import com.example.shopapp.Main.AddStock.StockObserver;
import com.example.shopapp.Main.CheckOut;
import com.example.shopapp.Main.Find;
import com.example.shopapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class AdminControl extends AppCompatActivity implements StockObserver {
    public Button addStock1, mButtonChooseImage, BackButton, deleteStock;
    public EditText Title, Manufact, Price, Category, Quantity;
    public String Title1, Manufact1, Category1, Quantity1, Price1;
    //public int Price1;

    public Uri mImageUri = null;
    public StorageTask mUploadTask;
    public StorageReference mStorageRef;
    private StockDAO stockDAO;

    public ImageView Profile1, CreateStock, Find1, HomeMain1;

    String id;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        Title = findViewById(R.id.name);
        Manufact = findViewById(R.id.Manufacturer);
        Price = findViewById(R.id.password4);
        Category = findViewById(R.id.Category);
        Quantity = findViewById(R.id.Quantity);
        addStock1 = findViewById(R.id.addStock);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        BackButton = findViewById(R.id.back1);
        deleteStock = findViewById(R.id.DeleteStock);

        mButtonChooseImage.setOnClickListener(view -> openFileChooser());
        addStock1.setOnClickListener(view -> inputStock());
        deleteStock.setOnClickListener(view -> deleteStock1());
        BackButton.setOnClickListener(v -> {
            back();
        });


        stockDAO = new StockDAO();
        stockDAO.addaObserver(this);
    }

    private void deleteStock1() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Stock");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("Item");
        }

        databaseReference.child(id).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(AdminControl.this, "Stock deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AdminControl.this, "Failed to delete stock: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        back();
    }

    private void back() {
            Intent intent = new Intent(getApplicationContext(), Find.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
    }

    private void inputStock() {
        Title1 = Title.getText().toString();
        Manufact1 = Manufact.getText().toString();
        Price1 = Price.getText().toString();
        Category1 = Category.getText().toString();
        Quantity1 = Quantity.getText().toString();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("Item");
        }

        if (mImageUri != null) {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {


                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            Stock stock = new Stock(id, Title1, Manufact1, Price1, Category1, imageUrl, Quantity1,null); // Data here

                            stockDAO.addaStock(stock, id);
                            Toast.makeText(AdminControl.this, "Entered Data", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(AdminControl.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(AdminControl.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onStockAdded(Stock stock) {
        Toast.makeText(this, "Stock updated: " + stock.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
