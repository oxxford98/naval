package com.example.hellojavafx.models;

import java.io.Serializable;

public class MyCanvas implements Serializable {
    int[] coordinates;
    int orientation;

    public MyCanvas(int[] coordinates, int orientation){
        this.coordinates = coordinates;
        this.orientation = orientation;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getOrientation() {
        return orientation;
    }
}
