package io.aashay.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class CanvasTest extends Application {
    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage  = primaryStage; 
        stage.setTitle("Canvas");
        stage.show();
    }
    

}