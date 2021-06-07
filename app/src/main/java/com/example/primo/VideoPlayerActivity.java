package com.example.primo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView videoPlayer;
    private SimpleExoPlayer simpleExoPlayer;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playbackPosition = 0;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT>=24){
            initPlayer();
        }
    }

    @Override
    protected void onStop() {
        if (Util.SDK_INT>=24){
            releasePlayer();
        }
        super.onStop();
    }

    private void releasePlayer() {
        if (simpleExoPlayer!=null){
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer.release();
            simpleExoPlayer=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT<24 || simpleExoPlayer==null)){
            initPlayer();
            hideSystemUi();
        }
    }

    private void hideSystemUi() {
        videoPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE|
                View.SYSTEM_UI_FLAG_FULLSCREEN|
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onPause() {
        if (Util.SDK_INT<24)
            releasePlayer();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);

        videoPlayer = findViewById(R.id.exo_player);
        progressBar = findViewById(R.id.progress_bar);
       // setupExoPlayer(getIntent().getStringExtra("mFileUrl"));
        initPlayer();

        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable @org.jetbrains.annotations.Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState==Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);

                }
                else if (playbackState == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

    }

/*    private void setupExoPlayer(String url){

        //load Control here
        LoadControl loadControl = new DefaultLoadControl();

        //bandwidth meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        //track selector
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );



        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector,loadControl);
        videoPlayer.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"Primo"));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

    }
 */
    private void initPlayer(){
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        videoPlayer.setPlayer(simpleExoPlayer);
        playYtVideo("https://www.youtube.com/watch?v=NGf_B81Hc2M");
    }

    private void playYtVideo(String youtubeUrl) {
        new YouTubeExtractor(this){

            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                if (ytFiles!=null){
                    int videoTag=137;
                    int audioTag= 140;
                    MediaSource audioSource = new ProgressiveMediaSource.
                            Factory(new DefaultHttpDataSource.Factory()).
                            createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).getUrl()));

                    MediaSource videoSource = new ProgressiveMediaSource.
                            Factory(new DefaultHttpDataSource.Factory()).
                            createMediaSource(MediaItem.fromUri(ytFiles.get(videoTag).getUrl()));
                    simpleExoPlayer.setMediaSource(new MergingMediaSource(
                            true,
                            videoSource,
                            audioSource),
                            true
                    );
                    simpleExoPlayer.prepare();
                    simpleExoPlayer.setPlayWhenReady(playWhenReady);
                    simpleExoPlayer.seekTo(currentWindow,playbackPosition);

                }
            }
        }.extract(youtubeUrl,false,true);
    }
}