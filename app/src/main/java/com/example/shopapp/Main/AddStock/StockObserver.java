package com.example.shopapp.Main.AddStock;

import com.example.shopapp.Entity.Stock;

public interface StockObserver {
    void onStockAdded(Stock stock);
}
