package com.example.demo.Controller;

import com.example.demo.Model.BoatModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Button btnhelp;

    @FXML
    private Button btnspin;

    @FXML
    private Button btnstart;

    @FXML
    private StackPane SpaceSpin;

    @FXML
    private Pane PaneBattle;

    @FXML
    private Canvas mycanvas;
    private List<BoatModel> boats;

    BoatModel currentBoat;
    GraphicsContext gc;

    private double initialX;
    private double initialY;
    private boolean isSelected;
    private boolean hasMoved;
    private int countboats;

    public GameController() {
        this.isSelected=false;
        this.hasMoved=false;
        this.boats = new ArrayList<>();
        this.countboats = 0;

    }

    @FXML
    public void initialize() {
        //boat = new BoatModel();
        gc = mycanvas.getGraphicsContext2D();
        configCanvas();
        //boat.createAircraftCarrier(gc);
        createNewBoat();

    }
    public void configCanvas(){

        mycanvas.setOnMousePressed(this::handleMousePressed);
        mycanvas.setOnMouseDragged(this::handleMouseDragged);
        mycanvas.setOnMouseReleased(this::handleMouseReleased);
    }
    private void createNewBoat() {
        if (boats.size() < 10) {
            System.out.println(countboats);
            currentBoat = new BoatModel();
            boats.add(currentBoat);
            if(countboats==0){
                currentBoat.createAircraftCarrier(gc);
            }if(countboats==1||countboats==2) {
                currentBoat.createSubmarine(gc);
            }if (countboats>=3 &&countboats<=5){
                currentBoat.createDestroyer(gc);
            }if(countboats>=6 &&countboats<=9) {
                currentBoat.createPatrolBoat(gc);
            }

            isSelected = true;
            hasMoved = false;
            countboats++;

        }
    }



    private void handleMousePressed(MouseEvent event) {
        // Verificar si el clic inicial está dentro de las coordenadas y dimensiones de BoatModel

        for (BoatModel boat : boats) {
            if (boat.isActive() &&
                    event.getX() >= boat.getRectX() && event.getX() <= boat.getRectX() + boat.getRectWidth() &&
                    event.getY() >= boat.getRectY() && event.getY() <= boat.getRectY() + boat.getRectHeight()) {
                initialX = event.getX() - boat.getRectX();
                initialY = event.getY() - boat.getRectY();
                isSelected = true;
                currentBoat = boat;
                mycanvas.setCursor(Cursor.MOVE);
                break;
            }
        }
    }
    private void handleMouseDragged(MouseEvent event) {

        if (!hasMoved && currentBoat != null&& currentBoat.isActive()) {

            // Actualiza las coordenadas del barco en BoatModel
            currentBoat.setRectX(event.getX() - initialX);
            currentBoat.setRectY(event.getY() - initialY);
            redrawBoats();
        }
    }
    private void handleMouseReleased(MouseEvent event) {
        if (isSelected && currentBoat != null && currentBoat.isActive()) {

            // Actualiza las coordenadas del barco existente
            currentBoat.setRectX(event.getX() - initialX);
            currentBoat.setRectY(event.getY() - initialY);

            isSelected = false;
            hasMoved = true;
            currentBoat.setActive(false);

            redrawBoats();
            mycanvas.setCursor(Cursor.DEFAULT);
            createNewBoat();
            // Limpia el canvas y redibuja el barco en la nueva posición
            //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        }
    }
    private void redrawBoats() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (BoatModel boat : boats) {
            // Dibujar solo los barcos que ya están inicializados
            if (boat.isInitialized()) {
                if(countboats==0){
                    boat.createAircraftCarrier(gc);
                }if(countboats==1||countboats==2) {
                    boat.createSubmarine(gc);
                }if (countboats>=3 &&countboats<=5){
                    boat.createDestroyer(gc);
                }if(countboats>=6) {
                    boat.createPatrolBoat(gc);
                }
            }
        }
    }

    public void mostrar() {
        System.out.println("Hola");
    }
    @FXML
    void OnActionStart(ActionEvent event) {
        System.out.println("Iniciar");
    }
    @FXML
    void OnActionHelp(ActionEvent event) {
        System.out.println("Ayuda");
    }
    @FXML
    void OnActionSpin(ActionEvent event) {
        System.out.println("Girar");
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Intercambia los valores de ancho y alto en BoatModel para rotar el barco
        double temp = currentBoat.getRectWidth();
        currentBoat.setRectWidth(currentBoat.getRectHeight());
        currentBoat.setRectHeight(temp);

        redrawBoats();
    }
    /*private void drawBoat() {
        // Limpia el canvas y redibuja el barco
        gc.clearRect(0, 0, mycanvas.getWidth(), mycanvas.getHeight());
        boat.createAircraftCarrier(gc);
    }*/





}

