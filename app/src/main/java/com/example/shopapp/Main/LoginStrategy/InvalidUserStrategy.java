package com.example.shopapp.Main.LoginStrategy;

import android.widget.Toast;


class InvalidUserStrategy implements LoginStrategy {
    @Override
    public void handleLogin(LogIn loginActivity, String email, String password) {
        Toast.makeText(loginActivity, "Invalid username", Toast.LENGTH_SHORT).show();
    }
}
