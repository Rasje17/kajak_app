package com.example.kajakcompas;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private float latitude;
    private float longitude;

    public Coordinate(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.latitude + " " + this.longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

}

