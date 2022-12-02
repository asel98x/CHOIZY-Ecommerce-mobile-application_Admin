package com.example.adminpanel.users_manage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.company;
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

import static android.app.Activity.RESULT_OK;

public class admin_company_update extends BottomSheetDialogFragment {
    ImageView image2;
    TextInputLayout name,email,mobile,disc,about,fetures,terms;
    Button btnUpdate,submit,cancel;
    ImageView btnImage;
    company comp;
    int Image_Request_Code = 07;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef,myRef2;
    FirebaseStorage mStorage;
    StorageReference sRef;
    Uri FilePathUri;
    TextView pass_update;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_update_popup,container,false);

        myRef2 = FirebaseDatabase.getInstance().getReference("Users");
        name = view.findViewById(R.id.com_name);
        email = view.findViewById(R.id.com_email);
        mobile = view.findViewById(R.id.com_mobile);
        disc = view.findViewById(R.id.com_dis_range_update);
        about = view.findViewById(R.id.com_about_update);
        fetures = view.findViewById(R.id.com_features_update);
        terms = view.findViewById(R.id.com_tearms_update);
        pass_update = view.findViewById(R.id.passUpdate);
        image2 = view.findViewById(R.id.img1);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnImage = view.findViewById(R.id.imgBrowse);

        name.getEditText().setText(comp.getCompany_name());
        email.getEditText().setText(comp.getCompany_email());
        mobile.getEditText().setText(comp.getCompany_mobile());
        disc.getEditText().setText(comp.getCompanyDiscountRange());
        about.getEditText().setText(comp.getCompanyAbout());
        fetures.getEditText().setText(comp.getCompanyFeatures());
        terms.getEditText().setText(comp.getCompanyTerms());

//        name.setText(comp.getCompany_name());
//        email.setText(comp.getCompany_email());
//        mobile.setText(comp.getCompany_mobile());
        Picasso.get().load(comp.getImageURL()).fit().into(image2);



        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        pass_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_update_dialogBox();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setError(null);
                email.setError(null);
                mobile.setError(null);

                if (email.getEditText().getText().toString().trim().isEmpty()) {
                    email.setError("Company email can not be empty!");
                    email.requestFocus();
                }else if(name.getEditText().getText().toString().trim().isEmpty()){
                    name.setError("Company name can not be empty!");
                    name.requestFocus();
                }else if(mobile.getEditText().getText().toString().trim().isEmpty()){
                    mobile.setError("Company mobile can not be empty!");
                    mobile.requestFocus();
                }else if(mobile.getEditText().getText().toString().trim().length()<10){
                    mobile.setError("Company count should be 10");
                    mobile.requestFocus();
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
                }
                else{

                Map<String, Object> map = new HashMap<>();
                map.put("company_name", name.getEditText().getText().toString());
                map.put("company_email", email.getEditText().getText().toString().toLowerCase());
                map.put("company_mobile", mobile.getEditText().getText().toString());
                map.put("companyDiscountRange", disc.getEditText().getText().toString());
                map.put("companyAbout", about.getEditText().getText().toString());
                map.put("companyFeatures", fetures.getEditText().getText().toString());
                map.put("companyTerms", terms.getEditText().getText().toString());

                if(email.getEditText().getText().toString().toLowerCase().equals(comp.getCompany_email())){
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
                                myRef2.child(comp.getKeey()).updateChildren(map2);
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
        return view;
    }

    public void update_details(Map<String, Object> map){
        FirebaseDatabase.getInstance().getReference().child("Company")
                .child(comp.getKeey()).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Company details updated successfully", Toast.LENGTH_SHORT).show();
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

    public admin_company_update(company comp) {
        this.comp = comp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                image2.setImageBitmap(bitmap);
                String url = comp.getImageURL();
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference("Company");
                mStorage = FirebaseStorage.getInstance();
                sRef = FirebaseStorage.getInstance().getReference("Company").child(System.currentTimeMillis()
                        + "." + getFileExtension(FilePathUri));

                sRef.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                mStorage.getReferenceFromUrl(url).delete();
                                comp.setImageURL(task.getResult().toString());
                                mRef.child(comp.getKeey()).setValue(comp);
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

    public void pass_update_dialogBox(){
        final androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.layout_dialog_company_password,null);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Company");

        submit = mView.findViewById(R.id.company_password_submit);
        cancel = mView.findViewById(R.id.company_password_cancel);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout password = mView.findViewById(R.id.new_company_password);
                TextInputLayout repassword = mView.findViewById(R.id.retype_company_password);
                password.setError(null);

                if(!password.getEditText().getText().toString().trim().isEmpty()) {

                    if(password.getEditText().getText().toString().equals(repassword.getEditText().getText().toString())){
                        comp.setCompany_password(password.getEditText().getText().toString().trim());
                        mRef.child(comp.getKeey()).setValue(comp);

                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("password",password.getEditText().getText().toString().toLowerCase());

                        myRef2.child(comp.getKeey()).updateChildren(map2);
                        Toast.makeText(getActivity(), "Company password changed Successfully ", Toast.LENGTH_LONG).show();

                        alertDialog.dismiss();
                    }else if(password.getEditText().getText().toString().trim().length()<8){
                        password.setError("Characters should be more than 8");
                        password.requestFocus();
                    }else if(!repassword.getEditText().getText().toString().equals(repassword.getEditText().getText().toString())){
                        repassword.setError("Password does not match!");
                        repassword.requestFocus();
                    }else{
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
    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(requireActivity().getContentResolver().getType(uri));
    }
}
