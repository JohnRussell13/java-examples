package forty_niner;

public class Play {
    public static void main( String[] args ) {
        System.out.print("\033\143");

        GoldRush game = new GoldRush();
        game.loadGame();
        game.survive();
    }
}
