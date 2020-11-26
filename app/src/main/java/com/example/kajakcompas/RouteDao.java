package com.example.kajakcompas;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.Update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface RouteDao {
    @Query("SELECT * FROM routes")
    List<Route> getRoutes();
    @Insert
    void insertRoute(Route... routes);
    @Delete
    void deleteRoute(Route route);
}
