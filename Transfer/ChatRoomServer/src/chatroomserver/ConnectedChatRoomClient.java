package chatroomserver;

import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
//import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectedChatRoomClient implements Runnable {

    //atributi koji se koriste za komunikaciju sa klijentom
    private Socket socket;
    private String userName;
    private List<Integer> missing = new ArrayList<Integer>();
    private List<Integer> doubles = new ArrayList<Integer>();
    private BufferedReader br;
    private PrintWriter pw;
    private ArrayList<ConnectedChatRoomClient> allClients;

    //getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //Konstruktor klase, prima kao argument socket kao vezu sa uspostavljenim klijentom
    public ConnectedChatRoomClient(Socket socket, ArrayList<ConnectedChatRoomClient> allClients) {
        this.socket = socket;
        this.allClients = allClients;

        //iz socket-a preuzmi InputStream i OutputStream
        try {
            //posto se salje tekst, napravi BufferedReader i PrintWriter
            //kojim ce se lakse primati/slati poruke (bolje nego da koristimo Input/Output stream
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
            this.pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            //zasad ne znamo user name povezanog klijenta
            this.userName = "";
        } catch (IOException ex) {
            Logger.getLogger(ConnectedChatRoomClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda prolazi i pravi poruku sa trenutno povezanik korisnicima u formatu
     * Users: ImePrvog ImeDrugog ImeTreceg ... kada se napravi poruka tog
     * formata, ona se salje svim povezanim korisnicima
     */
    void connectedClientsUpdateStatus() {
        //priprema string sa trenutno povezanim korisnicima u formatu 
        //Users: Milan Dusan Petar
        //i posalji svim korisnicima koji se trenutno nalaze u chat room-u
        String connectedUsers = "Users:";
        for (ConnectedChatRoomClient c : this.allClients) {
            connectedUsers += " " + c.getUserName();
        }

        //prodji kroz sve klijente i svakom posalji info o novom stanju u sobi
        for (ConnectedChatRoomClient svimaUpdateCB : this.allClients) {
            svimaUpdateCB.pw.println(connectedUsers);
        }

        System.out.println(connectedUsers);
        // System.out.println(missing);
    }

    @Override
    public void run() {
        //Server prima od svakog korisnika najpre njegovo korisnicko ime
        //a kasnije poruke koje on salje ostalim korisnicima u chat room-u
        while (true) {
            try {
                //ako nije poslato ime, najpre cekamo na njega
                if (this.userName.equals("")) {
                    this.userName = this.br.readLine();
                    if (this.userName != null) {
                        System.out.println("Connected user: " + this.userName);
                        generateValues();
                        //informisi sve povezane klijente da imamo novog 
                        //clana u chat room-u
                        connectedClientsUpdateStatus();

                        sendData(this.allClients.size()-1);
                    } else {
                        //ako je userName null to znaci da je terminiran klijent thread
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
                    ////////CEKAMO PORUKU/////////
                } else {
                    //vec nam je korisnik poslao korisnicko ime, poruka koja je 
                    //stigla je za nekog drugog korisnika iz chat room-a (npr Milana) u 
                    //formatu Milan: Cao Milane, kako si?
                    System.out.println("cekam poruku");
                    String line = this.br.readLine();
                    System.out.println(line);
                    System.out.println("stigla poruka");
                    if (line != null) {
                        /*prepoznaj za koga je poruka, pronadji tog 
                        korisnika i njemu prosledi poruku
                        Npr stigla je poruka "Milan: Cao Milane, kako si?" od Dusana
                        sa kojim komuniciramo u ovoj niti. 
                        To znaci da server treba da prosledi poruku Milanu sa tekstom
                        Dusan: Cao Milane, kako si?
                         */
                        String[] informacija = line.split(": ");

                        String type = informacija[0];

                        if(type.equals("M")){
                            String payload = informacija[1];
                            missing.clear();
                            String[] ms = payload.split(" ");
                            for(String m : ms){
                                missing.add(Integer.parseInt(m));
                            }
                        }
                        else if(type.equals("D")){
                            String payload = informacija[1];
                            doubles.clear();
                            String[] ds = payload.split(" ");
                            for(String d : ds){
                                doubles.add(Integer.parseInt(d));
                            }
                        }
                        else if(type.equals("Q")){
                            String primacKorisnik = informacija[1];
                            for (ConnectedChatRoomClient clnt : this.allClients) {
                                if (clnt.getUserName().equals(primacKorisnik)) {
                                    System.out.println(clnt.getUserName());
                                    //prosledi poruku namenjenom korisniku
                                    String msg = "Q1:";
                                    Boolean flN = true;

                                    for(int m : missing){
                                        for(int d : clnt.doubles){
                                            if(m == d){
                                                msg += " " + m;
                                                flN = false;
                                            }
                                        }
                                    }
                                    if(flN){
                                        msg += " ";
                                    }

                                    this.pw.println(msg);

                                    msg = "Q2:";
                                    flN = true;
                                    for(int m : clnt.missing){
                                        for(int d : doubles){
                                            if(m == d){
                                                msg += " " + m;
                                                flN = false;
                                            }
                                        }
                                    }
                                    if(flN){
                                        msg += " ";
                                    }

                                    this.pw.println(msg);
                                    System.out.println(msg);
                                }
                            }
                        }
                        else if(type.equals("R")){
                            String primacKorisnik = informacija[1];
                            String payload0 = informacija[2];
                            String payload1 = informacija[3];

                            for (ConnectedChatRoomClient clnt : this.allClients) {
                                if (clnt.getUserName().equals(primacKorisnik)) {
                                    System.out.println(clnt.getUserName());
                                    
                                    String msg = "R: " + this.userName + ": " + payload0 + ": " + payload1;
                                    
                                    clnt.pw.println(msg);
                                    System.out.println(msg);
                                }
                            }
                        }
                        else if(type.equals("T")){
                            String primacKorisnik = informacija[1];
                            String[] payload0 = informacija[2].split(" ");
                            String[] payload1 = informacija[3].split(" ");

                            for (ConnectedChatRoomClient clnt : this.allClients) {
                                if (clnt.getUserName().equals(primacKorisnik)) {

                                    try{
                                        for(String pl : payload0){
                                            if (!pl.equals("")) {
                                                this.missing.remove(this.missing.indexOf(Integer.parseInt(pl)));
                                                clnt.doubles.remove(clnt.doubles.indexOf(Integer.parseInt(pl)));
                                            }
                                        }
    
                                        for(String pl : payload1){
                                            if (!pl.equals("")) {
                                                this.doubles.remove(this.doubles.indexOf(Integer.parseInt(pl)));
                                                clnt.missing.remove(clnt.missing.indexOf(Integer.parseInt(pl)));
                                            }
                                        }
                                    } catch(Exception e){
                                        System.out.println("Already traded");
                                    }
                                    
                                    String msg = "M:";
                                    for(int m : this.missing){
                                        msg += " " + m;
                                    }
                                    this.pw.println(msg);
                                    msg = "D:";
                                    for(int d : this.doubles){
                                        msg += " " + d;
                                    }
                                    this.pw.println(msg);

                                    System.out.println(msg);

                                    
                                    msg = "M:";
                                    for(int m : clnt.missing){
                                        msg += " " + m;
                                    }
                                    clnt.pw.println(msg);
                                    msg = "D:";
                                    for(int d : clnt.doubles){
                                        msg += " " + d;
                                    }
                                    clnt.pw.println(msg);

                                    System.out.println(msg);

                                }
                            }
                        }


                        // for (ConnectedChatRoomClient clnt : this.allClients) {
                        //     if (clnt.getUserName().equals(primacKorisnik)) {
                        //         System.out.println(clnt.getUserName());
                        //         //prosledi poruku namenjenom korisniku
                        //         clnt.pw.println(this.userName + ": " + poruka);
                        //         System.out.println(primacKorisnik + ": " + poruka);
                        //     } else {
                        //         //ispisi da je korisnik kome je namenjena poruka odsutan
                        //         if (primacKorisnik.equals("")) {
                        //             this.pw.println("Korisnik " + primacKorisnik + " je odsutan!");
                        //         }
                        //     }
                        // }

                    } else {
                        //slicno kao gore, ako je line null, klijent se diskonektovao
                        //ukloni tog korisnika iz liste povezanih korisnika u chat room-u
                        //i obavesti ostale da je korisnik napustio sobu
                        System.out.println("Disconnected user: " + this.userName);

                        //Ovako se uklanja element iz kolekcije 
                        //ne moze se prolaziti kroz kolekciju sa foreach a onda u 
                        //telu petlje uklanjati element iz te iste kolekcije
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
                //npr, ovakvo uklanjanje moze dovesti do izuzetka, pogledajte kako je 
                //to gore uradjeno sa iteratorom
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


    void sendData(int ind){
        String msg = "M:";
        for(int m : missing){
            msg += " " + m;
        }
        this.allClients.get(ind).pw.println(msg);
        msg = "D:";
        for(int d : doubles){
            msg += " " + d;
        }
        this.allClients.get(ind).pw.println(msg);
    }

    void generateValues(){
        Random rand = new Random();
        for(int i = 0; i < 20; i++){
            int temp = rand.nextInt((99 - 1) + 1) + 1;
            Boolean fl = true;
            for(int j = 0; j < missing.size(); j++){
                if(missing.get(j) == temp){
                    fl = false;
                    break;
                }
            }
            if(fl){
                missing.add(temp);
            }
        }

        for(int i = 0; i < 20; i++){
            int temp = rand.nextInt((99 - 1) + 1) + 1;
            Boolean fl = true;
            for(int j = 0; j < missing.size(); j++){
                if(missing.get(j) == temp){
                    fl = false;
                    break;
                }
            }
            for(int j = 0; j < doubles.size(); j++){
                if(doubles.get(j) == temp){
                    fl = false;
                    break;
                }
            }
            if(fl){
                doubles.add(temp);
            }
        }
    }


}
