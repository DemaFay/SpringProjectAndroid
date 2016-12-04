package com.androidapp.demafayz.aberoy.network;

import com.androidapp.demafayz.aberoy.network.data.RequestResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class RequestUtils {

    public static final int BUFFER_SIZE = 1000;

    public static RequestResult baseRequest(String requestUrl, RequestMethods requestMethod, String requestBody) {
        RequestResult requestResult = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (requestMethod == RequestMethods.GET) {
                conn.setDoInput(true);
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
            }
            requestResult = new RequestResult();
            int requestCode = conn.getResponseCode();
            requestResult.setCode(requestCode);
            if (requestCode == HttpURLConnection.HTTP_OK) {
                String result = readStream(conn.getInputStream());
                requestResult.setMessage(result);
            } else {
                String result = readStream(conn.getErrorStream());
                requestResult.setMessage(result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return requestResult;
        }
    }

    private static String readStream(InputStream inputStream) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream), BUFFER_SIZE);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        return sb.toString();
    }

    public enum RequestMethods {
        GET, POST, PUT
    }
}
