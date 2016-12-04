package com.androidapp.demafayz.aberoy.utils;

import android.util.Log;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class AppUtils {
    public static final boolean DEBUG = true;

    public static void dLog(Class classObj, String text) {
        if (DEBUG) {
            Log.d(classObj.getSimpleName(), text);
        }
    }

    public static void eLog(Class classObj, String text) {
        if (DEBUG) {
            Log.e(classObj.getSimpleName(), text);
        }
    }
}
