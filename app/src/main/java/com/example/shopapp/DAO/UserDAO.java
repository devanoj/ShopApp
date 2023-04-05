package com.example.shopapp.DAO;


import com.example.shopapp.Entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO {
    private DatabaseReference databaseReference;

    public UserDAO(User person1, String uid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("User");

        person1.setUid(uid);
        databaseReference.child(uid).setValue(person1);

    }
}
