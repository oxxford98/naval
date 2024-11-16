package com.example.hellojavafx.models;
import com.example.hellojavafx.models.Board;
import com.example.hellojavafx.models.Player;

import java.util.*;

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
        HashMap<String, Object>[][] predefinedBoard3 = new HashMap[10][10];
        HashMap<String, Object>[][] predefinedBoard4 = new HashMap[10][10];
        HashMap<String, Object>[][] predefinedBoard5 = new HashMap[10][10];

        initializePredefinedBoard(predefinedBoard1);
        initializePredefinedBoard(predefinedBoard2);
        initializePredefinedBoard(predefinedBoard3);
        initializePredefinedBoard(predefinedBoard4);
        initializePredefinedBoard(predefinedBoard5);

        List<HashMap<String, Object>[][]> predefinedBoards = new ArrayList<>();
        predefinedBoards.add(predefinedBoard1);
        predefinedBoards.add(predefinedBoard2);
        predefinedBoards.add(predefinedBoard3);
        predefinedBoards.add(predefinedBoard4);
        predefinedBoards.add(predefinedBoard5);



        // Barcos de tipo 1 (1 posición)
        predefinedBoard1[0][0].put("type", 1);
        predefinedBoard1[0][0].put("coordinates", new int[] {0, 0});
        predefinedBoard1[0][0].put("canvas", new MyCanvas(new int[] {0, 0}, 0));

        predefinedBoard1[2][3].put("type", 1);
        predefinedBoard1[2][3].put("coordinates", new int[] {2, 3});
        predefinedBoard1[2][3].put("canvas", new MyCanvas(new int[] {75, 50}, 0));

        predefinedBoard1[4][7].put("type", 1);
        predefinedBoard1[4][7].put("coordinates", new int[] {4, 7});
        predefinedBoard1[4][7].put("canvas", new MyCanvas(new int[] {175, 100}, 0));

        predefinedBoard1[7][1].put("type", 1);
        predefinedBoard1[7][1].put("coordinates", new int[] {7, 1});
        predefinedBoard1[7][1].put("canvas", new MyCanvas(new int[] {25, 175}, 0));

// Barcos de tipo 2 (2 posiciones)
        predefinedBoard1[1][5].put("type", 2);
        predefinedBoard1[1][5].put("coordinates", new int[][] {{1, 5}, {1, 6}});
        predefinedBoard1[1][5].put("canvas", new MyCanvas(new int[] {125, 25}, 0));

        predefinedBoard1[3][2].put("type", 2);
        predefinedBoard1[3][2].put("coordinates", new int[][] {{3, 2}, {4, 2}});
        predefinedBoard1[3][2].put("canvas", new MyCanvas(new int[] {50, 75}, 0));

        predefinedBoard1[6][8].put("type", 2);
        predefinedBoard1[6][8].put("coordinates", new int[][] {{6, 8}, {6, 9}});
        predefinedBoard1[6][8].put("canvas", new MyCanvas(new int[] {200, 150}, 0));

// Barcos de tipo 3 (3 posiciones)
        predefinedBoard1[5][0].put("type", 3);
        predefinedBoard1[5][0].put("coordinates", new int[][] {{5, 0}, {5, 1}, {5, 2}});
        predefinedBoard1[5][0].put("canvas", new MyCanvas(new int[] {0, 125}, 1));

        predefinedBoard1[8][4].put("type", 3);
        predefinedBoard1[8][4].put("coordinates", new int[][] {{8, 4}, {8, 5}, {8, 6}});
        predefinedBoard1[8][4].put("canvas", new MyCanvas(new int[] {100, 200}, 1));

