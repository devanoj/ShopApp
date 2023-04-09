package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.Main.AddStock.AddStock;
import com.example.shopapp.R;

public class Profile extends AppCompatActivity {
    ImageView Profile1, CreateStock, Find1, HomeMain1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        sideNavMenu();
    }

    private void sideNavMenu() {
        CreateStock = findViewById(R.id.CreateStock);
        Profile1 = findViewById(R.id.Profile);
        Find1 = findViewById(R.id.Find);
        HomeMain1 = findViewById(R.id.HomeMain);


        CreateStock.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, AddStock.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        Find1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, Find.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        HomeMain1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Profile.this, Home.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }
}
