package com.goldrush;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * JavaFX App
 */
public class App extends Application {
    /*      IMAGES      */

    Image playerLeft = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/playerLeft.png");
    Image playerRight = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/playerRight.png");

    Image background = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/background.png");
    Image river = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/river.png");
    Image tree = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/tree.png");
    Image bridge = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/bridge.png");


    /*      IMAGE CONFIGURATIONS        */

    private ImgConfig imgConfig = new ImgConfig();

    /*      CONFIG      */
    int topTreeCount = 11;
    int botTreeCount = 14;

    private boolean fs = true;

    private float stepSize = 5;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gold Rush");

        /*      CREATE BACKGROUND       */

        final ImageView backgroundImage = new ImageView(background);
        //backgroundImage.setPreserveRatio(true); // false if strech
        backgroundImage.setFitHeight(imgConfig.getIHBG());
        backgroundImage.setFitWidth(imgConfig.getIWBG());
        ImgDims idBG = new ImgDims(imgConfig.getIHBG(), imgConfig.getIWBG(), imgConfig.getRFS());

        /*      CREATE PLAYER       */

        ImageView playerImage = new ImageView(playerLeft); // initial orientation
        // idk why, but it breaks when init location is set directly
        playerImage.relocate(imgConfig.getIWBG()/2 - imgConfig.getIWPL()/2, imgConfig.getIHBG()/2 - imgConfig.getIHPL()/2); 
        ImgDims idPL = new ImgDims(imgConfig.getIHPL(), imgConfig.getIWPL(), imgConfig.getRFS());

        /*      CREATE RIVER        */

        ImageView riverImage = new ImageView(river);
        riverImage.setFitHeight(imgConfig.getIHRV());
        riverImage.setFitWidth(imgConfig.getIWRV());
        ImgDims idRV = new ImgDims(imgConfig.getIHRV(), imgConfig.getIWRV(), imgConfig.getRFS());

        /*      CREATE BRIDGE       */

        ImageView bridgeImage = new ImageView(bridge);
        bridgeImage.setFitHeight(imgConfig.getIHBR());
        bridgeImage.setFitWidth(imgConfig.getIWBR());
        bridgeImage.relocate(90, 200); 
        ImgDims idBR = new ImgDims(imgConfig.getIHBR(), imgConfig.getIWBR(), imgConfig.getRFS());

        /*      CREATE TREES BELOW PLAYER   */

        ImageView botTreeImage[] = new ImageView[botTreeCount];
        for(int i = 0; i < botTreeCount; i++) {
            botTreeImage[i] = new ImageView(tree);
            botTreeImage[i].setFitHeight(imgConfig.getIHTR());
            botTreeImage[i].setFitWidth(imgConfig.getIWTR());
            if(i < 7) {
                botTreeImage[i].relocate(150 - imgConfig.getIWTR()/2 + i*imgConfig.getIWTR(), 10 - imgConfig.getIHTR()/3);
            }
            else {
                botTreeImage[i].relocate(150 + i%7*imgConfig.getIWTR(), 10);
            }
        }
        ImgDims idTR = new ImgDims(imgConfig.getIHTR(), imgConfig.getIWTR(), imgConfig.getRFS());

        /*      CREATE TREES ABOVE PLAYER   */

        ImageView topTreeImage[] = new ImageView[topTreeCount];
        for(int i = 0; i < topTreeCount; i++) {
            topTreeImage[i] = new ImageView(tree);
            topTreeImage[i].setFitHeight(imgConfig.getIHTR());
            topTreeImage[i].setFitWidth(imgConfig.getIWTR());
            if(i < 4) {
                topTreeImage[i].relocate(320 - imgConfig.getIWTR()/2 + i*imgConfig.getIWTR(), 285 - imgConfig.getIHTR()/3);
            }
            else if(i < 8) {
                topTreeImage[i].relocate(320 + i%4*imgConfig.getIWTR(), 285);
            }
            else {
                topTreeImage[i].relocate(320 + imgConfig.getIWTR()/2 + i%8*imgConfig.getIWTR(), 285 + imgConfig.getIHTR()/3);
            }
        }

        /*      CREATE LAYOUT       */

        Pane layout = new Pane();
        layout.getChildren().add(backgroundImage);
        for(int i = 0; i < botTreeCount; i++) {
            layout.getChildren().add(botTreeImage[i]);
        }
        layout.getChildren().add(riverImage);
        layout.getChildren().add(bridgeImage);
        layout.getChildren().add(playerImage);
        for(int i = 0; i < topTreeCount; i++) {
            layout.getChildren().add(topTreeImage[i]);
        }
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        /*      CREATE THE SCENE        */

