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

/**
 * This class is one where all the main classes are initialised it controls all user input
 * I also sets up the  GUI
 */
public class App2 extends Application {
    private Stage stage;
    private Canon canon;
    private Sea sea;
    private int size = 40;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * It sets up the start stage and handles startup
     * The sea class is initialised and the start scene is put on display by default
     */
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

    /**
     * This sets up and return the start scene it
     * @return Start Scene with Button to start the game
     */
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

    /**
     * Sets up and returns the game scene also adds handlers to all buttons of the game
     * Sets up all the handling of user input
     * @return scene with game buttons and end button
     */
    public Scene gameScene(){
        ArrayList<String> sunkStatusStrings = new ArrayList<>();
        sunkStatusStrings.add("Aircraft Carrier");
        sunkStatusStrings.add("Battle Ship");
        sunkStatusStrings.add("Cruiser");
        sunkStatusStrings.add("Destroyer");
        sunkStatusStrings.add("Destroyer");

        ArrayList<Integer> shipsSunk = new ArrayList<>();
        
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
                        // Gets the position of the button which was clicked since tis is a generic handler
                        Button trigger = (Button) event.getSource();
                        int col = GridPane.getColumnIndex(trigger);
                        int row = GridPane.getRowIndex(trigger);
                        System.out.println(col + "," + row);

                        // Fires on the position of the button that was clicked
                        if(canon.fire(col, row)){
                            System.out.println("Hit");
                            status.setText("Hit");
                        }else{
                            System.out.println("Miss");
                            status.setText("Miss");
                        }

                        // Changes the colour of the button that was clicked 
                        // So that user may know that they have already clicked that button
                        trigger.getStyleClass().remove("waterButton");
                        trigger.getStyleClass().add("firedWaterButton");
                        

                        // Checks which ship if any was sunk by this click and informs the user accordingly

                        for (int k = 0; k < 5; k++) {
                            // This checks if the ship was previously marked as sunk so that it is not shown again
                            if(shipsSunk.indexOf(k) != -1){
                                ;
                            }
                            else if(sea.sunk(k)){
                                shipsSunk.add(k);
                                sunkStatus.setText(sunkStatusStrings.get(k) + " was sunk");
                                System.out.println(sunkStatusStrings.get(k) + " was sunk");
                            }
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