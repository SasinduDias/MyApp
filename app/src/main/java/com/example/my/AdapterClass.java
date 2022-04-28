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
    private ViewHolder.RecyclerViewClickListener clickListener;

    public AdapterClass(ArrayList<ModelClass> userList, ViewHolder.RecyclerViewClickListener clickListener) {
        this.userList = userList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template,parent,false);
        return new ViewHolder(view,clickListener);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       private TextView tv_name,tv_details;
       private ImageView img_user;
       RecyclerViewClickListener clicklist;
        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.textView);
            tv_details=itemView.findViewById(R.id.textView2);
            img_user=itemView.findViewById(R.id.imageView);
            this.clicklist=clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         clicklist.onClickListner(getAdapterPosition());
        }

        public interface RecyclerViewClickListener {
            void onClickListner(int position);
        }
    }
}
