package com.example.hellojavafx.controllers;

import com.example.hellojavafx.view.GameView;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class WelcomeController {

    @FXML
    private GridPane paneBattle;




    private int orientation = 1;
    private int size = 1;
    private boolean showBoard = false;
    private Image floatingImage;

    /**
     * Inicia el juego con el tamaño especificado.
     *
     * @param event El evento de acción que desencadena el inicio del juego.
     */
    @FXML
    public void startGame(ActionEvent event) throws IOException {
        GameView gameView = new GameView();
        gameView.show();
    }

    @FXML
    public void initialize() {
        //addButtons();
    }

    public void openInstructions(ActionEvent event) {
        try {
            File file = new File(getClass().getResource("/com/example/hellojavafx/instrucciones.html").toURI());
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void addButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                javafx.scene.control.Button button = new javafx.scene.control.Button();
                button.setMinSize(25, 25);
                button.setMaxSize(25, 25);
                button.setOnAction(this::handleButtonAction);
                int finalI = i;
                int finalJ = j;
                button.setOnMouseEntered(event -> handleMouseHover(event, finalI, finalJ));
                paneBattle.add(button, i, j);
            }
        }
    }

    private void handleButtonAction(ActionEvent actionEvent) {
    }

    private void handleMouseHover(MouseEvent event, int row, int col) {
        System.out.println("Hovered over button at row: " + row + ", col: " + col);
        changeButtonColors(col, row);
    }

    private void handleMouseMoved(MouseEvent event) {
        System.out.println("Mouse moved");
        int row = (int) (event.getY() / 25);
        int col = (int) (event.getX() / 25);
        changeButtonColors(row, col);
    }

    public void handleCreateFrigate() {
        startSelection(1, orientation);

        size = 1;
        orientation = (orientation == 1) ? 0 : 1;
    }

    public void handleCreateDestroyer() {
        startSelection(2, orientation);
        size = 2;
        orientation = (orientation == 1) ? 0 : 1;
    }

    public void handleCreateSubmarine() {
        startSelection(3, orientation);
        size = 3;
        orientation = (orientation == 1) ? 0 : 1;
    }

    public void handleCreateAircraft() {
        startSelection(4, orientation);
        size = 4;
        orientation = (orientation == 1) ? 0 : 1;
        floatingImage = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4.png"));
        showBoard = true;
    }

    public void startSelection(int size, int orientation) {
        this.size = size;
        this.orientation = orientation;
    }

    private void changeButtonColors(int row, int col) {
        for (Node node : paneBattle.getChildren()) {
            if (node instanceof javafx.scene.control.Button) {
                node.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
            }
        }

        for (int i = 0; i < size; i++) {
            int targetRow = row + (orientation == 0 ? i : 0);
            int targetCol = col + (orientation == 1 ? i : 0);
            if (targetRow < 10 && targetCol < 10) {
                javafx.scene.control.Button button = getButtonAt(targetRow, targetCol);
                if (button != null) {
                    button.setStyle("-fx-background-color: blue;");
                }
            }
        }
    }

    private javafx.scene.control.Button getButtonAt(int row, int col) {
        for (Node node : paneBattle.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }
}