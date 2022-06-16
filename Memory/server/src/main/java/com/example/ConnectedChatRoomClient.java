package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectedChatRoomClient implements Runnable {

    private Socket socket;
    private String userName;
    private static ArrayList<Integer> playing = new ArrayList<Integer>();
    private BufferedReader br;
    private PrintWriter pw;
    private ArrayList<ConnectedChatRoomClient> allClients;

    public String getUserName() {
        return userName;
    }

    public ConnectedChatRoomClient(Socket socket, ArrayList<ConnectedChatRoomClient> allClients) {
        this.socket = socket;
        this.allClients = allClients;

        try {
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
            this.pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            this.userName = "";
        } catch (IOException ex) {
            Logger.getLogger(ConnectedChatRoomClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void connectedClientsUpdateStatus() {
        String idUser = "ID: ";
        String connectedUsers = "Users:";
        String lockedUsers = "Locked:";

        // int ind = 0;
        // for (ConnectedChatRoomClient cl : this.allClients) {
        //     if (cl.getUserName().equals(this.userName)) {
        //         idUser += Integer.toString(ind);
        //         cl.pw.println(idUser); // static ID
        //         break;
        //     }
        //     ind++;
        // }

        for (ConnectedChatRoomClient c : this.allClients) {
            connectedUsers += " " + c.getUserName();
        }

        for (Integer i : this.playing) {
            lockedUsers += " " + i.toString();
        }

        int ind = 0;
        for (ConnectedChatRoomClient everyoneUpdateCB : this.allClients) {
            idUser = "ID: " + Integer.toString(ind);
            ind++;
            everyoneUpdateCB.pw.println(idUser); // dynamic ID
            everyoneUpdateCB.pw.println(connectedUsers);
            everyoneUpdateCB.pw.println(lockedUsers);
        }

        // System.out.println(idUser);
        System.out.println(connectedUsers);
        System.out.println(lockedUsers);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.userName.equals("")) {
                    this.userName = this.br.readLine();
                    if (this.userName != null) {
                        System.out.println("Connected user: " + this.userName);
                        // generateValues();
                        connectedClientsUpdateStatus();

                        // sendData(this.allClients.size()-1);
                    } else {
                        System.out.println("Disconnected user: " + this.userName);
                        for (ConnectedChatRoomClient cl : this.allClients) {
                            if (cl.getUserName().equals(this.userName)) {
                                this.allClients.remove(cl);
                                break;
                            }
                        }
                        connectedClientsUpdateStatus();
                        break;
                    }
                } else {
                    System.out.println("Waiting for the message");
                    String line = this.br.readLine();
                    System.out.println(line);
                    System.out.println("Message arrived");
                    if (line != null) {
                        System.out.println("line not null");
                        String[] info = line.split(": ");

                        String type = info[0];
                        System.out.println(type);

                        if(type.equals("Q")){
                            String[] parts = info[1].split(" ");
                            int payload = Integer.parseInt(parts[0]);
                            int ind = 0;
                            for (ConnectedChatRoomClient cl : this.allClients) {
                                if (cl.getUserName().equals(this.userName)) {
                                    break;
                                }
                                ind++;
                            }

                            askReq(payload, ind, parts[1].equals("1"));
                        }

                        else if(type.equals("A")){
                            String[] parts = info[1].split(" ");
                            int payload = Integer.parseInt(parts[0]);
                            int ind = 0;
                            for (ConnectedChatRoomClient cl : this.allClients) {
                                if (cl.getUserName().equals(this.userName)) {
                                    break;
                                }
                                ind++;
                            }

                            if(parts[1].equals("1")){
                                staReq(payload, ind);
                            }
                            else{
                                rejReq(payload, ind);
                            }
                        }

                        else if(type.equals("P")){
                            int payload = Integer.parseInt(info[1]);
                            int ind = 0;
                            for (ConnectedChatRoomClient cl : this.allClients) {
                                if (cl.getUserName().equals(this.userName)) {
                                    break;
                                }
                                ind++;
                            }
                            playReq(ind, payload);
                        }

                        else if(type.equals("F")){
                            int ind = 0;
                            for (ConnectedChatRoomClient cl : this.allClients) {
                                if (cl.getUserName().equals(this.userName)) {
                                    break;
                                }
                                ind++;
                            }
                            finReq(ind);
                        }


                    } else {
                        System.out.println("Disconnected user: " + this.userName);
                        Iterator<ConnectedChatRoomClient> it = this.allClients.iterator();
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
            } catch (IOException ex) {
                System.out.println("Disconnected user: " + this.userName);
                for (ConnectedChatRoomClient cl : this.allClients) {
                    if (cl.getUserName().equals(this.userName)) {
                        this.allClients.remove(cl);
                        connectedClientsUpdateStatus();
                        return;
                    }
                }

            }

        }
    }

    void askReq(int ind, int user, boolean answer){
        String msg = "Q:";
        msg += " " + Integer.toString(user);
        if(answer) msg += " 1"; 
        else msg += " 0";
        this.allClients.get(ind).pw.println(msg);
        System.out.println(msg);
    }

    void rejReq(int ind, int user){
        String msg = "R: ";
        this.allClients.get(ind).pw.println(msg);
        System.out.println(msg);
    }

    void staReq(int ind, int user){
        Random rand = new Random();
        String msg = "S:";
        msg += " " + rand.nextInt() + " " + ind;

        this.allClients.get(ind).pw.println(msg + " " + this.allClients.get(user).userName);
        System.out.println(msg + " " + this.allClients.get(user).userName);


        this.allClients.get(user).pw.println(msg + " " + this.allClients.get(ind).userName);
        System.out.println(msg + " " + this.allClients.get(ind).userName);

        playing.add(ind);
        playing.add(user);

        connectedClientsUpdateStatus();
    }

    void playReq(int ind, int val){
        String msg = "P:";
        msg += " " + val;

        int temp = this.playing.indexOf(ind);
        if( (temp & 1) == 0 ) {
            temp++;
        } else {
            temp--;
        }
        int user = this.playing.get(temp);

        this.allClients.get(user).pw.println(msg);
        System.out.println(msg);
    }

    void finReq(int ind){
        String msg = "F: ";
        System.out.println(msg);

        // extract opponent
        int temp = this.playing.indexOf(ind);
        if( (temp & 1) == 0 ) {
            temp++;
        } else {
            temp--;
        }

        int user = this.playing.get(temp);
        this.allClients.get(user).pw.println(msg);


        System.out.println(playing);
        playing.remove(Integer.valueOf(ind)); // deletes only the first element with value ind
        playing.remove(Integer.valueOf(user));
        System.out.println(playing);
        // playing.removeAll(Arrays.asList(ind)); deletes all with value ind

        connectedClientsUpdateStatus();
    }

}
