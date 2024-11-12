package com.example.demo.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoatModel {
    private double rectX;
    private double rectY;
    private double rectWidth;
    private double rectHeight;
    private boolean isInitialized;
    private boolean isActive;

    public BoatModel() {
        System.out.println("Barco creado");
        this.rectX = 0;
        this.rectY = 0;
        this.rectWidth = 0;
        this.rectHeight = 0;
        this.isInitialized = false;
        this.isActive = true;

    }

    public void createAircraftCarrier(GraphicsContext gc) {
        if (!isInitialized) {
            rectX = 200;
            rectY = 365;
            rectWidth = 25;
            rectHeight = 100;
            isInitialized = true;
        }
        gc.setFill(Color.GREEN);
        gc.fillRect(rectX, rectY, rectWidth, rectHeight);
    }

    public void createSubmarine(GraphicsContext gc){ // 2 units, 3 boxes
        if (!isInitialized) {
            rectX = 200;
            rectY = 365;
            rectWidth = 25;
            rectHeight = 75;
            isInitialized = true;
        }
        gc.setFill(Color.GREEN);
        gc.fillRect(rectX, rectY, rectWidth, rectHeight);
    }
    public void createDestroyer(GraphicsContext gc){ // 3 units, 2 boxes
        if (!isInitialized) {
            rectX = 200;
            rectY = 365;
            rectWidth = 25;
            rectHeight = 50;
            isInitialized = true;
        }
        gc.setFill(Color.GREEN);
        gc.fillRect(rectX, rectY, rectWidth, rectHeight);
    }
    public void createPatrolBoat(GraphicsContext gc){ // 4 units, 1 box
        if (!isInitialized) {
            rectX = 200;
            rectY = 365;
            rectWidth = 25;
            rectHeight = 25;
            isInitialized = true;
        }
        gc.setFill(Color.GREEN);
        gc.fillRect(rectX, rectY, rectWidth, rectHeight);
    }
    public double getRectX(){
        return rectX;
    }
    public double getRectY(){
        return rectY;
    }
    public double getRectWidth(){
        return rectWidth;
    }
    public double getRectHeight(){
        return rectHeight;
    }
    public void setRectX(double x){
        rectX = x;
    }
    public void setRectY(double y){
        rectY = y;
    }
    public void setRectWidth(double w){
        rectWidth = w;
    }
    public void setRectHeight(double h){
        rectHeight = h;
    }

    public void setInitialized(boolean b){
        isInitialized = b;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public void colocarBarcos(){
        System.out.println("Barcos colocados");
    }
    public void disparar(){
        System.out.println("Disparo realizado");
    }
    public void hundirBarco(){
        System.out.println("Barco hundido");
    }
    public void ganar(){
        System.out.println("Ganaste");
    }
    public void perder(){
        System.out.println("Perdiste");
    }
    public void reiniciar(){
        System.out.println("Juego reiniciado");
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}
