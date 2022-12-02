package com.example.adminpanel.student_pack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.setters_getters.Feedback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class feedback_list extends RecyclerView.Adapter<feedback_list.myViewHolder>{
    List<Feedback> feedbackModelList;

    public feedback_list() {
        this.feedbackModelList = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public feedback_list.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_fedback_main_item,parent,false);
        return new feedback_list.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull feedback_list.myViewHolder holder, int position) {
        Feedback feedbackModel = feedbackModelList.get(position);
        holder.brnch_name.setText(feedbackModel.getBrnch().getName());
        holder.msg.setText(feedbackModel.getMsg());
    }

    @Override
    public int getItemCount() {
        return feedbackModelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView brnch_name, msg;
        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            brnch_name = (TextView) itemView.findViewById(R.id.brnch_name);
            msg = (TextView) itemView.findViewById(R.id.stud_msg);

        }
    }

    public void setFeedbackModelList(List<Feedback> feedbackModelList) {
        this.feedbackModelList = feedbackModelList;
    }
}