// Barco de tipo 4 (4 posiciones)
        predefinedBoard1[9][6].put("type", 4);
        predefinedBoard1[9][6].put("coordinates", new int[][] {{9, 6}, {9, 7}, {9, 8}, {9, 9}});
        predefinedBoard1[9][6].put("canvas", new MyCanvas(new int[] {150, 225}, 1));


        // Barcos de tipo 1 (1 posición)
        predefinedBoard2[0][3].put("type", 1);
        predefinedBoard2[0][3].put("coordinates", new int[] {0, 3});
        predefinedBoard2[0][3].put("canvas", new MyCanvas(new int[] {75, 0}, 0));

        predefinedBoard2[1][7].put("type", 1);
        predefinedBoard2[1][7].put("coordinates", new int[] {1, 7});
        predefinedBoard2[1][7].put("canvas", new MyCanvas(new int[] {175, 25}, 0));

        predefinedBoard2[5][5].put("type", 1);
        predefinedBoard2[5][5].put("coordinates", new int[] {5, 5});
        predefinedBoard2[5][5].put("canvas", new MyCanvas(new int[] {125, 125}, 0));

        predefinedBoard2[8][9].put("type", 1);
        predefinedBoard2[8][9].put("coordinates", new int[] {8, 9});
        predefinedBoard2[8][9].put("canvas", new MyCanvas(new int[] {225, 200}, 0));

// Barcos de tipo 2 (2 posiciones)
        predefinedBoard2[0][8].put("type", 2);
        predefinedBoard2[0][8].put("coordinates", new int[][] {{0, 8}, {0, 9}});
        predefinedBoard2[0][8].put("canvas", new MyCanvas(new int[] {200, 0}, 0));

        predefinedBoard2[3][0].put("type", 2);
        predefinedBoard2[3][0].put("coordinates", new int[][] {{3, 0}, {3, 1}});
        predefinedBoard2[3][0].put("canvas", new MyCanvas(new int[] {0, 75}, 0));

        predefinedBoard2[6][4].put("type", 2);
        predefinedBoard2[6][4].put("coordinates", new int[][] {{6, 4}, {7, 4}});
        predefinedBoard2[6][4].put("canvas", new MyCanvas(new int[] {100, 150}, 0));

// Barcos de tipo 3 (3 posiciones)
        predefinedBoard2[2][6].put("type", 3);
        predefinedBoard2[2][6].put("coordinates", new int[][] {{2, 6}, {2, 7}, {2, 8}});
        predefinedBoard2[2][6].put("canvas", new MyCanvas(new int[] {150, 50}, 1));

        predefinedBoard2[4][2].put("type", 3);
        predefinedBoard2[4][2].put("coordinates", new int[][] {{4, 2}, {5, 2}, {6, 2}});
        predefinedBoard2[4][2].put("canvas", new MyCanvas(new int[] {50, 100}, 1));

// Barco de tipo 4 (4 posiciones)
        predefinedBoard2[9][1].put("type", 4);
        predefinedBoard2[9][1].put("coordinates", new int[][] {{9, 1}, {9, 2}, {9, 3}, {9, 4}});
        predefinedBoard2[9][1].put("canvas", new MyCanvas(new int[] {25, 225}, 1));

        // Barcos de tipo 1 (1 posición)
        predefinedBoard3[1][0].put("type", 1);
        predefinedBoard3[1][0].put("coordinates", new int[] {1, 0});
        predefinedBoard3[1][0].put("canvas", new MyCanvas(new int[] {0, 25}, 0));

        predefinedBoard3[3][4].put("type", 1);
        predefinedBoard3[3][4].put("coordinates", new int[] {3, 4});
        predefinedBoard3[3][4].put("canvas", new MyCanvas(new int[] {100, 75}, 0));

        predefinedBoard3[6][9].put("type", 1);
        predefinedBoard3[6][9].put("coordinates", new int[] {6, 9});
        predefinedBoard3[6][9].put("canvas", new MyCanvas(new int[] {225, 150}, 0));

        predefinedBoard3[9][5].put("type", 1);
        predefinedBoard3[9][5].put("coordinates", new int[] {9, 5});
        predefinedBoard3[9][5].put("canvas", new MyCanvas(new int[] {125, 225}, 0));

