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

    private Image playerLeft = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/playerLeft.png");
    private Image playerRight = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/playerRight.png");

    private Image background = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/background.png");
    private Image river = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/river.png");
    private Image tree = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/tree.png");
    private Image bridge = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/bridge.png");


    /*      IMAGE CONFIGURATIONS        */

    private ImgConfig imgConfig = new ImgConfig();
    private ImgPos imgPosBridge = new ImgPos("bridge");
    private ImgPos imgPosBotFor = new ImgPos("botForest");
    private ImgPos imgPosTopFor = new ImgPos("topForest");
    private ImgDims idBG = new ImgDims(imgConfig.getIHBG(), imgConfig.getIWBG(), imgConfig.getRFS());
    private ImgDims idPL = new ImgDims(imgConfig.getIHPL(), imgConfig.getIWPL(), imgConfig.getRFS());
    private ImgDims idRV = new ImgDims(imgConfig.getIHRV(), imgConfig.getIWRV(), imgConfig.getRFS());
    private ImgDims idBR = new ImgDims(imgConfig.getIHBR(), imgConfig.getIWBR(), imgConfig.getRFS());
    private ImgDims idTR = new ImgDims(imgConfig.getIHTR(), imgConfig.getIWTR(), imgConfig.getRFS());

    private boolean fs = true;

    private double stepSize = 5;
    
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

        /*      CREATE PLAYER       */

        ImageView playerImage = new ImageView(playerLeft); // initial orientation
        playerImage.setFitHeight(imgConfig.getIHPL());
        playerImage.setFitWidth(imgConfig.getIWPL());
        // idk why, but it breaks when init location is set directly
        playerImage.relocate(imgConfig.getIWBG()/2 - imgConfig.getIWPL()/2, imgConfig.getIHBG()/2 - imgConfig.getIHPL()/2);

        /*      CREATE RIVER        */

        ImageView riverImage = new ImageView(river);
        riverImage.setFitHeight(imgConfig.getIHRV());
        riverImage.setFitWidth(imgConfig.getIWRV());

        /*      CREATE BRIDGE       */

        ImageView bridgeImage = new ImageView(bridge);
        bridgeImage.setFitHeight(imgConfig.getIHBR());
        bridgeImage.setFitWidth(imgConfig.getIWBR());
        bridgeImage.relocate(imgPosBridge.getPosX(0), imgPosBridge.getPosY(0)); 

        /*      CREATE TREES BELOW PLAYER   */

        ImageView botTreeImage[] = new ImageView[imgPosBotFor.getCount()];
        for(int i = 0; i < botTreeImage.length; i++) {
            botTreeImage[i] = new ImageView(tree);
            botTreeImage[i].setFitHeight(imgConfig.getIHTR());
            botTreeImage[i].setFitWidth(imgConfig.getIWTR());
            botTreeImage[i].relocate(imgPosBotFor.getPosX(i), imgPosBotFor.getPosY(i));
        }

        /*      CREATE TREES ABOVE PLAYER   */

        ImageView topTreeImage[] = new ImageView[imgPosTopFor.getCount()];
        for(int i = 0; i < topTreeImage.length; i++) {
            topTreeImage[i] = new ImageView(tree);
            topTreeImage[i].setFitHeight(imgConfig.getIHTR());
            topTreeImage[i].setFitWidth(imgConfig.getIWTR());
            topTreeImage[i].relocate(imgPosTopFor.getPosX(i), imgPosTopFor.getPosY(i));
        }

        /*      CREATE LAYOUT       */

        Pane layout = new Pane();
        layout.getChildren().add(backgroundImage);
        for(int i = 0; i < botTreeImage.length; i++) {
            layout.getChildren().add(botTreeImage[i]);
        }
        layout.getChildren().add(riverImage);
        layout.getChildren().add(bridgeImage);
        layout.getChildren().add(playerImage);
        for(int i = 0; i < topTreeImage.length; i++) {
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
                    go(playerImage, 'W', botTreeImage, topTreeImage, backgroundImage);
                    break;
                case S:
                    go(playerImage, 'S', botTreeImage, topTreeImage, backgroundImage);
                    break;
                case A:
                    playerImage.setImage(playerLeft);
                    go(playerImage, 'A', botTreeImage, topTreeImage, backgroundImage);
                    break;
                case D:
                    playerImage.setImage(playerRight);
                    go(playerImage, 'D', botTreeImage, topTreeImage, backgroundImage);
                    break;
                case F:
                    if(fs) {
                        upScaler(backgroundImage, idBG);
                        upScaler(playerImage, idPL);
                        upScaler(riverImage, idRV);
                        upScaler(bridgeImage, idBR);
                        upScaler(botTreeImage, idTR);
                        upScaler(topTreeImage, idTR);
                        stepSize = upScaler(stepSize);
                        primaryStage.setFullScreen(true);
                        fs = !fs;
                    }
                    else{
                        downScaler(backgroundImage, idBG);
                        downScaler(playerImage, idPL);
                        downScaler(riverImage, idRV);
                        downScaler(bridgeImage, idBR);
                        downScaler(botTreeImage, idTR);
                        downScaler(topTreeImage, idTR);
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
                        downScaler(botTreeImage, idTR);
                        downScaler(topTreeImage, idTR);
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

    private double downScaler(double val) {
        return val / imgConfig.getRFS();
    }

    private void downScaler(ImageView img[], ImgDims id) {
        for(int i = 0; i < img.length; i++){
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

    private double upScaler(double val) {
        return val * imgConfig.getRFS();
    }

    private void upScaler(ImageView img[], ImgDims id) {
        for(int i = 0; i < img.length; i++){
            img[i].setFitHeight(id.getFH());
            img[i].setFitWidth(id.getFW());
            img[i].setLayoutX( imgConfig.getRFS() * (img[i].getLayoutX() + id.getIW()/2) - id.getFW()/2 + imgConfig.getBSW() );
            img[i].setLayoutY( imgConfig.getRFS() * (img[i].getLayoutY() + id.getIH()/2) - id.getFH()/2 );
        }
    }

    private void go(ImageView img, char dir, ImageView[] botF, ImageView[] topF, ImageView bkg){
        double posX = img.getLayoutX();
        double posY = img.getLayoutY();
        double newX = posX;
        double newY = posY;

        switch(dir){
        case 'W':
            newY = newY - stepSize;
            break;
        case 'S':
            newY = newY + stepSize;
            break;
        case 'A':
            newX = newX - stepSize;
            break;
        case 'D':
            newX = newX + stepSize;
            break;
        default:
            break;
        }

        double t1;
        double t2;

        /*      NO HITS WITH BOTTOM TREES       */
        
        for(int i = 0; i < botF.length; i++){
            t1 = ((botF[i].getLayoutX() + botF[i].getFitWidth()/2) - (newX + img.getFitWidth()/2));
            t2 = ((botF[i].getLayoutY() + botF[i].getFitHeight()) - (newY + img.getFitHeight()));
            if(-botF[i].getFitWidth() < t1 && t1 < botF[i].getFitWidth() 
            && 0.4*botF[i].getFitHeight() < t2 && t2 < 1.2*botF[i].getFitHeight()) {
                return;
            }
        }

        /*      NO HITS WITH TOP TREES      */
        
        for(int i = 0; i < topF.length; i++){
            t1 = ((topF[i].getLayoutX() + topF[i].getFitWidth()/2) - (newX + img.getFitWidth()/2));
            t2 = ((topF[i].getLayoutY() + topF[i].getFitHeight()) - (newY + img.getFitHeight()));
            if(-0.4*topF[i].getFitWidth() < t1 && t1 < topF[i].getFitWidth() 
            && 0.1*topF[i].getFitHeight() < t2 && t2 < 0.7*topF[i].getFitHeight()) return;
        }

        /*      NO HITS WITH BORDERS     */

        System.out.println(bkg.getLayoutX() + bkg.getFitWidth());
        System.out.println(img.getFitWidth());
        if(bkg.getLayoutX() > newX || newX + 1.1*img.getFitWidth() > bkg.getLayoutX() + bkg.getFitWidth() 
        || bkg.getLayoutY() > newY || newY + 1.1*img.getFitHeight() > bkg.getLayoutY() + bkg.getFitHeight()) return;

        img.relocate(newX, newY);
    }
    
}