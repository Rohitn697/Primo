package com.example.primo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.primo.MovieDetails;
import com.example.primo.R;
import com.example.primo.model.BannerMovies;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BannerMoviePagerAdapter extends PagerAdapter {
    Context context;
    List<BannerMovies> bannerMoviesList;

    public BannerMoviePagerAdapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @org.jetbrains.annotations.NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout,null);
        ImageView bannerImage = view.findViewById(R.id.bannerImage);

        Glide.with(context).load("http://image.tmdb.org/t/p/w500"+bannerMoviesList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("movieId",bannerMoviesList.get(position).getId());
                i.putExtra("TvId",bannerMoviesList.get(position).getTvId());
                i.putExtra("movieName",bannerMoviesList.get(position).getMovieName());
                i.putExtra("movieImageUrl",bannerMoviesList.get(position).getImageUrl());
                i.putExtra("movieFile",bannerMoviesList.get(position).getFileUrl());
                i.putExtra("movieRating",bannerMoviesList.get(position).getRating());
                context.startActivity(i);
            }
        });
        return view;
    }
}
