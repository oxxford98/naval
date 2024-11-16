package com.example.hellojavafx.models;
import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Player;

import java.util.*;

public class RobotPlayer extends Player {
    public RobotPlayer(String name, Board board) {
        super(name, board);
    }

    public void setBoard(Board board) {
        super.setBoard(board);
    }
}