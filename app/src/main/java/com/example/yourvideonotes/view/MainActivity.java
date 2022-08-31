package com.example.yourvideonotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yourvideonotes.adapter.VideoInfoAdapter;
import com.example.yourvideonotes.databinding.ActivityMainBinding;
import com.example.yourvideonotes.databinding.AddDialogBinding;
import com.example.yourvideonotes.model.VideoInfo;
import com.example.yourvideonotes.roomdb.VideoDatabase;
import com.example.yourvideonotes.roomdb.VideoInfoDao;

import java.util.ArrayList;
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
                float secondFloat = 0;

                if (!titleString.isEmpty() && !videoexpString.isEmpty() && !linkStirng.isEmpty() && !secondString.isEmpty())
                {
                    secondFloat = Float.parseFloat(secondString);
                    VideoInfo video = new VideoInfo(titleString, linkStirng, videoexpString,secondFloat);
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



}