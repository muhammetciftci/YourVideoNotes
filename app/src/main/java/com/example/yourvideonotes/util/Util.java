package com.example.yourvideonotes.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

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
        int minInt = Math.round(hour);
        int secInt = Math.round(hour);

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

}

