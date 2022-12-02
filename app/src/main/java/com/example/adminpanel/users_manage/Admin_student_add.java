package com.example.adminpanel.users_manage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminpanel.R;
import com.example.adminpanel.connection.network_change_listner;
import com.example.adminpanel.setters_getters.student;
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
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_student_add extends AppCompatActivity {
    AutoCompleteTextView stud_gender;
    Button sbmt;
    CircleImageView Sprofile;
    EditText etDate;
    TextInputLayout id,name,mail,gndr,nic,mbl,pass,rePass,birthday;
    TextView lon;
    ImageView brws;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference,databaseReference3;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    DatePickerDialog.OnDateSetListener setListener;
    network_change_listner networkChangeListener = new network_change_listner();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_add);

        stud_gender = findViewById(R.id.autoCompleteTextView);

        storageReference = FirebaseStorage.getInstance().getReference("Student");
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        databaseReference3 = FirebaseDatabase.getInstance().getReference();
        brws = findViewById(R.id.imgBrowse);
        sbmt = findViewById(R.id.btn_submit);
        Sprofile = findViewById(R.id.stud_pic);
        id = findViewById(R.id.stud_id);
        name = findViewById(R.id.stud_name);
        mail = findViewById(R.id.stud_email);
        gndr = findViewById(R.id.stud_gender);
        etDate = findViewById(R.id.et_date);
        birthday = findViewById(R.id.bDate);
        nic = findViewById(R.id.stud_nic);
        mbl = findViewById(R.id.stud_mobile);
        pass = findViewById(R.id.stud_password);
        rePass = findViewById(R.id.stud_repassword);
        lon = findViewById(R.id.loan);

        progressDialog = new ProgressDialog(Admin_student_add.this);


        String[] gender = {"Male", "Female"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.admin_stud_gender_dropdown,gender);

        //to make defoult value
        stud_gender.setText(arrayAdapter.getItem(0).toString(),false);
        stud_gender.setAdapter(arrayAdapter);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Admin_student_add.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


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
                upload_student_details();
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
                Sprofile.setImageBitmap(bitmap);
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

    public void upload_student_details(){
        id.setError(null);
        name.setError(null);
        mail.setError(null);
        etDate.setError(null);
        nic.setError(null);
        mbl.setError(null);
        pass.setError(null);
        rePass.setError(null);

        if(id.getEditText().getText().toString().trim().isEmpty()){
            id.setError("Student id can not be empty!");
            id.requestFocus();
        }else if(name.getEditText().getText().toString().trim().isEmpty()){
            name.setError("Student name can not be empty!");
            name.requestFocus();
        }else if(mail.getEditText().getText().toString().trim().isEmpty()){
            mail.setError("Student mail can not be empty!");
            mail.requestFocus();
        }else if(birthday.getEditText().getText().toString().trim().isEmpty()){
            etDate.setError("Student birthday can not be empty!");
            etDate.requestFocus();
        }else if(nic.getEditText().getText().toString().trim().isEmpty()){
            nic.setError("Student NIC can not be empty!");
            nic.requestFocus();
        }else if(mbl.getEditText().getText().toString().trim().isEmpty()){
            mbl.setError("Student mobile can not be empty!");
            mbl.requestFocus();
        }else if(mbl.getEditText().getText().toString().trim().length()<10){
            mbl.setError("Number count should be 10");
            mbl.requestFocus();
        }else if(pass.getEditText().getText().toString().trim().isEmpty()){
            pass.setError("Student password can not be empty!");
            pass.requestFocus();
        }else if(pass.getEditText().getText().toString().trim().length()<8){
            pass.setError("Characters should be more than 8");
            pass.requestFocus();
        }
        else if(!rePass.getEditText().getText().toString().equals(pass.getEditText().getText().toString())){
            rePass.setError("Password does not match!");
            rePass.requestFocus();
        }else {
            databaseReference3.child("Student").orderByChild("student_email").equalTo(mail.getEditText().getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                    databaseReference3.removeEventListener(this);
                    if(dataSnapshot.exists()){
                        mail.setError("This Email is already taken");
                        mail.requestFocus();
                        return;
                    }else {
                        Insert_student();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                }
            });
        }


    }

    public void Insert_student(){
        if (FilePathUri != null) {

            progressDialog.setTitle("Student data is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Uri> task) {

                                    String student_id = id.getEditText().getText().toString().trim();
                                    String student_name = name.getEditText().getText().toString().trim();
                                    String student_email = mail.getEditText().getText().toString().trim();
                                    String student_gender = gndr.getEditText().getText().toString().trim();
                                    String student_bday = etDate.getText().toString().trim();
                                    String student_nic = nic.getEditText().getText().toString().trim();
                                    String student_mobile = mbl.getEditText().getText().toString().trim();
                                    String student_password = pass.getEditText().getText().toString().trim();
                                    //String student_loan = pass.getEditText().getText().toString().trim();
                                    double student_loan = 5000;


                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Student data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                    clear_data();
                                    @SuppressWarnings("VisibleForTests")
                                    student student_details = new student(student_id, student_name, student_email, student_gender, student_bday,
                                            student_nic, student_mobile, student_password,student_loan, task.getResult().toString());
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(student_details);

                                }
                            });


                        }
                    });
        }else {

            Toast.makeText(Admin_student_add.this, "Please Select Image", Toast.LENGTH_LONG).show();

        }
    }

    public void clear_data(){
        id.getEditText().setText(null);
        name.getEditText().setText(null);
        mail.getEditText().setText(null);
        gndr.getEditText().setText(null);
        etDate.setText(null);
        nic.getEditText().setText(null);
        mbl.getEditText().setText(null);
        pass.getEditText().setText(null);
        rePass.getEditText().setText(null);
        Sprofile.setImageResource(R.drawable.student_male);
        id.requestFocus();
//        Intent intent = new Intent(Admin_student_add.this, AdminStudentFragment.class);
//        startActivity(intent);
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