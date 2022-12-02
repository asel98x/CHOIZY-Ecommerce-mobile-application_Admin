package com.example.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adminpanel.adapters.SessionManager;
import com.example.adminpanel.connection.network_change_listner;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_login extends AppCompatActivity {
    TextInputLayout username, password;
    Button login;
    DatabaseReference DRef;
    ProgressDialog progressDialog;
    network_change_listner networkChangeListener = new network_change_listner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(admin_login.this, R.color.black));

        username = findViewById(R.id.login_admn_email);
        password = findViewById(R.id.login_admn_password);
        login = findViewById(R.id.ad_btn_login);
        progressDialog = new ProgressDialog(admin_login.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DRef = FirebaseDatabase.getInstance().getReference();
                DRef.child("Admin").orderByChild("admin_email").equalTo(username.getEditText().getText().toString().trim().toLowerCase()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                        DRef.removeEventListener(this);
                        String pswrd = null;
                        String usenme = null;
                        username.setError(null);
                        password.setError(null);

                        if (username.getEditText().getText().toString().isEmpty()) {
                            username.setError("Email can't be empty!");
                            username.requestFocus();
                        } else if (password.getEditText().getText().toString().isEmpty()) {
                            password.setError("password can't be empty!");
                            password.requestFocus();
                        } else {
                            if (snapshot.exists()) {
                                progressDialog.setTitle("Authenticating...");
                                progressDialog.show();

                                String name = null, email = null, mobile = null, gender = null, nic = null, address = null, keey = null, url = null;
                                for (DataSnapshot one : snapshot.getChildren()) {
                                    name = one.child("admin_name").getValue(String.class);
                                    email = one.child("admin_email").getValue(String.class);
                                    mobile = one.child("admin_mobile").getValue(String.class);
                                    gender = one.child("admin_gender").getValue(String.class);
                                    nic = one.child("admin_nic").getValue(String.class);
                                    address = one.child("admin_address").getValue(String.class);
                                    keey = one.getKey();

                                    pswrd = one.child("admin_password").getValue(String.class);
                                    url = one.child("imageURL").getValue(String.class);
                                    break;
                                }
                                if (password.getEditText().getText().toString().trim().equals(pswrd)) {
                                    String finalName = name;
                                    String finalEmail = email;
                                    String finalMobile = mobile;
                                    String finalGender = gender;
                                    String finalNic = nic;
                                    String finalAddress = address;
                                    String finalkeey = keey;
                                    String finalUrl = url;
                                    String finalpswrd = pswrd;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            SessionManager sessionManager = new SessionManager(admin_login.this);
                                            sessionManager.createLoginSession(finalName, finalEmail, finalMobile, finalGender, finalNic, finalAddress,finalkeey, finalUrl,finalpswrd);
                                            startActivity(new Intent(admin_login.this, MainActivity.class));
                                            progressDialog.dismiss();
                                            Toast.makeText(admin_login.this, "successfully loged in!", Toast.LENGTH_SHORT).show();

                                        }
                                    }, 1500);

                                } else {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            password.setError("Password is incorrect!");
                                            password.requestFocus();
                                        }
                                    },1500);

                                }
                            } else {
                                Toast.makeText(admin_login.this, "Username or Passowrd is incorrect", Toast.LENGTH_SHORT).show();
                                username.requestFocus();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                    }
                });
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