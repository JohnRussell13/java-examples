package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgPos {
    private float bridgeX;
    private float bridgeY;

    private float[] botForX;
    private float[] botForY;

    private float[] topForX;
    private float[] topForY;

    private int botTreeCount;
    private int topTreeCount;

    public ImgPos(){
        File bridgePos = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/bridge.txt");
        try {
            Scanner scanIn = new Scanner(bridgePos);
            bridgeX = Float.parseFloat(scanIn.nextLine());
            bridgeY = Float.parseFloat(scanIn.nextLine());
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }

        // GET THE NUMBER OF THREES AND INIT ARRAY
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/botForest.txt"));
            while (reader.readLine() != null) botTreeCount++;
            reader.close();
        } catch(Exception e) {
            e.getStackTrace();
        }

        botTreeCount = botTreeCount / 2;

        botForX = new float[botTreeCount];
        botForY = new float[botTreeCount];

        File botForPos = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/botForest.txt");
        try {
            Scanner scanIn = new Scanner(botForPos);
            for(int i = 0; i < botTreeCount; i++) {
                botForX[i] = Float.parseFloat(scanIn.nextLine());
                botForY[i] = Float.parseFloat(scanIn.nextLine());
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }

        // GET THE NUMBER OF THREES AND INIT ARRAY
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/topForest.txt"));
            while (reader.readLine() != null) topTreeCount++;
            reader.close();
        } catch(Exception e) {
            e.getStackTrace();
        }

        topTreeCount = topTreeCount / 2;

        topForX = new float[topTreeCount];
        topForY = new float[topTreeCount];

        File topForPos = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/topForest.txt");
        try {
            Scanner scanIn = new Scanner(topForPos);
            for(int i = 0; i < topTreeCount; i++) {
                topForX[i] = Float.parseFloat(scanIn.nextLine());
                topForY[i] = Float.parseFloat(scanIn.nextLine());
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public float getBRX() {return bridgeX;}
    public float getBRY() {return bridgeY;}

    public float getBFX(int pos) {return botForX[pos];}
    public float getBFY(int pos) {return botForY[pos];}

    public float getTFX(int pos) {return topForX[pos];}
    public float getTFY(int pos) {return topForY[pos];}

    public int getBC() {return botTreeCount;}
    public int getTC() {return topTreeCount;}
}
