package com.example.kajakcompas;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Route {
    String name;
    ArrayList<Coordinate> coordinates;

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

