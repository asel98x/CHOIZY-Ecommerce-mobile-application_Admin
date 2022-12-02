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
import com.example.adminpanel.setters_getters.upcommingAds;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ads_upcomming_list_adapter extends RecyclerView.Adapter<ads_upcomming_list_adapter.myViewHolder>{
        Context context;
        List<upcommingAds> upcommingAdsModelList;
    ads_upcomming_list_adapter.dataPass pass;

public ads_upcomming_list_adapter(Context context, List<upcommingAds> upcommingAdsModelList) {
        this.context = context;
        this.upcommingAdsModelList = upcommingAdsModelList;
        }

@NonNull
@NotNull
@Override
public ads_upcomming_list_adapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_main_item,parent,false);
        return new ads_upcomming_list_adapter.myViewHolder(v,pass);
        }

@Override
public void onBindViewHolder(@NonNull @NotNull ads_upcomming_list_adapter.myViewHolder holder, int position) {
        upcommingAds upcommingAdsModel = upcommingAdsModelList.get(position);
        Picasso.get().load(upcommingAdsModel.getUrl()).centerCrop().fit().into(holder.img);
        }
@Override
public int getItemCount() {
        return upcommingAdsModelList.size();
        }

class myViewHolder extends RecyclerView.ViewHolder{

    TextView type;
    ImageView img;
    Button btndelete;


    public myViewHolder(@NonNull @NotNull View itemView, ads_upcomming_list_adapter.dataPass pass) {
        super(itemView);

        img = (ImageView) itemView.findViewById(R.id.ads_img1);
        //btndelete = (Button) itemView.findViewById(R.id.ad_delete);

        itemView.setOnClickListener(new View.OnClickListener() {
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

    public void setUpcommingAdsModelList(List<upcommingAds> upcommingAdsModelList) {
        this.upcommingAdsModelList = upcommingAdsModelList;
    }

public interface dataPass{
    void deleteButton(int position);
}

    public void setPass(ads_upcomming_list_adapter.dataPass pass) {
        this.pass = pass;
    }
}
