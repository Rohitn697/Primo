package com.example.primo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.primo.adapter.MainRecyclerAdapter;
import com.example.primo.adapter.movieDetailsAdapter;
import com.example.primo.model.AllCategories;
import com.example.primo.model.BannerMovies;
import com.example.primo.model.MovieDetailsModel;
import com.example.primo.model.categoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {
    ImageView movieImage;
    TextView movieName,movieRating;
    Button playButton;

    String mName,mImage,mFileUrl;
    Integer mId,TVid;
    Double mRating;

    com.example.primo.adapter.movieDetailsAdapter movieDetailsAdapter;

    RecyclerView movieRecycler;

    private static String defaultVideoURl,defaultCastUrl,defaultSimilarUrl,TvSimilarUrl;
    List<categoryItem> movieDetailsModelList,movieDetailsModelList1,movieDetailsModelList2;
    List<AllCategories> allCategoriesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieImage = findViewById(R.id.movie_image);
        movieRating = findViewById(R.id.ratings);
        movieName = findViewById(R.id.MovieNameText);
        playButton = findViewById(R.id.play_button);

        mId = getIntent().getIntExtra("movieId",1);
        TVid = getIntent().getIntExtra("TvId",1);
        mImage = getIntent().getStringExtra("movieImageUrl");
        mFileUrl = getIntent().getStringExtra("movieFile");
        mName = getIntent().getStringExtra("movieName");
        mRating = getIntent().getDoubleExtra("movieRating",9.18);

        if (mId!=1){
            defaultVideoURl = "https://api.themoviedb.org/3/movie/"+mId+"/videos?api_key=9d2bff12ed955c7f1f74b83187f188ae";
            defaultCastUrl = "https://api.themoviedb.org/3/movie/"+mId+"/credits?api_key=9d2bff12ed955c7f1f74b83187f188ae";
            defaultSimilarUrl = "https://api.themoviedb.org/3/movie/"+mId+"/similar?api_key=9d2bff12ed955c7f1f74b83187f188ae&language=en-US";
        }
        else {
            defaultVideoURl = "https://api.themoviedb.org/3/tv/"+TVid+"/videos?api_key=9d2bff12ed955c7f1f74b83187f188ae";
            defaultCastUrl = "https://api.themoviedb.org/3/tv/"+TVid+"/credits?api_key=9d2bff12ed955c7f1f74b83187f188ae";
            TvSimilarUrl = "https://api.themoviedb.org/3/tv/"+TVid+"/similar?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        }


        Glide.with(this).load("http://image.tmdb.org/t/p/w500"+mImage).into(movieImage);
        movieName.setText(mName);
        movieRating.setText(mRating.toString());

        movieDetailsModelList = new ArrayList<>();
        movieDetailsModelList1 = new ArrayList<>();
        movieDetailsModelList2 = new ArrayList<>();
        GetVideoKeyData getVideoKeyData = new GetVideoKeyData();
        getVideoKeyData.execute();
        allCategoriesList = new ArrayList<>();


        GetCastData getCastData = new GetCastData();
        getCastData.execute();

        GetSimilarData getSimilarData = new GetSimilarData();
        getSimilarData.execute();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieDetails.this,PlayVideoActivity.class);
                i.putExtra("mFileUrl",mFileUrl);
                startActivity(i);
            }
        });

    }

    public class GetVideoKeyData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(defaultVideoURl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data!=-1){
                        current+=(char) data;
                        data = isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0 ;i<1;i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    mFileUrl = jsonObject1.getString("key");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class GetCastData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(defaultCastUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data!=-1){
                        current+=(char) data;
                        data = isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("cast");
                for (int i = 0 ;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    categoryItem model = new categoryItem();
                    model.setMovieName(jsonObject1.getString("original_name"));
                    model.setImageUrl(jsonObject1.getString("profile_path"));
                    movieDetailsModelList.add(model);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            allCategoriesList.add(new AllCategories(1,"Cast",movieDetailsModelList));
        }
    }

    public class GetSimilarData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(defaultSimilarUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data!=-1){
                        current+=(char) data;
                        data = isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0 ;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    categoryItem model = new categoryItem();
                    model.setId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("original_title"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    movieDetailsModelList1.add(model);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            allCategoriesList.add(new AllCategories(2,"Similar Shows You May Like",movieDetailsModelList1));
            setMovieDetailsRecycler(allCategoriesList);
        }
    }

    public class GetTvSimilarData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(TvSimilarUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data!=-1){
                        current+=(char) data;
                        data = isr.read();

                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0 ;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    categoryItem model = new categoryItem();
                    model.setId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("original_name"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    movieDetailsModelList2.add(model);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            allCategoriesList.add(new AllCategories(3,"Similar Shows You May Like",movieDetailsModelList1));
            setMovieDetailsRecycler(allCategoriesList);
        }
    }


    public void setMovieDetailsRecycler(List<AllCategories> allCategoriesList){
        movieRecycler  = findViewById(R.id.details_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        movieRecycler.setLayoutManager(layoutManager);
        movieDetailsAdapter= new movieDetailsAdapter(this,allCategoriesList);
        movieRecycler.setAdapter(movieDetailsAdapter);
    }



}