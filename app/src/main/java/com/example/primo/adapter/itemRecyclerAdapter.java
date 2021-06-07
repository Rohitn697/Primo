package com.example.primo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primo.MainActivity;
import com.example.primo.MovieDetails;
import com.example.primo.R;
import com.example.primo.model.categoryItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class itemRecyclerAdapter extends RecyclerView.Adapter<itemRecyclerAdapter.itemViewHolder> {

    Context context;
    List<categoryItem> categoryItemList;

    public itemRecyclerAdapter(Context context, List<categoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @NotNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new itemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull itemRecyclerAdapter.itemViewHolder holder, int position) {


        Glide.with(context).load("http://image.tmdb.org/t/p/w500"+categoryItemList.get(position).getImageUrl()).into(holder.itemImage);


    
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("movieId",categoryItemList.get(position).getId());
                i.putExtra("TvId",categoryItemList.get(position).getTvId());
                i.putExtra("movieName",categoryItemList.get(position).getMovieName());
                i.putExtra("movieImageUrl",categoryItemList.get(position).getImageUrl());
                i.putExtra("movieRating",categoryItemList.get(position).getRating());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class itemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;

        public itemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}
