package com.example.shopapp.Main.LoginStrategy;

import com.example.shopapp.Main.LoginStrategy.LogIn;

interface LoginStrategy {
    void handleLogin(LogIn loginActivity, String email, String password);
}
