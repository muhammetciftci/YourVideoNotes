package com.example.yourvideonotes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VideoInfo {



    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="videolink")
    public String videoLink;

    @ColumnInfo(name="videpexp")
    public String videoExplanation;

    @ColumnInfo(name="videotitle")
    public String videoTitle;

    @ColumnInfo(name="date")
    public String date;

    @ColumnInfo(name="videosec")
    public float videoSec;



    public VideoInfo(String videoTitle, String videoLink, String videoExplanation, float videoSec, String date) {
        this.videoLink = videoLink;
        this.videoExplanation = videoExplanation;
        this.videoSec = videoSec;
        this.videoTitle = videoTitle;
    }

}
