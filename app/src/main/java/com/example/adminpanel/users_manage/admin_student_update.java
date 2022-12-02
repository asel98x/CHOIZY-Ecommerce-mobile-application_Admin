package com.example.adminpanel.users_manage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class admin_student_update extends BottomSheetDialogFragment {
    CircleImageView image2;
    TextInputLayout birthday,gender,NIC,email,mobile;
    TextView loan;
    EditText name;
    Button btnUpdate,submit,cancel;
    ImageButton reset;
    ImageView btnImage;
    student stud;
    int Image_Request_Code = 07;
    Uri FilePathUri;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef,myRef2;
    FirebaseStorage mStorage;
    StorageReference sRef;
    TextView student_pass_update;
    AutoCompleteTextView stud_gender;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_popup,container,false);

        myRef2 = FirebaseDatabase.getInstance().getReference("Student");
         name = view.findViewById(R.id.stud_textName);
         birthday = view.findViewById(R.id.stud_textBirthday);
         gender = view.findViewById(R.id.stud_textGender);
         NIC = view.findViewById(R.id.stud_textNIC);
         email = view.findViewById(R.id.stud_textEmail);
         mobile = view.findViewById(R.id.stud_textMobile);
         image2 = view.findViewById(R.id.stud_img1);
         loan = view.findViewById(R.id.av_loan);
        reset = view.findViewById(R.id.btn_reset);
         student_pass_update = view.findViewById(R.id.stud_passUpdate);
         btnUpdate = view.findViewById(R.id.stud_btnUpdate);
         btnImage = view.findViewById(R.id.stud_imgBrowse);
        stud_gender = view.findViewById(R.id.autoCompleteTextView);

        String[] gender = {"Male", "Female"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.admin_stud_gender_dropdown,gender);

        //to make defoult value
        stud_gender.setText(arrayAdapter.getItem(0).toString(),false);
        stud_gender.setAdapter(arrayAdapter);

        name.setText(stud.getStudent_name());

        birthday.getEditText().setText(stud.getStudent_bday());
        if(stud.getStudent_gender().equals("Male")){
            stud_gender.setText("Male",false);
        }else{
            stud_gender.setText("Female",false);
        }
        //stud_gender.setSelection(stud.getStudent_gender());
        //gender.getEditText().setText(stud.getStudent_gender());
        NIC.getEditText().setText(stud.getStudent_nic());
        email.getEditText().setText(stud.getStudent_email());
        mobile.getEditText().setText(stud.getStudent_mobile());
        loan.setText( "RS."+stud.getStudent_loan());
        Picasso.get().load(stud.getImageURL()).centerCrop().fit().into(image2);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setError(null);
                birthday.setError(null);
                NIC.setError(null);
                email.setError(null);
                mobile.setError(null);

                if (name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Student name can not be empty!", Toast.LENGTH_SHORT).show();
                }else if(birthday.getEditText().getText().toString().trim().isEmpty()){
                    birthday.setError("Student birthday can not be empty!");
                    birthday.requestFocus();
                }else if(NIC.getEditText().getText().toString().trim().isEmpty()){
                    NIC.setError("Student NIC can not be empty!");
                    NIC.requestFocus();
                }else if(email.getEditText().getText().toString().trim().isEmpty()){
                    email.setError("Student email can not be empty!");
                    email.requestFocus();
                }else if(mobile.getEditText().getText().toString().trim().isEmpty()){
                    mobile.setError("Student mobile can not be empty!");
                    mobile.requestFocus();
                }else if(mobile.getEditText().getText().toString().trim().length()<10){
                    mobile.setError("Number count should be 10");
                    mobile.requestFocus();
                }
                else{

                Map<String, Object> map = new HashMap<>();
                map.put("student_name", name.getText().toString());
                map.put("student_bday", birthday.getEditText().getText().toString());
                map.put("student_gender", stud_gender.getText().toString());
                map.put("student_nic", NIC.getEditText().getText().toString());
                map.put("student_email", email.getEditText().getText().toString());
                map.put("student_mobile", mobile.getEditText().getText().toString());
                //map.put("imageURL", stud.getImageURL());
                //map.put("imageURL",image2.getimag
                    if(email.getEditText().getText().toString().toLowerCase().equals(stud.getStudent_email())){
                        update_details(map);
                    }else{

                        myRef2.orderByChild("email").equalTo(email.getEditText().getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                myRef2.removeEventListener(this);
                                if(snapshot.exists()){
                                    email.setError("This Email is already taken");
                                }else{
                                    Map<String, Object> map2 = new HashMap<>();
                                    map2.put("email",email.getEditText().getText().toString().toLowerCase());
                                    myRef2.child(stud.getKeey()).updateChildren(map2);
                                    update_details(map);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }


            }
        }
        });

        student_pass_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_pass_update_dialogBox();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Student Loan reset?");
                builder.setMessage("Are you sure that you want to reset student loan?");
                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Map<String, Object> map = new HashMap<>();
                        double student_loan = 5000;
                        map.put("student_loan", student_loan);
                        update_details(map);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
            }
        });

        return view;
    }


    public void update_details(Map<String, Object> map){
        FirebaseDatabase.getInstance().getReference().child("Student")
                .child(stud.getKeey()).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Student updated successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
    }

    public void student_pass_update_dialogBox(){
        final androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.layout_dialog_student_password,null);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Student");

        submit = mView.findViewById(R.id.student_password_submit);
        cancel = mView.findViewById(R.id.student_password_cancel);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                TextInputLayout password = mView.findViewById(R.id.new_student_password);
                TextInputLayout repassword = mView.findViewById(R.id.retype_student_password);
                password.setError(null);

                if(!password.getEditText().getText().toString().trim().isEmpty()) {

                    if(password.getEditText().getText().toString().equals(repassword.getEditText().getText().toString())){
                        stud.setStudent_password(password.getEditText().getText().toString().trim());
                        mRef.child(stud.getKeey()).setValue(stud);
                        Toast.makeText(getActivity(), "Student password changed Successfully ", Toast.LENGTH_LONG).show();

                        alertDialog.dismiss();
                    }else if(password.getEditText().getText().toString().trim().length()<8){
                        password.setError("Characters should be more than 8");
                        password.requestFocus();
                    }else if(!repassword.getEditText().getText().toString().equals(repassword.getEditText().getText().toString())){
                        repassword.setError("Password does not match!");
                        repassword.requestFocus();
                    }
                    else{
                        repassword.setError("Password does not match!");
                        repassword.requestFocus();
                    }


                }
                else{
                    password.setError("Password can't be empty!");
                    password.requestFocus();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public admin_student_update(student stud) {
        this.stud = stud;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                image2.setImageBitmap(bitmap);
                String url = stud.getImageURL();
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference("Student");
                mStorage = FirebaseStorage.getInstance();
                sRef = FirebaseStorage.getInstance().getReference("Student").child(System.currentTimeMillis()
                        + "." + getFileExtension(FilePathUri));

                sRef.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                mStorage.getReferenceFromUrl(url).delete();
                                stud.setImageURL(task.getResult().toString());
                                mRef.child(stud.getKeey()).setValue(stud);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Snackbar.make(getView(),"Error while image uploading!",Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(requireActivity().getContentResolver().getType(uri));
    }
}
