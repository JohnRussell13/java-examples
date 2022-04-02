package com.goldrush;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
// import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
// import javafx.scene.layout.Background;
// import javafx.scene.layout.BackgroundFill;
// import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    private Image sellerCradleLeft = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/sellerCradleLeft.png");
    private Image sellerCradleRight = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/sellerCradleRight.png");

    private Image sellerFoodLeft = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/sellerFoodLeft.png");
    private Image sellerFoodRight = new Image("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/sellerFoodRight.png");

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

    private ImgPos imgPosPlayer = new ImgPos("player");
    private ImgPos imgPosBridge = new ImgPos("bridge");
    private ImgPos imgPosRiver = new ImgPos("river");
    private ImgPos imgPosSaloon = new ImgPos("saloon");
    private ImgPos imgPosHouse = new ImgPos("house");
    private ImgPos imgPosMenu = new ImgPos("menu");
    private ImgPos imgPosBotFor = new ImgPos("botForest");
    private ImgPos imgPosTopFor = new ImgPos("topForest");

    // private ImgPos imgPosSellerFood = new ImgPos();
    // private ImgPos imgPosSellerCradle = new ImgPos();

    private ImgDims idBG = new ImgDims(imgConfig.getInitialHeight("background"), imgConfig.getInitialWidth("background"), imgConfig.getRFS());
    private ImgDims idMN = new ImgDims(imgConfig.getInitialHeight("menu"), imgConfig.getInitialWidth("menu"), imgConfig.getRFS());
    private ImgDims idPL = new ImgDims(imgConfig.getInitialHeight("player"), imgConfig.getInitialWidth("player"), imgConfig.getRFS());
    private ImgDims idRV = new ImgDims(imgConfig.getInitialHeight("river"), imgConfig.getInitialWidth("river"), imgConfig.getRFS());
    private ImgDims idBR = new ImgDims(imgConfig.getInitialHeight("bridge"), imgConfig.getInitialWidth("bridge"), imgConfig.getRFS());
    private ImgDims idTR = new ImgDims(imgConfig.getInitialHeight("tree"), imgConfig.getInitialWidth("tree"), imgConfig.getRFS());
    private ImgDims idSL = new ImgDims(imgConfig.getInitialHeight("saloon"), imgConfig.getInitialWidth("saloon"), imgConfig.getRFS());
    private ImgDims idHS = new ImgDims(imgConfig.getInitialHeight("house"), imgConfig.getInitialWidth("house"), imgConfig.getRFS());
    private ImgDims idPU = new ImgDims(imgConfig.getInitialHeight("popUp"), imgConfig.getInitialWidth("popUp"), imgConfig.getRFS());

    private ImgDims idSF = new ImgDims(imgConfig.getInitialHeight("sellerFood"), imgConfig.getInitialWidth("sellerFood"), imgConfig.getRFS());
    private ImgDims idSC = new ImgDims(imgConfig.getInitialHeight("sellerCradle"), imgConfig.getInitialWidth("sellerCradle"), imgConfig.getRFS());

    private ImgDims idBB = new ImgDims(imgConfig.getInitialHeight("background"), imgConfig.getBSW(), imgConfig.getRFS());

    private boolean fs = true; // non-fullscreen flag
    private boolean fps = true; // non-popUpS flag
    private boolean fph = true; // non-popUpH flag
    private boolean fpw = true; // non-popUpH flag
    private boolean fp = true; // non-popUpH flag
    private boolean faf = false; // seller flag
    private boolean fac = false; // seller flag

    private double stepSize = 5;
    private double speed = 0.1;

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

    private ImageView sellerFoodImage = new ImageView(sellerFoodRight); // initial orientation
    private ImageView sellerCradleImage = new ImageView(sellerCradleRight); // initial orientation

    private Rectangle blackBandL = new Rectangle();
    private Rectangle blackBandR = new Rectangle();

    private double destXFood = 0;
    private double destYFood = 0;
    private double destXCradle = 0;
    private double destYCradle = 0;
            
    private PathTransition pathTransitionFood = new PathTransition();
    private PathTransition pathTransitionCradle = new PathTransition();

    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gold Rush");

        /*      CREATE BACKGROUND       */
        //backgroundImage.setPreserveRatio(true); // false if strech
        backgroundImage.setFitHeight(imgConfig.getInitialHeight("background"));
        backgroundImage.setFitWidth(imgConfig.getInitialWidth("background"));

        /*      SET IMAGES      */

        imgSet(menuImage, imgPosMenu, "menu");
        imgSet(playerImage, imgPosPlayer, "player");
        imgSet(riverImage, imgPosRiver, "river");
        imgSet(bridgeImage, imgPosBridge, "bridge");
        imgSet(saloonImage, imgPosSaloon, "saloon");
        imgSet(houseImage, imgPosHouse, "house");

        sellerFoodImage.setFitHeight(imgConfig.getInitialHeight("sellerFood"));
        sellerFoodImage.setFitWidth(imgConfig.getInitialWidth("sellerFood"));
        sellerFoodImage.relocate(-idSF.getIW(), 200);

        sellerCradleImage.setFitHeight(imgConfig.getInitialHeight("sellerCradle"));
        sellerCradleImage.setFitWidth(imgConfig.getInitialWidth("sellerCradle"));
        sellerCradleImage.relocate(-idSC.getIW(), 200);

        /*      CREATE TREES        */

        ImageView botTreeImage[] = new ImageView[imgPosBotFor.getCount()];
        imgSet(botTreeImage, imgPosBotFor, "tree");
        ImageView topTreeImage[] = new ImageView[imgPosTopFor.getCount()];
        imgSet(topTreeImage, imgPosTopFor, "tree");

        /*      CREATE BLACK STRIPES FOR FULLSCREEN     */

        blackBandL.setWidth(imgConfig.getBSW() / imgConfig.getRFS());
        blackBandL.setHeight(imgConfig.getInitialHeight("background") / imgConfig.getRFS());
        blackBandL.relocate(-imgConfig.getBSW(), 0);

        blackBandR.setWidth(imgConfig.getBSW() / imgConfig.getRFS());
        blackBandR.setHeight(imgConfig.getInitialHeight("background") / imgConfig.getRFS());
        blackBandR.relocate(imgConfig.getInitialWidth("background"), 0);

        /*      CREATE LAYOUT       */

        layout.getChildren().add(backgroundImage);
        for(int i = 0; i < botTreeImage.length; i++) {
            layout.getChildren().add(botTreeImage[i]);
        }
        layout.getChildren().add(saloonImage);
        layout.getChildren().add(riverImage);
        layout.getChildren().add(bridgeImage);
        layout.getChildren().add(houseImage);
        layout.getChildren().add(sellerFoodImage);
        layout.getChildren().add(sellerCradleImage);
        layout.getChildren().add(playerImage);
        for(int i = 0; i < topTreeImage.length; i++) {
            layout.getChildren().add(topTreeImage[i]);
        }
        layout.getChildren().add(menuImage);
        // layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().add(blackBandL);
        layout.getChildren().add(blackBandR);


        /*      CREATE THE SCENE        */

        Scene scene = new Scene(layout, imgConfig.getInitialWidth("background"), imgConfig.getInitialHeight("background"));
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
                case ESCAPE:
                    scaler(backgroundImage, idBG);
                    scaler(playerImage, idPL);
                    scaler(riverImage, idRV);
                    scaler(bridgeImage, idBR);
                    scaler(botTreeImage, idTR);
                    scaler(topTreeImage, idTR);
                    scaler(saloonImage, idSL);
                    scaler(houseImage, idHS);
                    scaler(menuImage, idMN);
                    scaler(popUpSImage, idPU);
                    scaler(popUpHImage, idPU);
                    scaler(blackBandL, idBB);
                    scaler(blackBandR, idBB);
                    stepSize = scaler(stepSize);
                    speed = scaler(speed);
                    primaryStage.setFullScreen(fs);
                    scalerAI(sellerFoodImage, idSF, pathTransitionFood, faf, "sellerFood");
                    scalerAI(sellerCradleImage, idSC, pathTransitionCradle, fac, "sellerCradle");
                    fs = !fs;
                    break;
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
                    break;
                case T: // test
                    if(!faf){
                        animAI("sellerFood", 0, 100);
                        // layout.getChildren().remove(sellerFoodImage);
                        faf = !faf;
                    }
                    if(!fac){
                        // animAI("sellerCradles");
                        // faf = !fac;
                    }
                    break;
                case Q: // test
                    if(fs) animAI("sellerFood", 0, 200);
                    else animAI("sellerFood", posMap(0, !fs, 'x'), posMap(200, !fs, 'y'));
                    break;
                case R: // test
                    animAI("sellerFood", 100, 300);
                    break;
                default:
                    break;
                }
            }
            
        });

        /*      CREATE POP UP       */
        imgSet(popUpSImage, backgroundImage.getLayoutX() + 0.1*backgroundImage.getFitWidth(), 
            backgroundImage.getLayoutY() + 0.1*backgroundImage.getFitHeight(), "popUp");
        imgSet(popUpHImage, backgroundImage.getLayoutX() + 0.1*backgroundImage.getFitWidth(), 
            backgroundImage.getLayoutY() + 0.1*backgroundImage.getFitHeight(), "popUp");        
    }

    /*      FULLSCREEN FUNCTIONS        */

    private void scaler(ImageView img, ImgDims id) {
        if(fs){
            img.setFitHeight(id.getFH());
            img.setFitWidth(id.getFW());
        }
        else{
            img.setFitHeight(id.getIH());
            img.setFitWidth(id.getIW());
        }
        img.setLayoutX( posMap(img.getLayoutX(), id, 'x') );
        img.setLayoutY( posMap(img.getLayoutY(), id, 'y') );
    }

    private void scalerAI(ImageView img, ImgDims id, PathTransition pathTransition, boolean moving, String name) {

        if(fs){
            img.setFitHeight(id.getFH());
            img.setFitWidth(id.getFW());
        }
        else{
            img.setFitHeight(id.getIH());
            img.setFitWidth(id.getIW());
        }

        img.setLayoutX( posMapNoBSW(img.getLayoutX(), id) );
        img.setLayoutY( posMap(img.getLayoutY(), id, 'y') );
        
        img.setTranslateX( posMap(img.getTranslateX(), id, 'x') );
        img.setTranslateY( posMap(img.getTranslateY(), id, 'y') );

        if(moving) {
            pathTransition.stop();

            double destX = 0;
            double destY = 0;

            switch(name){
            case "sellerFood":
                destX = destXFood;
                destY = destYFood;
                break;
            case "sellerCradle":
                destX = destXCradle;
                destY = destYCradle;
                break;
            default:
                break;
            }
    
            destX = posMap(destX, fs, 'x');
            destY = posMap(destY, fs, 'y');

            animAI(name, destX, destY);
        }
    }

    private void scaler(Rectangle img, ImgDims id) {
        if(fs){
            img.setHeight(id.getFH());
            img.setWidth(id.getFW());
        }
        else{
            img.setHeight(id.getIH());
            img.setWidth(id.getIW());
        }
        img.setLayoutX( posMap(img.getLayoutX(), id, 'x') );
        img.setLayoutY( posMap(img.getLayoutY(), id, 'y') );
    }

    private double scaler(double val) {
        if(fs) return val * imgConfig.getRFS();
        else return val / imgConfig.getRFS();
    }

    private void scaler(ImageView img[], ImgDims id) {
        for(int i = 0; i < img.length; i++){
            if(fs){
                img[i].setFitHeight(id.getFH());
                img[i].setFitWidth(id.getFW());
            }
            else{
                img[i].setFitHeight(id.getIH());
                img[i].setFitWidth(id.getIW());
            }
            img[i].setLayoutX( posMap(img[i].getLayoutX(), id, 'x') );
            img[i].setLayoutY( posMap(img[i].getLayoutY(), id, 'y') );
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

    private void imgSet(ImageView imageView, ImgPos imgPos, String name){
        imageView.setFitHeight(imgConfig.getInitialHeight(name));
        imageView.setFitWidth(imgConfig.getInitialWidth(name));
        imageView.relocate(imgPos.getPosX(0), imgPos.getPosY(0)); 
    }

    private void imgSet(ImageView imageView, double posX, double posY, String name){
        imageView.setFitHeight(imgConfig.getInitialHeight(name));
        imageView.setFitWidth(imgConfig.getInitialWidth(name));
        imageView.relocate(posX, posY); 
    }

    private void imgSet(ImageView[] imageView, ImgPos imgPos, String name){
        for(int i = 0; i < imageView.length; i++) {
            imageView[i] = new ImageView(tree);
            imageView[i].setFitHeight(imgConfig.getInitialHeight(name));
            imageView[i].setFitWidth(imgConfig.getInitialWidth(name));
            imageView[i].relocate(imgPos.getPosX(i), imgPos.getPosY(i));
        }
    }

    private void animAI(String name, double destX, double destY){
        switch(name) {
        case "sellerFood":
            moveAI(sellerFoodImage, destX, destY);
            destXFood = destX;
            destYFood = destY;
            break;
        case "sellerCradle":
            moveAI(sellerCradleImage, destX, destY);
            destXCradle = destX;
            destYCradle = destY;
            break;
        default:
            break;
        }
    }

    private void moveAI(ImageView aiImage, double destX, double destY){
        double posX = destX-aiImage.getLayoutX()+aiImage.getFitWidth()/2;
        double posY = destY-aiImage.getLayoutY()+aiImage.getFitHeight()/2;
        MoveTo moveToFood = new MoveTo(aiImage.getTranslateX()+aiImage.getFitWidth()/2, aiImage.getTranslateY()+aiImage.getFitHeight()/2);
        LineTo lineToFood = new LineTo(posX, posY);

        double dX = destX-(aiImage.getLayoutX() + aiImage.getTranslateX());
        double dY = destY-(aiImage.getLayoutY() + aiImage.getTranslateY());

        Path path = new Path();
        path.getElements().add(moveToFood);
        path.getElements().add(lineToFood);

        double time = Math.sqrt(dX*dX + dY*dY) / speed;
        pathTransitionFood.setDuration(Duration.millis(time));
        pathTransitionFood.setNode(aiImage);
        pathTransitionFood.setPath(path);
        pathTransitionFood.play();
    }

    private double posMap(double val, boolean size, char axis){
        switch(axis){
        case 'x':
            if(size){ // X small -> X full
                return imgConfig.getRFS() * (val) + imgConfig.getBSW();
            }
            else{ // X full -> X small
                return (val - imgConfig.getBSW()) / imgConfig.getRFS();
            }
        case 'y':
            if(size){ // Y small -> Y full
                return imgConfig.getRFS() * (val);
            }
            else{ // Y full -> Y small
                return (val) / imgConfig.getRFS();
            }
        default:
            return 0;
        }
    }

    private double posMap(double val, ImgDims id, char axis){
        switch(axis){
        case 'x':
            if(fs){ // X small -> X full
                return imgConfig.getRFS() * (val + id.getIW()/2) - id.getFW()/2 + imgConfig.getBSW();
            }
            else{ // X full -> X small
                return (val + id.getFW()/2 - imgConfig.getBSW()) / imgConfig.getRFS() -id.getIW()/2;
            }
        case 'y':
            if(fs){ // Y small -> Y full
                return imgConfig.getRFS() * (val + id.getIH()/2) - id.getFH()/2;
            }
            else{ // Y full -> Y small
                return (val + id.getFH()/2) / imgConfig.getRFS() - id.getIH()/2;
            }
        default:
            return 0;
        }
    }

    private double posMapNoBSW(double val, ImgDims id){
        if(fs){ // X small -> X full
            return imgConfig.getRFS() * (val + id.getIW()/2) - id.getFW()/2;
        }
        else{ // X full -> X small
            return (val + id.getFW()/2) / imgConfig.getRFS() -id.getIW()/2;
        }
    }

    // public static void delay(long millis, Runnable continuation) {
    //     Task<Void> sleeper = new Task<Void>() {
    //         @Override
    //         protected Void call() throws Exception {
    //             try { Thread.sleep(millis); }
    //             catch (InterruptedException e) { }
    //             return null;
    //         }
    //     };
    //     sleeper.setOnSucceeded(event -> continuation.run());
    //     new Thread(sleeper).start();
    //   }

    // delay(2000, () -> faf = false);
    
}