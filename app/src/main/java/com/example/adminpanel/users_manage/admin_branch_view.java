package com.example.adminpanel.users_manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.branch;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class admin_branch_view extends BottomSheetDialogFragment {

    Button delete;
    branch brnch;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    TextView Bcity,Bemail,Bname,Bmobile,Baddress,Blatitude,Blongitude;
    CheckBox box,box2;

    public admin_branch_view(branch branch) {
        this.brnch = branch;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branch_view_popup,container,false);

        mRef = FirebaseDatabase.getInstance().getReference("Branch");
        Bcity = view.findViewById(R.id.city);
        Bemail = view.findViewById(R.id.Email);
        Bname = view.findViewById(R.id.Name);
        Bmobile = view.findViewById(R.id.Mobile);
        Baddress = view.findViewById(R.id.Address);
        Blatitude = view.findViewById(R.id.Latitude);
        Blongitude = view.findViewById(R.id.Longitude);
        box = view.findViewById(R.id.hv_deelvry);
        box2 = view.findViewById(R.id.hv_adnce);

        Bcity.setText(brnch.getCity());
        Bemail.setText(brnch.getEmail());
        Bname.setText(brnch.getName());
        Bmobile.setText(brnch.getMobile());
        Baddress.setText(brnch.getNo_adres()+brnch.getStreetAddress()+brnch.getCity());
        Blatitude.setText(brnch.getLatitude()+"");
        Blongitude.setText(brnch.getLongitude()+"");

        box.setChecked(brnch.isHaveDelivering());
        box2.setChecked(brnch.isHaveAdvance());

        return view;
    }
}
