package com.example.shopapp.Main.LoginStrategy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shopapp.Main.Home;

class SuccessStrategy implements LoginStrategy {
    @Override
    public void handleLogin(LogIn loginActivity, String email, String password) {
        Toast.makeText(loginActivity, "User signed in", Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("password", password);

        Intent intent = new Intent(loginActivity, Home.class);
        intent.putExtras(bundle);
        loginActivity.startActivity(intent);
    }
}
