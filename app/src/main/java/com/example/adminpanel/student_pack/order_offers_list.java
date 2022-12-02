package com.example.adminpanel.student_pack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.Cart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class order_offers_list extends RecyclerView.Adapter<order_offers_list.myViewHolder>{
    List<Cart> cartModelList;

    public order_offers_list() {
        this.cartModelList = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public order_offers_list.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_history_list,parent,false);
        return new order_offers_list.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull order_offers_list.myViewHolder holder, int position) {
        Cart cartModel = cartModelList.get(position);
        holder.title.setText(cartModel.getOffer().getTitle());
        holder.quantity.setText(cartModel.getQut()+"");
        holder.price.setText(cartModel.getOffer().getPrice()+"");

    }
    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView title, quantity,price;
        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.ordr_title);
            quantity = (TextView) itemView.findViewById(R.id.ordr_quantity);
            price = (TextView) itemView.findViewById(R.id.ordr_price);


        }

    }
    public void setCartModelList(List<Cart> cartModelList) {
        this.cartModelList = cartModelList;
    }
}
