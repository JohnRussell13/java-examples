package com.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private GridPane grid = new GridPane();
    private final TextField username = new TextField();
    private Button submit = new Button("Posalji");
    private Label errormsg = new Label();
    private Label title = new Label();
    private Button swap = new Button("Promeni listu");

    private List<CheckBox> checkboxes = new ArrayList<CheckBox>();
    private Button delete = new Button("Obrisi");
    private GridPane gridCB = new GridPane();

    private String name;
    private List<Integer> missing = new ArrayList<Integer>();
    private List<Integer> doubles = new ArrayList<Integer>();
    private List<Integer> req1 = new ArrayList<Integer>();
    private List<Integer> req2 = new ArrayList<Integer>();
    private List<List<Integer>> requests1 = new ArrayList<>();
    private List<List<Integer>> requests2 = new ArrayList<>();
    private List<String> requestsN = new ArrayList<>();

    private Boolean swapFlag = true;

    private Separator separator0 = new Separator();
    private Separator separator1 = new Separator();
    private Button ask = new Button("Moguce razmene");
    private Button sw2 = new Button("Trguj");
    private boolean flsw2 = true;
    private Label you = new Label();
    private Label him = new Label();
    private GridPane gridCB2 = new GridPane();
    private GridPane gridCB3 = new GridPane();
    private List<CheckBox> checkboxes2 = new ArrayList<CheckBox>();
    private List<CheckBox> checkboxes3 = new ArrayList<CheckBox>();
    private Button sw3 = new Button("Ponude");
    private boolean flsw3 = true;
    private Button transf = new Button("Posalji");
    private GridPane gridLB = new GridPane();
    private List<Label> lables = new ArrayList<Label>();
    // private List<Button> btnsA = new ArrayList<Button>();
    // private List<Button> btnsD = new ArrayList<Button>();
    private List<RadioButton> rdbtns = new ArrayList<RadioButton>();
    private ToggleGroup tggrp = new ToggleGroup();
    private Button btnA = new Button("Prihvatam");
    private Button btnD = new Button("Odbijam");

    private String[] names;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Transfer");
        
        //Creating a GridPane container
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //Defining the Name text field
        username.setPromptText("Unesi username.");
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
        title.setText("Logovanje");
        GridPane.setConstraints(title, 0, 0);
        grid.getChildren().add(title);

        // you.setText("Ti");
        // him.setText("Ona");

        btnD.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");   
        
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

        sw2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                swapScreen();
            }
        });

        sw3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                swapScreen2();
            }
        });

        ask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                query();
            }
        });

        transf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transfer();
            }
        });

        // tggrp.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
        //     int x = Integer.parseInt(((RadioButton)newVal).getText());
        //     System.out.println(x + " was selected");
        // });

        btnA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnA();
            }
        });

        btnD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnD();
            }
        });
        
    }


    public static void main(String[] args) {
        launch();
    }


    private void setGridCB(){
        checkboxes.clear();
        if(swapFlag){
            int width = ((int)Math.ceil(Math.sqrt(missing.size())));
    
            for(int i = 0; i < missing.size(); i++){
                CheckBox checkbox = new CheckBox(Integer.toString(missing.get(i)));
                checkboxes.add(checkbox);
                GridPane.setConstraints(checkboxes.get(i), i%width, i/width);
                gridCB.getChildren().add(checkboxes.get(i));
            }
        }
        else{
            int width = ((int)Math.ceil(Math.sqrt(doubles.size())));
    
            for(int i = 0; i < doubles.size(); i++){
                CheckBox checkbox = new CheckBox(Integer.toString(doubles.get(i)));
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
            title.setText("Slicice koje ti nedostaju");
        } 
        else{
            title.setText("Duplikati");
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

        GridPane.setConstraints(sw2, 3, 0);
        grid.getChildren().add(sw2);

        GridPane.setConstraints(sw3, 4, 0);
        grid.getChildren().add(sw3);
    }

    private void swapScreen(){
        gridCB.getChildren().clear();
        grid.getChildren().clear();
        flsw2 = !flsw2;

        if(flsw2){
            sw2.setText("Trguj");
            swapList();
        } 
        else{
            sw2.setText("Menjaj stanje");

            freshCB();
    
            GridPane.setConstraints(cbUsers, 0, 0);
            grid.getChildren().add(cbUsers);
            GridPane.setConstraints(ask, 1, 0);
            grid.getChildren().add(ask);
            GridPane.setConstraints(sw2, 2, 0);
            grid.getChildren().add(sw2);
        }
    } 

    private void swapScreen2(){
        gridCB.getChildren().clear();
        grid.getChildren().clear();
        flsw3 = !flsw3;

        if(flsw3){
            sw3.setText("Ponude");
            swapList();
        } 
        else{
            sw3.setText("Menjaj stanje");

            setGridLB();
            GridPane.setConstraints(gridLB, 0, 1);
            grid.getChildren().add(gridLB);
            
            GridPane.setConstraints(sw3, 0, 0);
            grid.getChildren().add(sw3);

            GridPane.setConstraints(btnA, 0, 2);
            grid.getChildren().add(btnA);

            GridPane.setConstraints(btnD, 1, 2);
            grid.getChildren().add(btnD);
        }
    }

    private void btnA(){
        int x = Integer.parseInt(((RadioButton)tggrp.getSelectedToggle()).getText());
        String msg = "T: " + requestsN.get(x) + ":";
        for(int r : requests1.get(x)){
            msg += " " + r;
        }
        msg += ":";
        for(int r : requests2.get(x)){
            msg += " " + r;
        }

        System.out.println(msg);

        pw.println(msg);

        requests1.remove(x);
        requests2.remove(x);
        requestsN.remove(x);

        flsw3 = !flsw3;
        swapScreen2();
    }

    private void btnD(){
        int x = Integer.parseInt(((RadioButton)tggrp.getSelectedToggle()).getText());
        requests1.remove(x);
        requests2.remove(x);
        requestsN.remove(x);

        flsw3 = !flsw3;
        swapScreen2();
    }

    private void setGridLB(){
        gridLB.getChildren().clear();
        lables.clear();
        rdbtns.clear();
        // btnsA.clear();
        // btnsD.clear();

        for(int i = 0; i < requestsN.size(); i++){
            String msg = " " + requestsN.get(i) + ": " + "(";

            for(int r : requests1.get(i)){
                msg += r + ", ";
            }
            msg = msg.substring(0, msg.length()-2);

            msg += ") <-> (";

            for(int r : requests2.get(i)){
                msg += r + ", ";
            }
            msg = msg.substring(0, msg.length()-2);

            msg += ")";

            Label lb = new Label(msg);
            lables.add(lb);
            RadioButton rd = new RadioButton(Integer.toString(i));
            rd.setToggleGroup(tggrp);
            rdbtns.add(rd);
            // Button bt = new Button("Prihvatam");
            // btnsA.add(bt);
            // Button btd = new Button("Odbijam");
            // btd.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");
            // btnsD.add(btd);

            GridPane.setConstraints(lables.get(i), 1, i);
            gridLB.getChildren().add(lables.get(i));
            GridPane.setConstraints(rdbtns.get(i), 0, i);
            gridLB.getChildren().add(rdbtns.get(i));

            // GridPane.setConstraints(btnsA.get(i), 2, i);
            // gridLB.getChildren().add(btnsA.get(i));
            // GridPane.setConstraints(btnsD.get(i), 3, i);
            // gridLB.getChildren().add(btnsD.get(i));
        }
    }

    public void setMissing(String[] miss){
        for(String m : miss){
            if (!m.equals("")) {
                missing.add(Integer.parseInt(m));
            }
        }
    }

    public void setDouble(String[] doubl){
        for(String d : doubl){
            if (!d.equals("")) {
                doubles.add(Integer.parseInt(d));
            }
        }
    }

    private void changeScene(){
        grid.getChildren().clear();

        title.setText("Slicice koje ti nedostaju");
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

        GridPane.setConstraints(sw2, 3, 0);
        grid.getChildren().add(sw2);

        GridPane.setConstraints(sw3, 4, 0);
        grid.getChildren().add(sw3);
    }

    private void delete(){
        List<Integer> deleteArray = new ArrayList<Integer>();
        if(swapFlag){
            for(int i = 0; i < missing.size(); i++){
                if(checkboxes.get(i).isSelected()){
                    deleteArray.add(missing.get(i));
                }
            }
        }
        else{
            for(int i = 0; i < doubles.size(); i++){
                if(checkboxes.get(i).isSelected()){
                    deleteArray.add(doubles.get(i));
                }
            }
        }
        gridCB.getChildren().clear();
        if(swapFlag){
            missing.removeAll(deleteArray);
        }
        else{
            doubles.removeAll(deleteArray);
        }
        sendData();
        setGridCB();
    }

    void sendData(){
        String msg = "";
        if(swapFlag){
            msg += "M:";
            for(int m : this.missing){
                msg += " " + m;
            }
        }
        else{
            msg += "D:";
            for(int d : this.doubles){
                msg += " " + d;
            }
        }

        this.pw.println(msg);
    }

    public void setNames(String[] imena){
        names = imena;
    }

    private void freshCB(){
        getCbUsers().getItems().clear();

        for (String n : names) {
            if (!n.equals("")) {
                cbUsers.getItems().add(n.trim());
            }
        }
    }

    public void setReq1(String[] q1){
        req1.clear();
        for(String q : q1){
            if (!q.equals("")) {
                req1.add(Integer.parseInt(q));
            }
        }
    }

    public void setReq2(String[] q2){
        req2.clear();
        for(String q : q2){
            if (!q.equals("")) {
                req2.add(Integer.parseInt(q));
            }
        }
    }
    
    private void query(){
        you.setText(name);
        him.setText(cbUsers.getValue());
        pw.println("Q: " + cbUsers.getValue());
        flsw2 = !flsw2;
        grid.getChildren().clear();
        swapScreen();

        GridPane.setConstraints(you, 0, 1);
        grid.getChildren().add(you);
        GridPane.setConstraints(him, 0, 2);
        grid.getChildren().add(him);

        gridCB2.getChildren().clear();
        gridCB3.getChildren().clear();

        setGridCB2();
        setGridCB3();

        GridPane.setConstraints(gridCB2, 2, 1);
        grid.getChildren().add(gridCB2);
        GridPane.setConstraints(gridCB3, 2, 2);
        grid.getChildren().add(gridCB3);
        
        GridPane.setConstraints(transf, 0, 3);
        grid.getChildren().add(transf);
    }

    private void setGridCB2(){
        checkboxes2.clear();

        for(int i = 0; i < req2.size(); i++){
            CheckBox checkbox2 = new CheckBox(Integer.toString(req2.get(i)));
            checkboxes2.add(checkbox2);
            GridPane.setConstraints(checkboxes2.get(i), i, 0);
            gridCB2.getChildren().add(checkboxes2.get(i));
        }
    }

    private void setGridCB3(){
        checkboxes3.clear();

        for(int i = 0; i < req1.size(); i++){
            CheckBox checkbox3 = new CheckBox(Integer.toString(req1.get(i)));
            checkboxes3.add(checkbox3);
            GridPane.setConstraints(checkboxes3.get(i), i, 0);
            gridCB3.getChildren().add(checkboxes3.get(i));
        }
    }

    private void transfer(){
        int x = 0;
        int y = 0;
        String msg = "R: " + him.getText() + ":";

        for(int i = 0; i < req2.size(); i++){
            if(checkboxes2.get(i).isSelected()){
                msg += " " + req2.get(i);
                x++;
            }
        }

        msg += ":";

        for(int i = 0; i < req1.size(); i++){
            if(checkboxes3.get(i).isSelected()){
                msg += " " + req1.get(i);
                y++;
            }
        }
        
        if(x == y && x > 0){
            this.pw.println(msg);
        }
    }

    public void setRRN(String OG){
        requestsN.add(OG);
    }

    public void setRR1(String[] rr){
        List<Integer> rq = new ArrayList<Integer>();
        for(String r : rr){
            if (!r.equals("")) {
                rq.add(Integer.parseInt(r));
            }
        }
        requests1.add(rq);
    }

    public void setRR2(String[] rr){
        List<Integer> rq = new ArrayList<Integer>();
        for(String r : rr){
            if (!r.equals("")) {
                rq.add(Integer.parseInt(r));
            }
        }
        requests2.add(rq);
    }


    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private ReceiveMessageFromServer rmfs;

    private ComboBox<String> cbUsers = new ComboBox<>();
    private TextArea taReceivedMessages;

    //getteri i setteri za komponente koje ce nam trebati u ReceiveMessageFromServer klasi
    
    public void setTaReceivedMessages(String poruka) {
        taReceivedMessages.appendText(poruka + "\n");
    }

    public ComboBox<String> getCbUsers() {
        return cbUsers;
    }
    
    
    public BufferedReader getBr() {
        return br;
    }
    
    public Socket getSoc() {
        return socket;
    }

    /*
    Kada se klikne na dugme "Konektuj se" poziva se ova metoda
    */
    private void login() {
        try {
            if (!this.username.getText().equals("")) {
                name = username.getText();
                //Kreiraj novi socket (ako nije localhost, treba promeniti IP adresu)
                this.socket = new Socket("3.84.15.4", 6001);
                //napravi BufferedReader i PrintWriter kako bi slao i primao poruke
                this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
                //za prijem poruka od servera (stizace asinhrono) koristi poseban thread
                //da bismo u novom thread-u mogli da menjamo sadrzaj komponenti (npr Combo Box-a)
                //konstruktoru novog thread-a se prosledjuje this
                this.rmfs = new ReceiveMessageFromServer(this);
                Thread thr = new Thread(rmfs);
                thr.start();
                this.pw.println(name);
                changeScene();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }   
    }
}