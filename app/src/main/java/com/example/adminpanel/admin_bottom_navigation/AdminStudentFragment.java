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

import com.example.adminpanel.student_pack.student_pack;
import com.example.adminpanel.users_manage.Admin_student_add;
import com.example.adminpanel.R;
import com.example.adminpanel.users_manage.admin_student_update;
import com.example.adminpanel.setters_getters.student;
import com.example.adminpanel.adapters.student_list_adapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminStudentFragment extends Fragment implements student_list_adapter.dataPass {
    FloatingActionButton btn_add_student;
    RecyclerView recyclerView;
    student_list_adapter mainAdapter;
    SearchView searchView;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    List<student> studentMdlList;
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

    public AdminStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminStudentFragment newInstance(String param1, String param2) {
        AdminStudentFragment fragment = new AdminStudentFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_student, container, false);

        btn_add_student = view.findViewById(R.id.btn_add_stud);

        recyclerView = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.stud_search);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Student");
        mStorage = FirebaseStorage.getInstance();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        studentMdlList = new ArrayList<student>();
        mainAdapter = new student_list_adapter(getActivity(),studentMdlList);
        mainAdapter.setPass(this);

        recyclerView.setAdapter(mainAdapter);
//        FirebaseRecyclerOptions<student> options =
//                new FirebaseRecyclerOptions.Builder<student>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student"), student.class)
//                        .build();

        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Admin_student_add.class);
                startActivity(intent);
            }
        });

        stud_load("");

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                studentMdlList.clear();
//                for(DataSnapshot one:snapshot.getChildren()){
//                    student stud1 = one.getValue(student.class);
//                    stud1.setKeey(one.getKey());
//                    studentMdlList.add(stud1);
//                }
//                mainAdapter.setStudentModelList(studentMdlList);
//                mainAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//        mRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
////                student studentModel = snapshot.getValue(student.class);
////                studentModel.setKeey(snapshot.getKey());
////                studentMdlList.add(studentModel);
////                mainAdapter.notifyDataSetChanged();
////                recyclerView.setAdapter(mainAdapter);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                mainAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processsearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processsearch(s);
//                return false;
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                stud_load(s);
                return true;
            }
        });



        return view;
    }

    public void stud_load(String srch){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                studentMdlList.clear();
                for(DataSnapshot one:snapshot.getChildren()){
                    student stud1 = one.getValue(student.class);
                    stud1.setKeey(one.getKey());
                    if(!srch.trim().isEmpty()){
                        if(stud1.getStudent_name().toLowerCase().contains(srch.toLowerCase())||stud1.getStudent_id().toLowerCase().contains(srch.toLowerCase())||stud1.getStudent_email().toLowerCase().contains(srch.toLowerCase())){
                            studentMdlList.add(stud1);
                        }
                    }else{
                        studentMdlList.add(stud1);
                    }

                }
                mainAdapter.setStudentModelList(studentMdlList);
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
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void editButton(int position) {
        admin_student_update update = new admin_student_update(studentMdlList.get(position));
        update.show(getChildFragmentManager(),"stud_update_box");
//        student stud = studentMdlList.get(position);
//
//        final DialogPlus dialogPlus = DialogPlus.newDialog(getActivity())
//                .setContentHolder(new ViewHolder(R.layout.update_popup))
//                .setExpanded(true,1500)
//                .create();
//
//        //dialogPlus.show();
//
//        View view = dialogPlus.getHolderView();
//        EditText name = view.findViewById(R.id.textName);
//        EditText birthday = view.findViewById(R.id.textBirthday);
//        EditText gender = view.findViewById(R.id.textGender);
//        EditText NIC = view.findViewById(R.id.textNIC);
//        EditText email = view.findViewById(R.id.textEmail);
//        EditText mobile = view.findViewById(R.id.textMobile);
//        EditText image = view.findViewById(R.id.textImageUrl);
//        image2 = view.findViewById(R.id.img1);
//        Button btnUpdate = view.findViewById(R.id.btnUpdate);
//        ImageView btnImage = view.findViewById(R.id.imgBrowse);
//
//
//
//
//        name.setText(stud.getStudent_name());
//        birthday.setText(stud.getStudent_bday());
//        gender.setText(stud.getStudent_gender());
//        NIC.setText(stud.getStudent_nic());
//        email.setText(stud.getStudent_email());
//        mobile.setText(stud.getStudent_mobile());
//        image.setText(stud.getImageURL());
//        Picasso.get().load(stud.getImageURL()).centerCrop().fit().into(image2);
//
//        dialogPlus.show();
//
//        btnImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
//
//            }
//        });
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                Map<String,Object> map = new HashMap<>();
//                map.put("student_name",name.getText().toString());
//                map.put("student_bday",birthday.getText().toString());
//                map.put("student_gender",gender.getText().toString());
//                map.put("student_nic",NIC.getText().toString());
//                map.put("student_email",email.getText().toString());
//                map.put("student_mobile",mobile.getText().toString());
//                map.put("imageURL",image.getText().toString());
//                map.put("imageURL",stud.getImageURL());
//                //map.put("imageURL",image2.getimag
//
//
//                FirebaseDatabase.getInstance().getReference().child("Student")
//                        .child(stud.getKeey()).updateChildren(map)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(getContext(), "Student details updated successfully", Toast.LENGTH_SHORT).show();
//                                dialogPlus.dismiss();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull @NotNull Exception e) {
//                                Toast.makeText(getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
//                                dialogPlus.dismiss();
//                            }
//                        });
//            }
//        });
    }

    @Override
    public void deleteButton(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Student delete?");
        builder.setMessage("Are you sure that you want to delete student?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseStorage.getInstance().getReferenceFromUrl(studentMdlList.get(position).getImageURL()).delete();
                FirebaseDatabase.getInstance().getReference().child("Student")
                        .child(studentMdlList.get(position).getKeey()).removeValue();
                Toast.makeText(getContext(), "Student deleted!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void packButton(int position) {
        Intent intent = new Intent(getActivity(), student_pack.class);
        intent.putExtra("StudentId",studentMdlList.get(position).getKeey());
        startActivity(intent);
    }

//    public void search(String str){
//        ArrayList<student> myLlist = new ArrayList<>();
//        for(student object:studentMdlList){
//            if(object.getStudent_name().toLowerCase().contains(str.toLowerCase())||object.getStudent_id().toLowerCase().contains(str.toLowerCase())||object.getStudent_email().toLowerCase().contains(str.toLowerCase())){
//                myLlist.add(object);
//            }
//        }
//        student_list_adapter adapterclass = new student_list_adapter(getContext(),myLlist);
//        recyclerView.setAdapter(adapterclass);
//        adapterclass.setPass(this);
//
//    }


//    private void processsearch(String s) {
//        FirebaseRecyclerOptions<student> options =
//                new FirebaseRecyclerOptions.Builder<student>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Student").
//                        orderByChild("student_id").startAt(s).endAt(s+"\uf8ff"), student.class)
//                        .build();
//        mainAdapter = new student_list_adapter(options);
//        //mainAdapter.startListening();
//        recyclerView.setAdapter(mainAdapter);
//
//    }

}