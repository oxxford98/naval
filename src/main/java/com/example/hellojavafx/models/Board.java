package com.example.hellojavafx.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private ArrayList<ArrayList<HashMap<String, Object>>> board;

    public Board(ArrayList<ArrayList<HashMap<String, Object>>> board) {
        if (board.size() == 10 && board.stream().allMatch(row -> row.size() == 10)) {
            this.board = board;
        } else {
            throw new IllegalArgumentException("Board must be a 10x10 ArrayList");
        }
    }

    public ArrayList<ArrayList<HashMap<String, Object>>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<HashMap<String, Object>>> board) {
        if (board.size() == 10 && board.stream().allMatch(row -> row.size() == 10)) {
            this.board = board;
        } else {
            throw new IllegalArgumentException("Board must be a 10x10 ArrayList");
        }


    }

    public void clickCell(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            HashMap<String, Object> cell = board.get(row).get(col);
            cell.put("used", true);
        } else {
            throw new IndexOutOfBoundsException("Row and column must be between 0 and 9");
        }
    }

    public HashMap<String, Object> validateAttack(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            HashMap<String, Object> cell = board.get(row).get(col);
            int type = (int) cell.get("type");
            HashMap<String, Object> result = new HashMap<>();
            if (type == 0) {
                result.put("status", 0);
                result.put("items", new String[]{"x.png"});
            } else {
                result.put("status", 1);
                result.put("items", new String[]{"tocado.png"});
            }
            return result;
        } else {
            throw new IndexOutOfBoundsException("Row and column must be between 0 and 9");
        }
    }


}
