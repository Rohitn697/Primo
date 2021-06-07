package com.example.primo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primo.R;
import com.example.primo.model.AllCategories;
import com.example.primo.model.categoryItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    Context context;
    List<AllCategories> allCategoriesList;

    public MainRecyclerAdapter(Context context, List<AllCategories> allCategoriesList) {
        this.context = context;
        this.allCategoriesList = allCategoriesList;
    }

    @NonNull
    @NotNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainRecyclerAdapter.MainViewHolder holder, int position) {
        holder.categoryName.setText(allCategoriesList.get(position).getCategoryTitle());
        setItemRecycler(holder.itemRecycler,allCategoriesList.get(position).getCategoryItemList());

    }

    @Override
    public int getItemCount() {
        return allCategoriesList.size();
    }


    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        RecyclerView itemRecycler;

        public MainViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.item_category);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
        }
    }

    private void setItemRecycler(RecyclerView recyclerView, List<categoryItem> categoryItemList){
        itemRecyclerAdapter itemRecyclerAdapter = new itemRecyclerAdapter(context,categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }

}
