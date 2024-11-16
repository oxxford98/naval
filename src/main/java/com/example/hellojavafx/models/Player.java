package com.example.hellojavafx.models;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private Board board;

    public Player(String name, Board board){
        this.name = name;
        this.board = board;
    }

    public String getName(){
        return this.name;
    }

    public Board getBoard(){
        return this.board;
    }

    protected void setBoard(Board board) {
        this.board = board;
    }
}
