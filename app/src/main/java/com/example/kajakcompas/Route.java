package com.example.kajakcompas;

import android.location.Location;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "routes")
public class Route{
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "coordinates")
    private ArrayList<Location> coordinates;

    public Route(int id, String name, ArrayList<Location> coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }

    public Route(String name, ArrayList<Location> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Location> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Location> coordinates) {
        this.coordinates = coordinates;
    }
}

