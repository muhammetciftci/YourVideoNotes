package com.example.yourvideonotes.util;

import android.content.Context;
import android.widget.Toast;

public class Util {

    public static float videoSecondMaker(int hour, int minute, int second, Context context) {
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
}

