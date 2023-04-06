package com.example.shopapp.DAO;

import com.example.shopapp.Entity.Comment;
import com.example.shopapp.Entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentDAO {
    private DatabaseReference databaseReference;

    public CommentDAO(Comment c1) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Comment");
        String stockId = databaseReference.push().getKey();
        c1.setcID(stockId);
        databaseReference.child(stockId).setValue(c1);

    }
}
