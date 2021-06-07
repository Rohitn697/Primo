package com.example.primo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primo.R;
import com.example.primo.model.AllCategories;
import com.example.primo.model.categoryItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class movieDetailsAdapter extends RecyclerView.Adapter<movieDetailsAdapter.movieViewHolder> {
    Context context;
    List<AllCategories> allCategoriesList;

    public movieDetailsAdapter(Context context, List<AllCategories> allCategoriesList) {
        this.context = context;
        this.allCategoriesList = allCategoriesList;
    }

    @NonNull
    @NotNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new movieViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull movieDetailsAdapter.movieViewHolder holder, int position) {
        holder.categoryName.setText(allCategoriesList.get(position).getCategoryTitle());
        setMovieItemRecycler(holder.itemRecycler,allCategoriesList.get(position).getCategoryItemList());

    }

    @Override
    public int getItemCount() {
          return allCategoriesList.size();
    }


    public static final class movieViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        RecyclerView itemRecycler;

        public movieViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.item_category);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
        }
    }
    private void setMovieItemRecycler(RecyclerView recyclerView, List<categoryItem> categoryItemList){
        movieItemsRecyclerAdapter movieItemsRecyclerAdapter = new movieItemsRecyclerAdapter(context,categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(movieItemsRecyclerAdapter);
    }
}
