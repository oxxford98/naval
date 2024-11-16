package com.example.hellojavafx.models;
import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Player;

/**
 * Represents a human player in the Battleship game.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a new HumanPlayer with the specified name and board.
     *
     * @param name The name of the player.
     * @param board The player's board.
     */
    public HumanPlayer(String name, Board board) {
        super(name, board);
    }
}
