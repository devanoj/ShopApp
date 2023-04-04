package com.example.shopapp.Main.LoginStrategy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shopapp.Main.ForgotPassword;
import com.example.shopapp.Main.SignUp;
import com.example.shopapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LogIn extends AppCompatActivity { //Test This
    EditText email1, password1;
    TextView forgotPswd;
    Button bLogin, SignUp;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    LoginStrategy loginStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        email1 = findViewById(R.id.username);
        password1 = findViewById(R.id.password);

        click();
    }

    private void click() {
        SignUp = findViewById(R.id.SignUp);
        bLogin = findViewById(R.id.loginbtn);
        forgotPswd = findViewById(R.id.forgotPassword);

        forgotPswd.setOnClickListener(v -> {
            String email = email1.getText().toString();
            Bundle bundle1 = new Bundle();
            bundle1.putString("email", email);
            Intent intent1 = new Intent(LogIn.this, ForgotPassword.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });

        SignUp.setOnClickListener(view -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(LogIn.this, SignUp.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
            Toast.makeText(LogIn.this, "Register Page", Toast.LENGTH_SHORT).show();
        });

        bLogin.setOnClickListener(view -> {
            String email = email1.getText().toString();
            String password = password1.getText().toString();
            loginUser(email, password);
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loginStrategy = new SuccessStrategy();
                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        loginStrategy = new InvalidUserStrategy();
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        loginStrategy = new InvalidCredentialsStrategy();
                    } else {
                        loginStrategy = new FailureStrategy();
                    }
                    loginStrategy.handleLogin(LogIn.this, email, password);
                });
    }
}

