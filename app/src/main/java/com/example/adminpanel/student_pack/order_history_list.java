package com.example.adminpanel.student_pack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.Orders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class order_history_list extends RecyclerView.Adapter<order_history_list.myViewHolder>{

    List<Orders> orderModelList;
    order_history_list.dataPass pass;

    public order_history_list() {

        this.orderModelList = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public order_history_list.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_history_main_item,parent,false);
        return new order_history_list.myViewHolder(v,pass);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull order_history_list.myViewHolder holder, int position) {
        Orders orderModel = orderModelList.get(position);
        holder.order_id.setText(orderModel.getOrderID());
        holder.title.setText(orderModel.getCartList().get(0).getOffer().getTitle());
        holder.date.setText(orderModel.getDate());

    }
    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView order_id, title,date;
        public myViewHolder(@NonNull @NotNull View itemView, order_history_list.dataPass pass) {
            super(itemView);

            order_id = (TextView) itemView.findViewById(R.id.order_id);
            title = (TextView) itemView.findViewById(R.id.ordr_title);
            date = (TextView) itemView.findViewById(R.id.ordr_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pass!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            pass.viewButton(position);

                        }
                    }
                }
            });
        }

    }

    public void setOrderModelList(List<Orders> OrderModelList) {
        this.orderModelList = OrderModelList;
    }
    public interface dataPass{
        void viewButton(int position);
    }

    public void setPass(order_history_list.dataPass pass) {
        this.pass = pass;
    }
}
