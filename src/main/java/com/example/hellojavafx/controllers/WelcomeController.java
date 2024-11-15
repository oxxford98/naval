package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.MyCanvas;
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
import java.util.HashMap;

public class WelcomeController {

    @FXML
    private GridPane paneBattle;




    private int orientation = 1;
    private int size = 1;
    private boolean showBoard = false;
    private Image floatingImage;
    private HashMap<String, Object>[][] positions;

    /**
     * Inicia el juego con el tamaño especificado.
     *
     * @param event El evento de acción que desencadena el inicio del juego.
     */
    @FXML
    public void startGame(ActionEvent event) throws IOException {
        GameView gameView = new GameView(positions);
        gameView.show();
    }

    @FXML
    public void initialize() {
        addButtons();
        positions = createPositions();
    }

    public HashMap<String, Object>[][] createPositions() {
        HashMap<String, Object>[][] positions = new HashMap[10][10];

        // Inicializar cada HashMap en la matriz
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new HashMap<>();
                positions[i][j].put("used", 0);
            }
        }
        return positions;
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
        Button button = (Button) actionEvent.getSource();
        Integer row = GridPane.getRowIndex(button);
        Integer col = GridPane.getColumnIndex(button);

        if (row == null || col == null) {
            return;
        }

        boolean isValid = true;
        for (int i = 0; i < size; i++) {
            int targetRow = row + (orientation == 0 ? i : 0);
            int targetCol = col + (orientation == 1 ? i : 0);
            if (targetRow >= 10 || targetCol >= 10 || positions[targetRow][targetCol].containsKey("occupied")) {
                isValid = false;
                break;
            }
        }

        if (isValid) {
            int[][] coordinates = new int[size][2];
            for (int i = 0; i < size; i++) {
                int targetRow = row + (orientation == 0 ? i : 0);
                int targetCol = col + (orientation == 1 ? i : 0);
                positions[targetRow][targetCol].put("occupied", true);
                positions[targetRow][targetCol].put("type", size);
                coordinates[i][0] = targetRow;
                coordinates[i][1] = targetCol;
                Button targetButton = getButtonAt(targetRow, targetCol);
                if (targetButton != null) {
                    targetButton.setStyle("-fx-background-color: blue;");
                }
            }
            for (int i = 0; i < size; i++) {
                int targetRow = row + (orientation == 0 ? i : 0);
                int targetCol = col + (orientation == 1 ? i : 0);
                positions[targetRow][targetCol].put("coordinates", coordinates);
            }
            positions[row][col].put("canvas", new MyCanvas(new int[] {col*25, row*25}, orientation));
            System.out.println("Valid position: row " + row + ", col " + col);
            printPositions();
        } else {
            System.out.println("Invalid position: row " + row + ", col " + col);
        }
    }

    private void printPositions() {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                System.out.println("Position [" + i + "][" + j + "]: " + positions[i][j]);
            }
        }
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
                int rowP = GridPane.getRowIndex(node);
                int colP = GridPane.getColumnIndex(node);
                if (positions[rowP][colP].containsKey("occupied")) {
                    node.setStyle("-fx-background-color: blue;");
                } else {
                    node.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
                }
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