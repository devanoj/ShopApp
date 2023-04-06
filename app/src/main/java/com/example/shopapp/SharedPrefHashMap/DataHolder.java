package com.example.shopapp.SharedPrefHashMap;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DataHolder {
    public static void saveHashMap(Context context, HashMap<String, String> hashMap) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String hashMapJson = gson.toJson(hashMap);
        editor.putString("hashMap", hashMapJson);
        editor.apply();
    }

    public static HashMap<String, String> getHashMap(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String hashMapJson = prefs.getString("hashMap", "");
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        HashMap<String, String> hashMap = gson.fromJson(hashMapJson, type);
        return hashMap;
    }
}
