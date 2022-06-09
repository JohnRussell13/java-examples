package com.example;

import java.io.BufferedReader;
import java.io.IOException;
public class ReceiveMessageFromServer implements Runnable {

    App parent;
    BufferedReader br;

    public ReceiveMessageFromServer(App parent) {
        this.parent = parent;
        this.br = parent.getBr();
    }

    @Override
    public void run() {
        while (true) {
            String line;
            try {
                line = this.br.readLine();
                System.out.println(line);

                // if (line.startsWith("Users: ")) {
                //     String[] imena = line.split(":")[1].split(" ");
                //     parent.setNames(imena);
                // }
                // else if(line.startsWith("Q1:")){
                //     String[] q1 = line.split(":")[1].split(" ");
                //     parent.setReq1(q1);
                // }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
