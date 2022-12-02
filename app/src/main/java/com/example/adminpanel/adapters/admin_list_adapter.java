package com.example.adminpanel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.admin;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class admin_list_adapter extends RecyclerView.Adapter<admin_list_adapter.myViewHolder>{

    Context context;
    List<admin> adminModelList;
    admin_list_adapter.dataPass pass;

    public admin_list_adapter(Context context, List<admin> admin_list_adapter) {
        this.context = context;
        this.adminModelList = admin_list_adapter;
    }

    @NonNull
    @NotNull
    @Override
    public admin_list_adapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_main_item,parent,false);
        return new admin_list_adapter.myViewHolder(v,pass);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull admin_list_adapter.myViewHolder holder, int position) {
        admin adminModel = adminModelList.get(position);
        holder.email.setText(adminModel.getAdmin_email());
        holder.name.setText(adminModel.getAdmin_name());

        Picasso.get().load(adminModel.getImageURL()).centerCrop().fit().into(holder.img);

    }
    @Override
    public int getItemCount() {
        return adminModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView email,name;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull @NotNull View itemView, admin_list_adapter.dataPass pass) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            email = (TextView) itemView.findViewById(R.id.admn_mailtext);
            name = (TextView) itemView.findViewById(R.id.admn_nametext);
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
    public void setAdminModelList(List<admin> adminModelList) {
        this.adminModelList = adminModelList;
    }

    public interface dataPass{
        void editButton(int position);
        void deleteButton(int position);
    }

    public void setPass(admin_list_adapter.dataPass pass) {
        this.pass = pass;
    }
}
