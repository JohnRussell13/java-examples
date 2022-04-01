package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgPos {
    private double[] posX;
    private double[] posY;

    private int count;

    public ImgPos(String loc){
        // GET THE NUMBER OF BRIDGES AND INIT ARRAY
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/" + loc + ".txt"));
            while (reader.readLine() != null) count++;
            reader.close();
        } catch(Exception e) {
            e.getStackTrace();
        }

        count = count / 2;

        posX = new double[count];
        posY = new double[count];

        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/" + loc + ".txt");
        try {
            Scanner scanIn = new Scanner(file);
            for(int i = 0; i < count; i++) {
                posX[i] = Double.parseDouble(scanIn.nextLine());
                posY[i] = Double.parseDouble(scanIn.nextLine());
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public double getPosX(int pos) {return posX[pos];}
    public double getPosY(int pos) {return posY[pos];}

    public int getCount() {return count;}
}
