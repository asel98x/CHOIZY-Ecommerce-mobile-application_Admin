package com.example.adminpanel.student_pack;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class db_class {
    private final Context context;
    FirebaseDatabase db;
    DatabaseReference myRef;
    FirebaseStorage firebaseStorage ;
    StorageReference myStorage;

    public db_class(Context context) {
        this.context = context;
        db = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        myStorage = firebaseStorage.getReference();
        myRef = db.getReference();

    }
}
