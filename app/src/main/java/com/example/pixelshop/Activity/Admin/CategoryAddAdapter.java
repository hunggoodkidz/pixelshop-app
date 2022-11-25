package com.example.pixelshop.Activity.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pixelshop.Activity.ThongKeDanhMuc2Activity;
import com.example.pixelshop.Model.CategoryModels;
import com.example.pixelshop.R;

import java.util.List;

public class CategoryAddAdapter extends RecyclerView.Adapter<CategoryAddAdapter.CategoryViewHolder> {

    Context context;
    List<CategoryModels> categoryList;

    public CategoryAddAdapter(Context context, List<CategoryModels> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModels categoryModels = categoryList.get(position);

        //holder.categoryName.setText(categoryModels.getTenloai());
        Glide.with(context).load(categoryModels.getHinhanh()).placeholder(R.drawable.smartphone).into(holder.categoryImage);
        String name = categoryList.get(position).getTenloai();
        //Glide.with(context).load(categoryList.get(position).getHinhanh()).into(holder.categoryImage);

        holder.setCategory(name,position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = (ImageView) itemView.findViewById(R.id.categoryImage);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);

        }
        private void setCategory(final String name, final int position){
            categoryName.setText(name);
            itemView.setOnClickListener(view -> {
                if (position != 0){
                    Intent categoryIntent = new Intent(itemView.getContext(), ThongKeDanhMuc2Activity.class);
                    categoryIntent.putExtra("KEY",name);
                    itemView.getContext().startActivity(categoryIntent);
                }
            });

        }
    }



}

// lets import all the category images