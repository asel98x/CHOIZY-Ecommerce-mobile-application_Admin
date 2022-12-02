package com.example.adminpanel.admin_company_tabs;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.adminpanel.R;
import com.example.adminpanel.adapters.branch_list_adapter;
import com.example.adminpanel.setters_getters.branch;
import com.example.adminpanel.users_manage.admin_branch_view;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_company_branch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_company_branch extends Fragment implements branch_list_adapter.dataPass{
    RecyclerView recyclerView;
    branch_list_adapter mainAdapter;
    SearchView searchView;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    List<branch> branchMdlList;
    int Image_Request_Code = 07;
    Uri FilePathUri;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_company_branch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_company_branch.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_company_branch newInstance(String param1, String param2) {
        admin_company_branch fragment = new admin_company_branch();
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
        View view = inflater.inflate(R.layout.fragment_admin_company_branch, container, false);

        recyclerView = view.findViewById(R.id.rv);
        searchView = view.findViewById(R.id.branch_search);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Branch");
        mStorage = FirebaseStorage.getInstance();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        branchMdlList = new ArrayList<branch>();
        mainAdapter = new branch_list_adapter(getActivity(),branchMdlList);
        mainAdapter.setPass(this);

        recyclerView.setAdapter(mainAdapter);

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                branchMdlList.clear();
//                for(DataSnapshot one:snapshot.getChildren()){
//                    branch brnch1 = one.getValue(branch.class);
//                    brnch1.setKeey(one.getKey());
//                    branchMdlList.add(brnch1);
//                }
//                mainAdapter.setBranchModelList(branchMdlList);
//                mainAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
        branch_load("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                branch_load(s);
                return true;
            }
        });

        return view;
    }

    public void branch_load(String srch){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                branchMdlList.clear();
                for(DataSnapshot one:snapshot.getChildren()){
                    branch brnch1 = one.getValue(branch.class);
                    brnch1.setKeey(one.getKey());

                    if(!srch.trim().isEmpty()){
                        if(brnch1.getName().toLowerCase().contains(srch.toLowerCase())||brnch1.getCity().toLowerCase().contains(srch.toLowerCase())){
                            branchMdlList.add(brnch1);
                        }
                    }else{
                        branchMdlList.add(brnch1);
                    }
                }
                mainAdapter.setBranchModelList(branchMdlList);
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void editButton(int position) {
        admin_branch_view view = new admin_branch_view(branchMdlList.get(position));
        view.show(getChildFragmentManager(),"branch_view_box");
    }

//    public void search(String str){
//        ArrayList<branch> myLlist = new ArrayList<>();
//        for(branch object:branchMdlList){
//            if(object.getName().toLowerCase().contains(str.toLowerCase())||object.getCity().toLowerCase().contains(str.toLowerCase())){
//                myLlist.add(object);
//            }
//        }
//        branch_list_adapter adapterclass = new branch_list_adapter(getContext(),myLlist);
//        recyclerView.setAdapter(adapterclass);
//    }
}