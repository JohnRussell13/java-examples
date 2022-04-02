package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgConfig {
    private double initHeightBG;
    private double initWidthBG;
    private double initHeightPL;
    private double initWidthPL;
    private double initHeightRV;
    private double initWidthRV;
    private double initHeightTR;
    private double initWidthTR;
    private double initHeightBR;
    private double initWidthBR;
    private double initHeightSL;
    private double initWidthSL;
    private double initHeightHS;
    private double initWidthHS;
    private double initHeightMN;
    private double initWidthMN;
    private double initHeightPU;
    private double initWidthPU;
    private double initHeightSF;
    private double initWidthSF;
    private double initHeightSC;
    private double initWidthSC;
    private double initHeightWR;
    private double initWidthWR;

    private double ratioFS;
    private double blackStripWidth;
    
    public ImgConfig() {
        /*      POSITION FILES      */
    
        File config = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/config.txt");

        double maxWidth;
        double fullHeightBG;
        try {
            Scanner scanIn = new Scanner(config);
            initHeightBG = Double.parseDouble(scanIn.nextLine());
            initWidthBG = Double.parseDouble(scanIn.nextLine());
            fullHeightBG = Double.parseDouble(scanIn.nextLine());
            maxWidth = Double.parseDouble(scanIn.nextLine());
            initHeightPL = Double.parseDouble(scanIn.nextLine());
            initWidthPL = Double.parseDouble(scanIn.nextLine());
            initHeightRV = Double.parseDouble(scanIn.nextLine());
            initWidthRV = Double.parseDouble(scanIn.nextLine());
            initHeightTR = Double.parseDouble(scanIn.nextLine());
            initWidthTR = Double.parseDouble(scanIn.nextLine());
            initHeightBR = Double.parseDouble(scanIn.nextLine());
            initWidthBR = Double.parseDouble(scanIn.nextLine());
            initHeightSL = Double.parseDouble(scanIn.nextLine());
            initWidthSL = Double.parseDouble(scanIn.nextLine());
            initHeightHS = Double.parseDouble(scanIn.nextLine());
            initWidthHS = Double.parseDouble(scanIn.nextLine());
            initHeightMN = Double.parseDouble(scanIn.nextLine());
            initWidthMN = Double.parseDouble(scanIn.nextLine());
            initHeightSF = Double.parseDouble(scanIn.nextLine());
            initWidthSF = Double.parseDouble(scanIn.nextLine());
            initHeightSC = Double.parseDouble(scanIn.nextLine());
            initWidthSC = Double.parseDouble(scanIn.nextLine());
            initHeightWR = Double.parseDouble(scanIn.nextLine());
            initWidthWR = Double.parseDouble(scanIn.nextLine());
            scanIn.close();

            initHeightPU = 0.8*initHeightBG;
            initWidthPU = 0.8*initWidthBG;

            ratioFS = fullHeightBG / initHeightBG;
            blackStripWidth = (maxWidth - ratioFS*initWidthBG)/2;
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public double getInitialHeight(String name) {
        switch (name){
        case "background":
            return initHeightBG;
        case "player":
            return initHeightPL;
        case "river":
            return initHeightRV;
        case "tree":
            return initHeightTR;
        case "bridge":
            return initHeightBR;
        case "saloon":
            return initHeightSL;
        case "house":
            return initHeightHS;
        case "menu":
            return initHeightMN;
        case "popUp":
            return initHeightPU;
        case "sellerFood":
            return initHeightSF;
        case "sellerCradle":
            return initHeightSC;
        case "work":
            return initHeightWR;
        default:
            return 0;
        }
    }

    public double getInitialWidth(String name) {
    switch (name){
        case "background":
            return initWidthBG;
        case "player":
            return initWidthPL;
        case "river":
            return initWidthRV;
        case "tree":
            return initWidthTR;
        case "bridge":
            return initWidthBR;
        case "saloon":
            return initWidthSL;
        case "house":
            return initWidthHS;
        case "menu":
            return initWidthMN;
        case "popUp":
            return initWidthPU;
        case "sellerFood":
            return initWidthSF;
        case "sellerCradle":
            return initWidthSC;
        case "work":
            return initWidthWR;
        default:
            return 0;
        }
    }

    public double getRFS() {return ratioFS;}
    public double getBSW() {return blackStripWidth;}
    
}
