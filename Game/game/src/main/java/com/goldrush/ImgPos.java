package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgPos {
    private float[] posX;
    private float[] posY;

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

        posX = new float[count];
        posY = new float[count];

        File file = new File(System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/" + loc + ".txt");
        try {
            Scanner scanIn = new Scanner(file);
            for(int i = 0; i < count; i++) {
                posX[i] = Float.parseFloat(scanIn.nextLine());
                posY[i] = Float.parseFloat(scanIn.nextLine());
            }
            scanIn.close();
        } catch (Exception e) {
            System.out.println(e); 
        }
    }

    public float getPosX(int pos) {return posX[pos];}
    public float getPosY(int pos) {return posY[pos];}

    public int getCount() {return count;}
}
