package forty_niner;
import java.util.*;
import java.io.*;

public class GoldRush {
    private FortyNiner fortyNiner;
    private File savedGame;

    private int week;

    public GoldRush() {
        savedGame = new File("GoldRushSaved.txt");
        // System.out.println("GoldRush");
    }

    public void loadGame() {
        int endurance = 0;
        int money = 0;
        ArrayList<Tool> tools = new ArrayList<>();

        try {
            Scanner scanIn = new Scanner(savedGame);
            String data;
            int durability;
            String regex = "[^\\d]+";
            
            data = scanIn.nextLine();
            week = Integer.parseInt(data.split(regex)[1]);

            data = scanIn.nextLine();
            endurance = Integer.parseInt(data.split(regex)[1]);

            data = scanIn.nextLine();
            money = Integer.parseInt(data.split(regex)[1]);

            data = scanIn.nextLine();
            durability = Integer.parseInt(data.split(regex)[1]);

            Pan pan = new Pan();
            tools.add(pan);
            Sluice sluice = new Sluice(durability);
            tools.add(sluice);

            while(scanIn.hasNextLine()) {
                data = scanIn.nextLine();
                durability = Integer.parseInt(data.split(regex)[1]);

                Cradle cradle = new Cradle(durability);
                tools.add(cradle);
            }

            fortyNiner = new FortyNiner(endurance, money, tools);
            scanIn.close();

            System.out.println("Game loaded.");
        } catch (FileNotFoundException e){
            System.out.println("Starting new game.");
        }
    }

    public void survive() {
        if(fortyNiner == null) {
            fortyNiner = new FortyNiner();
            week = 1;
        }

        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch(Exception e) {}
        System.out.print("\033\143");
        
        while(week < 20){

            System.out.println("Week number " + week + " has arrived.");
            System.out.println("Current money supply: $" + fortyNiner.getMoney());
            System.out.println("Current endurance level: " + fortyNiner.getEndurance() + "%");
            System.out.println("Current sluice durability level: " + fortyNiner.getTools().get(1).getDurability() + "%");
            System.out.println("Current number of cradles: " + (fortyNiner.getTools().size() - 2));
            System.out.println();

            System.out.println("Do you want to leave the game?");
            System.out.println("1. Yes;");
            System.out.println("2. No.");

            boolean fl = true;

            Console console = System.console();
            do {
                try{
                    int option = Integer.parseInt(console.readLine());
        
                    switch(option) {
                        case 1:
                            saveGame(week);
                            fl = false;
                            System.out.println("Game saved!");
                            System.out.print("\033\143");
                            System.exit(0);
                            break;
                        case 2:
                            // nothing
                            fl = false;
                            break;
                        default:
                            System.out.println("Try again...");
                            fl = true;
                            break;
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Try again... This time with number.");
                }
            }
            while(fl);

            System.out.print("\033\143");

            System.out.println("Week number " + week + " has arrived.");
            System.out.println("Current money supply: $" + fortyNiner.getMoney());
            System.out.println("Current endurance level: " + fortyNiner.getEndurance() + "%");
            System.out.println("Current sluice durability level: " + fortyNiner.getTools().get(1).getDurability() + "%");
            System.out.println("Current number of cradles: " + (fortyNiner.getTools().size() - 2));
            System.out.println();

            try {
                fortyNiner.itIsSundayAgain();
            } catch (IOException e) {
                // Problem when writing to the file
                e.printStackTrace();
            }

            System.out.println("Press Enter key to continue...");
            try {
                System.in.read();
            } catch(Exception e) {}
            System.out.print("\033\143");

            fortyNiner.useTools();

            System.out.println("Press Enter key to continue...");
            try {
                System.in.read();
            } catch(Exception e) {}
            System.out.print("\033\143");

            fortyNiner.buyFood();

            System.out.println("Press Enter key to continue...");
            try {
                System.in.read();
            } catch(Exception e) {}
            System.out.print("\033\143");

            fortyNiner.loseEndurance();

            System.out.println("Press Enter key to continue...");
            try {
                System.in.read();
            } catch(Exception e) {}
            System.out.print("\033\143");


            week++;

            System.out.println("Do you long for more cradles?");
            System.out.println("1. Yes;");
            System.out.println("2. No.");

            fl = true;
            do {
                try{
                    int option = Integer.parseInt(console.readLine());
        
                    switch(option) {
                        case 1:
                            System.out.print("\033\143");
                            System.out.println("How many cradles shall we buy?");
                            int count = 0;
                            do {
                                try {
                                    count = Integer.parseInt(console.readLine());
                                    boolean mf = true;

                                    int newMoney = count * 30;

                                    while(fortyNiner.getMoney() < newMoney) {
                                        mf = false;
                                        count--;
                                        newMoney = count * 30;
                                    }
        
                                    for(int item = 0; item < count; item++) {
                                        Cradle cradle = new Cradle();
                                        fortyNiner.setTools(cradle);
                                    }


                                    fortyNiner.setMoney(fortyNiner.getMoney() - newMoney);

                                    if(mf) {
                                        System.out.println("You spent $" + newMoney + " on new cradles.");
                                    }
                                    else {
                                        System.out.println("You don't have enough money for all of them! You bought only " + count + " of them.");
                                        System.out.println("You spent $" + newMoney + " on new cradles.");
                                    }
                                    

                                    System.out.println("Press Enter key to continue...");
                                    try {
                                        System.in.read();
                                    } catch(Exception e) {}
                                    System.out.print("\033\143");

                                    fl = false;
                                } catch(NumberFormatException e){
                                    System.out.println("Try again... This time with number.");
                                }
                            }
                            while(fl);
    
                            fl = false;
                            break;
                        case 2:
                            System.out.print("\033\143");
                            // nothing
                            fl = false;
                            break;
                        default:
                            System.out.println("Try again...");
                            fl = true;
                            break;
                    }
                } catch(NumberFormatException e) {
                System.out.println("Try again... This time with number.");
                }
                
            }
            while(fl);
        }
    }

    private void saveGame(int week) {
        try {
            FileOutputStream fos = new FileOutputStream(savedGame);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            bw.write("Week no. " + week);
            bw.newLine();

            bw.write("49er endurance: " + fortyNiner.getEndurance() + "%");
            bw.newLine();

            bw.write("49er money: $" + fortyNiner.getMoney());
            bw.newLine();

            ArrayList<Tool> tools = fortyNiner.getTools();
            Tool sluice = (Tool) tools.get(1);

            bw.write("Sluice durability: " + sluice.getDurability() + "%");
            bw.newLine();

            for(int i = 2; i < tools.size(); i++){
                Tool cradle = (Tool) tools.get(i);

                bw.write("Cradle durability: " + cradle.getDurability() + "%");
                bw.newLine();
            }

            bw.close();

        } catch (FileNotFoundException e){
            // File was not found
            e.printStackTrace();
        } catch (IOException e) {
            // Problem when writing to the file
            e.printStackTrace();
        }
    }
    
}
