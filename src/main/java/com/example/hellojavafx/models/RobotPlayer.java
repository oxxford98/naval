package com.example.hellojavafx.models;
import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Player;

import java.util.*;

/**
 * Represents a robot player in the game.
 * This class extends the Player class and provides additional functionality specific to a robot player.
 */
public class RobotPlayer extends Player {

    /**
     * Constructs a new RobotPlayer with the specified name and board.
     *
     * @param name The name of the player.
     * @param board The player's board.
     */
    public RobotPlayer(String name, Board board) {
        super(name, board);
    }

    /**
     * Sets the game board associated with the player.
     *
     * @param board The new game board.
     */
    public void setBoard(Board board) {
        super.setBoard(board);
    }
}