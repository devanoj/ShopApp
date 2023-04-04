package com.example.shopapp.Main.LoginStrategy;

import android.widget.Toast;



class InvalidCredentialsStrategy implements LoginStrategy {
    @Override
    public void handleLogin(LogIn loginActivity, String email, String password) {
        Toast.makeText(loginActivity, "Invalid password", Toast.LENGTH_SHORT).show();
    }
}
