package com.transfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class User {
    private String username;
    private List<Integer> missing = new ArrayList<Integer>();
    private List<Integer> doubles = new ArrayList<Integer>();
    private TransferClient client = new TransferClient();

    public User(String name){
        Boolean isNew = false;
        try {
            Scanner myReader = new Scanner(new File(System.getProperty("user.dir") + "/users.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataArray = data.split(",");
                if(dataArray[0].equals(name)){
                    isNew = true;
                    username = name;
                    String[] valArray = dataArray[1].split(":");

                    for(int i = 0; i < valArray.length; i++){
                        missing.add(Integer.parseInt(valArray[i]));
                    }

                    valArray = dataArray[2].split(":");
                    for(int i = 0; i < valArray.length; i++){
                        doubles.add(Integer.parseInt(valArray[i]));
                    }
                    
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if(!isNew){
            Random rand = new Random();
            username = name;
            for(int i = 0; i < 7; i++){
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

            for(int i = 0; i < 7; i++){
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

            appendUser();
        }

        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        System.out.println(response);
    }

    private void appendUser(){
        Writer output;
        String payload = new String();
        payload = username + ",";
        for(int i = 0; i < missing.size(); i++){
            payload += Integer.toString(missing.get(i)) + ":";
        }
        payload = payload.substring(0, payload.length() - 1);
        payload += ",";
        for(int i = 0; i < doubles.size(); i++){
            payload += Integer.toString(doubles.get(i)) + ":";
        }
        payload = payload.substring(0, payload.length() - 1);
        payload += "\n";
        try{
            output = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/users.txt", true));
            output.append(payload);
            output.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private void changeUser(){
        try{
            BufferedReader file = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/users.txt"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                if(line.split(",")[0].equals(username)){
                    String payload = new String();
                    payload = username + ",";

                    if(!missing.isEmpty()){
                        for(int j = 0; j < missing.size(); j++){
                            payload += Integer.toString(missing.get(j)) + ":";
                        }
                        payload = payload.substring(0, payload.length() - 1);
                    }

                    payload += ",";
                    
                    if(!doubles.isEmpty()){
                        for(int j = 0; j < doubles.size(); j++){
                            payload += Integer.toString(doubles.get(j)) + ":";
                        }
                        payload = payload.substring(0, payload.length() - 1);
                    }

                    line = payload;
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
    
            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "/users.txt");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List<Integer> getMissing(){
        return missing;
    }

    public List<Integer> getDoubles(){
        return doubles;
    }
    
    public void deleteMissing(List<Integer> deleteArray){
        for(int i = 0; i < deleteArray.size(); i++){
            for(int j = 0; j < missing.size(); j++){
                if(deleteArray.get(i) == missing.get(j)){
                    missing.remove(j);
                    break;
                }
            }
        }
        changeUser();
    }
    
    public void deleteDoubles(List<Integer> deleteArray){
        for(int i = 0; i < deleteArray.size(); i++){
            for(int j = 0; j < doubles.size(); j++){
                if(deleteArray.get(i) == doubles.get(j)){
                    doubles.remove(j);
                    break;
                }
            }
        }
        changeUser();
    }
}
