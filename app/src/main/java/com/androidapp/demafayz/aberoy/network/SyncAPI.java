package com.androidapp.demafayz.aberoy.network;

import android.net.Uri;

import com.androidapp.demafayz.aberoy.network.data.RequestResult;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class SyncAPI {

    public static final String HOST = "http://10.0.3.2:8080";
    public static final String GET_ALL_LECTURERS = HOST + "/lecturers";

    public static RequestResult getAllLecturers() {
        RequestResult result = RequestUtils.baseRequest(GET_ALL_LECTURERS, RequestUtils.RequestMethods.GET, null);
        return result;
    }

    public static RequestResult getLecturer(long mItemId) {
        Uri uri = Uri.parse(GET_ALL_LECTURERS);
        uri = uri.buildUpon().appendPath(String.valueOf(mItemId)).build();
        String newUrl = uri.toString();
        RequestResult result = RequestUtils.baseRequest(newUrl, RequestUtils.RequestMethods.GET, null);
        return result;
    }
}
