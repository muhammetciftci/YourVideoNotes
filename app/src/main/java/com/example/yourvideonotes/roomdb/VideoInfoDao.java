package com.example.yourvideonotes.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yourvideonotes.model.VideoInfo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VideoInfoDao {

    @Query("Select * From videoinfo")
    List<VideoInfo> getAll();

    @Query("Select * From videoinfo where id = :videoId")
    List<VideoInfo> getVideo(int videoId);

    @Insert
    void insertAll(VideoInfo... infos);

    @Query("Delete from videoinfo where id=:videoId")
    void deleteVideo(int videoId);

    @Query("Update videoinfo set videotitle=:videoTitle, videpexp=:videoExp, videolink=:videoUrl, videosec=:videoSec where id=:videoId")
    void updateVideo(int videoId, String videoUrl, String videoTitle, String videoExp, float videoSec);

}
