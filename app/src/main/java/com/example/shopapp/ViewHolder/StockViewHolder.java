package com.example.shopapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Main.AddToCart;
import com.example.shopapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StockViewHolder extends RecyclerView.ViewHolder {
    public TextView title, quantity, dead, desc, stat, id, idfromUser;
    public Button select;
    public ImageView picOfStock;
    private Context context;
    public String animalID;

    public StockViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        title = itemView.findViewById(R.id.TitleEditTV1);
        desc = itemView.findViewById(R.id.manufacturerEditTV1);
        dead = itemView.findViewById(R.id.priceEditTV1);
        stat = itemView.findViewById(R.id.categoryEditTV1);
        id = itemView.findViewById(R.id.idStock);
        idfromUser = itemView.findViewById(R.id.idUser);
        quantity = itemView.findViewById(R.id.quantityEditTV1);

        picOfStock = itemView.findViewById(R.id.pictureOfStock);
        select = itemView.findViewById(R.id.selectA);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String cUser = currentUser.getUid();
        DatabaseReference idorg = FirebaseDatabase.getInstance().getReference().child("User").child(cUser).child("organisation");
        HashMap<String, String> shoppingCart = new HashMap<>();

        select.setOnClickListener(v -> {
            Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show();
            idorg.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean value = Boolean.TRUE.equals(dataSnapshot.getValue(Boolean.class));
                    if(value) {
                        Log.w("GET_USER", cUser + "TRUE");
                    } else {
                        Log.w("GET_USER", cUser + " FALSE");
                        String itemId = title.getText().toString();
                        //String quantityNum = quantity.getText().toString();
                        //shoppingCart.put(itemId, quantityNum);
                        //Log.w("GET_USER", itemId + quantityNum);

                        Intent intent = new Intent(itemView.getContext(), AddToCart.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                        Bundle bundle = new Bundle();
                        bundle.putString("Item", itemId);
                        intent.putExtras(bundle);


                        context.startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // handle any errors here
                }
            });
        });
    }


}
