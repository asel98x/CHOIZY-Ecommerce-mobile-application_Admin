package com.example.adminpanel.admin_company_tabs;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adminpanel.R;
import com.example.adminpanel.adapters.category_list_adapter;
import com.example.adminpanel.setters_getters.category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_company_categories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_company_categories extends Fragment implements category_list_adapter.dataPass{
    FloatingActionButton btn_add_catg;
    RecyclerView recyclerView;
    category_list_adapter mainAdapter;
    SearchView searchView;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    StorageReference storageReference;
    FirebaseStorage mStorage;
    List<category> categoryMdlList;
    category category_detail;
    int Image_Request_Code = 07;
    Uri FilePathUri;
    CircleImageView image2;
    CircleImageView category_img;
    EditText title;
    ProgressDialog progressDialog ;
    EditText category_text,ctgy_update;
    Button submit, cancel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_company_categories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_company_categories.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_company_categories newInstance(String param1, String param2) {
        admin_company_categories fragment = new admin_company_categories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_company_categories, container, false);

        btn_add_catg = view.findViewById(R.id.btn_add_category);
        recyclerView = view.findViewById(R.id.catgr_rv);
        searchView = view.findViewById(R.id.catgr_search);
        category_text = view.findViewById(R.id.category_name);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Category");
        storageReference = FirebaseStorage.getInstance().getReference("Category");
        mStorage = FirebaseStorage.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        categoryMdlList = new ArrayList<category>();
        mainAdapter = new category_list_adapter(getActivity(),categoryMdlList);
        mainAdapter.setPass(this);

        recyclerView.setAdapter(mainAdapter);
        progressDialog = new ProgressDialog(getActivity());
        btn_add_catg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_showDialog();
            }
        });
        category_search("");
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                categoryMdlList.clear();
//                for(DataSnapshot one:snapshot.getChildren()){
//                    category catg1 = one.getValue(category.class);
//                    catg1.setKeey(one.getKey());
//                    categoryMdlList.add(catg1);
//                }
//                mainAdapter.setCategoryModelList(categoryMdlList);
//                mainAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                category_search(s);
                return true;
            }
        });


        return view;
    }

    public void category_search(String srch){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                categoryMdlList.clear();
                for(DataSnapshot one:snapshot.getChildren()){
                    category catg1 = one.getValue(category.class);
                    catg1.setKeey(one.getKey());
                    if(!srch.trim().isEmpty()){
                        if(catg1.getCategory_name().toLowerCase().contains(srch.toLowerCase())){
                            categoryMdlList.add(catg1);
                        }
                    }else{
                        categoryMdlList.add(catg1);
                    }

                }
                mainAdapter.setCategoryModelList(categoryMdlList);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                category_img.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }



    @Override
    public void editButton(int position) {
        pass_category_update_dialogBox(position);
    }

    private void pass_category_update_dialogBox(int position) {
        final androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.layout_dialog_category_update,null);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Category");

        submit = mView.findViewById(R.id.category_name_submit);
        cancel = mView.findViewById(R.id.category_name_cancel);
        //ctgy_update = mView.findViewById(R.id.category_name_update_txt);


        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        //ctgy_update.setText(ctg.getCategory_name());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout category = mView.findViewById(R.id.category_name_update_txt);

                if(!category.getEditText().getText().toString().isEmpty()) {
                    category_detail=categoryMdlList.get(position);
                    category_detail.setCategory_name(category.getEditText().getText().toString().trim());
                    mRef.child(category_detail.getKeey()).setValue(category_detail);
                    Toast.makeText(getActivity(), "Category name changed Successfully ", Toast.LENGTH_LONG).show();

                    alertDialog.dismiss();
                }
                else{
                    Toast.makeText(getActivity(), "Category name can't be empty!", Toast.LENGTH_LONG).show();
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

    @Override
    public void deleteButton(int position) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Category delete?");
        builder.setMessage("Are you sure that you want to delete category?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseStorage.getInstance().getReferenceFromUrl(categoryMdlList.get(position).getImageURL()).delete();
                FirebaseDatabase.getInstance().getReference().child("Category")
                        .child(categoryMdlList.get(position).getKeey()).removeValue();
                Toast.makeText(getContext(), "Category deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

//    public void search(String str){
//        ArrayList<category> myLlist = new ArrayList<>();
//        for(category object:categoryMdlList){
//            if(object.getCategory_name().toLowerCase().contains(str.toLowerCase())){
//                myLlist.add(object);
//            }
//        }
//        category_list_adapter adapterclass = new category_list_adapter(getContext(),myLlist);
//        recyclerView.setAdapter(adapterclass);
//    }

    public void btn_showDialog(){
        final androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.layout_dialog,null);
        submit = mView.findViewById(R.id.category_add);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        EditText category_text = mView.findViewById(R.id.category_name);
        EditText category_colour = mView.findViewById(R.id.category_color);
        category_img = mView.findViewById(R.id.catgr_pic);


        category_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(category_text.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Category name can't be empty!", Toast.LENGTH_LONG).show();
                }else if(category_colour.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Category colour can't be empty!", Toast.LENGTH_LONG).show();
                }else{
                    if (FilePathUri != null) {

                        progressDialog.setTitle("Category data is Uploading...");
                        progressDialog.show();
                        StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
                        storageReference2.putFile(FilePathUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                                String category = category_text.getText().toString().trim();
                                                String category_color = category_colour.getText().toString().trim();
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "Admin data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                                category_text.setText(null);
                                                category_colour.setText(null);
                                                category_text.requestFocus();
                                                category_img.setImageResource(R.drawable.baseline_category);
                                                @SuppressWarnings("VisibleForTests")
                                                category category_details = new category(category,category_color, task.getResult().toString());
                                                String ImageUploadId = mRef.push().getKey();
                                                mRef.child(ImageUploadId).setValue(category_details);
                                            }
                                        });


                                    }
                                });
                    }
                    else {

                        Toast.makeText(getActivity(), "Please Select Image", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
        alertDialog.show();
    }



    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver =getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }

}