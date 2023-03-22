package com.example.shopapp.DAO;

import com.example.shopapp.Entity.Stock;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StockDAO {
    private DatabaseReference databaseReference;

    public StockDAO(Stock item1) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Stock");
        String stockId = databaseReference.push().getKey(); // generate unique animal ID

        item1.setStockId(stockId); // set the ID in the animal object
        databaseReference.child(stockId).setValue(item1);



    }
}
