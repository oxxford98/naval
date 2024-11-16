package com.example.hellojavafx.view;

import com.example.hellojavafx.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Representa la vista del juego de Sudoku.
 */
public class GameView extends Stage {

    private GameController gameController;

    /**
     * Constructor de la clase GameView.
     *
     * @throws IOException Si no se puede cargar el archivo FXML.
     */
    public GameView(HashMap<String, Object>[][] positions, boolean loadGame, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/hellojavafx/game-view.fxml")
        );
        loader.setController(new GameController(positions, loadGame, username));
        Parent root = loader.load();
        this.gameController = loader.getController();
        this.setTitle("Guerra Naval");
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/com/example/hellojavafx/images/icono.png")
        ));
        this.setScene(scene);
        this.show();
    }

    /**
     * Obtiene el controlador del juego.
     *
     * @return El controlador del juego.
     */
    public GameController getGameController() {
        return this.gameController;
    }
}