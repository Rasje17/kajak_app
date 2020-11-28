package com.example.kajakcompas;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
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
