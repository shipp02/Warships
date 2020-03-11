package io.aashay.ui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App2 extends Application {
    private Stage stage;
    private Canon canon;
    private Sea sea;
    private int size = 40;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Init game
        this.size = 10;
        sea = new Sea(this.size);
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
        ArrayList<String> sunkStatusStrings = new ArrayList<>();
        sunkStatusStrings.add("Aircraft Carrier");
        sunkStatusStrings.add("Battle Ship");
        sunkStatusStrings.add("Cruiser");
        sunkStatusStrings.add("Destroyer");
        sunkStatusStrings.add("Destroyer");
        
        GridPane rootPane = new GridPane();

        AnchorPane scorePane = new AnchorPane();

        Button endBtn = new Button("End Game");
        AnchorPane.setTopAnchor(endBtn, 10.0);
        AnchorPane.setRightAnchor(endBtn, 10.0);
        endBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                stage.setScene(startScene());
            }
        });

        Label status = new Label();
        status.setText("No shots yet");
        AnchorPane.setTopAnchor(status, 50.0);
        AnchorPane.setLeftAnchor(status, 10.0);

        Label sunkStatus = new Label();
        sunkStatus.setText("No ships sunk");
        AnchorPane.setTopAnchor(sunkStatus, 90.0);
        AnchorPane.setLeftAnchor(sunkStatus, 10.0);

        scorePane.getChildren().addAll(endBtn,status,sunkStatus);



        scorePane.setPrefWidth(100.0);
        GridPane.setRowSpan(scorePane, 10);
        rootPane.addColumn(this.size, scorePane);
        

        for(int i =0;i<this.size;i++){
            for(int j = 0;j<this.size;j++){
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
                        String statusText = "";
                        for (int k = 0; k < 5; k++) {
                            if(sea.sunk(k)){
                                sunkStatus.setText(sunkStatusStrings.get(k) + " was sunk");
                            }
                        }
                        System.out.println(statusText);
                    }
                });

            }
        }
        

        Scene scene = new Scene(rootPane); 
        scene.getStylesheets().add("gameStyle.css");
        return scene;
    }
    
}