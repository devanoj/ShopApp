package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.DAO.UserDAO;
import com.example.shopapp.Entity.User;
import com.example.shopapp.Main.AddStock.AddStock;
import com.example.shopapp.Main.LoginStrategy.LogIn;
import com.example.shopapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser;
    Button createAcc, goBack;
    EditText email2, password2, pwRetype, Name, dateOfBirth, phoneNo, eircode;
    CheckBox myCheckbox;
    Boolean seller=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        createAcc = findViewById(R.id.createAccount);
        goBack = findViewById(R.id.backButton);
        email2 = findViewById(R.id.email);
        password2 = findViewById(R.id.password3);
        pwRetype = findViewById(R.id.password4);
        Name = findViewById(R.id.Name);
        dateOfBirth = findViewById(R.id.DateOfBirth);
        myCheckbox = findViewById(R.id.my_checkbox);
        phoneNo = findViewById(R.id.phoneNo);
        eircode = findViewById(R.id.eircode);

        myCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            seller = isChecked;
        });

        createAcc.setOnClickListener(view2 -> {
            createAccount();
        });

        goBack.setOnClickListener(view -> {
            goToLogIn();
        });
    }

    private void goToLogIn() {
        Toast.makeText(SignUp.this, "Login Page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LogIn.class);
        startActivity(intent);
    }

    private void createAccount() {
        String email = email2.getText().toString();
        String password = password2.getText().toString();
        String name1 = Name.getText().toString();
        String dateOfBirth1 = dateOfBirth.getText().toString();
        String phoneNo1 = phoneNo.getText().toString();
        String eircode1 = eircode.getText().toString();

        registerUser(email, password, name1, dateOfBirth1, seller, phoneNo1, eircode1);
    }

    private void registerUser(String email, String password, String name1, String dateOfBirth1, Boolean seller, String phoneNo1, String eircode1) {
        String passwordRetype = pwRetype.getText().toString();

        // Check if password2 and pwRetype are the same
        if (!password.equals(passwordRetype)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("myActivity", "createUserWithEmail:success");
                        mUser = mAuth.getCurrentUser();
                        String uid = mUser.getUid();


                        User person1 = new User(name1, dateOfBirth1, phoneNo1, eircode1, email, uid, seller);
                        UserDAO uDAO = new UserDAO(person1, uid);

                        Intent intent = new Intent(getApplicationContext(), AddStock.class);
                        startActivity(intent);
                    } else {

                        // If sign in fails, display a message to the user.
                        Log.w("myActivity", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUp.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
