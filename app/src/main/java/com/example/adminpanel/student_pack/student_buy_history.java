package com.example.adminpanel.student_pack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.Orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link student_buy_history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class student_buy_history extends Fragment implements order_history_list.dataPass{
    RecyclerView recyclerView;
    admin_helper db_helper;
    ValueEventListener history_listener;
    ArrayList<Orders> order_list;
    order_history_list historyList_adapter;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public student_buy_history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_buy_history.
     */
    // TODO: Rename and change types and number of parameters
    public static student_buy_history newInstance(String param1, String param2) {
        student_buy_history fragment = new student_buy_history();
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
        View view = inflater.inflate(R.layout.fragment_student_buy_history, container, false);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db_helper = new admin_helper(getContext());
        order_list = new ArrayList<>();
        historyList_adapter = new order_history_list();
        recyclerView.setAdapter(historyList_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyList_adapter.setPass(this);

        load_history();

        return view;
    }

    private void load_history() {
        history_listener = db_helper.getStudent_order_history(mParam1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                order_list.clear();
                for (DataSnapshot one:snapshot.getChildren()) {
                    Orders order = one.getValue(Orders.class);
                    order.setOrderID(one.getKey());
                    order_list.add(order);
                }
                historyList_adapter.setOrderModelList(order_list);
                historyList_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void viewButton(int position) {
        history_adapter view = new history_adapter(order_list.get(position));
        view.show(getChildFragmentManager(),"view_box");
    }
}