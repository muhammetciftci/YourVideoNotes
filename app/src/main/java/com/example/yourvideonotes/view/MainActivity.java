package com.example.yourvideonotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.yourvideonotes.R;
import com.example.yourvideonotes.adapter.VideoInfoAdapter;
import com.example.yourvideonotes.databinding.ActivityMainBinding;
import com.example.yourvideonotes.databinding.AddDialogBinding;
import com.example.yourvideonotes.model.VideoInfo;
import com.example.yourvideonotes.roomdb.VideoDatabase;
import com.example.yourvideonotes.roomdb.VideoInfoDao;
import com.example.yourvideonotes.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    List<VideoInfo> videoInfoArrayList;
    VideoInfoAdapter adapter;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new CountDownTimer(1000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        refreshRecylerView();
                        binding.swiperefresh.setRefreshing(false);
                    }

                }.start();
            }
        });



        refreshRecylerView();


        dialog = new Dialog(this);


        binding.addButtonToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              addButtonFunction();

            }
        });

    }





    public void refreshRecylerView()
    {
        videoInfoArrayList = getDataList();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoInfoAdapter((ArrayList<VideoInfo>) videoInfoArrayList);
        binding.recyclerView.setAdapter(adapter);
    }

    public List<VideoInfo> getDataList()
    {
        VideoDatabase db = Room.databaseBuilder(getApplicationContext(), VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
        VideoInfoDao videoInfoDao = db.videoInfoDao();
       return videoInfoDao.getAll();

    }

    public void addButtonFunction()
    {
        AddDialogBinding dialogBinding = AddDialogBinding.inflate(getLayoutInflater());
        View view = dialogBinding.getRoot();
        dialog.setContentView(view);


        VideoDatabase db = Room.databaseBuilder(getApplicationContext(), VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
        VideoInfoDao videoInfoDao = db.videoInfoDao();

        dialogBinding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleString = dialogBinding.titleDialogEdittext.getText().toString();
                String videoexpString = dialogBinding.explanationDialogEdittext.getText().toString();
                String linkStirng = dialogBinding.linkDialogEdittext.getText().toString();
                String date = Util.getDate(getApplicationContext());

                String hour = dialogBinding.hourDialogEdittext.getText().toString();
                String min = dialogBinding.minuteDialogEdittext.getText().toString();
                String sec = dialogBinding.secondDialogEdittext.getText().toString();
                float secondFloat = 0;

                if (Util.isEmptyStringParams(titleString,videoexpString,linkStirng,hour,min,sec))
                {
                    if (Util.videoStartTimeEditTextControl(hour,min,sec)){

                        secondFloat = Util.toSecondConvert(Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec), getApplicationContext());

                        VideoInfo video = new VideoInfo(titleString, linkStirng, videoexpString,secondFloat,date);
                        videoInfoDao.insertAll(video);
                        dialog.dismiss();
                        refreshRecylerView();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter valid time", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill in the blanks!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        dialog.show();
    }





}