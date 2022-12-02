package com.example.adminpanel.student_pack;

import android.content.Context;

import com.google.firebase.database.Query;

public class admin_helper extends db_class{
    public admin_helper(Context context) {
        super(context);
    }

    public Query getStudent_order_history(String student_key){
        return myRef.child("OrderHistory").orderByChild("studentKey").equalTo(student_key);
    }

    public Query getStudent_feedback(String feedback){
        return myRef.child("Feedback").orderByChild("studID").equalTo(feedback);
    }

    public Query getbranch(String brnch){
        return myRef.child("Branch").child(brnch);
    }
}
