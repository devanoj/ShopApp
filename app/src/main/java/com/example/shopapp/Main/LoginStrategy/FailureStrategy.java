package com.example.shopapp.Main.LoginStrategy;

import android.util.Log;
import android.widget.Toast;



class FailureStrategy implements LoginStrategy {
    @Override
    public void handleLogin(LogIn loginActivity, String email, String password) {
        Log.w("MySignIn", "SignInUserWithEmail:failure");
        Toast.makeText(loginActivity, "Failed", Toast.LENGTH_SHORT).show();
    }
}
