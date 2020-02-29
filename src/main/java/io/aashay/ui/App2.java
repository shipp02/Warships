package io.aashay.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        return new Scene(root, 100.0, 100.0);
    }

    public Scene gameScene(){
        GridPane rootPane = new GridPane();

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
                        }else{
                            System.out.println("Miss");
                        }
                    }
                });
            }
        }
        StackPane scorePane = new StackPane();
        Button endBtn = new Button("End Game");

        endBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                System.exit(0);
            }
        });
        scorePane.getChildren().add(endBtn);

        scorePane.setPrefWidth(100.0);
        rootPane.addColumn(10, scorePane);

        Scene scene = new Scene(rootPane, 400.0, 400.0); 
        scene.getStylesheets().add("gameStyle.css");
        return scene;
    }
    
}