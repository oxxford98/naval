package com.example.hellojavafx;

import com.example.hellojavafx.view.WelcomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 */
public class Main extends Application {

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método que inicia la aplicación JavaFX.
     *
     * @param primaryStage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar la vista de bienvenida.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeView.getInstance();
    }
}