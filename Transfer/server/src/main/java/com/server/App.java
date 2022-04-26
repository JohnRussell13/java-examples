package com.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    private ServerSocket ssocket;
    private int port;
    private ArrayList<ConnectedClient> clients;


    public ServerSocket getSsocket() {
        return ssocket;
    }

    public void setSsocket(ServerSocket ssocket) {
        this.ssocket = ssocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void acceptClients() {
        Socket client = null;
        Thread thr;
        while (true) {
            try {
                System.out.println("Waiting for new clients..");
                client = this.ssocket.accept();
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            if (client != null) {
                ConnectedClient clnt = new ConnectedClient(client, clients);
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
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App server = new App(6666);
        
        System.out.println("Server running, listening on port 6666");
        server.acceptClients();
        
    }

}
