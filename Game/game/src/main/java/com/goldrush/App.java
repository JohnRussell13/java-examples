package com.goldrush;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
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

    Image background = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/background.png");

    final float initHeightBG = 457;
    final float initWidthBG = 600;
    final float fullHeightBG = 1080;
    final float ratioFS = fullHeightBG/initHeightBG;
    final float fullWidthBG = ratioFS * initWidthBG;

    final float initHeightPL = 56;
    final float initWidthPL = 37;
    final float fullHeightPL = ratioFS * initHeightPL;
    final float fullWidthPL = ratioFS * initWidthPL;

    boolean fs = true;

    float stepSize = 5;
    
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
        //backgroundImage.setPreserveRatio(true); // false if strech
        backgroundImage.setX(0);
        backgroundImage.setY(0);
        backgroundImage.setFitHeight(initHeightBG);
        backgroundImage.setFitWidth(initWidthBG);


        ImageView userImage = new ImageView(playerLeft);
        userImage.setX(0);
        userImage.setY(0);

        Pane layout = new Pane();
        layout.getChildren().add(backgroundImage);
        layout.getChildren().add(button);
        layout.getChildren().add(userImage);

        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, initWidthBG, initHeightBG);
        // primaryStage.setFullScreen(true); // for fullscreen
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println(ratioFS * initHeightBG);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                case W:
                    userImage.relocate(userImage.getLayoutX(), userImage.getLayoutY() - stepSize);
                    break;
                case S:
                    userImage.relocate(userImage.getLayoutX(), userImage.getLayoutY() + stepSize);
                    break;
                case A:
                    userImage.setImage(playerLeft);
                    userImage.relocate(userImage.getLayoutX() - stepSize, userImage.getLayoutY());
                    break;
                case D:
                    userImage.setImage(playerRight);
                    userImage.relocate(userImage.getLayoutX() + stepSize, userImage.getLayoutY());
                    break;
                case F:
                    if(fs) {
                        backgroundImage.setFitHeight(fullHeightBG); // for fullscreen
                        backgroundImage.setFitWidth(fullWidthBG); // for fullscreen
                        backgroundImage.setLayoutX((1920-fullWidthBG)/2);
                        userImage.setFitHeight(fullHeightPL);
                        userImage.setFitWidth(fullWidthPL);
                        userImage.setLayoutX( ratioFS * (userImage.getLayoutX() + initWidthPL/2) - fullWidthPL/2 + (1920-fullWidthBG)/2 );
                        userImage.setLayoutY( ratioFS * (userImage.getLayoutY() + initHeightPL/2) - fullHeightPL/2 );
                        primaryStage.setFullScreen(true); // for fullscreen
                        stepSize = ratioFS * stepSize;
                        fs = !fs;
                    }
                    else{
                        backgroundImage.setFitHeight(initHeightBG); // for fullscreen
                        backgroundImage.setFitWidth(initWidthBG); // for fullscreen
                        backgroundImage.setLayoutX(0);
                        userImage.setFitHeight(initHeightPL);
                        userImage.setFitWidth(initWidthPL);
                        userImage.setLayoutX( (userImage.getLayoutX() + fullWidthPL/2 - (1920-fullWidthBG)/2) / ratioFS - initWidthPL/2 );
                        userImage.setLayoutY( (userImage.getLayoutY() + fullHeightPL/2) / ratioFS - initHeightPL/2 );
                        primaryStage.setFullScreen(false); // for fullscreen
                        stepSize = stepSize / ratioFS;
                        fs = !fs;
                    }
                    break;
                case ESCAPE:
                    if(!fs) {
                        backgroundImage.setFitHeight(initHeightBG); // for fullscreen
                        backgroundImage.setFitWidth(initWidthBG); // for fullscreen
                        backgroundImage.setLayoutX(0);
                        userImage.setFitHeight(initHeightPL);
                        userImage.setFitWidth(initWidthPL);
                        userImage.setLayoutX( (userImage.getLayoutX() + fullWidthPL/2 - (1920-fullWidthBG)/2) / ratioFS - initWidthPL/2 );
                        userImage.setLayoutY( (userImage.getLayoutY() + fullHeightPL/2) / ratioFS - initHeightPL/2 );
                        primaryStage.setFullScreen(false); // for fullscreen
                        stepSize = stepSize / ratioFS;
                        fs = !fs;
                    }
                default:
                    break;
                }
            }
            
        });
    }
}