public class Space {
    private String symbol;

    public Space(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void win() {
        System.out.println("Nobody has won! " + getSymbol());
    }
}
