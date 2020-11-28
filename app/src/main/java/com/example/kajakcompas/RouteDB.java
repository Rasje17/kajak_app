package com.example.kajakcompas;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Route.class, version = 1)
@TypeConverters({Converters.class})
public abstract class RouteDB extends RoomDatabase {
    private static final String DB_NAME = "route_db";
    private static RouteDB instance;

    public static synchronized RouteDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RouteDB.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract RouteDao routeDao();
}
