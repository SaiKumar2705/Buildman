package com.buildman.buildman.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
    Context mContext;
    public static String URL = "http://portal.buildmantech.com//webservice.asmx/";
    public static String MyPREFERENCES="my_preferences";

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @NonNull
    public static String readStream(InputStream in){
        StringBuilder result=null;
        try
        {
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null){
                result.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}