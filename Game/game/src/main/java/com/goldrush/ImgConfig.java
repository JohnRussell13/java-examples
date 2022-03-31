package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgConfig {
    private float initHeightBG;
    private float initWidthBG;
    private float initHeightPL;
    private float initWidthPL;
    private float initHeightRV;
    private float initWidthRV;
    private float initHeightTR;
    private float initWidthTR;
    private float initHeightBR;
    private float initWidthBR;

    private float ratioFS;
    private float blackStripWidth;
    
    public ImgConfig() {
        /*      POSITION FILES      */
    
        File config = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/config.txt");

        float maxWidth;
        float fullHeightBG;
        try {
            Scanner scanIn = new Scanner(config);
            initHeightBG = Float.parseFloat(scanIn.nextLine());
            initWidthBG = Float.parseFloat(scanIn.nextLine());
            fullHeightBG = Float.parseFloat(scanIn.nextLine());
            maxWidth = Float.parseFloat(scanIn.nextLine());
            initHeightPL = Float.parseFloat(scanIn.nextLine());
            initWidthPL = Float.parseFloat(scanIn.nextLine());
            initHeightRV = Float.parseFloat(scanIn.nextLine());
            initWidthRV = Float.parseFloat(scanIn.nextLine());
            initHeightTR = Float.parseFloat(scanIn.nextLine());
            initWidthTR = Float.parseFloat(scanIn.nextLine());
            initHeightBR = Float.parseFloat(scanIn.nextLine());
            initWidthBR = Float.parseFloat(scanIn.nextLine());
            scanIn.close();

            ratioFS = fullHeightBG / initHeightBG;
            blackStripWidth = (maxWidth - ratioFS*initWidthBG)/2;
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public float getIHBG() {return initHeightBG;}
    public float getIWBG() {return initWidthBG;}
    public float getIHPL() {return initHeightPL;}
    public float getIWPL() {return initWidthPL;}
    public float getIHRV() {return initHeightRV;}
    public float getIWRV() {return initWidthRV;}
    public float getIHTR() {return initHeightTR;}
    public float getIWTR() {return initWidthTR;}
    public float getIHBR() {return initHeightBR;}
    public float getIWBR() {return initWidthBR;}

    public float getRFS() {return ratioFS;}
    public float getBSW() {return blackStripWidth;}
    
}
