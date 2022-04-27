package com.example.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{
    private ArrayList<ModelClass> userList;

    public AdapterClass(ArrayList<ModelClass> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.ViewHolder holder, int position) {
      String name,details;
      int photo;
      name=userList.get(position).getName();
      details=userList.get(position).getDetails();
      photo=userList.get(position).getPhoto();

      holder.tv_name.setText(name);
      holder.tv_details.setText(details);
      holder.img_user.setImageResource(photo);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView tv_name,tv_details;
       private ImageView img_user;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.textView);
            tv_details=itemView.findViewById(R.id.textView2);
            img_user=itemView.findViewById(R.id.imageView);
        }
    }
}
