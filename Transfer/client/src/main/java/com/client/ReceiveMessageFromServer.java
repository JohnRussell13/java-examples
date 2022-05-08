package com.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
//import java.util.Arrays;

//import javax.swing.JComboBox;
//import javax.swing.JOptionPane;
/**
 * Ova klasa se koristi za prijem poruka od strane servera jer ce one stizati
 * asinhrono (ne znamo u kom trenutku ce se novi korisnik ukljuciti u Chat room,
 * kao ni kada ce nam poslati poruku)
 *
 */
public class ReceiveMessageFromServer implements Runnable {

    App parent;
    BufferedReader br;

    public ReceiveMessageFromServer(App parent) {
        //parent ce nam trebati da bismo mogli iz ovog thread-a da menjamo sadrzaj 
        //komponenti u osnovnom GUI prozoru (npr da popunjavamo Combo Box sa listom
        //korisnika
        this.parent = parent;
        //BufferedReader koristimo za prijem poruka od servera, posto su sve
        //poruke u formi Stringa i linija teksta, BufferedReader je zgodniji nego
        //da citamo poruke iz InputStream objekta
        this.br = parent.getBr();
    }

    @Override
    public void run() {
        //Beskonacna petlja
        while (true) {
            String line;
            try {
                /* 
                   Cekaj da ti stigne linija teksta od servera. Postoje dve poruke koje nam server salje:
                1. spisak korisnika koji je uvek u formatu Users: Milan Dusan Dragan Dimitrije
                2. poruka koja nam stize od nekog drugog korisnika iz Chat room-a, koja je uvek u formatu
                    Ime korisnika koji salje poruku: Tekst poruke
                 */

                line = this.br.readLine();
                System.out.println(line);

                if (line.startsWith("Users: ")) {
                    /* 
                    1. parsiraj pristiglu poruku, 
                    2. prepoznaj korisnike koji su trenutno u Chat roomu
                    3. azuriraj ComboBox sa spiskom korisnika koji su trenutno u Chat Room-u
                    4. Prikazi prozor sa obavestenjem da je novi clan dosao/izasao iz Chat room-a (npr JOptionPane.showMessageDialog)
                     */

                    String[] imena = line.split(":")[1].split(" ");

                    parent.setNames(imena);
                } 
                else if(line.startsWith("M:")){
                    String[] miss = line.split(":")[1].split(" ");                    
                    parent.setMissing(miss);
                }
                else if(line.startsWith("D:")){
                    String[] doubl = line.split(":")[1].split(" ");
                    parent.setDouble(doubl);
                }

                else if(line.startsWith("Q1:")){
                    String[] q1 = line.split(":")[1].split(" ");
                    parent.setReq1(q1);
                    
                }
                else if(line.startsWith("Q2:")){
                    String[] q1 = line.split(":")[1].split(" ");
                    parent.setReq2(q1);
                }
                else if(line.startsWith("R:")){
                    String sourceOG = line.split(":")[1].split(" ")[1];
                    parent.setRRN(sourceOG);
                    String[] r1 = line.split(":")[2].split(" ");
                    parent.setRR1(r1);
                    String[] r2 = line.split(":")[3].split(" ");
                    parent.setRR2(r2);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
