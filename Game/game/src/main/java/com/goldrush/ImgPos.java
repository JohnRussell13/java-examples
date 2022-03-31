package com.goldrush;

import java.io.*;
import java.util.*;

public class ImgPos {

    public ImgPos(){
        File bridgePos = new File("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/bridge.txt");
        File botForPos = new File("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/botForest.txt");
        File topForPos = new File("file://" + System.getProperty("user.dir") + "/src/main/resources/com/goldrush/pos/topForest.txt");
    }
}