// Barcos de tipo 2 (2 posiciones)
        predefinedBoard3[0][6].put("type", 2);
        predefinedBoard3[0][6].put("coordinates", new int[][] {{0, 6}, {0, 7}});
        predefinedBoard3[0][6].put("canvas", new MyCanvas(new int[] {150, 0}, 0));

        predefinedBoard3[4][8].put("type", 2);
        predefinedBoard3[4][8].put("coordinates", new int[][] {{4, 8}, {5, 8}});
        predefinedBoard3[4][8].put("canvas", new MyCanvas(new int[] {200, 100}, 0));

        predefinedBoard3[7][3].put("type", 2);
        predefinedBoard3[7][3].put("coordinates", new int[][] {{7, 3}, {8, 3}});
        predefinedBoard3[7][3].put("canvas", new MyCanvas(new int[] {75, 175}, 0));

// Barcos de tipo 3 (3 posiciones)
        predefinedBoard3[2][1].put("type", 3);
        predefinedBoard3[2][1].put("coordinates", new int[][] {{2, 1}, {2, 2}, {2, 3}});
        predefinedBoard3[2][1].put("canvas", new MyCanvas(new int[] {25, 50}, 1));

        predefinedBoard3[6][0].put("type", 3);
        predefinedBoard3[6][0].put("coordinates", new int[][] {{6, 0}, {7, 0}, {8, 0}});
        predefinedBoard3[6][0].put("canvas", new MyCanvas(new int[] {0, 150}, 1));

// Barco de tipo 4 (4 posiciones)
        predefinedBoard3[9][6].put("type", 4);
        predefinedBoard3[9][6].put("coordinates", new int[][] {{9, 6}, {9, 7}, {9, 8}, {9, 9}});
        predefinedBoard3[9][6].put("canvas", new MyCanvas(new int[] {150, 225}, 1));


        // Barcos de tipo 1 (1 posición)
        predefinedBoard4[0][2].put("type", 1);
        predefinedBoard4[0][2].put("coordinates", new int[] {0, 2});
        predefinedBoard4[0][2].put("canvas", new MyCanvas(new int[] {50, 0}, 0));

        predefinedBoard4[2][6].put("type", 1);
        predefinedBoard4[2][6].put("coordinates", new int[] {2, 6});
        predefinedBoard4[2][6].put("canvas", new MyCanvas(new int[] {150, 50}, 0));

        predefinedBoard4[5][3].put("type", 1);
        predefinedBoard4[5][3].put("coordinates", new int[] {5, 3});
        predefinedBoard4[5][3].put("canvas", new MyCanvas(new int[] {75, 125}, 0));

        predefinedBoard4[9][2].put("type", 1);
        predefinedBoard4[9][2].put("coordinates", new int[] {9, 2});
        predefinedBoard4[9][2].put("canvas", new MyCanvas(new int[] {50, 225}, 0));

// Barcos de tipo 2 (2 posiciones)
        predefinedBoard4[1][8].put("type", 2);
        predefinedBoard4[1][8].put("coordinates", new int[][] {{1, 8}, {1, 9}});
        predefinedBoard4[1][8].put("canvas", new MyCanvas(new int[] {200, 25}, 0));

        predefinedBoard4[3][1].put("type", 2);
        predefinedBoard4[3][1].put("coordinates", new int[][] {{3, 1}, {4, 1}});
        predefinedBoard4[3][1].put("canvas", new MyCanvas(new int[] {25, 75}, 0));

        predefinedBoard4[7][4].put("type", 2);
        predefinedBoard4[7][4].put("coordinates", new int[][] {{7, 4}, {8, 4}});
        predefinedBoard4[7][4].put("canvas", new MyCanvas(new int[] {100, 175}, 0));

// Barcos de tipo 3 (3 posiciones)
        predefinedBoard4[0][7].put("type", 3);
        predefinedBoard4[0][7].put("coordinates", new int[][] {{0, 7}, {0, 8}, {0, 9}});
        predefinedBoard4[0][7].put("canvas", new MyCanvas(new int[] {175, 0}, 1));

        predefinedBoard4[4][5].put("type", 3);
        predefinedBoard4[4][5].put("coordinates", new int[][] {{4, 5}, {4, 6}, {4, 7}});
        predefinedBoard4[4][5].put("canvas", new MyCanvas(new int[] {125, 100}, 1));

