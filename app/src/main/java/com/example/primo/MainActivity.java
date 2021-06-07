package com.example.primo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.primo.adapter.BannerMoviePagerAdapter;
import com.example.primo.adapter.MainRecyclerAdapter;
import com.example.primo.model.AllCategories;
import com.example.primo.model.BannerMovies;
import com.example.primo.model.categoryItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    BannerMoviePagerAdapter bannerMoviePagerAdapter;
    TabLayout tabLayout,categoryTab;
    ViewPager bannerMoviesViewPager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> TvShowsBannerList;
    List<BannerMovies> MoviesBannerList;

    List<AllCategories> allCategoriesList;
    List<categoryItem> homeCatList1,homeCatList2,homeCatList3,homeCatList4;
    private static String JSON_URL,TvJson_URL,TrendingTvURL,TrendingMovieURL,homeBannerTrendingURL,movieBannerTrendingURL,TVBannerTrendingURL;
    String movieRating;
    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        TvJson_URL = "https://api.themoviedb.org/3/tv/popular?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        TrendingTvURL = "https://api.themoviedb.org/3/trending/tv/week?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        TrendingMovieURL = "https://api.themoviedb.org/3/trending/movie/week?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        homeBannerTrendingURL = "https://api.themoviedb.org/3/trending/all/week?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        movieBannerTrendingURL = "https://api.themoviedb.org/3/trending/movie/week?api_key=9d2bff12ed955c7f1f74b83187f188ae";
        TVBannerTrendingURL = "https://api.themoviedb.org/3/trending/tv/week?api_key=9d2bff12ed955c7f1f74b83187f188ae";


        tabLayout = findViewById(R.id.tabIndicator);
        categoryTab = findViewById(R.id.TabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);




        homeBannerList  = new ArrayList<>();
        TvShowsBannerList = new ArrayList<>();
        MoviesBannerList  = new ArrayList<>();

        GetHomeData getHomeData = new GetHomeData();
        getHomeData.execute();

        GetTVBannerData getTVBannerData = new GetTVBannerData();
        getTVBannerData.execute();

        GetMoviesBannerData getMoviesBannerData = new GetMoviesBannerData();
        getMoviesBannerData.execute();




        // on specific tab click
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){

                    case 1:
                        setScrollDefaultState();
                        setMoviesPagerAdapter(TvShowsBannerList);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setMoviesPagerAdapter(MoviesBannerList);
                        return;

                    default:
                        setScrollDefaultState();
                        setMoviesPagerAdapter(homeBannerList);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        homeCatList1 = new ArrayList<>();
        homeCatList2 = new ArrayList<>();
        homeCatList3 = new ArrayList<>();
        homeCatList4 = new ArrayList<>();
        allCategoriesList = new ArrayList<>();

        GetData getData = new GetData();
        getData.execute();

        GetTVData getTVData = new GetTVData();
        getTVData.execute();

        GetTrendingTVData getTrendingTVData = new GetTrendingTVData();
        getTrendingTVData.execute();

        GetTrendingMoviesData getTrendingMoviesData = new GetTrendingMoviesData();
        getTrendingMoviesData.execute();
    }

    private void setMoviesPagerAdapter(List<BannerMovies> bannerMoviesList){
        bannerMoviesViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviePagerAdapter = new BannerMoviePagerAdapter(this,bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviePagerAdapter);
        tabLayout.setupWithViewPager(bannerMoviesViewPager);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new autoSlider(),2000,10000);
        tabLayout.setupWithViewPager(bannerMoviesViewPager,true);
    }

    class autoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bannerMoviesViewPager.getCurrentItem()<8){
                        bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem()+1);
                    }
                    else {
                        bannerMoviesViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }



    private void setScrollDefaultState(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

    public class GetData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(JSON_URL);
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
                    model.setMovieName(jsonObject1.getString("title"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    homeCatList1.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            allCategoriesList.add(new AllCategories(1,"Popular Movies",homeCatList1));
        }
    }

    public class GetTVData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(TvJson_URL);
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
                    model.setTvId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("name"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    homeCatList2.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            allCategoriesList.add(new AllCategories(2,"Popular Tv Shows",homeCatList2));


        }
    }

    public class GetTrendingTVData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(TrendingTvURL);
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
                    model.setTvId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("name"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    homeCatList3.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            allCategoriesList.add(new AllCategories(3,"Trending Tv Shows",homeCatList3));

        }
    }

    public class GetTrendingMoviesData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(TrendingMovieURL);
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
                    homeCatList4.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            allCategoriesList.add(new AllCategories(4,"Trending Movies",homeCatList4));

            setMainRecycler(allCategoriesList);

        }
    }


    public class GetHomeData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(homeBannerTrendingURL);
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
                for (int i = 0 ;i<8;i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    BannerMovies model = new BannerMovies();
                    model.setId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("title"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    homeBannerList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setMoviesPagerAdapter(homeBannerList);

        }
    }

    public class GetTVBannerData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(TVBannerTrendingURL);
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
                for (int i = 0 ;i<8;i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    BannerMovies model = new BannerMovies();
                    model.setTvId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("original_name"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    TvShowsBannerList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class GetMoviesBannerData extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(movieBannerTrendingURL);
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
                for (int i = 0 ;i<8;i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    BannerMovies model = new BannerMovies();
                    model.setId(jsonObject1.getInt("id"));
                    model.setMovieName(jsonObject1.getString("original_title"));
                    model.setImageUrl(jsonObject1.getString("poster_path"));
                    model.setRating(jsonObject1.getDouble("vote_average"));
                    MoviesBannerList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



    public void setMainRecycler(List<AllCategories> allCategoriesList){
        mainRecycler  = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this,allCategoriesList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

}