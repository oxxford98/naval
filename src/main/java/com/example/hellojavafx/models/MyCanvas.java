package com.example.hellojavafx.models;

import java.io.Serializable;


/**
 * Represents a canvas in the Battleship game.
 */
public class MyCanvas implements Serializable {
    int[] coordinates;
    int orientation;

    /**
     * Constructs a new MyCanvas with the specified coordinates and orientation.
     *
     * @param coordinates The coordinates of the canvas.
     * @param orientation The orientation of the canvas.
     */
    public MyCanvas(int[] coordinates, int orientation){
        this.coordinates = coordinates;
        this.orientation = orientation;
    }

    /**
     * Returns the coordinates of the canvas.
     *
     * @return The coordinates of the canvas.
     */
    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * Returns the orientation of the canvas.
     *
     * @return The orientation of the canvas.
     */
    public int getOrientation() {
        return orientation;
    }
}
