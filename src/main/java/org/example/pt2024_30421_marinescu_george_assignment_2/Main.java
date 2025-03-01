package org.example.pt2024_30421_marinescu_george_assignment_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Task;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Assignment 2");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}