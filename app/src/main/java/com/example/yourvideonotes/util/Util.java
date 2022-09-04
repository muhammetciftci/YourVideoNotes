package com.example.yourvideonotes.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.room.Room;

import com.example.yourvideonotes.databinding.AddDialogBinding;
import com.example.yourvideonotes.model.VideoInfo;
import com.example.yourvideonotes.roomdb.VideoDatabase;
import com.example.yourvideonotes.roomdb.VideoInfoDao;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {



    public static float toSecondConvert(int hour, int minute, int second, Context context) {
        if (hour <= 60 && minute <= 60 && second <= 60) {
            minute = minute + (60*hour);
            second = second + (minute * 60);
            return Float.parseFloat(String.valueOf(second));
        }
        else {
            Toast.makeText(context, "invalid Video Time", Toast.LENGTH_SHORT).show();
            return 0.0f;
        }

    }

    public static String[] toHourMinSecondConvert(float second) {
        float hour=0, min=0;
        if (second > 59){
            min = second/60;
            second = second % 60;
        }
        if (min > 59){
            hour = min/60;
            min = min % 60;
        }

        int hourInt = Math.round(hour);
        int minInt = Math.round(min);
        int secInt = Math.round(second);

        String[] result = stepControl(hourInt,minInt,secInt);
        return result;
    }

    public static String[] stepControl(int hour,int min, int sec){

        String hourStr,minStr,secStr;

        // hour
        if (10 > hour){
            hourStr = "0"+String.valueOf(hour);
        }
        else{
            hourStr = String.valueOf(hour);
        }

        // min
        if (10 > min){
            minStr = "0"+String.valueOf(min);
        }
        else{
            minStr = String.valueOf(min);
        }

        //sec
        if (10 > sec){
            secStr = "0"+String.valueOf(sec);
        }
        else{
            secStr = String.valueOf(sec);
        }

        return new String[] {hourStr,minStr,secStr};

    }

    public static boolean videoStartTimeEditTextControl(String hour, String min, String sec){

        if (Integer.parseInt(hour) > 60){
            return false;
        }
        if (Integer.parseInt(min) > 60){
            return false;
        }
        if (Integer.parseInt(sec) > 60){
            return false;
        }

        return true;
    }

    public static boolean isEmptyStringParams(String... strings){
        int i = 0;
        while (strings.length > i){
            if (strings[i].isEmpty()){
                return false;
            }
            i++;
        }
        return true;
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

    public static void deleteVideo(Context context, int videoId){
        VideoDatabase db = Room.databaseBuilder(context, VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
        VideoInfoDao videoInfoDao = db.videoInfoDao();
        videoInfoDao.deleteVideo(videoId);
    }

    public static void dialogEdit(Context context, VideoInfo info, int videoId){
        Dialog dialog = new Dialog(context);
        LayoutInflater inflater = dialog.getLayoutInflater();
        AddDialogBinding dialogBinding = AddDialogBinding.inflate(inflater);
        View view = dialogBinding.getRoot();
        dialog.setContentView(view);


        VideoDatabase db = Room.databaseBuilder(context, VideoDatabase.class, "videoinfo").allowMainThreadQueries().build();
        VideoInfoDao videoInfoDao = db.videoInfoDao();

        // current edittext
        dialogBinding.explanationDialogEdittext.setText(info.videoExplanation);
        dialogBinding.titleDialogEdittext.setText(info.videoTitle);
        dialogBinding.linkDialogEdittext.setText(info.videoLink);

        // step control
        String[] convertedSecond = Util.toHourMinSecondConvert(info.videoSec);
        dialogBinding.hourDialogEdittext.setText(convertedSecond[0]);
        dialogBinding.minuteDialogEdittext.setText(convertedSecond[1]);
        dialogBinding.secondDialogEdittext.setText(convertedSecond[2]);

        // for return VideoInfo:
        String date = info.date;


        dialogBinding.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dialogLinkEdittextStr = dialogBinding.linkDialogEdittext.getText().toString();
                String dialogExpEdittextStr = dialogBinding.explanationDialogEdittext.getText().toString();
                String dialogTitleEdittextStr = dialogBinding.titleDialogEdittext.getText().toString();

                // time
                String dialogHourEdittextStr = dialogBinding.hourDialogEdittext.getText().toString();
                String dialogMinEdittextStr = dialogBinding.minuteDialogEdittext.getText().toString();
                String dialogSecEdittextStr = dialogBinding.secondDialogEdittext.getText().toString();


                if (Util.isEmptyStringParams(dialogLinkEdittextStr,dialogExpEdittextStr,dialogTitleEdittextStr,dialogHourEdittextStr,dialogMinEdittextStr,dialogSecEdittextStr))
                {
                    if (Util.videoStartTimeEditTextControl(dialogHourEdittextStr,dialogMinEdittextStr,dialogSecEdittextStr))
                    {
                        //convert for youtubeplayer
                        float convertToSec= Util.toSecondConvert(Integer.parseInt(dialogHourEdittextStr),Integer.parseInt(dialogMinEdittextStr),Integer.parseInt(dialogSecEdittextStr),v.getContext());
                        videoInfoDao.updateVideo(videoId,dialogLinkEdittextStr,dialogTitleEdittextStr,dialogExpEdittextStr,convertToSec);
                        dialog.dismiss();
                    }
                    else {
                        Toast.makeText(v.getContext(), "Please enter valid time", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(v.getContext(), "Please fill in the blanks", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }


}

