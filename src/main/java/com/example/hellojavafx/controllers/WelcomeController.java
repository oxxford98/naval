package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.MyCanvas;
import com.example.hellojavafx.view.GameView;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Controller class for the welcome screen of the Battleship game.
 */
public class WelcomeController {

    @FXML
    private GridPane paneBattle;
    @FXML
    private Button btnStartGame;
    @FXML
    private Label lblFrigate, lblDestroyer, lblSubmarine, lblAircraft, lblUsername, countShipsUser, countShipsRobot;
    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnStartOldGame;

    private int orientation = 1;
    private int size = 1;
    private HashMap<String, Object>[][] positions;
    private int frigateCount = 0, destroyerCount = 0, submarineCount = 0, aircraftCount = 0;
    private String humanPlayerName;
    private String humanPlayerShipsCount;
    private String robotPlayerShipsCount;

    /**
     * starts a new game with the speccified size.
     *
     * @param event the action event that triggers the new game.
     */
    @FXML
    public void startNewGame(ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            new AlertBox().showAlert("Error", "Nombre de usuario vacio", "Por favor ingrese un nombre de usuario");
            return;
        }
        GameView gameView = new GameView(positions, false, username);
        gameView.show();
    }

    /**
     * Starts an old game.
     *
     * @param event The action event that triggers the start of the old game.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void startOldGame(ActionEvent event) throws IOException {
        String username = humanPlayerName;
        GameView gameView = new GameView(positions, true, username);
        gameView.show();
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        addButtons();
        positions = createPositions();
        changeTotalShips();
        boolean oldGame = loadGameStateFromFile();
        if (oldGame) {
            lblUsername.setText("Juego Guardado, usuario: "+humanPlayerName);
            countShipsUser.setText("Barcos hundidos: "+humanPlayerShipsCount);
            countShipsRobot.setText("Barcos hundidos maquina:"+robotPlayerShipsCount);
        } else {
            btnStartOldGame.setDisable(true);
        }
    }

    /**
     * Updates the total ships count labels.
     */
    private void changeTotalShips() {
        lblFrigate.setText(frigateCount + "/4");
        lblDestroyer.setText(destroyerCount + "/3");
        lblSubmarine.setText(submarineCount + "/2");
        lblAircraft.setText(aircraftCount + "/1");
    }

    /**
     * Creates the positions for the game board.
     *
     * @return A 2D array of HashMaps representing the positions.
     */
    public HashMap<String, Object>[][] createPositions() {
        HashMap<String, Object>[][] positions = new HashMap[10][10];


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new HashMap<>();
                positions[i][j].put("type", 0);
                positions[i][j].put("used", 0);
                positions[i][j].put("type", 0);
            }
        }
        return positions;
    }

    /**
     * Opens the instructions file.
     *
     * @param event The action event that triggers the opening of the instructions.
     */
    public void openInstructions(ActionEvent event) {
        try {
            File file = new File(getClass().getResource("/com/example/hellojavafx/instrucciones.html").toURI());
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds buttons to the game board.
     */
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

    /**
     * Handles the button action event.
     *
     * @param actionEvent The action event.
     */
    private void handleButtonAction(ActionEvent actionEvent) {
        if (isCompleteBoard()) {
            new AlertBox().showAlert("Error", "Tablero completado", "Ya has completado el tablero y puedes iniciar un juego nuevo");
            return;
        }
        Button button = (Button) actionEvent.getSource();
        Integer row = GridPane.getRowIndex(button);
        Integer col = GridPane.getColumnIndex(button);

        if (row == null || col == null) {
            return;
        }

        boolean isValid = true;
        boolean isAll = false;
        for (int i = 0; i < size; i++) {
            int targetRow = row + (orientation == 0 ? i : 0);
            int targetCol = col + (orientation == 1 ? i : 0);
            if (targetRow >= 10 || targetCol >= 10 || positions[targetRow][targetCol].containsKey("occupied")) {
                isValid = false;
                break;
            }
        }

        switch (size) {
            case 1:
                if (frigateCount >= 4) {
                    isValid = false;
                    isAll = true;
                    new AlertBox().showAlert("Error", null, "Ya no puedes usar mas fragatas");
                }
                break;
            case 2:
                if (destroyerCount >= 3) {
                    isValid = false;
                    isAll = true;
                    new AlertBox().showAlert("Error", null, "Ya no puedes usar mas destructores");
                }
                break;
            case 3:
                if (submarineCount >= 2) {
                    isValid = false;
                    isAll = true;
                    new AlertBox().showAlert("Error", null, "Ya no puedes usar mas submarinos");
                }
                break;
            case 4:
                if (aircraftCount >= 1) {
                    isValid = false;
                    isAll = true;
                    new AlertBox().showAlert("Error", null, "Ya no puedes usar mas portaaviones");
                }
                break;
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
            switch (size) {
                case 1:
                    frigateCount++;
                    break;
                case 2:
                    destroyerCount++;
                    break;
                case 3:
                    submarineCount++;
                    break;
                case 4:
                    aircraftCount++;
                    break;
            }
            changeTotalShips();
            int[][] coords = (int[][]) positions[row][col].get("coordinates");
            for (int i = 0; i < coords.length; i++) {
                System.out.println("Coordinates: " + coords[i][0] + ", " + coords[i][1]);
            }
            System.out.println("Valid position: row " + row + ", col " + col);
        } else {
            if (!isAll) {
                new AlertBox().showAlert("Error", "Posicion invalida", "No puedes colocar la pieza en esa posicion");
            }
            System.out.println("Invalid position: row " + row + ", col " + col);
        }
        if (isCompleteBoard()) {
            btnStartGame.setDisable(false);
        }
    }

    /**
     * Checks if the board is complete.
     *
     * @return True if the board is complete, false otherwise.
     */
    private boolean isCompleteBoard() {
        return frigateCount == 4 && destroyerCount == 3 && submarineCount == 2 && aircraftCount == 1;
    }

    private void printPositions() {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                System.out.println("Position [" + i + "][" + j + "]: " + positions[i][j]);
            }
        }
    }

    /**
     * Handles the mouse hover event.
     *
     * @param event The mouse event.
     * @param row   The row index.
     * @param col   The column index.
     */
    private void handleMouseHover(MouseEvent event, int row, int col) {
        if (!isCompleteBoard()) {
            changeButtonColors(col, row);
        }
    }

    /**
     * Handles the creation of a frigate.
     */
    public void handleCreateFrigate() {
        if (frigateCount >= 4) {
            System.out.print("Frigate limit reached");
        } else {
            size = 1;
            orientation = (orientation == 1) ? 0 : 1;
        }
    }

    /**
     * Handles the creation of a destroyer.
     */
    public void handleCreateDestroyer() {
        if (destroyerCount >= 3) {
            System.out.print("Destroyer limit reached");
        } else {
            size = 2;
            orientation = (orientation == 1) ? 0 : 1;
        }
    }

    /**
     * Handles the creation of a submarine.
     */
    public void handleCreateSubmarine() {
        if (submarineCount >= 2) {
            System.out.print("Submarine limit reached");
        } else {
            size = 3;
            orientation = (orientation == 1) ? 0 : 1;
        }
    }

    /**
     * Handles the creation of an aircraft.
     */
    public void handleCreateAircraft() {
        if (aircraftCount >= 1) {
            System.out.print("Aircraft limit reached");
        } else {
            size = 4;
            orientation = (orientation == 1) ? 0 : 1;
        }
    }

    /**
     * Changes the colors of the buttons on the game board.
     *
     * @param row The row index.
     * @param col The column index.
     */
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

    /**
     * Gets the button at the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The button at the specified row and column, or null if not found.
     */
    private javafx.scene.control.Button getButtonAt(int row, int col) {
        for (Node node : paneBattle.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    /**
     * Loads the game state from a file.
     *
     * @return True if the game state was loaded successfully, false otherwise.
     */
    public boolean loadGameStateFromFile() {
        String filePath = "game_state.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("HumanPlayer: ")) {
                    humanPlayerName = line.substring(13);
                } else if (line.startsWith("HumanPlayerShipsCount: ")) {
                    humanPlayerShipsCount = line.substring(22);
                } else if (line.startsWith("RobotPlayerShipsCount: ")) {
                    robotPlayerShipsCount = line.substring(22);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}