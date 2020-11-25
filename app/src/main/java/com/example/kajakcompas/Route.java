package com.example.kajakcompas;

import android.location.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Route {
    private String name;
    private ArrayList<Location> coordinates;

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

