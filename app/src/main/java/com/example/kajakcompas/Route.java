package com.example.kajakcompas;

import android.location.Location;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "routes")

public class Route{
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("coordinates")
    @ColumnInfo(name = "coordinates")
    private ArrayList<Coordinate> coordinates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Route(String name, ArrayList<Coordinate> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}

