package com.example.adminpanel.student_pack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.adminpanel.R;
import com.google.android.material.tabs.TabLayout;

public class student_pack extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    student_pack_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pack);

        tabLayout = findViewById(R.id.tab_layout_stud_pack);
        pager2 = findViewById(R.id.stud_pack_view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        Bundle bn = getIntent().getExtras();
        String id = bn.getString("StudentId");
        adapter = new student_pack_adapter(fm,getLifecycle(),id);
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Ordered history"));
        tabLayout.addTab(tabLayout.newTab().setText("Feedbacks"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}