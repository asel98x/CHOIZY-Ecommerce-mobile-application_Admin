package com.example.adminpanel.admin_ads_tabs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adminpanel.R;
import com.example.adminpanel.adapters.ads_upcomming_list_adapter;
import com.example.adminpanel.setters_getters.upcommingAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_ads_upcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_ads_upcoming extends Fragment implements ads_upcomming_list_adapter.dataPass{
    FloatingActionButton btn_add_upcomming_ads;
    RecyclerView recyclerView;
    ads_upcomming_list_adapter mainAdapter;
    SearchView searchView;
    Button submit;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    StorageReference storageReference;
    ImageView ad_img;
    List<upcommingAds> upcommingAdsMdlList;
    int Image_Request_Code = 07;
    Uri FilePathUri;
    ProgressDialog progressDialog ;
    ImageView image2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_ads_upcoming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_ads_recomended.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_ads_upcoming newInstance(String param1, String param2) {
        admin_ads_upcoming fragment = new admin_ads_upcoming();
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
        View view = inflater.inflate(R.layout.fragment_admin_ads_recomended, container, false);

        btn_add_upcomming_ads = view.findViewById(R.id.btn_add_ads_upcmn);
        recyclerView = view.findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("UpcomingAD");
        mStorage = FirebaseStorage.getInstance();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        upcommingAdsMdlList = new ArrayList<upcommingAds>();
        mainAdapter = new ads_upcomming_list_adapter(getActivity(),upcommingAdsMdlList);
        mainAdapter.setPass(this);
        progressDialog = new ProgressDialog(getActivity());
        recyclerView.setAdapter(mainAdapter);

        btn_add_upcomming_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_showDialog();
            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                upcommingAdsMdlList.clear();
                for(DataSnapshot one:snapshot.getChildren()){
                    upcommingAds upcmn1 = one.getValue(upcommingAds.class);
                    upcmn1.setKeey(one.getKey());
                    upcommingAdsMdlList.add(upcmn1);
                }
                mainAdapter.setUpcommingAdsModelList(upcommingAdsMdlList);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                ad_img.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteButton(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Upcomming ad delete?");
        builder.setMessage("Are you sure that you want to delete ad?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseStorage.getInstance().getReferenceFromUrl(upcommingAdsMdlList.get(position).getUrl()).delete();
                FirebaseDatabase.getInstance().getReference().child("UpcomingAD")
                        .child(upcommingAdsMdlList.get(position).getKeey()).removeValue();
                Toast.makeText(getContext(), "Upcomming ad deleted!", Toast.LENGTH_SHORT).show();
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

    public void btn_showDialog(){
        final androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        View mView = getLayoutInflater().inflate(R.layout.ads_upcomming_layout_dialog,null);
        submit = mView.findViewById(R.id.ad_upcoming_submit);
        alert.setView(mView);
        final androidx.appcompat.app.AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        ad_img = mView.findViewById(R.id.ad_upcoming_add);

        ad_img.setOnClickListener(new View.OnClickListener() {
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
                if (FilePathUri != null) {

                    progressDialog.setTitle("advertisment is Uploading...");
                    progressDialog.show();
                    StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("UpcomingAD").child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
                    storageReference2.putFile(FilePathUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Advertisment Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                            upcommingAds upcmn1 = new upcommingAds();
                                            upcmn1.setUrl(task.getResult().toString());
                                            upcmn1.setType("university");
                                            mRef.push().setValue(upcmn1);
                                            ad_img.setImageResource(R.drawable.baseline_business);
                                            @SuppressWarnings("VisibleForTests")
                                            String ImageUploadId = mRef.push().getKey();


                                        }
                                    });


                                }
                            });
                } else {

                    Toast.makeText(getActivity(), "Please Select Image", Toast.LENGTH_LONG).show();

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