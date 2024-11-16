package com.example.hellojavafx.models;

import java.io.Serializable;

/**
 * Represents a player in the Battleship game.
 */
public class Player implements Serializable {
    private String name;
    private Board board;

    /**
     * Constructs a new Player with the specified name and board.
     *
     * @param name The name of the player.
     * @param board The player's board.
     */
    public Player(String name, Board board){
        this.name = name;
        this.board = board;
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the game board associated with the player.
     *
     * @return The game board.
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Sets the game board associated with the player.
     *
     * @param board The new game board.
     */
    protected void setBoard(Board board) {
        this.board = board;
    }
}
