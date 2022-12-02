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
import com.example.adminpanel.setters_getters.Feedback;
import com.example.adminpanel.setters_getters.branch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link student_feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class student_feedback extends Fragment {
    RecyclerView recyclerView;
    admin_helper db_helper;
    feedback_list feedback_list_adapter;
    ArrayList<Feedback>feedback_list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public student_feedback() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static student_feedback newInstance(String param1, String param2) {
        student_feedback fragment = new student_feedback();
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
        View view = inflater.inflate(R.layout.fragment_student_feedback, container, false);

        recyclerView = view.findViewById(R.id.rv);
        feedback_list_adapter = new feedback_list();
        recyclerView.setAdapter(feedback_list_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedback_list = new ArrayList<>();
        db_helper = new admin_helper(getContext());

        db_helper.getStudent_feedback(mParam1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                feedback_list.clear();
                for (DataSnapshot one:snapshot.getChildren()) {
                    Feedback fdbk = one.getValue(Feedback.class);

                    db_helper.getbranch(fdbk.getBranchId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            branch brnch = snapshot.getValue(branch.class);
                            fdbk.setBrnch(brnch);
                            feedback_list.add(fdbk);
                            feedback_list_adapter.setFeedbackModelList(feedback_list);
                            feedback_list_adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}