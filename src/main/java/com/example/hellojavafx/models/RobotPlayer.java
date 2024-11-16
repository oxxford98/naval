package com.example.hellojavafx.models;
import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RobotPlayer extends Player {
    private int [] boat1;
    private int [] cordenates;
    private int [][] boat2;
    private int [][] boat3;
    private int [][] boat4;
    private int posx=0;
    private int posy=0;
    private HashMap<String, Object>[][] array;
    private Set<String> occupiedPositions;

    public void RobotPlayer() {
        this.array = new HashMap[10][10];
        this.boat1 = new int[]{0, 0};
        this.boat2 = new int[][]{{0, 0}, {0, 1}};
        this.boat3 = new int[][]{{0, 0}, {0, 1}, {0, 2}};
        this.boat4 = new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}};
        this.occupiedPositions = new HashSet<>();
        posx=0;
        posy=0;
    }
    public RobotPlayer(String name) {

        super(name, new Board(selectRandomPredefinedBoard()));
    }

    private static HashMap<String, Object>[][] selectRandomPredefinedBoard() {
        HashMap<String, Object>[][] predefinedBoard1 = new HashMap[10][10];
        HashMap<String, Object>[][] predefinedBoard2 = new HashMap[10][10];

        initializePredefinedBoard(predefinedBoard1);
        initializePredefinedBoard(predefinedBoard2);

        predefinedBoard1[0][0].put("type", 1);
        predefinedBoard1[0][1].put("type", 1);

        predefinedBoard2[1][0].put("type", 1);
        predefinedBoard2[1][1].put("type", 1);

        Random random = new Random();
        int choice = random.nextInt(2);
        return choice == 0 ? predefinedBoard1 : predefinedBoard2;

        /*initializeBoardrandom();
        return array;*/
    }

    private static void initializePredefinedBoard(HashMap<String, Object>[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HashMap<String, Object> cell = new HashMap<>();
                cell.put("type", 0);
                cell.put("used", 0);
                board[i][j] = cell;
            }
        }
    }
    public void initializeBoardrandom(){


        // Inicializar cada HashMap en la matriz
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = new HashMap<>();
                array[i][j].put("type", 0);
                array[i][j].put("coordinates", new int[] {i, j});
                array[i][j].put("canvas", new MyCanvas(new int[] {j*25, i*25}, 0));
            }
        }
        for(int i=0; i<4; i++){
            locateboat1();
            boolean b = !insideboardsmall(boat1) || coallitionsmall(boat1);
            do {
                if (insideboardsmall(boat1)&&!coallitionsmall(boat1)){
                    array[boat1[0]][boat1[1]].put("type", 1);
                    array[boat1[0]][boat1[1]].put("coordinatesArray", boat1);
                    cordenates= new int[]{posy * 25, posx * 25};
                    array[boat1[0]][boat1[1]].put("canvas", new MyCanvas( cordenates, 0));
                }
                locateboat1();
            } while (b);
        }
        for(int i=0; i<3; i++){
            locateboat2();
            boolean b = !insideboard(boat2) || coallition(boat2);
            do {
                if (insideboard(boat2)&&!coallition(boat2)){
                    array[boat2[0][0]][boat2[0][1]].put("type", 1);
                    array[boat2[0][0]][boat2[0][1]].put("coordinatesArray", boat2);
                    cordenates= new int[]{posy * 25, posx * 25};
                    array[boat2[0][0]][boat2[0][1]].put("canvas", new MyCanvas( cordenates, 0));
                }
                locateboat2();
            } while (b);
        }
        for (int i = 0; i < 2; i++) {
            locateboat3();
            boolean b = !insideboard(boat3) || coallition(boat3);
            do {

                if (insideboard(boat3) && !coallition(boat3)) {
                    array[boat3[0][0]][boat3[0][1]].put("type", 1);
                    array[boat3[0][0]][boat3[0][1]].put("coordinatesArray", boat3);
                    cordenates= new int[]{posy * 25, posx * 25};
                    array[boat3[0][0]][boat3[0][1]].put("canvas", new MyCanvas( cordenates, 0));
                }
                locateboat3();
            } while (b);
        }
        for (int i = 0; i < 1; i++) {
            locateboat4();
            boolean b = !insideboard(boat4) || coallition(boat4);
            do {
                if (insideboard(boat4) && !coallition(boat4)) {
                    array[boat4[0][0]][boat4[0][1]].put("type", 1);
                    array[boat4[0][0]][boat4[0][1]].put("coordinatesArray", boat4);
                    cordenates= new int[]{posy * 25, posx * 25};
                    array[boat4[0][0]][boat4[0][1]].put("canvas", new MyCanvas( cordenates, 0));
                }
                locateboat4();
            } while (b);
        }
    }



    public boolean insideboard(int[][] boat){
        for (int i = 1; i < boat.length; i++) {
            for(int j = 0; j < boat[i].length; j++){
                if(boat[i][j] > 9){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean insideboardsmall(int[] boat){
        if (boat[0] > 9 || boat[1] > 9){
            return false;
        }
        return false;
    }
    public boolean coallition(int[][] boat){
        for (int[] position : boat) {
            String key = position[0] + "," + position[1]; // Convertir coordenadas a formato "x,y"
            if (occupiedPositions.contains(key)) {
                return false; // Si alguna posición ya está ocupada, no se puede añadir
            }
        }
        // Si todas las posiciones están libres, añadirlas al conjunto
        for (int[] position : boat) {
            String key = position[0] + "," + position[1];
            occupiedPositions.add(key);
        }
        return true;
    }
    private boolean coallitionsmall(int[] boat) {
        String key = boat[0] + "," + boat[1]; // Convertir coordenadas a formato "x,y"
        if (occupiedPositions.contains(key)) {
            return false; // Si la posición ya está ocupada, no se puede añadir
        }
        // Si la posición está libre, añadirla al conjunto
        occupiedPositions.add(key);
        return true;
    }
    public int[][] rotateBoat(int[][] boat) {
        boolean isVertical = boat[0][0] == boat[1][0];

        int[][] rotatedBoat = new int[boat.length][2];

        if (isVertical) {
            // Convertir de vertical a horizontal
            int baseX = boat[0][0]; // Fija la posición X
            for (int i = 0; i < boat.length; i++) {
                rotatedBoat[i][0] = baseX; // Misma X
                rotatedBoat[i][1] = boat[0][1] + i; // Incrementar Y
            }
        } else {
            // Convertir de horizontal a vertical
            int baseY = boat[0][1]; // Fija la posición Y
            for (int i = 0; i < boat.length; i++) {
                rotatedBoat[i][0] = boat[0][0] + i; // Incrementar X
                rotatedBoat[i][1] = baseY; // Misma Y
            }
        }

        return rotatedBoat;
    }


    public void  locateboat1(){
        //boat1= new int[]{0, 0};

        Random randomx = new Random();
        Random randomy = new Random();

        posx = randomx.nextInt(10);
        posy = randomy.nextInt(10);

        boat1= new int[]{posx, posy};

    }
    public void  locateboat2(){
        //boat2 = new int [][]{{0,0}, {0,1}};

        Random randomx = new Random();
        Random randomy = new Random();

        posx = randomx.nextInt(10);
        posy = randomy.nextInt(10);

        boat2 = new int[][]{{posx, posy}, {posx, posy + 1}};


    }
    public void  locateboat3(){
        //boat2 = new int [][]{{0,0}, {0,1}, {0,2}};
        Random randomx = new Random();
        Random randomy = new Random();

        posx = randomx.nextInt(10);
        posy = randomy.nextInt(10);

        boat3 = new int[][]{{posx, posy}, {posx, posy + 1}, {posx, posy + 2}};
    }
    public void  locateboat4(){
        //boat2 = new int [][]{{0,0},{0,1},{0,2},{0,3}};
        Random randomx = new Random();
        Random randomy = new Random();

        posx = randomx.nextInt(10);
        posy = randomy.nextInt(10);

        boat4 = new int[][]{{posx, posy}, {posx, posy + 1}, {posx, posy + 2}, {posx, posy + 3}};
    }

    public void setBoard(Board board) {
        super.setBoard(board);
    }
}
