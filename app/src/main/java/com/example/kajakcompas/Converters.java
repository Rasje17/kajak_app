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
    public static Route fromString(String value) {
        Type type = new TypeToken<Route>(){}.getType();
        Gson gson = new Gson().fromJson(value, type);
        Log.d("Converter", gson.toString());
        return new Gson().fromJson(value, type);
    }
    @TypeConverter
    public static String fromArrayList(Route route) {
        Type listType = new TypeToken<Route>(){}.getType();
        Gson gson = new Gson();
        Log.d("Converter", "" + route);
        String json = gson.toJson(route, listType);
        Log.d("Converter", "" + json);
        return json;
    }
}
