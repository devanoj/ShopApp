package com.example.shopapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Adapter.newAdapter;
import com.example.shopapp.Entity.Stock;
import com.example.shopapp.Main.AddStock.AddStock;
import com.example.shopapp.R;
import com.example.shopapp.ViewHolder.StockViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Find extends AppCompatActivity {
    ImageView Profile1, CreateStock, Find1, HomeMain1;

    RecyclerView recyclerView;
    DatabaseReference dref;
    FirebaseRecyclerOptions<Stock> options;
    FirebaseRecyclerAdapter<Stock, StockViewHolder> adapter;
    EditText editText;
    ArrayList<Stock> arrayList;

    //ImageView animalExplore, Profile1, Filter1, Find1, HomeMain1;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference drUser = database.getReference("User");
    String cUser;
    {
        assert currentUser != null;
        cUser = currentUser.getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_layout);

        recyclerView = findViewById(R.id.recyclerview);//
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();

        editText = findViewById(R.id.inputVariable);

        sideNavMenu();



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    searching(editable.toString());
                }
                else {
                    searching("");
                }
            }
        });

        dref = FirebaseDatabase.getInstance().getReference().child("Stock");

        options = new FirebaseRecyclerOptions.Builder<Stock>()
                .setQuery(dref,Stock.class).build();

        adapter = new FirebaseRecyclerAdapter<Stock, StockViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StockViewHolder holder, int position, @NonNull Stock model) {
                holder.title.setText(model.getTitle());
                holder.dead.setText(model.getTitle());
                holder.desc.setText(model.getManufacturer());
                holder.stat.setText(model.getPrice());
                holder.quantity.setText(model.getQuantity());
                holder.id.setText(model.getStockId());
                //holder.idfromUser.setText(model.getUsid());

                // Get the reference to the image URL
                DatabaseReference imageUrlRef = FirebaseDatabase.getInstance().getReference().child("Stock").child(model.getStockId()).child("imageUrl");

                // Add a value event listener to get the image URL
                imageUrlRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get the image URL value from the dataSnapshot
                        String imageUrl = dataSnapshot.getValue(String.class);

                        // Set the image using Picasso library
                        Picasso.get()
                                .load(imageUrl)
                                .fit()
                                .centerCrop()
                                .into(holder.picOfStock);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });


            }

            @NonNull
            @Override
            public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.stock_rcv_layout,parent,false);

                return new StockViewHolder(v, parent.getContext());
            }


        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    private void sideNavMenu() {
        Profile1 = findViewById(R.id.Profile);
        CreateStock = findViewById(R.id.CreateStock);
        Find1 = findViewById(R.id.Find);
        HomeMain1 = findViewById(R.id.HomeMain);


        Profile1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Find.this, Profile.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        HomeMain1.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Find.this, Home.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        CreateStock.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            Intent intent1 = new Intent(Find.this, AddStock.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }


    private void searching(String editable) {
        Query query = dref.orderByChild("category")
                .startAt(editable).endAt(editable+"\uf8ff");

        Query query1 = dref.orderByChild("title")
                .startAt(editable).endAt(editable+"\uf8ff");

        Query query2 = dref.orderByChild("manufacturer")
                .startAt(editable).endAt(editable+"\uf8ff");

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    arrayList.clear();
                    for(DataSnapshot sd: snapshot.getChildren()) {
                        final Stock tlist = sd.getValue(Stock.class);
                        arrayList.add(tlist);
                    }

                    newAdapter newAdapter = new newAdapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(newAdapter);
                    newAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    arrayList.clear();
                    for(DataSnapshot sd: snapshot.getChildren()) {
                        final Stock tlist = sd.getValue(Stock.class);
                        arrayList.add(tlist);
                    }

                    newAdapter newAdapter = new newAdapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(newAdapter);
                    newAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    arrayList.clear();
                    for(DataSnapshot sd: snapshot.getChildren()) {
                        final Stock tlist = sd.getValue(Stock.class);
                        arrayList.add(tlist);
                    }

                    newAdapter newAdapter = new newAdapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(newAdapter);
                    newAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if(adapter!=null)
            adapter.startListening();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }



}
