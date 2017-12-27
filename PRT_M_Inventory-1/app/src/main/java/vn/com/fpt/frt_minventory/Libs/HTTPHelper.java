package vn.com.fpt.frt_minventory.Libs;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Iterator;

import vn.com.fpt.frt_minventory.Model.AppSetting;

/**
 * Created by ADMIN on 11/20/2017.
 */

public class HTTPHelper {

    public static String makeHTTPRequest(String method, String url, JSONObject params, String apiKey) {

        Long startTime = Calendar.getInstance().getTimeInMillis();

        HttpURLConnection conn;
        OutputStreamWriter outputStream;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader;
        String line;
        try {
            method = method.toUpperCase();
            if (method.equals("GET")) {
                // GET
                if (params == null) {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                } else {
                    url += "?" + getRequestParamsFromJson(params);
                    conn = (HttpURLConnection) new URL(url).openConnection();
                }
            } else {
                // POST
                conn = (HttpURLConnection) new URL(url).openConnection();
            }

            conn.setRequestMethod(method);
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setConnectTimeout(AppSetting.DEFAULT_CONNECTION_TIMEOUT);

            // Set basic authorization key
            if (apiKey != null) {
                conn.setRequestProperty("Authorization", "Basic " + apiKey);
            }

            conn.connect();
            if (params != null && conn.getRequestMethod().equals("POST")) {
                outputStream = new OutputStreamWriter(conn.getOutputStream());
                outputStream.write(params.toString());
                outputStream.flush();
                outputStream.close();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            String responseText = sb.toString();

            Long duration = (Calendar.getInstance().getTimeInMillis() - startTime);
            String logText = "Request: " + url + " finished in ";
            if (duration > 1000) {
                logText += String.valueOf(duration / 1000) + " seconds";
            } else {
                logText += String.valueOf(duration) + " miliseconds";
            }
            Log.d("DBG-SV", logText);

            return responseText;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //post
    public static String getRequestParamsFromJson(JSONObject params) {

        String urlParams = "";
        try {
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.getString(key);
                if (urlParams.equals("")) {
                    urlParams += key + "=" + URLEncoder.encode(value, "UTF-8");
                }
                else {
                    urlParams += "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
                }
            }
            return urlParams;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}