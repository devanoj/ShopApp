package com.example.shopapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.shopapp.Entity.Stock;
import com.example.shopapp.Main.AddToCart;
import com.example.shopapp.R;

import java.util.ArrayList;

public class newAdapter extends RecyclerView.Adapter<newAdapter.StockViewHolder> {

    public Context c;
    public ArrayList<Stock> arrayList;

    public newAdapter(Context c, ArrayList<Stock> arrayList) {
        this.c=c;
        this.arrayList=arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_rcv_layout,parent,false);
        return new StockViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {

        Stock tlist1 = arrayList.get(position);
        holder.t1.setText(tlist1.getTitle());
        holder.t2.setText(tlist1.getManufacturer());
        holder.t3.setText(tlist1.getPrice());
        holder.t4.setText(tlist1.getCategory());
        holder.t5.setText(tlist1.getQuantity());
        holder.id.setText(tlist1.getStockId());
        //holder.idfromUser.setText(tlist1.getUsid());
        Glide.with(holder.itemView.getContext())
                .load(tlist1.getImageUrl())
                .fitCenter()
                .centerCrop()
                .into(holder.i1);


    }



    public static class StockViewHolder extends RecyclerView.ViewHolder {

        private Context context; // add final maybe?
        public TextView t1;
        public TextView t2;
        public TextView t3;
        public TextView t4;
        public TextView t5;
        public TextView id;
        public TextView idfromUser;
        public ImageView i1;

        public Button s;

        public StockViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            t1 = itemView.findViewById(R.id.TitleEditTV1);
            t2 = itemView.findViewById(R.id.manufacturerEditTV1);
            t3 = itemView.findViewById(R.id.priceEditTV1);
            t4 = itemView.findViewById(R.id.categoryEditTV1);
            t5 = itemView.findViewById(R.id.quantityEditTV1);
            id = itemView.findViewById(R.id.idStock);
            idfromUser = itemView.findViewById(R.id.idUser);
            i1 = itemView.findViewById(R.id.pictureOfStock);
            s = itemView.findViewById(R.id.selectA);

            s.setOnClickListener(v -> {
                Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), AddToCart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("Name", t4.getText().toString());
                bundle.putString("Age", t2.getText().toString());
                bundle.putString("Id", id.getText().toString());
                bundle.putString("idFromUser", idfromUser.getText().toString());

                intent.putExtras(bundle);

                context.startActivity(intent);

            });


        }
    }

}
