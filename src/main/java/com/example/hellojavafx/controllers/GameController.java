package com.example.hellojavafx.controllers;
import com.example.hellojavafx.models.MyCanvas;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.hellojavafx.models.Board;

public class GameController {
    private Board HumanPlayerBoard;
    private Board robotPlayerBoard;
    private boolean isHumanTurn;

    @FXML
    private Canvas mycanvas;
    private GraphicsContext gc;
    private int typeBoat;
    private double rectx;
    private double recty;
    private int orientation;
    private int iterador = 0;
    private Image imagen;
    private HashMap<String, Object>[][] positions;

    public GameController(HashMap<String, Object>[][] positions) {
        this.HumanPlayerBoard = new Board(positions);
        this.isHumanTurn = true;
        this.positions = positions;
        this.typeBoat = 0;
        this.rectx = 0;
        this.recty = 0;
        this.orientation = 0;
    }
    public void initialize() {
        gc = mycanvas.getGraphicsContext2D();
//        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/fondo.jpg"));
//        gc.drawImage(backgroundImage, 0, 0, mycanvas.getWidth(), mycanvas.getHeight());
        mycanvas.setStyle("-fx-background-color: blue;");
        // Draw the background color
        gc.setFill(javafx.scene.paint.Color.BLUE);
        gc.fillRect(0, 0, 250, 250);

        // Draw the grid lines
        gc.setStroke(javafx.scene.paint.Color.WHITE);
        for (int i = 0; i <= 250; i += 25) {
            gc.strokeLine(i, 0, i, 250);
            gc.strokeLine(0, i, 250, i);
        }
        setobjetoHashMap(positions);
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


}
