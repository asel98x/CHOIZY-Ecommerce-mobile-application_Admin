package com.example.adminpanel.student_pack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import org.jetbrains.annotations.NotNull;

public class student_pack_adapter extends FragmentStateAdapter {
    Bundle bundle;
    public student_pack_adapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle,String stud_id) {
        super(fragmentManager, lifecycle);
        bundle = new Bundle();

        bundle.putString("param1",stud_id);
        bundle.putString("param2","hello2");
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                student_buy_history history = new student_buy_history();
                history.setArguments(bundle);
                return  history;
            case 1:
                student_feedback feedback = new student_feedback();
                feedback.setArguments(bundle);
                return feedback;




        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
