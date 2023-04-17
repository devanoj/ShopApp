package com.example.shopapp.DAO;

import android.content.Context;
import android.widget.Toast;

import com.example.shopapp.Entity.Stock;


import com.example.shopapp.Main.AddStock.AddStock;
import com.example.shopapp.Main.AddStock.StockObserver;
import com.example.shopapp.Main.AdminStock.AdminControl;
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

    public void addObserver(AddStock observer) {
        observers.add(observer);
    }

    public void addaObserver(AdminControl observer){
        observers.add(observer);
    }

    public void addStock(Stock stock) {
        String stockId = databaseReference.push().getKey();
        stock.setStockId(stockId);

        // Notify all observers of the new stock
        for (StockObserver observer : observers) {
            observer.onStockAdded(stock);
        }
        databaseReference.child(stockId).setValue(stock);
    }

    public void addaStock(Stock stock, String sID) {
        stock.setStockId(sID);

        // Notify all observers of the new stock
        for (StockObserver observer : observers) {
            observer.onStockAdded(stock);
        }

        databaseReference.child(sID).setValue(stock);
    }

}

