package com.example.demo.Model;

import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;



public class BoardModel {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static TextField[][] textFields;

    public BoardModel() {

        System.out.println("Tablero creado");
        this.textFields = new TextField[ROWS][COLS];
    }
    public void configgridpane(GridPane gridpane1) {
        Pane pane = new Pane();
        GridPane gridPane = new GridPane();


        TextField textField = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                textField = new TextField();
                textField.setEditable(false);
                textField.setPrefHeight(25);
                textField.setPrefWidth(25);
                textField.setStyle("-fx-border-color: black;");

                /*TextField finalTextField = textField;
                textField.setOnMouseEntered(event -> {
                    finalTextField.setStyle("-fx-background-color: lightblue;");
                });
                final TextField textField2 = textField;
                textField.setOnMouseExited(event -> {
                    textField2.setStyle("-fx-background-color: white;");
                    textField2.setStyle("-fx-border-color: black;");
                });

                *
                 */
                /*TextField finalTextField1 = textField;
                textField.setOnDragOver(event -> {
                    if (event.getGestureSource() != finalTextField1 && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        finalTextField1.setStyle("-fx-background-color: lightgreen;");
                    }
                    event.consume();
                });
                TextField finalTextField2 = textField;
                textField.setOnDragDropped(event -> {
                    Dragboard dragboard = event.getDragboard();
                    boolean success = false;
                    if (dragboard.hasString()) {
                        // Realiza aquí la lógica para asignar el objeto modelBoat al TextField o interactuar con él
                        finalTextField2.setText("Boat Placed");  // Ejemplo de acción al soltar
                        System.out.println("Object modelBoat dropped on TextField");
                        success = true;
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });
                TextField finalTextField3 = textField;
                textField.setOnDragExited(event -> {
                    finalTextField3.setStyle("-fx-background-color: white;");
                    finalTextField3.setStyle("-fx-border-color: black;");
                    event.consume();
                });*/



                //textField.setVisible(true);
                textFields[i][j] = textField;
                gridpane1.add(textField, i, j);

                System.out.println("TextField creado");

            }
        }
    }

    public void paintBoard(Rectangle rectangle) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boolean isInside = textFields[i][j].getBoundsInParent().intersects(rectangle.getBoundsInParent());
                if (isInside) {
                    textFields[i][j].setStyle("-fx-background-color: black;");
                    System.out.println(textFields[i][j].getBoundsInParent());

                } else {
                    textFields[i][j].setStyle("");
                }
            }
        }
    }
    public static TextField getTextField(int i, int j) {
        return textFields[i][j];
    }
}