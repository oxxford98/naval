package com.example.hellojavafx.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private ArrayList<ArrayList<HashMap<String, Object>>> board;

    public Board(HashMap<String, Object>[][] positions) {
        this.board = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            ArrayList<HashMap<String, Object>> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(positions[i][j]);
            }
            this.board.add(row);
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
            cell.put("used", 1);
        } else {
            throw new IndexOutOfBoundsException("Row and column must be between 0 and 9");
        }
    }

    public HashMap<String, Object> validateAttack(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            HashMap<String, Object> cell = board.get(row).get(col);
            int type = (int) cell.get("type");
            HashMap<String, Object> result = new HashMap<>();
            if ((int) cell.get("used") == 1) {
                result.put("status", -1); // Already attacked
                result.put("items", new String[]{"already_attacked.png"});
                return result;
            }
            cell.put("used", 1);
            if (type == 0) {
                result.put("status", 0);
                result.put("items", new String[]{"x.png"});
            } else if (type == 1) {
                result.put("status", 2);
                result.put("items", new String[]{"hundido.png"});
            } else {
                result.put("status", 1);
                result.put("items", new String[]{"tocado.png"});
                boolean allHit = true;
                int[][] coordinates = (int[][]) cell.get("coordinates");
                for (int[] coordinate : coordinates) {
                    int r = coordinate[0];
                    int c = coordinate[1];
                    if ((int) board.get(r).get(c).get("used") == 0) {
                        allHit = false;
                        break;
                    }
                }
                if (allHit) {
                    result.put("status", 2);
                    result.put("items", new String[]{"destruido.png"});
                    for (int[] coordinate : coordinates) {
                        int r = coordinate[0];
                        int c = coordinate[1];
                        board.get(r).get(c).put("items", new String[]{"destruido.png"});
                    }
                }
            }
            return result;
        } else {
            throw new IndexOutOfBoundsException("Row and column must be between 0 and 9");
        }
    }


}
