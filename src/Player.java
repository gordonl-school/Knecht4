public class Player extends Space {
    private boolean won;
    private String name;

    public Player(String name) {
        super(name);
        won = false;
    }

    public boolean isWon() {
        return won;
    }



}
