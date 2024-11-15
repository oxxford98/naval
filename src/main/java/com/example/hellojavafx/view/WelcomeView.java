package com.example.hellojavafx.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Representa la vista de bienvenida del juego de Sudoku.
 */
public class WelcomeView extends Stage {

    private static VBox instance;

    /**
     * Constructor de la clase WelcomeView.
     *
     * @throws IOException Si no se puede cargar el archivo FXML.
     */
    public WelcomeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/hellojavafx/welcome-view.fxml")
        );
        Parent root = loader.load();
        this.setTitle("Guerra Naval");
        Scene scene = new Scene(root);
        this.getIcons().add(new Image(
                getClass().getResourceAsStream("/com/example/hellojavafx/images/icono.png")
        ));
        this.setScene(scene);
        this.show();
    }

    /**
     * Obtiene la instancia de WelcomeView.
     *
     * @return La instancia de WelcomeView.
     * @throws IOException Si no se puede crear la instancia.
     */
    public static WelcomeView getInstance() throws IOException {
        return WelcomeViewHolder.INSTANCE = new WelcomeView();
    }

    /**
     * Clase estática para mantener la instancia de WelcomeView.
     */
    private static class WelcomeViewHolder {
        private static WelcomeView INSTANCE;
    }
}