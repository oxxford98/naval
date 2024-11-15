package com.example.hellojavafx.models;

public class Player {
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
}
