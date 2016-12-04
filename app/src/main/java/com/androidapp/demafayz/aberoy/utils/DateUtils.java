package com.androidapp.demafayz.aberoy.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DemaFayz on 04.12.2016.
 */

public class DateUtils {

    private static final String BASE_APP_FORMAT = "dd-MM-yyyy";

    public static String getBaseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(BASE_APP_FORMAT);
        return sdf.format(date);
    }
}
