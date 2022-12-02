package com.example.adminpanel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.company;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class company_list_adapter extends RecyclerView.Adapter<company_list_adapter.myViewHolder>{
    Context context;
    List<company> companyModelList;
    dataPass pass;
    public company_list_adapter(Context context, List<company> companyModelList) {
        this.context = context;
        this.companyModelList = companyModelList;
    }

    @NonNull
    @NotNull
    @Override
    public company_list_adapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_main_item,parent,false);
        return new company_list_adapter.myViewHolder(v,pass);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull company_list_adapter.myViewHolder holder, int position) {
        company companyModel = companyModelList.get(position);
        holder.email.setText(companyModel.getCompany_email());
        holder.name.setText(companyModel.getCompany_name());

        Picasso.get().load(companyModel.getImageURL()).centerCrop().fit().into(holder.img);

    }
    @Override
    public int getItemCount() {
        return companyModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView email,name;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull @NotNull View itemView, dataPass pass) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img1);
            email = (TextView) itemView.findViewById(R.id.emailtext);
            name = (TextView) itemView.findViewById(R.id.nametext);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
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

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pass!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            pass.deleteButton(position);

                        }
                    }
                }
            });

        }
    }
    public void setCompanyModelList(List<company> companyModelList) {
        this.companyModelList = companyModelList;
    }

    public interface dataPass{
        void editButton(int position);
        void deleteButton(int position);
    }

    public void setPass(dataPass pass) {
        this.pass = pass;
    }

}
