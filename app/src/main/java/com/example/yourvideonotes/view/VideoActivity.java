package com.example.yourvideonotes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yourvideonotes.R;
import com.example.yourvideonotes.databinding.ActivityVideoBinding;
import com.example.yourvideonotes.model.VideoInfo;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    ActivityVideoBinding binding;

    String videoTitle;
    String videoUrl;
    String videoExp;
    float videoSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getIntentInfo();
        playVideo();




    }
    public void playVideo()
    {
        try {
            binding.youtubeplayerview.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                    // check youtube link
                    if(videoUrl.contains("=") && videoUrl.contains("https://www.youtube.com/"))
                    {
                        String[] sep = videoUrl.split("=");
                        videoUrl = sep[1];
                        youTubePlayer.loadVideo(videoUrl,videoSecond);
                    }
                    else{
                        Toast.makeText(VideoActivity.this, "video link not found", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: Please Check Video Link", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(VideoActivity.this, MainActivity.class);
            startActivity(i);
        }

    }

    public void getIntentInfo()
    {
        Bundle extras = getIntent().getExtras();
        videoUrl = extras.getString("url");
        videoExp = extras.getString("exp");
        videoTitle = extras.getString("title");
        videoSecond = extras.getFloat("sec",0f);

        binding.explanationVideoactivityText.setText(videoExp);
        binding.titleVideoactivityText.setText(videoTitle);
    }



}