package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private ServerSocket ssocket;
    private int port;
    private ArrayList<ConnectedChatRoomClient> clients;

    public void acceptClients() {
        Socket client = null;
        Thread thr;
        while (true) {
            try {
                System.out.println("Waiting for new clients..");
                client = this.ssocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (client != null) {
                ConnectedChatRoomClient clnt = new ConnectedChatRoomClient(client, clients);
                clients.add(clnt);
                thr = new Thread(clnt);
                thr.start();
            } else {
            	break;
            }
        }
    }

    public App(int port) {
        this.clients = new ArrayList<>();
        try {
            this.port = port;
            this.ssocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        App server = new App(6001);
        System.out.println("Server running on port 6001");
        server.acceptClients();
    }

}