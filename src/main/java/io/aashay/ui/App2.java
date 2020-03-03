package io.aashay.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App2 extends Application {
    private Stage stage;
    Canon canon;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Init game
        Sea sea = new Sea();
        canon = sea.getCanon();


        primaryStage.setTitle("Grid Pane");

        stage = primaryStage;

        Scene scene = startScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene startScene(){
        Button btn = new Button();
        btn.setText("Start Game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Game Started");
                stage.setScene(gameScene());
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        return new Scene(root);
    }

    public Scene gameScene(){
        GridPane rootPane = new GridPane();

        StackPane scorePane = new StackPane();
        Button endBtn = new Button("End Game");
        endBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                stage.setScene(startScene());
            }
        });

        Label status = new Label();
        status.setText("No shots yet");

        scorePane.getChildren().add(endBtn);

        scorePane.setAlignment(Pos.CENTER);

        scorePane.setPrefWidth(100.0);
        rootPane.addColumn(10, scorePane);

        for(int i =0;i<10;i++){
            for(int j = 0;j<10;j++){
                Button btn = new Button();
                btn.setText(i + "," + j);
                btn.getStyleClass().add("waterButton");
                GridPane.setConstraints(btn,i,j);
                rootPane.getChildren().add(btn);
                
                btn.setOnAction(new EventHandler<ActionEvent>() {
 
                    @Override
                    public void handle(ActionEvent event) {
                        Button trigger = (Button) event.getSource();
                        int col = GridPane.getColumnIndex(trigger);
                        int row = GridPane.getRowIndex(trigger);
                        System.out.println(col + "," + row);
                        if(canon.fire(col, row)){
                            System.out.println("Hit");
                            status.setText("Hit");
                        }else{
                            System.out.println("Miss");
                            status.setText("Miss");
                        }

                        
                    }
                });
            }
        }
        

        Scene scene = new Scene(rootPane); 
        scene.getStylesheets().add("gameStyle.css");
        return scene;
    }
    
}