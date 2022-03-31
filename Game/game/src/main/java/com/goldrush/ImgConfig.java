package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgConfig {
    float initHeightBG;
    float initWidthBG;
    float fullHeightBG;
    float fullWidthBG;
    float initHeightPL;
    float initWidthPL;
    float initHeightRV;
    float initWidthRV;
    float initHeightTR;
    float initWidthTR;
    float initHeightBR;
    float initWidthBR;

    float ratioFS;
    float blackStripWidth;
    
    public ImgConfig() {
        /*      POSITION FILES      */
    
        File config = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/config.txt");

        float maxWidth;
        try {
            Scanner scanIn = new Scanner(config);
            initHeightBG = Integer.parseInt(scanIn.nextLine());
            initWidthBG = Integer.parseInt(scanIn.nextLine());
            fullHeightBG = Integer.parseInt(scanIn.nextLine());
            maxWidth = Integer.parseInt(scanIn.nextLine());
            initHeightPL = Integer.parseInt(scanIn.nextLine());
            initWidthPL = Integer.parseInt(scanIn.nextLine());
            initHeightRV = Integer.parseInt(scanIn.nextLine());
            initWidthRV = Integer.parseInt(scanIn.nextLine());
            initHeightTR = Integer.parseInt(scanIn.nextLine());
            initWidthTR = Integer.parseInt(scanIn.nextLine());
            initHeightBR = Integer.parseInt(scanIn.nextLine());
            initWidthBR = Integer.parseInt(scanIn.nextLine());
            scanIn.close();

            ratioFS = fullHeightBG / initHeightBG;
            blackStripWidth = (maxWidth - ratioFS*initWidthBG)/2;
        } catch (Exception e) {
            System.out.println(e); 
        }


    }

    public float getIHBG() {return initHeightBG;}
    public float getIWBG() {return initWidthBG;}
    public float getFHBG() {return fullHeightBG;}
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
