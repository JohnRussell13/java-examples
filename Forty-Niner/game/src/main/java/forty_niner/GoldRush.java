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
            week = 0;
        }
        while(week < 20){
            System.out.println("Press Enter key to continue...");
            try {
                System.in.read();
            } catch(Exception e) {}
            System.out.print("\033\143");

            System.out.println("Week number " + week + " has arrived.");
            fortyNiner.useTools();
            fortyNiner.buyFood();
            fortyNiner.loseEndurance();

            week++;

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

            try {
                fortyNiner.itIsSundayAgain();
            } catch (IOException e) {
                // Problem when writing to the file
                e.printStackTrace();
            }

            System.out.println("Do you long for more cradles?");
            System.out.println("1. Yes;");
            System.out.println("2. No.");

            fl = true;
            do {
                try{
                    int option = Integer.parseInt(console.readLine());
        
                    switch(option) {
                        case 1:
                            System.out.println("How many cradles shall we buy?");
                            int count = 0;
                            do {
                                try {
                                    count = Integer.parseInt(console.readLine());
        
                                    for(int item = 0; item < count; item++) {
                                        Cradle cradle = new Cradle();
                                        fortyNiner.setTools(cradle);
                                    }
                                    fortyNiner.setMoney(fortyNiner.getMoney() - 30 * count);

                                    System.out.println("You spent $" + 30 * count + " on new cradles.");

                                    fl = false;
                                } catch(NumberFormatException e){
                                    System.out.println("Try again... This time with number.");
                                }
                            }
                            while(fl);
    
                            fl = false;
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
