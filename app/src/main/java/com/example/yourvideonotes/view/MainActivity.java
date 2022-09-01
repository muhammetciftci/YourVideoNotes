package com.example.yourvideonotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yourvideonotes.adapter.VideoInfoAdapter;
import com.example.yourvideonotes.databinding.ActivityMainBinding;
import com.example.yourvideonotes.databinding.AddDialogBinding;
import com.example.yourvideonotes.model.VideoInfo;
import com.example.yourvideonotes.roomdb.VideoDatabase;
import com.example.yourvideonotes.roomdb.VideoInfoDao;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        VideoDatabase db = Room.databaseBuilder(getApplicationContext(),
                VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
        VideoInfoDao videoInfoDao = db.videoInfoDao();

       return videoInfoDao.getAll();

    }

    public void addButtonFunction()
    {
        AddDialogBinding dialogBinding = AddDialogBinding.inflate(getLayoutInflater());
        View view = dialogBinding.getRoot();
        dialog.setContentView(view);


        VideoDatabase db = Room.databaseBuilder(getApplicationContext(),
                VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
        VideoInfoDao videoInfoDao = db.videoInfoDao();

        dialogBinding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleString = dialogBinding.titleDialogEdittext.getText().toString();
                String videoexpString = dialogBinding.explanationDialogEdittext.getText().toString();
                String linkStirng = dialogBinding.linkDialogEdittext.getText().toString();
                String secondString =  dialogBinding.secondDialogEdittext.getText().toString();
                String date = getDate(getApplicationContext());
                float secondFloat = 0;

                if (!titleString.isEmpty() && !videoexpString.isEmpty() && !linkStirng.isEmpty() && !secondString.isEmpty())
                {
                    secondFloat = Float.parseFloat(secondString);
                    VideoInfo video = new VideoInfo(titleString, linkStirng, videoexpString,secondFloat,date);
                    videoInfoDao.insertAll(video);
                    dialog.dismiss();
                    refreshRecylerView();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill in the blanks!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        dialog.show();
    }

    public static String getDate(Context context){
        Date date = new Date();
        DateFormat dateFormatTime = android.text.format.DateFormat.getTimeFormat(context);
        DateFormat dateFormatDate = android.text.format.DateFormat.getDateFormat(context);
        String dateStr = dateFormatDate.format(date); android.text.format.DateFormat.getTimeFormat(context);
        String timeStr = dateFormatTime.format(date); android.text.format.DateFormat.getTimeFormat(context);
        String localDateTimeString=dateStr +" "+ timeStr;
        return localDateTimeString;
    }



}