package com.example.hellojavafx.controllers;
import com.example.hellojavafx.models.HumanPlayer;
import com.example.hellojavafx.models.MyCanvas;
import com.example.hellojavafx.models.RobotPlayer;
import com.example.hellojavafx.view.alert.AlertBox;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

import java.util.HashMap;
import com.example.hellojavafx.models.Board;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameController {
    private HumanPlayer humanPlayer;
    private Board HumanPlayerBoard, RobotPlayerBoard;
    private RobotPlayer robotPlayer;
    private boolean isHumanTurn, loadGame, showBoardRobot = false;

    @FXML
    private Canvas mycanvas, robotcanvas;
    @FXML
    private GridPane paneAttack;
    @FXML
    private Pane PaneBattle;
    private GraphicsContext gc, rgc;
    private int typeBoat;
    private double rectx;
    private double recty;
    private int orientation;
    private int iterador = 0;
    private Image imagen;
    private HashMap<String, Object>[][] positions;

    public GameController(HashMap<String, Object>[][] positions, HashMap<String, Object>[][] robotPositions, boolean loadGame, String username) {
        this.loadGame = loadGame;
        if (loadGame) {
            this.HumanPlayerBoard = new Board(positions);
            this.RobotPlayerBoard = new Board(robotPositions);
            robotPlayer = new RobotPlayer("robot", RobotPlayerBoard);
            humanPlayer = new HumanPlayer(username, HumanPlayerBoard);
            loadGameState();
            this.isHumanTurn = true;
            this.typeBoat = 0;
            this.rectx = 0;
            this.recty = 0;
            this.orientation = 0;
        } else {
            this.HumanPlayerBoard = new Board(positions);
            this.isHumanTurn = true;
            this.positions = positions;
            this.typeBoat = 0;
            this.rectx = 0;
            this.recty = 0;
            this.orientation = 0;
            humanPlayer = new HumanPlayer(username, HumanPlayerBoard);
            this.RobotPlayerBoard = new Board(robotPositions);
            robotPlayer = new RobotPlayer("robot", RobotPlayerBoard);
        }
    }

    @FXML
    public void initialize() {
        printRobotBoard();
        Platform.runLater(() -> {
            Stage stage = (Stage) PaneBattle.getScene().getWindow();
            stage.setOnCloseRequest((WindowEvent event) -> {
                Platform.exit();
                System.exit(0);
            });
        });
        gc = mycanvas.getGraphicsContext2D();
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/fondo.png"));
        gc.drawImage(backgroundImage, 0, 0, mycanvas.getWidth(), mycanvas.getHeight());
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        // Draw the grid lines
        for (int i = 0; i <= 250; i += 25) {
            gc.strokeLine(i, 0, i, 250);
            gc.strokeLine(0, i, 250, i);
        }
        addButtons();
        if (!loadGame) {
            setobjetoHashMap(positions);
        } else {
            drawImagesButtons();
            drawHumanBoard();
        }
    }

    public void addButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                javafx.scene.control.Button button = new javafx.scene.control.Button();
                button.setMinSize(25, 25);
                button.setMaxSize(25, 25);
                button.setOnAction(this::getPositionAttack);
                paneAttack.add(button, i, j);
            }
        }
    }

    private void drawImagesButtons() {
        Board robotBoard = RobotPlayerBoard;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HashMap<String, Object> cell = robotBoard.getBoard().get(i).get(j);
                if ((int) cell.get("used") == 1) {
                    String image = (String) cell.get("image");
                    Button button = (Button) getNodeByRowColumnIndex(i, j);
                    Image img = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/" + image));
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);
                    button.setGraphic(imageView);
                }
            }
        }
    }

    private void drawHumanBoard() {
        int i = 0;
        for (ArrayList<HashMap<String, Object>> row : HumanPlayerBoard.getBoard()) {
            int j = 0;
            for (HashMap<String, Object> map : row) {
                try {
                    int type = (int) map.get("type");
                    if (type != 0) {
                        MyCanvas canvas = (MyCanvas) map.get("canvas");
                        if (canvas != null) {
                            int[] coordinates = canvas.getCoordinates();
                            int orientation = canvas.getOrientation();
                            drawBoat(coordinates[0], coordinates[1], type, orientation);
                        } else {
                            System.out.println("No hay un objeto Canvas en esta celda.");
                        }
                    }
                    int used = (int) map.get("used");
                    if (used == 1) {
                        String image = (String) map.get("image");
                        drawCanvasAttack(i, j, image);
                    }
                } catch (NullPointerException e) {
                    System.out.println("El valor de 'type' es null en la posición");
                }
                j++;
            }
            i++;
        }
    }

    public void drawRobotBoard(ActionEvent event) {
        showBoardRobot = !showBoardRobot;
        robotcanvas.setVisible(showBoardRobot);
        paneAttack.setVisible(!showBoardRobot);
        rgc = robotcanvas.getGraphicsContext2D();
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/fondo.png"));
        rgc.drawImage(backgroundImage, 0, 0, robotcanvas.getWidth(), robotcanvas.getHeight());
        rgc.setStroke(javafx.scene.paint.Color.BLACK);
        // Draw the grid lines
        for (int i = 0; i <= 250; i += 25) {
            rgc.strokeLine(i, 0, i, 250);
            rgc.strokeLine(0, i, 250, i);
        }

        int i = 0;
        for (ArrayList<HashMap<String, Object>> row : RobotPlayerBoard.getBoard()) {
            int j = 0;
            for (HashMap<String, Object> map : row) {
                try {
                    int type = (int) map.get("type");
                    if (type != 0) {
                        MyCanvas canvas = (MyCanvas) map.get("canvas");
                        if (canvas != null) {
                            int[] coordinates = canvas.getCoordinates();
                            int orientation = canvas.getOrientation();
                            drawBoatRobot(coordinates[1]*25, coordinates[0]*25, type, orientation);
                        } else {
                            System.out.println("No hay un objeto Canvas en esta celda.");
                        }
                    }
                    int used = (int) map.get("used");
                    if (used == 1) {
                        String image = (String) map.get("image");
                        drawCanvasRobotAttack(i, j, image);
                    }
                } catch (NullPointerException e) {
                    System.out.println("El valor de 'type' es null en la posición");
                }
                j++;
            }
            i++;
        }
    }

    private void changeStatusButtons(boolean status) {
        for (Node node : paneAttack.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(status);
            }
        }
    }

    private void drawCanvasAttack(int row, int col, String image) {
        imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/"+image));
        gc.drawImage(imagen, col*25, row*25, 25, 25);
    }

    private void drawCanvasRobotAttack(int row, int col, String image) {
        imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/"+image));
        rgc.drawImage(imagen, col*25, row*25, 25, 25);
    }

    private void getPositionAttack(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);
        HashMap<String, Object> response = handleAttack(row, col);
        ArrayList<int[]> buttons = (ArrayList<int[]>) response.get("buttons");
        int status = (int) response.get("status");
        if (status == -1) {
            new AlertBox().showAlert("Error", "Posicion invalida", "Ya has atacado esta posición");
            return;
        }
        String image = (String) response.get("image");
        for (int[] buttonCoords : buttons){
            int i = buttonCoords[0];
            int j = buttonCoords[1];
            Button button1 = (Button) getNodeByRowColumnIndex(i, j);
            Image img = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/" + image));
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            button1.setGraphic(imageView);
        }
        saveGameState();
        if (status == 0) {
            changeTurn();
            changeStatusButtons(true);
            robotAttack();
            saveGameState();
        }
        Board robotBoard = robotPlayer.getBoard();
        if (robotBoard.validateEndGame()) {
            new AlertBox().showAlert("Fin del juego", "Eres el ganador!!", "¡Felicidades!");
            changeStatusButtons(true);
            deleteGameStateFiles();
        }
    }

    private javafx.scene.control.Button getNodeByRowColumnIndex(int row, int col) {
        for (Node node : paneAttack.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if (nodeRow != null && nodeCol != null && nodeRow == row && nodeCol == col) {
                return (Button) node;
            }
        }
        return null;
    }

    public HashMap<String, Object> handleAttack(int row, int col) {
        Board currentBoard = isHumanTurn ? robotPlayer.getBoard() : HumanPlayerBoard;
        HashMap<String, Object> result = currentBoard.validateAttack(row, col);
        return result;
    }

    private void changeTurn() {
        isHumanTurn = !isHumanTurn;
    }

    private void printBoard() {
        for (ArrayList<HashMap<String, Object>> row : HumanPlayerBoard.getBoard()) {
            for (HashMap<String, Object> cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void printRobotBoard() {
        for (ArrayList<HashMap<String, Object>> row : robotPlayer.getBoard().getBoard()) {
            for (HashMap<String, Object> cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
  
    public void setobjetoHashMap(HashMap<String, Object>[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                HashMap<String, Object> map = array[i][j];
                try {
                    int type = (int) map.get("type");
                    if (type != 0) {
                        MyCanvas canvas = (MyCanvas) map.get("canvas");
                        if (canvas != null) {
                            int[] coordinates = canvas.getCoordinates();
                            int orientation = canvas.getOrientation();
                            drawBoat(coordinates[0], coordinates[1], type, orientation);
                        } else {
                            System.out.println("No hay un objeto Canvas en esta celda.");
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("El valor de 'type' es null en la posición [" + i + "][" + j + "]");
                }
            }
        }
    }


    public void drawBoat(int rectx, int  rexy, int type, int orientation) {
        if(type==1){
            if(orientation==0){
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/1.png"));
                gc.drawImage(imagen, rectx, rexy, 25, 25);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/1-h.png"));
                gc.drawImage(imagen, rectx, rexy, 25, 25);
            }
        } else if (type==2) {
            if(orientation==0) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/2.png"));
                gc.drawImage(imagen, rectx, rexy, 25, 50);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/2-h.png"));
                gc.drawImage(imagen, rectx, rexy, 50, 25);
            }
        } else if (type==3) {
            if(orientation==0) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/3.png"));
                gc.drawImage(imagen, rectx, rexy, 25, 75);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/3-h.png"));
                gc.drawImage(imagen, rectx, rexy, 75, 25);
            }
        }else if (type==4) {
            if(orientation==0) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4.png"));
                gc.drawImage(imagen, rectx, rexy, 25, 100);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4-h.png"));
                gc.drawImage(imagen, rectx, rexy, 100, 25);
            }
        }
    }

    public void drawBoatRobot(int rectx, int  rexy, int type, int orientation) {
        if (type == 1){
            if(orientation==0){
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/1.png"));
                rgc.drawImage(imagen, rectx, rexy, 25, 25);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/1-h.png"));
                rgc.drawImage(imagen, rectx, rexy, 25, 25);
            }
        } else if (type==2) {
            if(orientation==0) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/2.png"));
                rgc.drawImage(imagen, rectx, rexy, 25, 50);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/2-h.png"));
                rgc.drawImage(imagen, rectx, rexy, 50, 25);
            }
        } else if (type==3) {
            if(orientation==0) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/3.png"));
                rgc.drawImage(imagen, rectx, rexy, 25, 75);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/3-h.png"));
                rgc.drawImage(imagen, rectx, rexy, 75, 25);
            }
        }else if (type==4) {
            if(orientation==0) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4.png"));
                rgc.drawImage(imagen, rectx, rexy, 25, 100);
            } else if (orientation==1) {
                imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4-h.png"));
                rgc.drawImage(imagen, rectx, rexy, 100, 25);
            }
        }
    }

    private void robotAttack(){
        Random random = new Random();
        int row, col;
        boolean turn = true;
        do{
            row = random.nextInt(10);
            col = random.nextInt(10);
            HashMap<String, Object> response = handleAttack(row, col);
            ArrayList<int[]> buttons = (ArrayList<int[]>) response.get("buttons");
            int status = (int) response.get("status");
            if (status == -1) {
                continue;
            }
            String image = (String) response.get("image");
            for (int[] buttonCoords : buttons){
                int i = buttonCoords[0];
                int j = buttonCoords[1];
                drawCanvasAttack(i, j, image);
            }
            if (status == 0) {
                changeTurn();
                changeStatusButtons(false);
                turn = false;
            }

            if (HumanPlayerBoard.validateEndGame()) {
                new AlertBox().showAlert("Fin del juego", "Has perdido", "Lo siento, la maquina ha derumbado todas tus naves");
                changeStatusButtons(true);
                deleteGameStateFiles();
                turn = false;
            }
        }while (turn);
    }

    public void saveGameState() {
        String filePath = "game_state.ser";
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(HumanPlayerBoard);
            out.writeObject(robotPlayer.getBoard());
            System.out.println("Game state saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        filePath = "game_state.txt";
        int humanCanvasCount = countCanvasWithStatus(HumanPlayerBoard);
        int robotCanvasCount = countCanvasWithStatus(robotPlayer.getBoard());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("HumanPlayer: " + humanPlayer.getName());
            writer.newLine();
            writer.write("RobotPlayer: " + robotPlayer.getName());
            writer.newLine();
            writer.write("HumanPlayerShipsCount: " + humanCanvasCount);
            writer.newLine();
            writer.write("RobotPlayerShipsCount: " + robotCanvasCount);
            System.out.println("Player names and canvas counts saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts the number of objects in the board that have MyCanvas and status 2.
     *
     * @param board the board to count the objects in
     * @return the count of objects with MyCanvas and status 2
     */
    private int countCanvasWithStatus(Board board) {
        int count = 0;
        for (ArrayList<HashMap<String, Object>> row : board.getBoard()) {
            for (HashMap<String, Object> cell : row) {
                if (cell.containsKey("canvas") && cell.containsKey("status") && (int) cell.get("status") == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    public void loadGameState() {
        String filePath = "game_state.ser";
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            HumanPlayerBoard = (Board) in.readObject();
            RobotPlayerBoard = (Board) in.readObject();
            System.out.println("Game state loaded from " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteGameStateFiles() {
        String[] filePaths = {"game_state.ser", "game_state.txt"};
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(filePath + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete " + filePath);
                }
            } else {
                System.out.println(filePath + " does not exist.");
            }
        }
    }

    public void openInstructions(ActionEvent event) {
        try {
            File file = new File(getClass().getResource("/com/example/hellojavafx/instrucciones.html").toURI());
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
