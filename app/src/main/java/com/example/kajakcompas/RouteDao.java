package com.example.kajakcompas;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface RouteDao {
    @Query("SELECT * FROM routes")
    List<Route> getRoutes();
    @Insert
    void insertRoute(Route route);
    @Update
    void updateRoute(Route route);
    @Delete
    void deleteRoute(Route route);
}
