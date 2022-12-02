package com.example.adminpanel;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.adminpanel.admin_bottom_navigation.AdminAdminFragment;
import com.example.adminpanel.admin_bottom_navigation.AdminAdsFragment;
import com.example.adminpanel.admin_bottom_navigation.AdminCompanyFragment;
import com.example.adminpanel.admin_bottom_navigation.AdminProfileFragment;
import com.example.adminpanel.admin_bottom_navigation.AdminStudentFragment;
import com.example.adminpanel.connection.network_change_listner;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    network_change_listner networkChangeListener = new network_change_listner();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));
        //getSupportActionBar().hide();



        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_student,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminStudentFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_student:
                        fragment = new AdminStudentFragment();
                        break;
                    case R.id.bottom_nav_company:
                        fragment = new AdminCompanyFragment();
                        break;
                    case R.id.bottom_nav_ads:
                        fragment = new AdminAdsFragment();
                        break;
                    case R.id.bottom_nav_admin:
                        fragment = new AdminAdminFragment();
                        break;
                    case R.id.bottom_nav_profile:d:
                    fragment = new AdminProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}