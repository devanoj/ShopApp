package com.example.shopapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Main.SubmissionPage;
import com.example.shopapp.R;

public class StockViewHolder extends RecyclerView.ViewHolder {
    public TextView cat, dead, desc, stat, id, idfromUser, fromOrg;
    public Button select;
    public ImageView picOfStock;
    private Context context;
    public String animalID;

    public StockViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        cat = itemView.findViewById(R.id.TitleEditTV1);
        desc = itemView.findViewById(R.id.manufacturerEditTV1);
        dead = itemView.findViewById(R.id.priceEditTV1);
        stat = itemView.findViewById(R.id.categoryEditTV1);
        id = itemView.findViewById(R.id.idStock);
        idfromUser = itemView.findViewById(R.id.idUser);
        fromOrg = itemView.findViewById(R.id.quantityEditTV1);

        picOfStock = itemView.findViewById(R.id.pictureOfStock);
        select = itemView.findViewById(R.id.selectA);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), SubmissionPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("Name", cat.getText().toString());
                bundle.putString("Age", desc.getText().toString());
                bundle.putString("Id", id.getText().toString());
                bundle.putString("idFromUser", idfromUser.getText().toString());

                intent.putExtras(bundle);

                context.startActivity(intent);

            }
        });
    }


}
