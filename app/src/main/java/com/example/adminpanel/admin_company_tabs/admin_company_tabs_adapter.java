package com.example.adminpanel.admin_company_tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class admin_company_tabs_adapter extends FragmentStateAdapter {
    public admin_company_tabs_adapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new admin_company_company();
            case 1:
                return new admin_company_branch();
            case 2:
                return new admin_company_categories();




        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
