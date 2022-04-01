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
            scanIn.close();

            initHeightPU = 0.8*initHeightBG;
            initWidthPU = 0.8*initWidthBG;

            ratioFS = fullHeightBG / initHeightBG;
            blackStripWidth = (maxWidth - ratioFS*initWidthBG)/2;
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public double getIHBG() {return initHeightBG;}
    public double getIWBG() {return initWidthBG;}
    public double getIHPL() {return initHeightPL;}
    public double getIWPL() {return initWidthPL;}
    public double getIHRV() {return initHeightRV;}
    public double getIWRV() {return initWidthRV;}
    public double getIHTR() {return initHeightTR;}
    public double getIWTR() {return initWidthTR;}
    public double getIHBR() {return initHeightBR;}
    public double getIWBR() {return initWidthBR;}
    public double getIHSL() {return initHeightSL;}
    public double getIWSL() {return initWidthSL;}
    public double getIHHS() {return initHeightHS;}
    public double getIWHS() {return initWidthHS;}
    public double getIHMN() {return initHeightMN;}
    public double getIWMN() {return initWidthMN;}

    public double getIHPU() {return initHeightPU;}
    public double getIWPU() {return initWidthPU;}

    public double getRFS() {return ratioFS;}
    public double getBSW() {return blackStripWidth;}
    
}
