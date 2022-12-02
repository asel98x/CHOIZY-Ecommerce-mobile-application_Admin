package com.example.adminpanel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.branch;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class branch_list_adapter extends RecyclerView.Adapter<branch_list_adapter.myViewHolder>{
    Context context;
    List<branch> branchModelList;
    branch_list_adapter.dataPass pass;

    public branch_list_adapter(Context context, List<branch> branchModelList) {
        this.context = context;
        this.branchModelList = branchModelList;
    }
    @NonNull
    @NotNull
    @Override
    public branch_list_adapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_main_item,parent,false);
        return new branch_list_adapter.myViewHolder(v,pass);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull branch_list_adapter.myViewHolder holder, int position) {
        branch branchModel = branchModelList.get(position);
        holder.name.setText(branchModel.getName());
        holder.email.setText(branchModel.getEmail());
        holder.address.setText(branchModel.getNo_adres()+branchModel.getStreetAddress()+branchModel.getCity());

    }
    @Override
    public int getItemCount() {
        return branchModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView email,name,address;
        Button btnView;
        RelativeLayout view;

        public myViewHolder(@NonNull @NotNull View itemView, branch_list_adapter.dataPass pass) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Branch_name);
            email = (TextView) itemView.findViewById(R.id.Branch_email);
            address = (TextView) itemView.findViewById(R.id.Branch_address);
            view = (RelativeLayout) itemView.findViewById(R.id.box_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pass!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            pass.editButton(position);

                        }
                    }
                }
            });
//            btnView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(pass!=null){
//                        int position = getAdapterPosition();
//                        if (position!=RecyclerView.NO_POSITION) {
//                            pass.editButton(position);
//
//                        }
//                    }
//                }
//            });

        }
    }

    public void setBranchModelList(List<branch> branchModelList) {
        this.branchModelList = branchModelList;
    }

    public interface dataPass{
        void editButton(int position);
    }

    public void setPass(branch_list_adapter.dataPass pass) {
        this.pass = pass;
    }
}
