package com.julongsoft.measure.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tao on 2016/5/24.
 */


public class PrefUtil {

    public static void putBoolean(Context content, String key, boolean value){
        SharedPreferences sp = content.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context content, String key, boolean defValue){
        SharedPreferences sp = content.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }


    public static void putString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String getString(Context context, String key, String defValue){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void putInt(Context context, String key, int value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context, String key, int defValue){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
}
