package com.example.myapplication.Utility;

import android.content.SharedPreferences;

import java.util.Iterator;
import java.util.Map;

public class SharedPrefUtils {
    public static final String mypreference = "mypref";
    private SharedPreferences sharedPreferences;

    public SharedPrefUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean save(String key, String value) {
        Boolean result = Boolean.FALSE;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString(key, value);
            editor.commit();
            result = Boolean.TRUE;
        } catch (Exception e) {
            e.getStackTrace();
        }

        return result;
    }

    public String get(String key){
        return sharedPreferences.getString(key,null);
    }

    public void remove(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
