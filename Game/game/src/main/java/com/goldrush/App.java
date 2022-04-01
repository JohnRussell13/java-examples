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
    private Image saloon = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/saloon.png");
    private Image house = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/house.png");
    private Image menu = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/menu.png");
    private Image popUpS = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/popUpS.png");
    private Image popUpH = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/popUpH.png");


    /*      IMAGE CONFIGURATIONS        */

    private ImgConfig imgConfig = new ImgConfig();
    private ImgPos imgPosBridge = new ImgPos("bridge");
    private ImgPos imgPosRiver = new ImgPos("river");
    private ImgPos imgPosSaloon = new ImgPos("saloon");
    private ImgPos imgPosHouse = new ImgPos("house");
    private ImgPos imgPosMenu = new ImgPos("menu");
    private ImgPos imgPosBotFor = new ImgPos("botForest");
    private ImgPos imgPosTopFor = new ImgPos("topForest");
    private ImgDims idBG = new ImgDims(imgConfig.getIHBG(), imgConfig.getIWBG(), imgConfig.getRFS());
    private ImgDims idMN = new ImgDims(imgConfig.getIHMN(), imgConfig.getIWMN(), imgConfig.getRFS());
    private ImgDims idPL = new ImgDims(imgConfig.getIHPL(), imgConfig.getIWPL(), imgConfig.getRFS());
    private ImgDims idRV = new ImgDims(imgConfig.getIHRV(), imgConfig.getIWRV(), imgConfig.getRFS());
    private ImgDims idBR = new ImgDims(imgConfig.getIHBR(), imgConfig.getIWBR(), imgConfig.getRFS());
    private ImgDims idTR = new ImgDims(imgConfig.getIHTR(), imgConfig.getIWTR(), imgConfig.getRFS());
    private ImgDims idSL = new ImgDims(imgConfig.getIHSL(), imgConfig.getIWSL(), imgConfig.getRFS());
    private ImgDims idHS = new ImgDims(imgConfig.getIHHS(), imgConfig.getIWHS(), imgConfig.getRFS());
    private ImgDims idPU = new ImgDims(imgConfig.getIHPU(), imgConfig.getIWPU(), imgConfig.getRFS());

    private boolean fs = true; // non-fullscreen flag
    private boolean fps = true; // non-popUpS flag
    private boolean fph = true; // non-popUpH flag
    private boolean fpw = true; // non-popUpH flag
    private boolean fp = true; // non-popUpH flag

    private double stepSize = 5;

    /*      IMAGES      */
    private ImageView backgroundImage = new ImageView(background);
    private ImageView menuImage = new ImageView(menu);
    private ImageView playerImage = new ImageView(playerLeft); // initial orientation
    private ImageView riverImage = new ImageView(river);
    private ImageView bridgeImage = new ImageView(bridge);
    private ImageView saloonImage = new ImageView(saloon);
    private ImageView houseImage = new ImageView(house);
    private Pane layout = new Pane();

    private ImageView popUpSImage = new ImageView(popUpS);
    private ImageView popUpHImage = new ImageView(popUpH);

    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gold Rush");

        /*      CREATE BACKGROUND       */

        //backgroundImage.setPreserveRatio(true); // false if strech
        backgroundImage.setFitHeight(imgConfig.getIHBG());
        backgroundImage.setFitWidth(imgConfig.getIWBG());

        /*      CREATE MENU     */

        menuImage.setFitHeight(imgConfig.getIHMN());
        menuImage.setFitWidth(imgConfig.getIWMN());
        menuImage.relocate(imgPosMenu.getPosX(0), imgPosMenu.getPosY(0)); 

        /*      CREATE PLAYER       */

        playerImage.setFitHeight(imgConfig.getIHPL());
        playerImage.setFitWidth(imgConfig.getIWPL());
        // idk why, but it breaks when init location is set directly
        playerImage.relocate(imgConfig.getIWBG()/2 - imgConfig.getIWPL()/2, imgConfig.getIHBG()/2 - imgConfig.getIHPL()/2);

        /*      CREATE RIVER        */

        riverImage.setFitHeight(imgConfig.getIHRV());
        riverImage.setFitWidth(imgConfig.getIWRV());
        riverImage.relocate(imgPosRiver.getPosX(0), imgPosRiver.getPosY(0));

        /*      CREATE BRIDGE       */

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

        /*      CREATE SALOON       */

        saloonImage.setFitHeight(imgConfig.getIHSL());
        saloonImage.setFitWidth(imgConfig.getIWSL());
        saloonImage.relocate(imgPosSaloon.getPosX(0), imgPosSaloon.getPosY(0)); 

        /*      CREATE HOUSEE      */

        houseImage.setFitHeight(imgConfig.getIHHS());
        houseImage.setFitWidth(imgConfig.getIWHS());
        houseImage.relocate(imgPosHouse.getPosX(0), imgPosHouse.getPosY(0)); 

        /*      CREATE LAYOUT       */

        layout.getChildren().add(backgroundImage);
        for(int i = 0; i < botTreeImage.length; i++) {
            layout.getChildren().add(botTreeImage[i]);
        }
        layout.getChildren().add(saloonImage);
        layout.getChildren().add(riverImage);
        layout.getChildren().add(bridgeImage);
        layout.getChildren().add(houseImage);
        layout.getChildren().add(playerImage);
        for(int i = 0; i < topTreeImage.length; i++) {
            layout.getChildren().add(topTreeImage[i]);
        }
        layout.getChildren().add(menuImage);
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
                    if(fp) go('W', botTreeImage, topTreeImage);
                    break;
                case S:
                    if(fp) go('S', botTreeImage, topTreeImage);
                    break;
                case A:
                    playerImage.setImage(playerLeft);
                    if(fp) go('A', botTreeImage, topTreeImage);
                    break;
                case D:
                    playerImage.setImage(playerRight);
                    if(fp) go('D', botTreeImage, topTreeImage);
                    break;
                case F:
                    if(fs) {
                        upScaler(backgroundImage, idBG);
                        upScaler(playerImage, idPL);
                        upScaler(riverImage, idRV);
                        upScaler(bridgeImage, idBR);
                        upScaler(botTreeImage, idTR);
                        upScaler(topTreeImage, idTR);
                        upScaler(saloonImage, idSL);
                        upScaler(houseImage, idHS);
                        upScaler(menuImage, idMN);
                        upScaler(popUpSImage, idPU);
                        upScaler(popUpHImage, idPU);
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
                        downScaler(saloonImage, idSL);
                        downScaler(houseImage, idHS);
                        downScaler(menuImage, idMN);
                        downScaler(popUpSImage, idPU);
                        downScaler(popUpHImage, idPU);
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
                        downScaler(saloonImage, idSL);
                        downScaler(houseImage, idHS);
                        downScaler(menuImage, idMN);
                        downScaler(popUpSImage, idPU);
                        downScaler(popUpHImage, idPU);
                        stepSize = downScaler(stepSize);
                        primaryStage.setFullScreen(false);
                        fs = !fs;
                    }
                case K:
                    if(!fps) {
                        layout.getChildren().remove(popUpSImage);
                        fps = true;
                        fp = true;
                    }
                    else if(!fph) {
                        layout.getChildren().remove(popUpHImage);
                        fph = true;
                        fp = true;
                    }
                default:
                    break;
                }
            }
            
        });

        /*      CREATE POP UP       */

        popUpSImage.setFitHeight(imgConfig.getIHPU());
        popUpSImage.setFitWidth(imgConfig.getIWPU());
        popUpSImage.relocate(backgroundImage.getLayoutX() + 0.1*backgroundImage.getFitWidth(), 
            backgroundImage.getLayoutY() + 0.1*backgroundImage.getFitHeight()); 

        popUpHImage.setFitHeight(imgConfig.getIHPU());
        popUpHImage.setFitWidth(imgConfig.getIWPU());
        popUpHImage.relocate(backgroundImage.getLayoutX() + 0.1*backgroundImage.getFitWidth(), 
            backgroundImage.getLayoutY() + 0.1*backgroundImage.getFitHeight()); 
        
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

    private void go(char dir, ImageView[] botT, ImageView[] topT){
        double posX = playerImage.getLayoutX();
        double posY = playerImage.getLayoutY();
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
        
        for(int i = 0; i < botT.length; i++){
            t1 = ((botT[i].getLayoutX() + botT[i].getFitWidth()/2) - (newX + playerImage.getFitWidth()/2));
            t2 = ((botT[i].getLayoutY() + botT[i].getFitHeight()) - (newY + playerImage.getFitHeight()));
            if(-0.7*botT[i].getFitWidth() < t1 && t1 < 0.7*botT[i].getFitWidth() 
            && 0 < t2 && t2 < botT[i].getFitHeight()) {
                return;
            }
        }

        /*      NO HITS WITH TOP TREES      */
        
        for(int i = 0; i < topT.length; i++){
            t1 = ((topT[i].getLayoutX() + topT[i].getFitWidth()/2) - (newX + playerImage.getFitWidth()/2));
            t2 = ((topT[i].getLayoutY() + topT[i].getFitHeight()) - (newY + playerImage.getFitHeight()));
            if(-0.7*topT[i].getFitWidth() < t1 && t1 < 0.7*topT[i].getFitWidth() 
            && -0.35*topT[i].getFitHeight() < t2 && t2 < 0.2*topT[i].getFitHeight()) return;
        }

        /*      NO HITS WITH BORDERS     */

        if(backgroundImage.getLayoutX() > newX || newX + 1.1*playerImage.getFitWidth() > backgroundImage.getLayoutX() + backgroundImage.getFitWidth() 
        || backgroundImage.getLayoutY() > newY || newY + 1.1*playerImage.getFitHeight() > backgroundImage.getLayoutY() + backgroundImage.getFitHeight()) return;

        /*      NO HITS WITH RIVER, EXCEPT ON BRIDGE        */

        t1 = (newX + playerImage.getFitWidth()/2); // middle of the body width
        t2 = (newY + playerImage.getFitHeight()); // legs

        if(riverImage.getLayoutX() - playerImage.getFitWidth()/2 < t1 && t1 < riverImage.getLayoutX() + riverImage.getFitWidth() + playerImage.getFitWidth()/2) {
            if( !(bridgeImage.getLayoutY() + 3*stepSize < t2 && t2 < bridgeImage.getLayoutY() + bridgeImage.getFitHeight()) ) {
                return;
            }
        }

        /*      NO HITS WITH SALOON, EXCEPT WITH DOORS      */
        if((saloonImage.getLayoutY() + 3*stepSize < t2 && t2 < saloonImage.getLayoutY() + saloonImage.getFitHeight())
        && saloonImage.getLayoutX() < t1 && t1 < saloonImage.getLayoutX() + saloonImage.getFitWidth()){
            if(saloonImage.getLayoutX() + 0.3*saloonImage.getFitWidth() < t1 && t1 < saloonImage.getLayoutX() + 0.7*saloonImage.getFitWidth()){
                drinking();
            }
            return;
        }

        /*      HOME HITS       */

        if((houseImage.getLayoutX() - 3*stepSize < t1 && t1 < houseImage.getLayoutX() + houseImage.getFitWidth())
        && houseImage.getLayoutY() < t2 && t2 < houseImage.getLayoutY() + houseImage.getFitHeight() + 3*stepSize){
            if(houseImage.getLayoutY() + houseImage.getFitHeight() - 0.5*playerImage.getFitHeight() < t2 && t2 < houseImage.getLayoutY() + houseImage.getFitHeight()){
                enterHouse();
            }
            return;
        }

        playerImage.relocate(newX, newY);
    }

    private void drinking(){
        layout.getChildren().add(popUpSImage);
        fps = false;
        fp = false;
    }

    private void enterHouse(){
        layout.getChildren().add(popUpHImage);
        fph = false;
        fp = false;
    }
    
}