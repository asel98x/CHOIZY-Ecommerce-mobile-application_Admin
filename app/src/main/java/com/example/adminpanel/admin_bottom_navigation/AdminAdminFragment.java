package com.example.adminpanel.admin_bottom_navigation;

import android.app.AlertDialog;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adminpanel.R;
import com.example.adminpanel.adapters.admin_list_adapter;
import com.example.adminpanel.users_manage.admin_admin_update;
import com.example.adminpanel.setters_getters.admin;
import com.example.adminpanel.users_manage.Admin_admin_add;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AdminAdminFragment extends Fragment implements admin_list_adapter.dataPass {
    FloatingActionButton btn_add_admin;
    RecyclerView recyclerView;
    admin_list_adapter mainAdapter;
    SearchView searchView;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    List<admin> adminMdlList;
    int Image_Request_Code = 07;
    Uri FilePathUri;
    CircleImageView image2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminCompanyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminCompanyFragment newInstance(String param1, String param2) {
        AdminCompanyFragment fragment = new AdminCompanyFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_admin, container, false);

        btn_add_admin = view.findViewById(R.id.btn_add_admn);

        recyclerView = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.admn_search);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Admin");
        mStorage = FirebaseStorage.getInstance();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adminMdlList = new ArrayList<admin>();
        mainAdapter = new admin_list_adapter(getActivity(), adminMdlList);
        mainAdapter.setPass(this);

        recyclerView.setAdapter(mainAdapter);

        btn_add_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Admin_admin_add.class);
                startActivity(intent);
            }
        });
        admin_load("");

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                adminMdlList.clear();
//                for (DataSnapshot one : snapshot.getChildren()) {
//                    admin admn1 = one.getValue(admin.class);
//                    admn1.setKeey(one.getKey());
//                    adminMdlList.add(admn1);
//                }
//                mainAdapter.setAdminModelList(adminMdlList);
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
                admin_load(s);
                return true;
            }
        });
        return view;
    }

    public void admin_load(String srch){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                adminMdlList.clear();
                for (DataSnapshot one : snapshot.getChildren()) {
                    admin admn1 = one.getValue(admin.class);
                    admn1.setKeey(one.getKey());
                    if(!srch.trim().isEmpty()){
                        if(admn1.getAdmin_name().toLowerCase().contains(srch.toLowerCase()) || admn1.getAdmin_email().toLowerCase().contains(srch.toLowerCase())){
                            adminMdlList.add(admn1);
                        }
                    }else{
                        adminMdlList.add(admn1);
                    }

                }
                mainAdapter.setAdminModelList(adminMdlList);
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
                image2.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void editButton(int position) {
        admin_admin_update update = new admin_admin_update(adminMdlList.get(position));
        update.show(getChildFragmentManager(), "admin_update_box");
    }
    @Override
    public void deleteButton(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Admin delete?");
        builder.setMessage("Are you sure that you want to delete admin?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseStorage.getInstance().getReferenceFromUrl(adminMdlList.get(position).getImageURL()).delete();
                FirebaseDatabase.getInstance().getReference().child("Admin")
                        .child(adminMdlList.get(position).getKeey()).removeValue();
                Toast.makeText(getContext(), "Admin deleted!", Toast.LENGTH_SHORT).show();
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

//    public void search(String str) {
//        ArrayList<admin> myLlist = new ArrayList<>();
//        for (admin object : adminMdlList) {
//            if (object.getAdmin_name().toLowerCase().contains(str.toLowerCase()) || object.getAdmin_email().toLowerCase().contains(str.toLowerCase())) {
//                myLlist.add(object);
//            }
//        }
//        admin_list_adapter adapterclass = new admin_list_adapter(getContext(), myLlist);
//        recyclerView.setAdapter(adapterclass);
//    }
}
