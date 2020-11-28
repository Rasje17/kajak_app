package com.example.kajakcompas;

import android.location.Location;
import android.os.Parcelable;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "routes")

public class Route implements Serializable {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("coordinates")
    @ColumnInfo(name = "coordinates")
    private ArrayList<Coordinate> coordinates;

    public Route(String name, ArrayList<Coordinate> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return name;
    }
}

