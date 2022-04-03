package com.goldrush;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    private Image playerLeft = new Image(getClass().getResource("playerLeft.png").toString(), true);
    private Image playerRight = new Image(getClass().getResource("playerRight.png").toString(), true);
    private Image playerUp = new Image(getClass().getResource("playerUp.png").toString(), true);
    private Image playerDown = new Image(getClass().getResource("playerDown.png").toString(), true);

    private Image sellerCradleLeft = new Image(getClass().getResource("sellerCradleLeft.png").toString(), true);
    private Image sellerCradleRight = new Image(getClass().getResource("sellerCradleRight.png").toString(), true);

    private Image sellerFoodLeft = new Image(getClass().getResource("sellerFoodLeft.png").toString(), true);
    private Image sellerFoodRight = new Image(getClass().getResource("sellerFoodRight.png").toString(), true);

    private Image background = new Image(getClass().getResource("background.png").toString(), true);
    private Image river = new Image(getClass().getResource("river.png").toString(), true);
    private Image tree = new Image(getClass().getResource("tree.png").toString(), true);
    private Image bridge = new Image(getClass().getResource("bridge.png").toString(), true);
    private Image saloon = new Image(getClass().getResource("saloon.png").toString(), true);
    private Image work = new Image(getClass().getResource("work.png").toString(), true);
    private Image house = new Image(getClass().getResource("house.png").toString(), true);
    private Image menu = new Image(getClass().getResource("menu.png").toString(), true);
    private Image popUp = new Image(getClass().getResource("popUp.png").toString(), true);


    /*      IMAGE CONFIGURATIONS        */

    private ImgConfig imgConfig = new ImgConfig();

    private ImgPos imgPosPlayer = new ImgPos("player");
    private ImgPos imgPosBridge = new ImgPos("bridge");
    private ImgPos imgPosRiver = new ImgPos("river");
    private ImgPos imgPosSaloon = new ImgPos("saloon");
    private ImgPos imgPosWork = new ImgPos("work");
    private ImgPos imgPosHouse = new ImgPos("house");
    private ImgPos imgPosMenu = new ImgPos("menu");
    private ImgPos imgPosBotFor = new ImgPos("botForest");
    private ImgPos imgPosTopFor = new ImgPos("topForest");

    private AnimPoints animPointsSellerFood = new AnimPoints("sellerFood");
    private AnimPoints animPointsSellerCradle = new AnimPoints("sellerCradle");

    private ImgDims idBG = new ImgDims(imgConfig.getInitialHeight("background"), imgConfig.getInitialWidth("background"), imgConfig.getRFS());
    private ImgDims idMN = new ImgDims(imgConfig.getInitialHeight("menu"), imgConfig.getInitialWidth("menu"), imgConfig.getRFS());
    private ImgDims idPL = new ImgDims(imgConfig.getInitialHeight("player"), imgConfig.getInitialWidth("player"), imgConfig.getRFS());
    private ImgDims idRV = new ImgDims(imgConfig.getInitialHeight("river"), imgConfig.getInitialWidth("river"), imgConfig.getRFS());
    private ImgDims idBR = new ImgDims(imgConfig.getInitialHeight("bridge"), imgConfig.getInitialWidth("bridge"), imgConfig.getRFS());
    private ImgDims idTR = new ImgDims(imgConfig.getInitialHeight("tree"), imgConfig.getInitialWidth("tree"), imgConfig.getRFS());
    private ImgDims idSL = new ImgDims(imgConfig.getInitialHeight("saloon"), imgConfig.getInitialWidth("saloon"), imgConfig.getRFS());
    private ImgDims idWR = new ImgDims(imgConfig.getInitialHeight("work"), imgConfig.getInitialWidth("work"), imgConfig.getRFS());
    private ImgDims idHS = new ImgDims(imgConfig.getInitialHeight("house"), imgConfig.getInitialWidth("house"), imgConfig.getRFS());
    private ImgDims idPU = new ImgDims(imgConfig.getInitialHeight("popUp"), imgConfig.getInitialWidth("popUp"), imgConfig.getRFS());

    private ImgDims idSF = new ImgDims(imgConfig.getInitialHeight("sellerFood"), imgConfig.getInitialWidth("sellerFood"), imgConfig.getRFS());
    private ImgDims idSC = new ImgDims(imgConfig.getInitialHeight("sellerCradle"), imgConfig.getInitialWidth("sellerCradle"), imgConfig.getRFS());

    private ImgDims idBB = new ImgDims(imgConfig.getInitialHeight("background"), imgConfig.getBSW(), imgConfig.getRFS());

    private boolean fs = true; // non-fullscreen flag
    private boolean fps = true; // non-popUpS flag
    private boolean fph = true; // non-popUpH flag
    private boolean fpw = true; // non-popUpW flag
    private boolean fp = true; // non-popUp flag
    private boolean fpm = false; // non-popUpM flag
    private boolean faf = false; // seller flag
    private boolean fac = false; // seller flag
    // private boolean fnf = false; // near seller flag
    // private boolean fnc = false; // near seller flag
    private boolean fsf = false; // selling seller flag
    private boolean fsc = false; // selling seller flag
    private boolean fsg = false; // selling seller flag
    private boolean fad = true; // AnimPoint direction flag
    private boolean fbc = false; // AnimPoint direction flag
    private boolean feg = false; // EndGame read from keyboard flag

    private int countAI = -1;

    private double stepSize = 5;
    private double speed = 0.1;

    /*      IMAGES      */
    private ImageView backgroundImage = new ImageView(background);
    private ImageView menuImage = new ImageView(menu);
    private ImageView playerImage = new ImageView(playerLeft); // initial orientation
    private ImageView riverImage = new ImageView(river);
    private ImageView bridgeImage = new ImageView(bridge);
    private ImageView saloonImage = new ImageView(saloon);
    private ImageView workImage = new ImageView(work);
    private ImageView houseImage = new ImageView(house);
    private Pane layout = new Pane();

    private ImageView popUpImage = new ImageView(popUp);

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

    private double popUpFontSize = 30;
    private double menuFontSize = 12;
    Text textPopUp = new Text();
    Text textMenu = new Text();

    private double blur = 2;

    private int gp_fsm = 0;
    private GoldRush game = new GoldRush();
    private int week = 1;
    private int foodPrice = 0;
    private int cradlePrice = 0;
    private boolean cmpltAnim = false;
    private boolean cmpltAnimB = false;
    private boolean cmpltAnimC = false;
    private boolean cmpltAnimD = false;

    private String location = "";

    private String msg = "";

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
        imgSet(workImage, imgPosWork, "work");
        imgSet(houseImage, imgPosHouse, "house");

        sellerFoodImage.setFitHeight(imgConfig.getInitialHeight("sellerFood"));
        sellerFoodImage.setFitWidth(imgConfig.getInitialWidth("sellerFood"));
        sellerFoodImage.relocate(-idSF.getIW(), 0.43*imgConfig.getInitialHeight("background"));

        sellerCradleImage.setFitHeight(imgConfig.getInitialHeight("sellerCradle"));
        sellerCradleImage.setFitWidth(imgConfig.getInitialWidth("sellerCradle"));
        sellerCradleImage.relocate(-idSC.getIW(), 0.43*imgConfig.getInitialHeight("background"));

        /*      SET TEXT        */
        textMenu.setTextAlignment(TextAlignment.LEFT);
        textMenu.setWrappingWidth(menuImage.getFitWidth()*0.8);
        textMenu.setX( menuImage.getLayoutX() + menuImage.getFitWidth()*0.5 - textMenu.getWrappingWidth()*0.5 );
        textMenu.setY( menuImage.getLayoutY() + menuImage.getFitHeight()*0.2 );
        textMenu.setLineSpacing(menuImage.getFitHeight()*0.05);
        textMenu.setEffect(new GaussianBlur(blur));
        // textMenu.setFont(Font.font(null, FontWeight.BOLD, menuFontSize));

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
        layout.getChildren().add(workImage);
        for(int i = 0; i < topTreeImage.length; i++) {
            layout.getChildren().add(topTreeImage[i]);
        }
        layout.getChildren().add(menuImage);
        layout.getChildren().add(textMenu);
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
                    playerImage.setImage(playerUp);
                    if(fp) go('W', botTreeImage, topTreeImage);
                    break;
                case S:
                    playerImage.setImage(playerDown);
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
                case M:
                    if(fbc){
                        cradlePrice++;
                        msg = "How many cradles do you want (M/N)?\n";
                        msg += "No. of new cradles ";
                        msg += cradlePrice;
                        msg += ".";
                        if(cmpltAnim || cmpltAnimD) {
                            msg += "\n\nPress K to navigate";
                            fpm = false;
                        }
                        textPopUp.setText(msg);
                    }
                    break;
                case N:
                    if(fbc){
                        if(cradlePrice > 0) cradlePrice--;
                        msg = "How many cradles do you want (M/N)?\n";
                        msg += "No. of new cradles ";
                        msg += cradlePrice;
                        msg += ".";
                        if(cmpltAnim || cmpltAnimD) {
                            msg += "\n\nPress K to navigate";
                            fpm = false;
                        }
                        textPopUp.setText(msg);
                    }
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
                    scaler(workImage, idWR);
                    scaler(houseImage, idHS);
                    scaler(menuImage, idMN);
                    scaler(popUpImage, idPU);
                    scaler(blackBandL, idBB);
                    scaler(blackBandR, idBB);
                    scaler(animPointsSellerCradle);
                    scaler(animPointsSellerFood);
                    stepSize = scaler(stepSize);
                    speed = scaler(speed);
                    blur = scaler(blur);
                    popUpFontSize = scaler(popUpFontSize);
                    scaler(textPopUp, popUpFontSize);
                    menuFontSize = scaler(menuFontSize);
                    scaler(textMenu, menuFontSize);
                    primaryStage.setFullScreen(fs);
                    scalerAIAP(sellerFoodImage, idSF, faf, pathTransitionFood, "sellerFood", animPointsSellerFood);
                    scalerAIAP(sellerCradleImage, idSC, fac, pathTransitionCradle, "sellerCradle", animPointsSellerCradle);
                    fs = !fs;
                    break;
                case K:
                    if(!fps) {
                        exitSaloon();
                        if(gp_fsm == 4){
                            location = "saloon";
                            gameplay();
                        }
                    }
                    else if(!fph) {
                        exitHouse();
                        if(gp_fsm == 4 || gp_fsm == 8){
                            location = "house";
                            gameplay();
                        }
                    }
                    else if(!fpw) {
                        exitWork();
                        if(gp_fsm == 4 || gp_fsm == 8 || gp_fsm == 12){
                            location = "work";
                            gameplay();
                        }
                    }
                    else if(!fpm) {
                        gameplay();
                    }
                    break;
                case Q:
                    game.saveGame(week);
                    Platform.exit();
                    break;
                default:
                    break;
                }
            }
            
        });

        /*      CREATE POP UP       */
        imgSet(popUpImage, backgroundImage.getLayoutX() + 0.1*backgroundImage.getFitWidth(), 
            backgroundImage.getLayoutY() + 0.1*backgroundImage.getFitHeight(), "popUp");

        /*      SET TEXT        */
        textPopUp.setTextAlignment(TextAlignment.CENTER);
        textPopUp.setWrappingWidth(popUpImage.getFitWidth()*0.8);
        textPopUp.setX( popUpImage.getLayoutX() + popUpImage.getFitWidth()*0.5 - textPopUp.getWrappingWidth()*0.5 );
        textPopUp.setY( popUpImage.getLayoutY() + popUpImage.getFitHeight()*0.2 );
        textPopUp.setFont(new Font(popUpFontSize));
        textPopUp.setLineSpacing(popUpImage.getFitHeight()*0.05);
        textPopUp.setEffect(new GaussianBlur(blur));
  
            

        /*          START GAMEPLAY          */

        gameplay();
    }

    private void gameplay(){
        switch(gp_fsm){
        case 0:
            makeFortyNiner();
            msg = "GOLD RUSH\n";
            msg += "In this game, you are an ol' timer - 49er!";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            layout.getChildren().add(popUpImage);
            layout.getChildren().add(textPopUp);
            fpm = false;
            fp = false;
            break;
        case 1:
            msg = "Every weekend you can go to the saloon, rest at home or fix the broken sluice.";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            break;
        case 2:
            msg = "You will also have to buy food, but you may buy cradles as well.";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            break;
        case 3:
            layout.getChildren().remove(popUpImage);
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            break;
        case 4:       
            switch(location) {
                case "house":
                    msg = "Even God rested on Sunday!";
                    msg+= "\n\nPress K to navigate";
                    break;
                case "work":
                    msg = "Work is priority number one!";
                    msg+= "\n\nPress K to navigate";
                    break;
                case "saloon":
                    msg = "Enjoy your night's out!\n";
                    msg+= "Have one for me!";
                    msg+= "\n\nPress K to navigate";
                    break;
                default:
                    msg = "";
                    break;
            }
            game.getFortyNiner().itIsSundayAgain(location);
            textPopUp.setText(msg);
            layout.getChildren().add(popUpImage);
            layout.getChildren().add(textPopUp);
            fpm = false;
            fp = false;
            break;
        case 5:
            layout.getChildren().remove(popUpImage);
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            foodComes();
            break;
        case 6:
            buyFood();
            break;
        case 7:
            foodGoes();
            break;
        case 8:
            cmpltAnimC = true;
            msg = "You can now buy some more of those sweet cradles.";
            if(cmpltAnimB) {
                msg += "\n\nPress K to navigate";
                fpm = false;
                fp = false;
            }
            else {
                fpm = true;
                fp = false;
            }
            textPopUp.setText(msg);
            layout.getChildren().add(popUpImage);
            layout.getChildren().add(textPopUp);
            break;
        case 9:
            layout.getChildren().remove(popUpImage);
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            cradleComes();
            break;
        case 10:
            buyCradles();
            break;
        case 11:
            cradleGoes();
            break;
        case 12:
            cmpltAnimC = true;
            msg = "You worked hard this week and some tools are now destroyed.";
            if(cmpltAnimB) {
                msg += "\n\nPress K to navigate";
                fpm = false;
                fp = false;
            }
            else {
                fpm = true;
                fp = false;
            }
            textPopUp.setText(msg);
            layout.getChildren().add(popUpImage);
            layout.getChildren().add(textPopUp);
            game.getFortyNiner().useTools();
            game.getFortyNiner().loseEndurance();
            break;
        case 13:
            layout.getChildren().remove(popUpImage);
            layout.getChildren().remove(textPopUp);
            fpm = true;
            fp = true;
            week++;
            msg = "It's weekend again!";
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            layout.getChildren().add(popUpImage);
            layout.getChildren().add(textPopUp);
            fpm = false;
            fp = false;

            if(week == 20) {
                endgame();
            }
            break;
        default:
            return;
        }
        menuDisplay();
        gp_fsm++;
        if(gp_fsm >= 14){
            gp_fsm = 3;
        }
    }

    private void endgame(){
        layout.getChildren().remove(popUpImage);
        layout.getChildren().remove(textPopUp);
        msg = "Congratulations!\n";
        msg += "You WON!\n";
        msg += "You survived 20 weeks in the Wild West!\n";
        msg += "Press Q to leave or\n";
        msg += "press K to continue..."; // not sure why, but K deals with it without custom flag
        textPopUp.setText(msg);
        layout.getChildren().add(popUpImage);
        layout.getChildren().add(textPopUp);
    }

    private void makeFortyNiner(){
        week = game.loadGame();
        week = game.survive();
    }

    private void foodComes(){
        fsf = true;
        faf = true;
        countAI = 0;
        fad = true;
        fsg = true;
        cmpltAnim = false;
        sellerFoodImage.setImage(sellerFoodRight);

        msg = "Food for this week will cost you $";
        msg += foodPrice;
        msg += ".";
        msg += "\n\nPress K to navigate";

        cmpltAnimD = false;
        animAIF("sellerFood", sellerFoodImage, pathTransitionFood, animPointsSellerFood);
    }

    private void buyFood(){
        foodPrice = game.getFortyNiner().buyFood();

        msg = "Hi!\n";
        msg+= "Food for this week will cost you $";
        msg+= foodPrice;
        msg+= ".";
        if(cmpltAnim) {
            msg += "\n\nPress K to navigate";
            fpm = false;
        }
        layout.getChildren().add(popUpImage);
        textPopUp.setText(msg);
        layout.getChildren().add(textPopUp);
    }

    private void foodGoes(){
        cmpltAnimB = false;
        layout.getChildren().remove(popUpImage);
        layout.getChildren().remove(textPopUp);
        fpm = true;
        fp = true;

        fsf = false;
        faf = true;
        fad = false;

        msg = "You worked hard this week and some tools are now destroyed.";
        msg += "\n\nPress K to navigate";

        cmpltAnimC = false;
        sellerFoodImage.setImage(sellerFoodLeft);
        animAIR("sellerFood", sellerFoodImage, pathTransitionFood, animPointsSellerFood);
    }

    private void cradleComes(){
        fsc = true;
        fac = true;
        countAI = 0;
        fad = true;
        fsg = true;
        cmpltAnim = false;
        sellerCradleImage.setImage(sellerCradleRight);

        msg = "How many cradles do you want?\n";
        msg += "No. of new cradles ";
        msg += cradlePrice;
        msg += ".";
        msg += "\n\nPress K to navigate";

        fbc = true;

        cmpltAnimD = false;
        animAIF("sellerCradle", sellerCradleImage, pathTransitionCradle, animPointsSellerCradle);
    }

    private void buyCradles(){
        // cradle = fortyNiner.buyCradle();

        msg = "How many cradles do you want (M/N)?\n";
        msg += "No. of new cradles ";
        msg += cradlePrice;
        msg += ".";
        if(cmpltAnim) {
            msg += "\n\nPress K to navigate";
            fpm = false;
        }
        layout.getChildren().add(popUpImage);
        textPopUp.setText(msg);
        layout.getChildren().add(textPopUp);
    }

    private void cradleGoes(){
        cmpltAnimB = false;
        layout.getChildren().remove(popUpImage);
        layout.getChildren().remove(textPopUp);
        fpm = true;
        fp = true;

        fsc = false;
        fac = true;
        fad = false;

        fbc = false;

        int newMoney = cradlePrice * 30;

        while(game.getFortyNiner().getMoney() < newMoney) {
            cradlePrice--;
            newMoney = cradlePrice * 30;
        }

        for(int item = 0; item < cradlePrice; item++) {
            Cradle cradle = new Cradle();
            game.getFortyNiner().setTools(cradle);
        }

        game.getFortyNiner().setMoney(game.getFortyNiner().getMoney() - newMoney);

        cradlePrice = 0;

        msg = "You worked hard this week and some tools are now destroyed.";
        msg += "\n\nPress K to navigate";

        cmpltAnimC = false;
        sellerCradleImage.setImage(sellerCradleLeft);
        animAIR("sellerCradle", sellerCradleImage, pathTransitionCradle, animPointsSellerCradle);
    }

    private void animAIF(String name, ImageView imageView, PathTransition pathTransition, AnimPoints animPoints){
        int localCAI = countAI;

        if(!fsg) {
            switch(name){
            case "sellerFood":
                fsf = false;
            break;
            case "sellerCradle":
                fsc = false;
            break;
            default:
                break;
            }
            cmpltAnimD = true;
            layout.getChildren().remove(textPopUp);
            msg = textPopUp.getText();
            msg += "\n\nPress K to navigate";
            textPopUp.setText(msg);
            layout.getChildren().add(textPopUp);

            fpm = false;
            return;
        }

        if(localCAI >= animPoints.getCount()) {
            cmpltAnim = true;
            fpm = false;
            return;
        }

        double dX = 0;
        double dY = 0;

        if(localCAI % 2 == 0) {
            dX = animPoints.getPos(localCAI);
        }
        else{
            dY = animPoints.getPos(localCAI);
        }

        double destX = dX + (imageView.getLayoutX() + imageView.getTranslateX());
        double destY = dY + (imageView.getLayoutY() + imageView.getTranslateY());

        switch(name) {
            case "sellerFood":
                destXFood = destX;
                destYFood = destY;
                break;
            case "sellerCradle":
                destXCradle = destX;
                destYCradle = destY;
                break;
            default:
                return;
            }

        double posX = destX-imageView.getLayoutX()+imageView.getFitWidth()/2;
        double posY = destY-imageView.getLayoutY()+imageView.getFitHeight()/2;
        MoveTo moveTo = new MoveTo(imageView.getTranslateX()+imageView.getFitWidth()/2, imageView.getTranslateY()+imageView.getFitHeight()/2);
        LineTo lineTo = new LineTo(posX, posY);

        Path path = new Path();
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);

        double time = Math.sqrt(dX*dX + dY*dY) / speed;
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);

        pathTransition.setOnFinished(event -> {
            countAI++;
            animAIF(name, imageView, pathTransition, animPoints);
        });

        pathTransition.play();
    }


    private void animAIR(String name, ImageView imageView, PathTransition pathTransition, AnimPoints animPoints){
        countAI--;

        if(countAI < 0) {
            cmpltAnimB = true;

            if(cmpltAnimC){
                layout.getChildren().remove(textPopUp);
                msg = textPopUp.getText();
                msg += "\n\nPress K to navigate";
                textPopUp.setText(msg);
                layout.getChildren().add(textPopUp);

                fpm = false;    
                fp = false;
            }

            switch(name){
                case "sellerFood":
                    faf = false;
                break;
                case "sellerCradle":
                    fac = false;
                break;
                default:
                    break;
                }
            return;
        }

        double dX = 0;
        double dY = 0;

        if(countAI % 2 == 0) {
            dX = -animPoints.getPos(countAI);
        }
        else{
            dY = -animPoints.getPos(countAI);
        }

        double destX = dX + (imageView.getLayoutX() + imageView.getTranslateX());
        double destY = dY + (imageView.getLayoutY() + imageView.getTranslateY());

        switch(name) {
            case "sellerFood":
                destXFood = destX;
                destYFood = destY;
                break;
            case "sellerCradle":
                destXCradle = destX;
                destYCradle = destY;
                break;
            default:
                return;
            }

        double posX = destX-imageView.getLayoutX()+imageView.getFitWidth()/2;
        double posY = destY-imageView.getLayoutY()+imageView.getFitHeight()/2;
        LineTo lineTo = new LineTo(posX, posY);
        MoveTo moveTo = new MoveTo(imageView.getTranslateX()+imageView.getFitWidth()/2, imageView.getTranslateY()+imageView.getFitHeight()/2);

        Path path = new Path();
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);

        double time = Math.sqrt(dX*dX + dY*dY) / speed;
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);

        pathTransition.setOnFinished(event -> {
            animAIR(name, imageView, pathTransition, animPoints);
        });

        pathTransition.play();
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

    private void scalerAIAP(ImageView img, ImgDims id, boolean move, PathTransition pathTransition, String name, AnimPoints animPoints) {

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

        if(move){
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

            if(fad) {
                animAIF(name, img, pathTransition, animPoints);
            }
            else {
                animAIR(name, img, pathTransition, animPoints);
            }
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

    private void scaler(Text text, double font) {
        text.setWrappingWidth( scaler(text.getWrappingWidth()) );
        text.setX( posMap(text.getX(), fs, 'x') );
        text.setY( posMap(text.getY(), fs, 'y') );
        text.setFont(new Font(font));
        text.setLineSpacing( scaler(text.getLineSpacing()) );
        text.setEffect(new GaussianBlur(blur));
    }

    private void scaler(AnimPoints animPoints) {
        if(fs){
            for(int i = 0; i < animPoints.getCount(); i++){
                animPoints.setPos(animPoints.getPos(i) * imgConfig.getRFS(), i);
            }
        }
        else{
            for(int i = 0; i < animPoints.getCount(); i++){
                animPoints.setPos(animPoints.getPos(i) / imgConfig.getRFS(), i);
            }
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

        if(riverImage.getLayoutX() - playerImage.getFitWidth()/2 < t1 
        && t1 < riverImage.getLayoutX() + riverImage.getFitWidth() + playerImage.getFitWidth()/2) {
            if( !(bridgeImage.getLayoutY() + 3*stepSize < t2 
            && t2 < bridgeImage.getLayoutY() + bridgeImage.getFitHeight()) ) {
                return;
            }
        }

        /*      NO HITS WITH SALOON, EXCEPT WITH DOORS      */

        if((saloonImage.getLayoutY() + 3*stepSize < t2 && t2 < saloonImage.getLayoutY() + saloonImage.getFitHeight())
        && saloonImage.getLayoutX() < t1 && t1 < saloonImage.getLayoutX() + saloonImage.getFitWidth()){
            if(saloonImage.getLayoutX() + 0.3*saloonImage.getFitWidth() < t1 
            && t1 < saloonImage.getLayoutX() + 0.7*saloonImage.getFitWidth()){
                enterSaloon();
            }
            return;
        }

        /*      ALLOWED HITS WITH WORK, BUT ENTER WORKPLACE     */

        if((workImage.getLayoutY() < t2 && t2 < workImage.getLayoutY() + workImage.getFitHeight())
        && workImage.getLayoutX() < t1 && t1 < workImage.getLayoutX() + workImage.getFitWidth()){
            enterWork();
        }

        /*      HOME HITS       */

        if((houseImage.getLayoutX() - 3*stepSize < t1 && t1 < houseImage.getLayoutX() + houseImage.getFitWidth() + 3*stepSize)
        && houseImage.getLayoutY() < t2 && t2 < houseImage.getLayoutY() + houseImage.getFitHeight() + 3*stepSize){
            if(houseImage.getLayoutY() + houseImage.getFitHeight() - 0.5*playerImage.getFitHeight() < t2 
            && t2 < houseImage.getLayoutY() + houseImage.getFitHeight()){
                enterHouse();
            }
            return;
        }

        /*      SELLER HITS     */

        double objectX = sellerFoodImage.getLayoutX() + sellerFoodImage.getTranslateX();
        double objectY = sellerFoodImage.getLayoutY() + sellerFoodImage.getTranslateY();

        if((objectX - 3*stepSize < t1 && t1 < objectX + sellerFoodImage.getFitWidth() + 3*stepSize)
        && objectY < t2 && t2 < objectY + sellerFoodImage.getFitHeight() + 3*stepSize){
            if(objectY + sellerFoodImage.getFitHeight() - 0.5*playerImage.getFitHeight() < t2 
            && t2 < objectY + sellerFoodImage.getFitHeight() + 0.5*playerImage.getFitHeight()
            && t1 > objectX + sellerFoodImage.getFitWidth()
            && fsf){
                fsg = false;
                fp = false;
                fpm = true;
                gameplay();
            }
            return;
        }

        objectX = sellerCradleImage.getLayoutX() + sellerCradleImage.getTranslateX();
        objectY = sellerCradleImage.getLayoutY() + sellerCradleImage.getTranslateY();

        if((objectX - 3*stepSize < t1 && t1 < objectX + sellerCradleImage.getFitWidth() + 3*stepSize)
        && objectY < t2 && t2 < objectY + sellerCradleImage.getFitHeight() + 3*stepSize){
            if(objectY + sellerCradleImage.getFitHeight() - 0.5*playerImage.getFitHeight() < t2 
            && t2 < objectY + sellerCradleImage.getFitHeight() + 0.5*playerImage.getFitHeight()
            && t1 > objectX + sellerCradleImage.getFitWidth()
            && fsc){
                fsg = false;
                fp = false;
                fpm = true;
                gameplay();
            }
            return;
        }

        playerImage.relocate(newX, newY);
    }

    private void enterSaloon(){
        layout.getChildren().add(popUpImage);
        textPopUp.setText("Welcome to the Saloon!\n\nPress K to navigate");
        layout.getChildren().add(textPopUp);
        fps = false;
        fp = false;
    }

    private void exitSaloon(){
        layout.getChildren().remove(textPopUp);
        layout.getChildren().remove(popUpImage);
        playerImage.setImage(playerDown); // turn down when exiting house
        fps = true;
        fp = true;
    }

    private void enterHouse(){
        layout.getChildren().add(popUpImage);
        textPopUp.setText("Welcome Home!\n\nPress K to navigate");
        layout.getChildren().add(textPopUp);
        fph = false;
        fp = false;
    }

    private void exitHouse(){
        layout.getChildren().remove(textPopUp);
        layout.getChildren().remove(popUpImage);
        playerImage.setImage(playerLeft); // turn left when exiting house
        fph = true;
        fp = true;
    }

    private void enterWork(){
        layout.getChildren().add(popUpImage);
        textPopUp.setText("This time next year, you'll be a millionaire!\n\nPress K to navigate");
        layout.getChildren().add(textPopUp);
        fpw = false;
        fp = false;
    }

    private void exitWork(){
        layout.getChildren().remove(textPopUp);
        layout.getChildren().remove(popUpImage);
        fpw = true;
        fp = true;
    }

    private void menuDisplay(){
        String msgs = "Week: " + week + "\n";
        msgs += "Stamina: " + game.getFortyNiner().getEndurance() + "%\n";
        msgs += "Sluice health: " + game.getFortyNiner().getTools().get(1).getDurability() + "%\n";
        msgs += "No. of cradles: " + (game.getFortyNiner().getTools().size() - 2) + "\n";
        msgs += "Money: $" + game.getFortyNiner().getMoney() + "\n";
        textMenu.setText(msgs);
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
        ImageView aiImage;
        PathTransition pathTransition;

        switch(name) {
        case "sellerFood":
            aiImage = sellerFoodImage;
            pathTransition = pathTransitionFood;
            destXFood = destX;
            destYFood = destY;
            break;
        case "sellerCradle":
            aiImage = sellerCradleImage;
            pathTransition = pathTransitionCradle;
            destXCradle = destX;
            destYCradle = destY;
            break;
        default:
            return;
        }

        double posX = destX-aiImage.getLayoutX()+aiImage.getFitWidth()/2;
        double posY = destY-aiImage.getLayoutY()+aiImage.getFitHeight()/2;
        MoveTo moveTo = new MoveTo(aiImage.getTranslateX()+aiImage.getFitWidth()/2, aiImage.getTranslateY()+aiImage.getFitHeight()/2);
        LineTo lineTo = new LineTo(posX, posY);

        double dX = destX-(aiImage.getLayoutX() + aiImage.getTranslateX());
        double dY = destY-(aiImage.getLayoutY() + aiImage.getTranslateY());

        Path path = new Path();
        path.getElements().add(moveTo);
        path.getElements().add(lineTo);

        double time = Math.sqrt(dX*dX + dY*dY) / speed;
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setNode(aiImage);
        pathTransition.setPath(path);

        pathTransition.play();

        pathTransition.setOnFinished(event -> {
            switch(name) {
            case "sellerFood":
                faf = false;
                break;
            case "sellerCradle":
                fac = false;
                break;
            default:
                return;
            }
        });
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

    // private void scalerAI(ImageView img, ImgDims id, boolean move, PathTransition pathTransition, String name) {

    //     if(fs){
    //         img.setFitHeight(id.getFH());
    //         img.setFitWidth(id.getFW());
    //     }
    //     else{
    //         img.setFitHeight(id.getIH());
    //         img.setFitWidth(id.getIW());
    //     }

    //     img.setLayoutX( posMapNoBSW(img.getLayoutX(), id) );
    //     img.setLayoutY( posMap(img.getLayoutY(), id, 'y') );
        
    //     img.setTranslateX( posMap(img.getTranslateX(), id, 'x') );
    //     img.setTranslateY( posMap(img.getTranslateY(), id, 'y') );

    //     if(move){
    //         pathTransition.stop();

    //         double destX = 0;
    //         double destY = 0;

    //         switch(name){
    //         case "sellerFood":
    //             destX = destXFood;
    //             destY = destYFood;
    //             break;
    //         case "sellerCradle":
    //             destX = destXCradle;
    //             destY = destYCradle;
    //             break;
    //         default:
    //             break;
    //         }

    //         destX = posMap(destX, fs, 'x');
    //         destY = posMap(destY, fs, 'y');

    //         animAI(name, destX, destY);
    //     }
    // }

    
}