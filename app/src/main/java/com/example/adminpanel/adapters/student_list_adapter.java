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
import com.example.adminpanel.setters_getters.student;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class student_list_adapter extends RecyclerView.Adapter<student_list_adapter.myViewHolder>{

    Context context;
    List<student> studentModelList;
    dataPass pass;
    public student_list_adapter(Context context, List<student> studentModelList) {
        this.context = context;
        this.studentModelList = studentModelList;
    }

    @NonNull
    @NotNull
    @Override
    public student_list_adapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(v,pass);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull student_list_adapter.myViewHolder holder, int position) {
        student studentModel = studentModelList.get(position);
        holder.id.setText(studentModel.getStudent_id());
        holder.name.setText(studentModel.getStudent_name());

        Picasso.get().load(studentModel.getImageURL()).centerCrop().fit().into(holder.img);

    }

    @Override
    public int getItemCount() {
        return studentModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView id,name;
        Button btnEdit, btnDelete;
        ImageView pack;

        public myViewHolder(@NonNull @NotNull View itemView, dataPass pass) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            id = (TextView) itemView.findViewById(R.id.idtext);
            name = (TextView) itemView.findViewById(R.id.nametext);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            pack = (ImageView) itemView.findViewById(R.id.stud_pack);


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

            pack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pass!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            pass.packButton(position);

                        }
                    }
                }
            });

        }
    }

    public void setStudentModelList(List<student> studentModelList) {
        this.studentModelList = studentModelList;
    }

    public interface dataPass{
        void editButton(int position);
        void deleteButton(int position);
        void packButton(int position);
    }

    public void setPass(dataPass pass) {
        this.pass = pass;
    }
}
