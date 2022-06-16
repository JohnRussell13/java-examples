package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class App
{
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private ReceiveMessageFromServer rmfs;

    public static void main( String[] args )
    {
        App client = new App();
        client.login("Peter");

        String str = "";
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(System.in));
        while(!str.equals("stop")){
            try{
                str = bufRead.readLine();
                client.pw.println(str);
            } catch (Exception ex){
                System.out.println(ex);
            }
        }
    }

    public BufferedReader getBr() {
        return br;
    }

    private void login(String username) {
        try {
            if (!username.equals("")) {
                // Change IP if needed
                this.socket = new Socket("54.152.119.193", 6001);
                this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
                this.rmfs = new ReceiveMessageFromServer(this);
                Thread thr = new Thread(rmfs);
                thr.start();
                this.pw.println(username);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }   
    }
}
