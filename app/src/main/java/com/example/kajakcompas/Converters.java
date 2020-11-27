package com.example.kajakcompas;

import android.location.Location;
import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Coordinate> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Coordinate>>(){}.getType();
        Gson gson = new Gson().fromJson(value, listType);
        Log.d("Converter", gson.toString());
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayList(ArrayList<Coordinate> list) {
        Type listType = new TypeToken<ArrayList<Coordinate>>(){}.getType();
        Gson gson = new Gson();
        Log.d("Converter", "" + list);
        String json = gson.toJson(list, listType);
        Log.d("Converter", "" + json);
        return json;
    }
}
