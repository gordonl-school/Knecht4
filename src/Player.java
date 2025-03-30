public class Player extends Space {
    private String name;

    public Player(String symbol, String name) {
        super(symbol);
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public void win() {
        System.out.println(Colors.GREEN + getName() + "(" + getSymbol() + ")" + " has won! Congrats!");
    }
}
