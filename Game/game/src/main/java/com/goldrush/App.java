package com.goldrush;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;

import javafx.scene.shape.*;
import javafx.animation.PathTransition;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {
    
    Button button; 

    Image playerLeft = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/playerLeft.png");
    Image playerRight = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/playerRight.png");


    Image background = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/bg.jpg");

    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gold Rush");
        button = new Button();
        button.setText("Click me");


        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        final ImageView backgroundImage = new ImageView(background);
        backgroundImage.setX(0);
        backgroundImage.setY(0);


        // final ImageView demoUser = new ImageView(new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/player.png"));
        // demoUser.setX(0);
        // demoUser.setY(0);
    
        // Path path = new Path();
        // path.getElements().add (new MoveTo (400, 222));
        // path.getElements().add (new LineTo (83, 222));
    
        // PathTransition pathTransition = new PathTransition(); 
        // pathTransition.setDuration(Duration.millis(4000));
        // pathTransition.setNode(demoUser);
        // pathTransition.setPath(path);
    
        // pathTransition.play();


        ImageView userImage = new ImageView(playerLeft);
        userImage.setX(0);
        userImage.setY(0);

        Pane layout = new Pane();
        layout.getChildren().add(backgroundImage);
        layout.getChildren().add(button);
        layout.getChildren().add(userImage);

        Scene scene = new Scene(layout, 600, 457);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case W:
                        userImage.relocate(userImage.getLayoutX(), userImage.getLayoutY() - 5);
                        break;
                    case S:
                        userImage.relocate(userImage.getLayoutX(), userImage.getLayoutY() + 5);
                        break;
                    case A:
                        userImage.setImage(playerLeft);
                        userImage.relocate(userImage.getLayoutX() - 5, userImage.getLayoutY());
                        break;
                    case D:
                        userImage.setImage(playerRight);
                        userImage.relocate(userImage.getLayoutX() + 5, userImage.getLayoutY());
                        break;
                    default:
                        break;
                }
            }
            
        });
    }
}