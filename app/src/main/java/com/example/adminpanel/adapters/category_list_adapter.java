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
import com.example.adminpanel.setters_getters.category;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class category_list_adapter extends RecyclerView.Adapter<category_list_adapter.myViewHolder>{
    Context context;
    List<category> categoryModelList;
    dataPass pass;

    public category_list_adapter(Context context, List<category> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @NotNull
    @Override
    public category_list_adapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_main_item,parent,false);
        return new myViewHolder(v,pass);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull category_list_adapter.myViewHolder holder, int position) {
        category categoryModel = categoryModelList.get(position);
        holder.name.setText(categoryModel.getCategory_name());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        Button btndelete,btnEdit;


        public myViewHolder(@NonNull @NotNull View itemView, category_list_adapter.dataPass pass) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.categorytext);
            btndelete = (Button) itemView.findViewById(R.id.btn_category_Delete);
            btnEdit = (Button) itemView.findViewById(R.id.btn_category_Edit);

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

            btndelete.setOnClickListener(new View.OnClickListener() {
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

    public void setCategoryModelList(List<category> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public interface dataPass{
        void editButton(int position);
        void deleteButton(int position);
    }

    public void setPass(dataPass pass) {
        this.pass = pass;
    }
}
