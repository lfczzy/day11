package com.ztcx.videoplay.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ztcx.videoplay.app.MyApp;

/**
 * 该类用户存取key-value
 */
public class KVUtils {
    public static boolean add(String key,String  value){
        if (MyApp.sharePreferences == null){
            MyApp.sharePreferences = MyApp.getAppContext().getSharedPreferences("app_config",Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = MyApp.sharePreferences.edit();
        edit.putString(key,value);
        return edit.commit();
    }
    public static boolean delete(String key){
        if (MyApp.sharePreferences == null){
            MyApp.sharePreferences = MyApp.getAppContext().getSharedPreferences("app_config",Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = MyApp.sharePreferences.edit();
        edit.remove(key);
        return edit.commit();
    }
    public static String query(String key){
        if (MyApp.sharePreferences == null){
            MyApp.sharePreferences = MyApp.getAppContext().getSharedPreferences("app_config",Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = MyApp.sharePreferences.edit();
        edit.clear();
        String value = MyApp.sharePreferences.getString(key, "");
        return value;
    }
}
