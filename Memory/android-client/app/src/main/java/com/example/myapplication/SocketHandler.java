package com.example.myapplication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler {
    private static Socket socket;
    private static PrintWriter pw;
    private static BufferedReader br;
    private static int seed;
    private static int myID;
    private static int opID;
    private static int startID;
    private static boolean activityFlag;
    private static String myName;
    private static String opName;

    public static synchronized boolean getActivityFlag(){
        return activityFlag;
    }
    public static synchronized void setActivityFlag(boolean activityFlag){
        SocketHandler.activityFlag = activityFlag;
    }

    public static synchronized String getMyName(){
        return myName;
    }
    public static synchronized void setMyName(String myName){
        SocketHandler.myName = myName;
    }

    public static synchronized String getOpName(){
        return opName;
    }
    public static synchronized void setOpName(String opName){
        SocketHandler.opName = opName;
    }

    // testing if this works as well
    // probably better to send seed when in SecondActivity
    public static synchronized int getSeed(){
        return seed;
    }
    public static synchronized void setSeed(int seed){
        SocketHandler.seed = seed;
    }

    public static synchronized int getMyID(){
        return myID;
    }
    public static synchronized void setMyID(int myID){
        SocketHandler.myID = myID;
    }

    public static synchronized int getOpID(){
        return opID;
    }
    public static synchronized void setOpID(int opID){
        SocketHandler.opID = opID;
    }

    public static synchronized int getStartID(){
        return startID;
    }
    public static synchronized void setStartID(int startID){
        SocketHandler.startID = startID;
    }

    public static synchronized Socket getSocket(){
        return socket;
    }
    public static synchronized void setSocket(Socket socket){
        SocketHandler.socket = socket;
    }

    public static synchronized PrintWriter getPrintWriter(){
        return pw;
    }
    public static synchronized void setPrintWriter(PrintWriter pw){
        SocketHandler.pw = pw;
    }

    public static synchronized BufferedReader getBufferedReader(){
        return br;
    }
    public static synchronized void setBufferedReader(BufferedReader br){
        SocketHandler.br = br;
    }
}
