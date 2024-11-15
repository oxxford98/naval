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

public class GameController {

    private Image imagen;
    @FXML
    private Pane PaneBattle; // Add a Pane to the FXML file and link it here
    private GraphicsContext gc;
    private int typeBoat;
    private double rectx;
    private double recty;
    private int orientation;
    private int iterador = 0;

    public GameController() {
        this.typeBoat = 0;
        this.rectx = 0;
        this.recty = 0;
        this.orientation = 0;

    }
    public void initialize() {
        Canvas mycanvas = new Canvas(250, 250);
        gc = mycanvas.getGraphicsContext2D();

        mycanvas.setLayoutX(70);
        mycanvas.setLayoutY(80);
        mycanvas.setStyle("-fx-background-color: blue;");

        ////
        HashMap<String, Object>[][] array = new HashMap[10][10];

        // Inicializar cada HashMap en la matriz
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = new HashMap<>();
            }
        }

        // Definir los objetos en el array
        array[0][9].put("type", 1);
        array[0][9].put("coordinates", new int[] {0, 0});
        array[0][9].put("canvas", new MyCanvas(new int[] {225, 0}, 0));

        array[1][2].put("type", 2);
        array[1][2].put("coordinatesArray", new int[][] {{0, 1}, {2, 2}});
        array[1][2].put("canvas", new MyCanvas(new int[] {50, 25}, 0));

        array[1][5].put("type", 4);
        array[1][5].put("coordinatesArray", new int[][] {{0, 2}, {2, 5}, {3, 5}, {4, 5}});
        array[1][5].put("canvas", new MyCanvas(new int[] {125, 25}, 0));

        array[4][0].put("type", 2);
        array[4][0].put("coordinatesArray", new int[][] {{0, 3}, {4, 1}});
        array[4][0].put("canvas", new MyCanvas(new int[] {0, 100}, 1));

        array[5][7].put("type", 3);
        array[5][7].put("coordinatesArray", new int[][] {{0, 4}, {5, 8}, {5, 9}});
        array[5][7].put("canvas", new MyCanvas(new int[] {175, 100}, 1));

        array[7][7].put("type", 1);
        array[7][7].put("coordinates", new int[] {0, 5});
        array[7][7].put("canvas", new MyCanvas(new int[] {175, 175}, 0));

        array[8][0].put("type", 3);
        array[8][0].put("coordinatesArray", new int[][] {{0, 6}, {8, 1}, {8, 2}});
        array[8][0].put("canvas", new MyCanvas(new int[] {0, 200}, 1));

        array[8][5].put("type", 1);
        array[8][5].put("coordinates", new int[] {0, 7});
        array[8][5].put("canvas", new MyCanvas(new int[] {125, 200}, 0));

        array[9][9].put("type", 1);
        array[9][9].put("coordinates", new int[] {0, 8});
        array[9][9].put("canvas", new MyCanvas(new int[] {225, 225}, 0));
        ////

        setobjetoHashMap(array);


        PaneBattle.getChildren().add(mycanvas); // Add the Canvas to the Pane
    }

    public void setobjetoHashMap(HashMap<String, Object>[][] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                HashMap<String, Object> map = array[i][j];

                // Verificar si el HashMap no está vacío
                if (map != null && !map.isEmpty()) {
                    System.out.println("Valores en array[" + i + "][" + j + "]:");

                    // Iterar sobre las entradas del HashMap
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String clave = entry.getKey();
                        Object valor = entry.getValue();

                        // Dependiendo del tipo de valor, manejarlo adecuadamente
                        if (clave.equals("type")) {
                            typeBoat = (int) valor;
                        } else if (clave.equals("coordinatesArray")) {
                            int[][] coordinatesArray = (int[][]) valor;
                            System.out.println("  Clave: " + clave + ", Coordenadas: ");
                            for (int[] coords : coordinatesArray) {
                                rectx = coords[0];
                                recty = coords[1];
                            }
                        } else if (clave.equals("canvas")) {
                            /*int[] coordinates = (int[]) map.get("coordinates"); // Obtener las coordenadas
                            int orientationcanvas = (int) map.get("orientation"); // Obtener la orientación

                            MyCanvas canvas = new MyCanvas(coordinates, orientationcanvas);

                            orientation =canvas.getOrientation();*/
                        }
                    }

                }drawBoat(rectx, recty, typeBoat, 0);
                System.out.println("typeBoat: " + typeBoat);
                System.out.println("rectx: " + rectx);
                System.out.println("recty: " + recty);
                System.out.println("orientation: " + orientation);
                iterador ++;
                System.out.println("############iterador: " + iterador);


            }
        }
    }


    public void drawBoat(double rectx, double rexy, int type, int orientation) {
        for (double i = 0; i < 10; i++) {
            for (double j = 0; j < 10; j++) {
                if(rectx == i && rexy == j) {
                    if(type==1){
                        if(orientation==0){
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/1.png"));
                            gc.drawImage(imagen, i*25, j*25, 25, 25);
                        } else if (orientation==1) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/1-h.png"));
                            gc.drawImage(imagen, i*25, j*25, 25, 25);
                        }

                    } else if (type==2) {
                        if(orientation==0) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/2.png"));
                            gc.drawImage(imagen, i*25, j*25, 25, 50);
                        } else if (orientation==1) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/2-h.png"));
                            gc.drawImage(imagen, i*25, j*25, 50, 25);
                        }

                    } else if (type==3) {
                        if(orientation==0) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/3.png"));
                            gc.drawImage(imagen, i*25, j*25, 25, 75);

                        } else if (orientation==1) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/3-h.png"));
                            gc.drawImage(imagen, i*25, j*25, 75, 25);
                        }

                    }else if (type==4) {
                        if(orientation==0) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4.png"));
                            gc.drawImage(imagen, i*25, j*25, 25, 100);
                        } else if (orientation==1) {
                            imagen = new Image(getClass().getResourceAsStream("/com/example/hellojavafx/images/4-h.png"));
                            gc.drawImage(imagen, i*25, j*25, 100, 25);
                        }

                    }
                }

            }
        }

    }


}
