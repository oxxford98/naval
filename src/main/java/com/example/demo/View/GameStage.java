package com.example.demo.View;

import com.example.demo.Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage {
    private GameController gameController;

    private double xOffset = 200;
    private double yOffset = 200;

    public GameStage() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/borrador batalla naval.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        setTitle("Batalla Naval");
        setResizable(false);
        setScene(scene);
        show();

    }

    public GameController getController() {
        return gameController;
    }

    public static GameStage getInstance() throws IOException {
        if(GameStageHolder.INSTANCE == null){
            GameStageHolder.INSTANCE = new GameStage();
        }
        return GameStageHolder.INSTANCE;
    }

    public static class GameStageHolder{
        private static  GameStage INSTANCE;
    }

}
