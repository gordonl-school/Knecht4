public class GameBoard {
    private Space[][] gameBoard;
    private String player1;
    private String player2;

    public GameBoard(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        gameBoard = new Space[6][7];
    }

    public void start() {
        setUpBoard();
        printBoard();
    }

    public void setUpBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = new Space("＿");
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print(gameBoard[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
    }

}
