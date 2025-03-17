public class Player extends Space {
    private boolean won;
    private String name;

    public Player(String symbol, String name) {
        super(symbol);
        this.name = name;
        won = false;
    }

    public boolean isWon() {
        return won;
    }

    public String getName() {
        return name;
    }
}
