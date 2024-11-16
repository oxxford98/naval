package com.example.hellojavafx.models;
import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Player;

import java.util.HashMap;
import java.util.Random;

public class RobotPlayer extends Player {
    public RobotPlayer(String name) {
        super(name, new Board(selectRandomPredefinedBoard()));
    }

    private static HashMap<String, Object>[][] selectRandomPredefinedBoard() {
        HashMap<String, Object>[][] predefinedBoard1 = new HashMap[10][10];
        HashMap<String, Object>[][] predefinedBoard2 = new HashMap[10][10];

        initializePredefinedBoard(predefinedBoard1);
        initializePredefinedBoard(predefinedBoard2);

        predefinedBoard1[0][0].put("type", 1);
        predefinedBoard1[0][1].put("type", 1);

        predefinedBoard2[1][0].put("type", 1);
        predefinedBoard2[1][1].put("type", 1);

        Random random = new Random();
        int choice = random.nextInt(2);
        return choice == 0 ? predefinedBoard1 : predefinedBoard2;
    }

    private static void initializePredefinedBoard(HashMap<String, Object>[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HashMap<String, Object> cell = new HashMap<>();
                cell.put("type", 0);
                cell.put("used", 0);
                board[i][j] = cell;
            }
        }
    }

    public void setBoard(Board board) {
        super.setBoard(board);
    }
}
