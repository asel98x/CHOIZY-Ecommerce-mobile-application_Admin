package com.example.adminpanel.users_manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminpanel.R;
import com.example.adminpanel.connection.network_change_listner;
import com.example.adminpanel.setters_getters.admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_admin_add extends AppCompatActivity {
    AutoCompleteTextView admn_gender;
    Button sbmt;
    CircleImageView Aprofile;
    TextInputLayout name,mail,gndr,nic,address,mbl,pass,rePass;
    ImageView brws;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference,databaseReference3;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    network_change_listner networkChangeListener = new network_change_listner();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_admin_add);

        admn_gender = findViewById(R.id.autoCompleteTextView);

        storageReference = FirebaseStorage.getInstance().getReference("Admin");
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference3 = FirebaseDatabase.getInstance().getReference();
        brws = findViewById(R.id.ad_imgBrowse);
        sbmt = findViewById(R.id.ad_btn_submit);
        Aprofile = findViewById(R.id.admn_pic);
        name = findViewById(R.id.admn_name);
        mail = findViewById(R.id.admn_email);
        address = findViewById(R.id.admn_address);
        gndr = findViewById(R.id.ad_admn_gender);
        nic = findViewById(R.id.ad_admn_nic);
        mbl = findViewById(R.id.ad_admn_mobile);
        pass = findViewById(R.id.admn_password);
        rePass = findViewById(R.id.admin_repassword);

        progressDialog = new ProgressDialog(Admin_admin_add.this);

        String[] gender = {"Male", "Female"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.admin_stud_gender_dropdown,gender);

        //to make defoult value
        admn_gender.setText(arrayAdapter.getItem(0).toString(),false);
        admn_gender.setAdapter(arrayAdapter);

        brws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_admin_details();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                Aprofile.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void upload_admin_details(){
        name.setError(null);
        mail.setError(null);
        nic.setError(null);
        mbl.setError(null);
        address.setError(null);
        pass.setError(null);
        rePass.setError(null);
        if(name.getEditText().getText().toString().trim().isEmpty()){
            name.setError("Admin name can not be empty!");
            name.requestFocus();
        }else if(mail.getEditText().getText().toString().trim().isEmpty()){
            mail.setError("Admin email can not be empty!");
            mail.requestFocus();
        }else if(nic.getEditText().getText().toString().trim().isEmpty()){
            nic.setError("Admin mail can not be empty!");
            nic.requestFocus();
        }else if(address.getEditText().getText().toString().trim().isEmpty()){
            address.setError("Admin address can not be empty!");
            address.requestFocus();
        }else if(mbl.getEditText().getText().toString().trim().isEmpty()) {
            mbl.setError("Admin mobile can not be empty!");
            mbl.requestFocus();
        }else if(mbl.getEditText().getText().toString().trim().length()<10){
            mbl.setError("Number count should be 10");
            mbl.requestFocus();
        }else if(pass.getEditText().getText().toString().trim().isEmpty()){
            pass.setError("Admin password can not be empty!");
            pass.requestFocus();
        }else if(pass.getEditText().getText().toString().trim().length()<8){
            pass.setError("Characters should be more than 8");
            pass.requestFocus();
        }else if(!rePass.getEditText().getText().toString().equals(pass.getEditText().getText().toString())){
            rePass.setError("Password does not match!");
            rePass.requestFocus();
        }
        else{
            databaseReference3.child("Admin").orderByChild("admin_email").equalTo(mail.getEditText().getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                    databaseReference3.removeEventListener(this);
                    if(dataSnapshot.exists()){
                        mail.setError("This Email is already taken");
                        mail.requestFocus();
                        return;
                    }else {
                        insert_admin();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                }
            });
        }


    }

    public void insert_admin(){
        if (FilePathUri != null) {

            progressDialog.setTitle("Admin data is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                    String admin_name = name.getEditText().getText().toString().trim();
                                    String admin_email = mail.getEditText().getText().toString().toLowerCase().trim();
                                    String admin_gender = gndr.getEditText().getText().toString().trim();
                                    String admin_nic = nic.getEditText().getText().toString().trim();
                                    String admin_mobile = mbl.getEditText().getText().toString().trim();
                                    String admin_address = address.getEditText().getText().toString().trim();
                                    String admin_password = pass.getEditText().getText().toString().trim();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Admin data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                    clear_data();
                                    @SuppressWarnings("VisibleForTests")
                                    admin admin_details = new admin(admin_name,admin_email,admin_gender,admin_nic,admin_mobile,admin_address,
                                            admin_password, task.getResult().toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(admin_details);
                                }
                            });


                        }
                    });
        }
        else {

            Toast.makeText(Admin_admin_add.this, "Please Select Image", Toast.LENGTH_LONG).show();

        }
    }

    private void clear_data() {
        name.getEditText().setText(null);
        mail.getEditText().setText(null);
        nic.getEditText().setText(null);
        mbl.getEditText().setText(null);
        address.getEditText().setText(null);
        pass.getEditText().setText(null);
        rePass.getEditText().setText(null);
        Aprofile.setImageResource(R.drawable.baseline_admin_panel);
        name.requestFocus();

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
}