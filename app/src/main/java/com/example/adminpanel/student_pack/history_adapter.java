package com.example.adminpanel.student_pack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.Orders;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class history_adapter extends BottomSheetDialogFragment {
    TextView order_id,student_id,order_type,payment_type,time;
    RecyclerView offers;
    Orders order;
    order_offers_list offersList_adapter;
    public history_adapter(Orders orders) {
        this.order=orders;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordered_history_view_popup, container, false);

        order_id = view.findViewById(R.id.ordr_id);
        student_id = view.findViewById(R.id.ordr_stud_id);
        order_type = view.findViewById(R.id.ordr_type);
        payment_type = view.findViewById(R.id.ordr_payment_type);
        time = view.findViewById(R.id.ordr_time);
        offers = view.findViewById(R.id.rv);

        offersList_adapter = new order_offers_list();
        offersList_adapter.setCartModelList(order.getCartList());
        offers.setAdapter(offersList_adapter);
        offers.setLayoutManager(new LinearLayoutManager(getContext()));

        order_id.setText(order.getOrderID());
        student_id.setText(order.getStudentID());
        order_type.setText(order.getOrderType());
        payment_type.setText(order.getPaymentType());
        time.setText(order.getDate()+" "+order.getTime());




        return view;
    }
}
