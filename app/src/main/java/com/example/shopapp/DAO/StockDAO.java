package com.example.shopapp.DAO;

import com.example.shopapp.Entity.Stock;
import com.example.shopapp.Interface.StockObserver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StockDAO {
    private DatabaseReference databaseReference;
    private List<StockObserver> observers;

    public StockDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Stock");
        observers = new ArrayList<>();
    }

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

//    public void removeObserver(StockObserver observer) { Not used
//        observers.remove(observer);
//    }

    public void addStock(Stock stock) {
        String stockId = databaseReference.push().getKey();
        stock.setStockId(stockId);

        // Notify all observers of the new stock
        for (StockObserver observer : observers) {
            observer.onStockAdded(stock);
        }
        databaseReference.child(stockId).setValue(stock);
    }
}

