package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.Main.AddStock.AddStock;
import com.example.shopapp.R;

public class Home extends AppCompatActivity {
    ImageView Profile1, CreateStock, Find1, HomeMain1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        sideNavMenu();
    }

    private void sideNavMenu() {
        Profile1 = findViewById(R.id.Profile);
        CreateStock = findViewById(R.id.CreateStock);
        Find1 = findViewById(R.id.Find);
        HomeMain1 = findViewById(R.id.HomeMain);


        Profile1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Home.this, Profile.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        CreateStock.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Home.this, AddStock.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        Find1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Home.this, Find.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }
}
