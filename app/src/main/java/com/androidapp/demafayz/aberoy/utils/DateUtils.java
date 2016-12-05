package com.androidapp.demafayz.aberoy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DemaFayz on 04.12.2016.
 */

public class DateUtils {

    private static final String BASE_APP_FORMAT = "dd-MM-yyyy";
    private static final String BASE_UI_APP_FORMAT = "dd MMMM yyyy";
    private static final String DATE_PICKER_FORMAT = "yyyy/MM/dd";

    public static String getBaseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(BASE_APP_FORMAT);
        return sdf.format(date);
    }

    public static String getBaseUIDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(BASE_UI_APP_FORMAT);
        return sdf.format(date);
    }

    public static Date getLongByIntDate(int year, int month, int day) {
        month += 1;
        String currentDate = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date d = null;
        try {
            d = df.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
