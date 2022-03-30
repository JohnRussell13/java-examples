package forty_niner;
import java.util.*;
import java.io.*;

public class FortyNiner {
    private int endurance;
    private int money;
    private ArrayList<Tool> tools;
    private Random rnd;

    FortyNiner() {
        tools = new ArrayList<>();
        endurance = 100;
        money = 100;

        Sluice sluice = new Sluice();
        tools.add(sluice);
        Cradle cradle = new Cradle();
        tools.add(cradle);

        rnd = new Random();
        // System.out.println("FortyNiner");
    }

    FortyNiner(int edr, int mny, ArrayList<Tool> tls) {
        endurance = edr;
        money = mny;

        tools = tls;

        rnd = new Random();
        // System.out.println("FortyNiner");
    }

    public int getEndurance(){
        return endurance;
    }

    public int getMoney(){
        return money;
    }

    public ArrayList<Tool> getTools(){
        return tools;
    }

    public void setEndurance(int end){
        endurance = end;
    }

    public void setMoney(int mny){
        money = mny;
    }

    public void setTools(Tool tl){ // we can only add new cradles
        tools.add(tl);
    }

    public void useTools() {
        if(endurance == 0) {
            System.out.println("Low endurance! Go to a saloon for Heaven's sake!");
            return;
        }
        int newMoney;

        Tool tool = (Tool) tools.get(0);
        newMoney = tool.useTool();
        money += newMoney;
        System.out.println("Pan brought you $" + newMoney);

        tool = (Tool) tools.get(1);
        newMoney = tool.useTool();
        money += newMoney;
        System.out.println("Sluice brought you $" + newMoney);
        
        for(int i = 2; i < tools.size(); i++){
            tool = (Tool) tools.get(i);
            newMoney = tool.useTool();
            money += newMoney;
            System.out.println("Cradle brought you $" + newMoney);

            if(tool.getDurability() == 0) {
                tools.remove(tool);
                System.out.println("Dumped broken cradle.");
            }
        }

        System.out.println("Used tools. Current status:");
        
        tool = (Tool) tools.get(1);
        System.out.println("Sluice - " + tool.getDurability());

        for(int i = 2; i < tools.size(); i++){
            tool = (Tool) tools.get(i);
            // System.out.println("Cradle - " + tool.getDurability());
        }

        System.out.println("Current Seated Liberty stack - $" + money + ".");
    }

    public void buyFood() {
        money -= rnd.nextInt(50 - 30 + 1) + 30;

        System.out.println("Bought food, only $" + money + " left of ye old chips!");
    }

    public void loseEndurance() {
        endurance -= rnd.nextInt(25  - 10 + 1) + 10;
        if(endurance < 0) endurance = 0;

        System.out.println("Lost endurance - right now at " + endurance + "%.");
    }

    public void itIsSundayAgain() throws IOException {
        boolean fl = true;

        do {
            System.out.println("What shall we do this Sunday?");
            System.out.println("1. Nothing;");
            System.out.println("2. Fix the sluice;");
            System.out.println("3. Paint the town red!");

            Console console = System.console();
            try {
                int option = Integer.parseInt(console.readLine());
        
                switch(option) {
                    case 1:
                        System.out.println("Even God rested on Sunday!");
                        // nothing
                        fl = false;
                        break;
                    case 2:
                        System.out.println("Work is priority number one!");
                        fixSluice();
                        fl = false;
                        break;
                    case 3:
                        System.out.println("Enjoy your night's out! Have one for me!");
                        goToSaloon();
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

    private void goToSaloon() {
        money -= rnd.nextInt(200 - 50 + 1) + 50;

        endurance += rnd.nextInt(50 - 5 + 1) + 5;
        if(endurance > 100) endurance = 100;

        System.out.println("You saved $" + money + " for next Sunday! Endurance right now is at " + endurance + "%!");
    }

    private void fixSluice() {
        money -= 100;

        Sluice sluice = (Sluice) tools.get(1);
        sluice.repair();

        System.out.println("Only $" + money + " left after fixing the ruddy sluice!");
    }
}
