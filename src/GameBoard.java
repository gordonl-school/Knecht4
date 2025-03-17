import java.util.*;

public class GameBoard {
    private Space[][] gameBoard;
    private Player player1;
    private Player player2;
    private String player1Symbol;
    private String player2Symbol;
    private Scanner scan;

    public GameBoard() {
//        this.player1 = player1;
//        this.player2 = player2;
        gameBoard = new Space[6][7];
        scan = new Scanner(System.in);
    }

    public void start() {
        System.out.print("Enter Player 1: ");
        String name1 = scan.nextLine();
        System.out.print("Enter Player 2: ");
        String name2 = scan.nextLine();
        selectSymbol(name1, name2);
        System.out.println("Symbol 1:" + player1Symbol);
        System.out.println("Symbol 2: " + player2Symbol);
        player1 = new Player(player1Symbol, name1);
        player2 = new Player(player2Symbol, name2);

        System.out.println("Directions:\n To choose a column, reply with 1-7. With 1 being the outermost left while 7 is the outermost right.");
        while (true) {
            setUpBoard();
            printBoard();
            System.out.print(player1.getName() + " choose a move");
            int player1Move = scan.nextInt();
            for (int i = gameBoard.length - 1; i >= 0; i--) {
//                gameBoard[player1Move-1][i]
                if (gameBoard[player1Move-1][i].getSymbol().equals("＿")) {
                    gameBoard[player1Move-1][i] = new Space(player1.getSymbol());
                }
            }
        }


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

    public void selectSymbol(String name1, String name2) {
        String[] symbols = {"\uD83D\uDD34", "\uD83D\uDFE1", "\uD83C\uDF49", "\uD83C\uDF4A"};
        System.out.println("List of Symbols: ");
        for (int i = 1; i <= symbols.length; i++) {
            System.out.println(i + ". " + symbols[i-1]);
        }
        System.out.print(name1 + ", select a symbol: ");
        int symbol1 = scan.nextInt();
        player1Symbol = symbols[symbol1-1];
        while (true) {
            System.out.print(name2 + ", select a symbol: ");
            int symbol2 = scan.nextInt();
            player2Symbol = symbols[symbol2-1];
            if (player1Symbol.equals(player2Symbol)) {
                System.out.println("You cannot choose the same symbols. Choose again.");
                continue;
            }
            break;
        }
    }


}