        Scene scene = new Scene(layout, imgConfig.getIWBG(), imgConfig.getIHBG());
        primaryStage.setScene(scene);
        primaryStage.show();

        /*      KEYBOARD        */

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                case W:
                    playerImage.relocate(playerImage.getLayoutX(), playerImage.getLayoutY() - stepSize);
                    break;
                case S:
                    playerImage.relocate(playerImage.getLayoutX(), playerImage.getLayoutY() + stepSize);
                    break;
                case A:
                    playerImage.setImage(playerLeft);
                    playerImage.relocate(playerImage.getLayoutX() - stepSize, playerImage.getLayoutY());
                    break;
                case D:
                    playerImage.setImage(playerRight);
                    playerImage.relocate(playerImage.getLayoutX() + stepSize, playerImage.getLayoutY());
                    break;
                case F:
                    if(fs) {
                        upScaler(backgroundImage, idBG);
                        upScaler(playerImage, idPL);
                        upScaler(riverImage, idRV);
                        upScaler(bridgeImage, idBR);
                        upScaler(botTreeImage, botTreeCount, idTR);
                        upScaler(topTreeImage, topTreeCount, idTR);
                        stepSize = upScaler(stepSize);
                        primaryStage.setFullScreen(true);
                        fs = !fs;
                    }
                    else{
                        downScaler(backgroundImage, idBG);
                        downScaler(playerImage, idPL);
                        downScaler(riverImage, idRV);
                        downScaler(bridgeImage, idBR);
                        downScaler(botTreeImage, botTreeCount, idTR);
                        downScaler(topTreeImage, topTreeCount, idTR);
                        stepSize = downScaler(stepSize);
                        primaryStage.setFullScreen(false);
                        fs = !fs;
                    }
                    break;
                case ESCAPE:
                    if(!fs) {
                        downScaler(backgroundImage, idBG);
                        downScaler(playerImage, idPL);
                        downScaler(riverImage, idRV);
                        downScaler(bridgeImage, idBR);
                        downScaler(botTreeImage, botTreeCount, idTR);
                        downScaler(topTreeImage, topTreeCount, idTR);
                        stepSize = downScaler(stepSize);
                        primaryStage.setFullScreen(false);
                        fs = !fs;
                    }
                default:
                    break;
                }
            }
            
        });
    }

    /*      FULLSCREEN FUNCTIONS        */

    private void downScaler(ImageView img, ImgDims id) {
        img.setFitHeight(id.getIH());
        img.setFitWidth(id.getIW());
        img.setLayoutX( (img.getLayoutX() + id.getFW()/2 - imgConfig.getBSW()) / imgConfig.getRFS() - id.getIW()/2 );
        img.setLayoutY( (img.getLayoutY() + id.getFH()/2) / imgConfig.getRFS() - id.getIH()/2 );
    }

    private float downScaler(float val) {
        return val / imgConfig.getRFS();
    }

    private void downScaler(ImageView img[], int count, ImgDims id) {
        for(int i = 0; i < count; i++){
            img[i].setFitHeight(id.getIH());
            img[i].setFitWidth(id.getIW());
            img[i].setLayoutX( (img[i].getLayoutX() + id.getFW()/2 - imgConfig.getBSW()) / imgConfig.getRFS() - id.getIW()/2 );
            img[i].setLayoutY( (img[i].getLayoutY() + id.getFH()/2) / imgConfig.getRFS() - id.getIH()/2 );
        }
    }

    private void upScaler(ImageView img, ImgDims id) {
        img.setFitHeight(id.getFH());
        img.setFitWidth(id.getFW());
        img.setLayoutX( imgConfig.getRFS() * (img.getLayoutX() + id.getIW()/2) - id.getFW()/2 + imgConfig.getBSW() );
        img.setLayoutY( imgConfig.getRFS() * (img.getLayoutY() + id.getIH()/2) - id.getFH()/2 );
    }

    private float upScaler(float val) {
        return val * imgConfig.getRFS();
    }

    private void upScaler(ImageView img[], int count, ImgDims id) {
        for(int i = 0; i < count; i++){
            img[i].setFitHeight(id.getFH());
            img[i].setFitWidth(id.getFW());
            img[i].setLayoutX( imgConfig.getRFS() * (img[i].getLayoutX() + id.getIW()/2) - id.getFW()/2 + imgConfig.getBSW() );
            img[i].setLayoutY( imgConfig.getRFS() * (img[i].getLayoutY() + id.getIH()/2) - id.getFH()/2 );
        }
    }
}