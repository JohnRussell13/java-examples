package com.transfer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private User user;
    private static Scene scene;
    private GridPane grid = new GridPane();
    private final TextField username = new TextField();
    private Button submit = new Button("Submit");
    private Label errormsg = new Label();
    private Label title = new Label();
    private Button swap = new Button("Swap");

    private List<CheckBox> checkboxes = new ArrayList<CheckBox>();
    private Button delete = new Button("Delete");
    private GridPane gridCB = new GridPane();

    private Boolean swapFlag = true;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Transfer");
        
        //Creating a GridPane container
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //Defining the Name text field
        username.setPromptText("Enter your userame.");
        username.setPrefColumnCount(20);
        GridPane.setConstraints(username, 0, 1);
        grid.getChildren().add(username);

        //Defining the Submit button
        GridPane.setConstraints(submit, 1, 1);
        grid.getChildren().add(submit);

        //Defining the error label
        errormsg.setText("");
        GridPane.setConstraints(errormsg, 0, 2);
        grid.getChildren().add(errormsg);

        //Defining the title label
        title.setText("Sign in");
        GridPane.setConstraints(title, 0, 0);
        grid.getChildren().add(title);

        
        //Set scene
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //Creating a GridPane container
        gridCB.setPadding(new Insets(10, 10, 10, 10));
        gridCB.setVgap(5);
        gridCB.setHgap(5);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login();
            }
        });

        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    login();
                }
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                delete();
            }
        });

        swap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                swapList();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    private void login(){
        if(username.getText().indexOf(",") != -1){
            errormsg.setText("Username can not contain \",\"!");
        }
        else{
            user = new User(username.getText());
            grid.getChildren().clear();

            title.setText("Missing cards");
            GridPane.setConstraints(title, 0, 0);
            grid.getChildren().add(title);

            GridPane.setConstraints(swap, 1, 0);
            grid.getChildren().add(swap);

            setGridCB();
            GridPane.setConstraints(gridCB, 0, 1);
            grid.getChildren().add(gridCB);
            delete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");
            GridPane.setConstraints(delete, 0, 2);
            grid.getChildren().add(delete);
        }
    }

    private void delete(){
        List<Integer> deleteArray = new ArrayList<Integer>();
        if(swapFlag){
            for(int i = 0; i < user.getMissing().size(); i++){
                if(checkboxes.get(i).isSelected()){
                    deleteArray.add(user.getMissing().get(i));
                }
            }
        }
        else{
            for(int i = 0; i < user.getDoubles().size(); i++){
                if(checkboxes.get(i).isSelected()){
                    deleteArray.add(user.getDoubles().get(i));
                }
            }
        }
        gridCB.getChildren().clear();
        if(swapFlag){
            user.deleteMissing(deleteArray);
        }
        else{
            user.deleteDoubles(deleteArray);
        }
        setGridCB();
    }

    private void setGridCB(){
        checkboxes.clear();
        if(swapFlag){
            int width = ((int)Math.ceil(Math.sqrt(user.getMissing().size())));
    
            for(int i = 0; i < user.getMissing().size(); i++){
                CheckBox checkbox = new CheckBox(Integer.toString(user.getMissing().get(i)));
                checkboxes.add(checkbox);
                GridPane.setConstraints(checkboxes.get(i), i%width, i/width);
                gridCB.getChildren().add(checkboxes.get(i));
            }
        }
        else{
            int width = ((int)Math.ceil(Math.sqrt(user.getDoubles().size())));
    
            for(int i = 0; i < user.getDoubles().size(); i++){
                CheckBox checkbox = new CheckBox(Integer.toString(user.getDoubles().get(i)));
                checkboxes.add(checkbox);
                GridPane.setConstraints(checkboxes.get(i), i%width, i/width);
                gridCB.getChildren().add(checkboxes.get(i));
            }
        }
    }

    private void swapList(){
        gridCB.getChildren().clear();
        grid.getChildren().clear();
        swapFlag = !swapFlag;

        if(swapFlag){
            title.setText("Missing cards");
        } 
        else{
            title.setText("Double cards");
        }
        
        GridPane.setConstraints(title, 0, 0);
        grid.getChildren().add(title);

        GridPane.setConstraints(swap, 1, 0);
        grid.getChildren().add(swap);

        setGridCB();
        GridPane.setConstraints(gridCB, 0, 1);
        grid.getChildren().add(gridCB);
        delete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");
        GridPane.setConstraints(delete, 0, 2);
        grid.getChildren().add(delete);
    }

}