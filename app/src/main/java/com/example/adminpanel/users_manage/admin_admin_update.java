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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.admin;
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

public class admin_admin_update extends BottomSheetDialogFragment {
    CircleImageView image2;
    TextInputLayout address,gender,NIC,email,mobile;
    EditText name;
    Button btnUpdate,submit,cancel;
    ImageView btnImage;
    admin admn;
    int Image_Request_Code = 07;
    Uri FilePathUri;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef,myRef2;
    FirebaseStorage mStorage;
    StorageReference sRef;
    TextView admin_pass_update;
    AutoCompleteTextView admn_gender;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_update_popup,container,false);

        myRef2 = FirebaseDatabase.getInstance().getReference("Admin");
        name = view.findViewById(R.id.admin_textName);
        email = view.findViewById(R.id.admin_textemail);
        gender = view.findViewById(R.id.admin_textGender);
        NIC = view.findViewById(R.id.admin_textNIC);
        address = view.findViewById(R.id.admin_textAddress);
        mobile = view.findViewById(R.id.admin_textMobile);
        image2 = view.findViewById(R.id.admin_img1);
        btnUpdate = view.findViewById(R.id.admin_btnUpdate);
        btnImage = view.findViewById(R.id.admin_imgBrowse);
        admin_pass_update = view.findViewById(R.id.admin_passUpdate);
        admn_gender = view.findViewById(R.id.admn_autoCompleteTextView);

        String[] Agender = {"Male", "Female"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.admin_stud_gender_dropdown,Agender);

        //to make defoult value
        admn_gender.setText(arrayAdapter.getItem(0).toString(),false);
        admn_gender.setAdapter(arrayAdapter);

        if(admn.getAdmin_gender().equals("Male")){
            admn_gender.setText("Male",false);
        }else{
            admn_gender.setText("Female",false);
        }

        name.setText(admn.getAdmin_name());
        email.getEditText().setText(admn.getAdmin_email());
        //gender.getEditText().setText(admn.getAdmin_gender());
        NIC.getEditText().setText(admn.getAdmin_nic());
        address.getEditText().setText(admn.getAdmin_address());
        mobile.getEditText().setText(admn.getAdmin_mobile());
        Picasso.get().load(admn.getImageURL()).centerCrop().fit().into(image2);

        admin_pass_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_pass_update_dialogBox();
            }
        });

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
                email.setError(null);
                name.setError(null);
                NIC.setError(null);
                address.setError(null);
                mobile.setError(null);

                if (name.getText().toString().trim().isEmpty()) {
                    name.setError("Admin name can not be empty!");
                    name.requestFocus();
                }else if (email.getEditText().getText().toString().trim().isEmpty()) {
                    email.setError("Admin email can not be empty!");
                    email.requestFocus();
                }else if(NIC.getEditText().getText().toString().trim().isEmpty()){
                    NIC.setError("Admin NIC can not be empty!");
                    NIC.requestFocus();
                }else if(address.getEditText().getText().toString().trim().isEmpty()){
                    address.setError("Admin address can not be empty!");
                    address.requestFocus();
                }else if(mobile.getEditText().getText().toString().trim().isEmpty()){
                    mobile.setError("Admin mobile can not be empty!");
                    mobile.requestFocus();
                }else if(mobile.getEditText().getText().toString().trim().length()<10){
                    mobile.setError("Number count should be 10");
                    mobile.requestFocus();
                }
                else{
                Map<String, Object> map = new HashMap<>();
                map.put("admin_name", name.getText().toString());
                map.put("admin_email", email.getEditText().getText().toString().toLowerCase());
                map.put("admin_gender", admn_gender.getText().toString());
                map.put("admin_nic", NIC.getEditText().getText().toString());
                map.put("admin_address", address.getEditText().getText().toString());
                map.put("admin_mobile", mobile.getEditText().getText().toString());
                //map.put("imageURL",admn.getImageURL());
                //map.put("imageURL",image2.getimag
                    if(email.getEditText().getText().toString().toLowerCase().equals(admn.getAdmin_email())){
                        update_details(map);
                    }else{

                        myRef2.orderByChild("admin_email").equalTo(email.getEditText().getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                myRef2.removeEventListener(this);
                                if(snapshot.exists()){
                                    email.setError("This Email is already taken");
                                }else{
                                    Map<String, Object> map2 = new HashMap<>();
                                    map2.put("admin_email",email.getEditText().getText().toString().toLowerCase());
                                    myRef2.child(admn.getKeey()).updateChildren(map2);
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
        FirebaseDatabase.getInstance().getReference().child("Admin")
                .child(admn.getKeey()).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Admin details updated successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    public void admin_pass_update_dialogBox(){
        final androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.layout_dialog_admin_password,null);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Admin");

        submit = mView.findViewById(R.id.admin_password_submit);
        cancel = mView.findViewById(R.id.admin_password_cancel);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout password = mView.findViewById(R.id.new_admin_password);
                TextInputLayout repassword = mView.findViewById(R.id.retype_admin_password);

                if(!password.getEditText().getText().toString().trim().isEmpty()) {

                    if(password.getEditText().getText().toString().equals(repassword.getEditText().getText().toString())){
                        admn.setAdmin_password(password.getEditText().getText().toString().trim());
                        mRef.child(admn.getKeey()).setValue(admn);
                        Toast.makeText(getActivity(), "Admin password changed Successfully ", Toast.LENGTH_LONG).show();

                        alertDialog.dismiss();
                    }else{
                        repassword.setError("Password does not match!");
                        repassword.requestFocus();
                    }

                }
                else{
                    Toast.makeText(getActivity(), "Admin password can't be empty!", Toast.LENGTH_LONG).show();
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

    public admin_admin_update(admin admn) {
        this.admn = admn;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                image2.setImageBitmap(bitmap);
                String url = admn.getImageURL();
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference("Admin");
                mStorage = FirebaseStorage.getInstance();
                sRef = FirebaseStorage.getInstance().getReference("Admin").child(System.currentTimeMillis()
                        + "." + getFileExtension(FilePathUri));

                sRef.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                mStorage.getReferenceFromUrl(url).delete();
                                admn.setImageURL(task.getResult().toString());
                                mRef.child(admn.getKeey()).setValue(admn);
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
