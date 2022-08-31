package com.example.yourvideonotes.roomdb;

import androidx.room.RoomDatabase;

import com.example.yourvideonotes.model.VideoInfo;

@androidx.room.Database(entities = {VideoInfo.class}, version = 1)
public abstract class VideoDatabase extends RoomDatabase {

    private static  VideoDatabase INSTANCE;

    public abstract VideoInfoDao videoInfoDao();




}
