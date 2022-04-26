package com.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectedClient implements Runnable {

    private Socket socket;
    private String userName;
    private BufferedReader br;
    private PrintWriter pw;
    private ArrayList<ConnectedClient> allClients;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ConnectedClient(Socket socket, ArrayList<ConnectedClient> allClients) {
        this.socket = socket;
        this.allClients = allClients;

        try {
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
            this.pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            
            this.userName = "";
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    void connectedClientsUpdateStatus() {
        String connectedUsers = "Users:";
        for (ConnectedClient c : this.allClients) {
            connectedUsers += " " + c.getUserName();
        }

        for (ConnectedClient allUpdateCB : this.allClients) {
            allUpdateCB.pw.println(connectedUsers);
        }

        System.out.println(connectedUsers);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.userName.equals("")) {
                    this.userName = this.br.readLine();
                    if (this.userName != null) {
                        System.out.println("Connected user: " + this.userName);
                        connectedClientsUpdateStatus();
                    } else {
                        System.out.println("Disconnected user: " + this.userName);
                        for (ConnectedClient cl : this.allClients) {
                            if (cl.getUserName().equals(this.userName)) {
                                this.allClients.remove(cl);
                                break;
                            }
                        }
                        connectedClientsUpdateStatus();
                        break;
                    }
                } else {
                    System.out.println("Waiting...");
                    String line = this.br.readLine();
                    System.out.println(line);
                    System.out.println("Recieved message.");
                    if (line != null) {
                        String[] informacija = line.split(": ");
                        String recUser = informacija[0].trim();
                        System.out.println(recUser);
                        String poruka = informacija[1];
                        System.out.println(poruka);

                        for (ConnectedClient clnt : this.allClients) {
                            if (clnt.getUserName().equals(recUser)) {
                                System.out.println(clnt.getUserName());
                                
                                clnt.pw.println(this.userName + ": " + poruka);
                                System.out.println(recUser + ": " + poruka);
                            } else {
                                if (recUser.equals("")) {
                                    this.pw.println("User " + recUser + " is not here!");
                                }
                            }
                        }

                    } else {
                        System.out.println("Disconnected user: " + this.userName);

                        Iterator<ConnectedClient> it = this.allClients.iterator();
                        while (it.hasNext()) {
                            if (it.next().getUserName().equals(this.userName)) {
                                it.remove();
                            }
                        }
                        connectedClientsUpdateStatus();

                        this.socket.close();
                        break;
                    }

                }
            } catch (Exception e) {
                System.out.println("Disconnected user: " + this.userName);
                
                for (ConnectedClient cl : this.allClients) {
                    if (cl.getUserName().equals(this.userName)) {
                        this.allClients.remove(cl);
                        connectedClientsUpdateStatus();
                        return;
                    }
                }
            }

        }
    }

}
