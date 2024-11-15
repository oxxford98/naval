package com.example.demo.Controller;

import com.example.demo.Model.BoardModel;
import com.example.demo.Model.BoatModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private GridPane gridpane1;

    @FXML
    private Canvas mycanvas;
    private List<BoatModel> boats;

    BoatModel currentBoat;
    BoardModel board;
    GraphicsContext gc;

    private double initialX;
    private double initialY;
    private boolean isSelected;
    private boolean hasMoved;
    private int countboats;
    private double posx;
    private double posy;
    private Group boatGroup;


    public GameController() {
        this.isSelected=false;
        this.hasMoved=false;
        this.boats = new ArrayList<>();
        this.countboats = 0;
        this.posx=0;
        this.posy=0;
        this.board = new BoardModel();
        this.boatGroup = new Group();


    }

    @FXML
    public void initialize() {

        board.configgridpane(gridpane1);

        configevent();
        createNewBoat();
    }
    public void configevent(){

        PaneBattle.setOnMousePressed(this::handleMousePressed);
        PaneBattle.setOnMouseDragged(this::handleMouseDragged);
        PaneBattle.setOnMouseReleased(this::handleMouseReleased);

    }
    private void createNewBoat() {
        if (boats.size() < 10) {
            System.out.println(countboats);
            currentBoat = new BoatModel();
            boats.add(currentBoat);
            boatGroup = currentBoat.getBoatGroup();
            if(countboats==0){
                currentBoat.createAircraftCarrier();
            }if(countboats==1||countboats==2) {
                currentBoat.createSubmarine();
            }if (countboats>=3 &&countboats<=5){
                currentBoat.createDestroyer();
            }if(countboats>=6 &&countboats<=9) {
                currentBoat.createPatrolBoat();
            }
            positionBoat();
            PaneBattle.getChildren().add(currentBoat.getBoatGroup());
            isSelected = true;
            hasMoved = false;
            countboats++;

        }
    }
    public boolean selected(BoatModel boat, MouseEvent event) {
        if (event.getX() >= boat.getRectX() && event.getX() <= boat.getRectX() + boat.getRectWidth() &&
            event.getY() >= boat.getRectY() && event.getY() <= boat.getRectY() + boat.getRectHeight()) {

            return true;
        }
        return false;
    }



    private void handleMousePressed(MouseEvent event) {
        // Verificar si el clic inicial está dentro de las coordenadas y dimensiones de BoatModel

        for (BoatModel boat : boats) {
            if (boat.isActive() &&selected(boat, event)) {
                initialX = event.getX() - boat.getRectX();
                initialY = event.getY() - boat.getRectY();
                isSelected = true;
                currentBoat = boat;

                posx = boat.getRectX();
                posy = boat.getRectY();

                PaneBattle.setCursor(Cursor.MOVE);


                break;
            }
        }
    }
    private void handleMouseDragged(MouseEvent event) {

        if (!hasMoved && currentBoat != null&& currentBoat.isActive()) {

            // Actualiza las coordenadas del barco en BoatModel
            currentBoat.setRectX(event.getX() - initialX);
            currentBoat.setRectY(event.getY() - initialY);
            //board.paintBoard((Rectangle) currentBoat.getBoatGroup().getChildren().get(0));

            redrawBoats();



        }
    }
    private void handleMouseReleased(MouseEvent event) {
        if (isSelected && currentBoat != null && currentBoat.isActive()) {//

            // Actualiza las coordenadas del barco existente
            currentBoat.setRectX(event.getX() - initialX);
            currentBoat.setRectY(event.getY() - initialY);

            if(insidematrix() && insidebox()&& superimposed()) {
                isSelected = false;
                hasMoved = true;
                currentBoat.setActive(false);
                //touch=false;
                board.paintBoard((Rectangle) currentBoat.getBoatGroup().getChildren().get(0));
                System.out.println(currentBoat.getBoatGroup().getChildren().get(0).getBoundsInParent());
                System.out.println(currentBoat.getBoatGroup().getChildren().get(0).getBoundsInLocal());
                System.out.println(currentBoat.getBoatGroup().getChildren().get(0).getLayoutBounds());

                redrawBoats();
                PaneBattle.setCursor(Cursor.DEFAULT);
                createNewBoat();
            }else {
                currentBoat.setRectX(posx);
                System.out.println(posx);
                currentBoat.setRectY(posy);
                System.out.println(posy);
                redrawBoats();
                System.out.println(currentBoat.getBoatGroup().getChildren().get(0).getBoundsInParent());//obtengo el rectangulo del barco

            }

        }
    }
    public boolean insidematrix(){
        if(currentBoat.getRectX()>=70 && currentBoat.getRectX()+ currentBoat.getRectWidth()<=320 && currentBoat.getRectY()>=76 && currentBoat.getRectY()+ currentBoat.getRectHeight()<=326){
            return true;
        }
        return false;
    }
    public boolean insidebox(){
        if (currentBoat.getRectWidth()==15&& currentBoat.getRectHeight()==15){
            for(int i=70;i<=320;i+=25){
                for(int j=76;j<=326;j+=25){
                    if(currentBoat.getRectX()>i && currentBoat.getRectX()+ currentBoat.getRectWidth()<i+25 && currentBoat.getRectY()>j && currentBoat.getRectY()+ currentBoat.getRectHeight()<j+25){
                        return true;
                    }
                }
            }

        }else if(currentBoat.getRectWidth() < currentBoat.getRectHeight()) { // vertical
            for(int i = 70; i <= 320; i += 25) {
                if(currentBoat.getRectX() > i && currentBoat.getRectX() + currentBoat.getRectWidth() < i + 25) {
                    return true;
                }
            }
        } else if(currentBoat.getRectWidth() > currentBoat.getRectHeight()) { // horizontal
            for(int j = 76; j <= 326; j += 25) {
                if(currentBoat.getRectY() > j && currentBoat.getRectY() + currentBoat.getRectHeight() < j + 25) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean superimposed(){
        for (BoatModel boat : boats) {
            if (boat != currentBoat) {
                if (currentBoat.getRectX() < boat.getRectX() + boat.getRectWidth() &&
                        currentBoat.getRectX() + currentBoat.getRectWidth() > boat.getRectX() &&
                        currentBoat.getRectY() < boat.getRectY() + boat.getRectHeight() &&
                        currentBoat.getRectY() + currentBoat.getRectHeight() > boat.getRectY()) {
                    return false;
                }
            }
        }
        return true;
    }
    private void redrawBoats() {
        for (int i = 0; i < boats.size(); i++) {
            BoatModel boat = boats.get(i);
            // Draw only the boats that are already initialized
            if (boat.isInitialized()) {

                if (i == 0) {
                    boat.createAircraftCarrier();
                } else if (i == 1 || i == 2) {
                    boat.createSubmarine();
                } else if (i >= 3 && i <= 5) {
                    boat.createDestroyer();
                } else if (i >= 6) {
                    boat.createPatrolBoat();
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
        double centerX=0;
        double centerY=0;

        System.out.println("Girar");

        centerX = currentBoat.getRectX() + currentBoat.getRectWidth() / 2;
        centerY = currentBoat.getRectY() + currentBoat.getRectHeight() / 2;
        posx = centerX-currentBoat.getRectHeight()/2;
        posy = centerY-currentBoat.getRectWidth()/2;

        currentBoat.setRectX(posx);
        currentBoat.setRectY(posy);


        // Intercambia los valores de ancho y alto en BoatModel para rotar el barco
        double temp = currentBoat.getRectWidth();
        currentBoat.setRectWidth(currentBoat.getRectHeight());
        currentBoat.setRectHeight(temp);


        redrawBoats();
    }
    public void positionBoat(){
         posx = currentBoat.getRectX();
         posy = currentBoat.getRectY();
    }


}

