package com.example.shopapp.Interface;

import com.example.shopapp.Entity.Stock;

public interface StockObserver {
    void onStockAdded(Stock stock);
}
