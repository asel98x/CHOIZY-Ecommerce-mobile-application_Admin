package com.example.adminpanel.users_manage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.adminpanel.setters_getters.category;
import com.example.adminpanel.setters_getters.company;
import com.example.adminpanel.setters_getters.user;
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
import java.util.ArrayList;

public class Admin_company_add extends AppCompatActivity {
    AutoCompleteTextView company_categories;
    ImageView Com_pic,brws;
    Button sbmt;
    TextInputLayout Cemail,Cname,Cmobile,Ccategory,disc,about,fetures,terms,pass,rePass;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    ArrayList<String> catgr;
    network_change_listner networkChangeListener = new network_change_listner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_company_add);

        company_categories = findViewById(R.id.autoCompleteTextView);
        storageReference = FirebaseStorage.getInstance().getReference("Company");
        databaseReference = FirebaseDatabase.getInstance().getReference("Company");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Category");
        databaseReference3 = FirebaseDatabase.getInstance().getReference();
        brws = findViewById(R.id.imgBrowse);
        sbmt = findViewById(R.id.btn_submit);
        Com_pic = findViewById(R.id.company_pic);
        Cname = findViewById(R.id.com_name);
        Cmobile = findViewById(R.id.com_mobile);
        Cemail = findViewById(R.id.com_email);
        Ccategory = findViewById(R.id.com_categories);
        disc = findViewById(R.id.com_dis_range);
        about = findViewById(R.id.com_about);
        fetures = findViewById(R.id.com_features);
        terms = findViewById(R.id.com_tearms);
        pass = findViewById(R.id.com_password);
        rePass = findViewById(R.id.compn_repassword);
        catgr=new ArrayList<>();

        progressDialog = new ProgressDialog(Admin_company_add.this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.admin_stud_gender_dropdown,catgr);
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Ccategory.setError("No category found!");
                }else{
                    Ccategory.setError(null);
                    arrayAdapter.clear();
                    for (DataSnapshot passCtgr:snapshot.getChildren()) {
                        category ct1 = passCtgr.getValue(category.class);
                        arrayAdapter.add(ct1.getCategory_name());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Ccategory.setError(error.getMessage());
            }
        });


        //to make defoult value
        //company_categories.setText(arrayAdapter.getItem(0).toString(),false);
        company_categories.setAdapter(arrayAdapter);

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
                upload_company_details();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                Com_pic.setImageBitmap(bitmap);
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

    public void upload_company_details(){
        Cemail.setError(null);
        Cname.setError(null);
        Cmobile.setError(null);
        pass.setError(null);
        rePass.setError(null);

        if(Cemail.getEditText().getText().toString().trim().isEmpty()) {
            Cemail.setError("Company email can not be empty!");
            Cemail.requestFocus();

        }if(Cemail.getEditText().getText().toString().trim().isEmpty()) {
            Cemail.setError("Company email can not be empty!");
            Cemail.requestFocus();

        }else if(Cname.getEditText().getText().toString().trim().isEmpty()){
            Cname.setError("Company name can not be empty!");
            Cname.requestFocus();
        }else if(Cmobile.getEditText().getText().toString().trim().isEmpty()){
            Cmobile.setError("Company mobile can not be empty!");
            Cmobile.requestFocus();
        }else if(Cmobile.getEditText().getText().toString().trim().length()<10){
            Cmobile.setError("Number count should be 10");
            Cmobile.requestFocus();
        }else if(disc.getEditText().getText().toString().trim().isEmpty()){
            disc.setError("Discount range can not be empty!");
            disc.requestFocus();
        }else if(about.getEditText().getText().toString().trim().isEmpty()){
            about.setError("About field can not be empty!");
            about.requestFocus();
        }else if(fetures.getEditText().getText().toString().trim().isEmpty()){
            fetures.setError("Features field can not be empty!");
            fetures.requestFocus();
        }else if(terms.getEditText().getText().toString().trim().isEmpty()){
            terms.setError("Terms & conditions field can not be empty!");
            terms.requestFocus();
        }else if(pass.getEditText().getText().toString().trim().length()<8){
            pass.setError("Characters should be more than 8");
            pass.requestFocus();
        }
        else if(!rePass.getEditText().getText().toString().equals(pass.getEditText().getText().toString())){
            rePass.setError("Password does not match!");
            rePass.requestFocus();

        }else{
            databaseReference3.child("Users").orderByChild("email").equalTo(Cemail.getEditText().getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                    databaseReference3.removeEventListener(this);
                    if(dataSnapshot.exists()){
                        Cemail.setError("This Email is already taken");
                        Cemail.requestFocus();
                        return;
                    }else {
                        insertCompany();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void insertCompany() {
        if (FilePathUri != null) {
            user user1 = new user(Cemail.getEditText().getText().toString().toLowerCase(),pass.getEditText().getText().toString(),"Company");
            String uID = databaseReference3.child("Users").push().getKey();
            databaseReference3.child("Users").child(uID).setValue(user1);
            progressDialog.setTitle("Company data is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Uri> task) {

                                    String company_email = Cemail.getEditText().getText().toString().toLowerCase().trim();
                                    String company_name = Cname.getEditText().getText().toString().trim();
                                    String company_mobile = Cmobile.getEditText().getText().toString().trim();
                                    String company_category = Ccategory.getEditText().getText().toString().trim();
                                    String companyDiscountRange = disc.getEditText().getText().toString().trim();
                                    String companyAbout = about.getEditText().getText().toString().trim();
                                    String companyFeatures = fetures.getEditText().getText().toString().trim();
                                    String companyTerms = terms.getEditText().getText().toString().trim();
                                    String company_password = pass.getEditText().getText().toString().trim();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Company data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                    clear_data();
                                    @SuppressWarnings("VisibleForTests")
                                    company company_details = new company(company_email, company_name, company_mobile, company_category,companyDiscountRange,companyAbout,companyFeatures,companyTerms, company_password, task.getResult().toString());
                                    String ImageUploadId = databaseReference.child(uID).getKey();
                                    databaseReference.child(uID).setValue(company_details);
                                }
                            });


                        }
                    });
        } else {

            Toast.makeText(Admin_company_add.this, "Please Select Image", Toast.LENGTH_LONG).show();

        }
    }

    private void clear_data() {
        Cemail.getEditText().setText(null);
        Cname.getEditText().setText(null);
        Cmobile.getEditText().setText(null);
        Ccategory.getEditText().setText(null);
        disc.getEditText().setText(null);
        about.getEditText().setText(null);
        fetures.getEditText().setText(null);
        terms.getEditText().setText(null);
        pass.getEditText().setText(null);
        rePass.getEditText().setText(null);
        Com_pic.setImageResource(R.drawable.company_icon);
        Cemail.requestFocus();
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