// Barco de tipo 4 (4 posiciones)
        predefinedBoard4[6][0].put("type", 4);
        predefinedBoard4[6][0].put("coordinates", new int[][] {{6, 0}, {6, 1}, {6, 2}, {6, 3}});
        predefinedBoard4[6][0].put("canvas", new MyCanvas(new int[] {0, 150}, 1));


        // Barcos de tipo 1 (1 posición)
        predefinedBoard5[1][4].put("type", 1);
        predefinedBoard5[1][4].put("coordinates", new int[] {1, 4});
        predefinedBoard5[1][4].put("canvas", new MyCanvas(new int[] {100, 25}, 0));

        predefinedBoard5[3][7].put("type", 1);
        predefinedBoard5[3][7].put("coordinates", new int[] {3, 7});
        predefinedBoard5[3][7].put("canvas", new MyCanvas(new int[] {175, 75}, 0));

        predefinedBoard5[6][2].put("type", 1);
        predefinedBoard5[6][2].put("coordinates", new int[] {6, 2});
        predefinedBoard5[6][2].put("canvas", new MyCanvas(new int[] {50, 150}, 0));

        predefinedBoard5[8][9].put("type", 1);
        predefinedBoard5[8][9].put("coordinates", new int[] {8, 9});
        predefinedBoard5[8][9].put("canvas", new MyCanvas(new int[] {225, 200}, 0));

// Barcos de tipo 2 (2 posiciones)
        predefinedBoard5[0][6].put("type", 2);
        predefinedBoard5[0][6].put("coordinates", new int[][] {{0, 6}, {0, 7}});
        predefinedBoard5[0][6].put("canvas", new MyCanvas(new int[] {150, 0}, 0));

        predefinedBoard5[4][3].put("type", 2);
        predefinedBoard5[4][3].put("coordinates", new int[][] {{4, 3}, {4, 4}});
        predefinedBoard5[4][3].put("canvas", new MyCanvas(new int[] {75, 100}, 0));

        predefinedBoard5[7][8].put("type", 2);
        predefinedBoard5[7][8].put("coordinates", new int[][] {{7, 8}, {7, 9}});
        predefinedBoard5[7][8].put("canvas", new MyCanvas(new int[] {200, 175}, 0));

// Barcos de tipo 3 (3 posiciones)
        predefinedBoard5[2][1].put("type", 3);
        predefinedBoard5[2][1].put("coordinates", new int[][] {{2, 1}, {2, 2}, {2, 3}});
        predefinedBoard5[2][1].put("canvas", new MyCanvas(new int[] {25, 50}, 1));

        predefinedBoard5[5][5].put("type", 3);
        predefinedBoard5[5][5].put("coordinates", new int[][] {{5, 5}, {5, 6}, {5, 7}});
        predefinedBoard5[5][5].put("canvas", new MyCanvas(new int[] {125, 125}, 1));

// Barco de tipo 4 (4 posiciones)
        predefinedBoard5[9][0].put("type", 4);
        predefinedBoard5[9][0].put("coordinates", new int[][] {{9, 0}, {9, 1}, {9, 2}, {9, 3}});
        predefinedBoard5[9][0].put("canvas", new MyCanvas(new int[] {0, 225}, 1));

        Random random = new Random();
        int choice = random.nextInt(predefinedBoards.size());
        return predefinedBoards.get(choice);





        /*initializeBoardrandom();
        return array;*/
    }

    private static void initializePredefinedBoard(HashMap<String, Object>[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HashMap<String, Object> cell = new HashMap<>();
                cell.put("used", 0);
                cell.put("type", 0);
                cell.put("coordinates", new int[] {i, j});
                cell.put("canvas", new MyCanvas(new int[] {j*25, i*25}, 0));
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