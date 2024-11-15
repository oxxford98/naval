package com.example.hellojavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.hellojavafx.models.Board;

public class GameController {
    private Board HumanPlayerBoard;
    private Board robotPlayerBoard;
    private boolean isHumanTurn;

    public GameController(HashMap<String, Object>[][] positions) {
        this.HumanPlayerBoard = new Board(positions);
        this.isHumanTurn = true;
    }

    @FXML
    public void initialize() {
    }

    public void handleAttack(int row, int col) {
        Board currentBoard = isHumanTurn ? robotPlayerBoard : HumanPlayerBoard;
        HashMap<String, Object> result = currentBoard.validateAttack(row, col);
        int status = (int) result.get("status");

        // Si el ataque falla (status = 0), cambia el turno
        if (status == 0) {
            changeTurn();
        }

        // Actualiza la vista del juego según el resultado del ataque
    }

    private void changeTurn() {
        isHumanTurn = !isHumanTurn;
    }

    public boolean isHumanTurn() {
        return isHumanTurn;
    }

    private void printBoard() {
        for (ArrayList<HashMap<String, Object>> row : HumanPlayerBoard.getBoard()) {
            for (HashMap<String, Object> cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public Board getHumanPlayerBoard() {
        return HumanPlayerBoard;
    }
}