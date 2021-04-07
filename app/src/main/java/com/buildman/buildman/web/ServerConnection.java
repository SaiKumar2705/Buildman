package com.buildman.buildman.web;


import android.net.http.HttpResponseCache;
import android.util.Log;


import com.buildman.buildman.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerConnection {
    String output = null;
    InputStream in = null;
    HttpURLConnection conn = null;
    public ServerConnection() {
    }

    public String getmethods(String urls) {

        try {
            URL url = new URL(urls);
            Log.d("url", "url" + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            in = new BufferedInputStream(conn.getInputStream());
            if (conn.getResponseCode() == 200) {
                output = Utils.readStream(in);
            } else {
                output = "ERROR";
            }
            Log.d("Login Response", "Login Response" + in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            output = "ERROR";
        } catch (Exception e) {
            e.printStackTrace();
            output = "ERROR";
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                output = "ERROR";
            }
        }
        return output;
    }
    public String editfee(JSONObject urlString,String method) {
        try
        {
            URL url = new URL(Utils.URL+method);
            Log.d("inserturlfeeedti", "inserturlfeeedti" + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.setAllowUserInteraction(true);
            HttpResponseCache.setDefault(null);
            conn.setRequestProperty("Content-Type", "application/json");
            String input = new Gson().toJson(urlString);
            Log.d("insertdata", "insertdata" + input);
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(urlString.toString());
            os.flush();
            if (conn.getResponseCode() == 200)
            {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                {
                    sb.append(line);
                }
                output = sb.toString();
                System.out.println("Output11" + output);
                conn.disconnect();
            } else {}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {}
        return output;
    }

}




