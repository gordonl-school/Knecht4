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
        setUpBoard();

        System.out.println("Directions:\n To choose a column, reply with 1-7. With 1 being the outermost left while 7 is the outermost right.");
        while (true) {
            printBoard();
            System.out.print(player1.getName() + " choose a move: ");
            int player1Move = scan.nextInt();

//            move(player1Move, player1);
            if (check(player1Move, player1)) {
                move(player1Move, player1);
            } else {
                System.out.println("Error! Out of bounds!");
                while (!check(player1Move, player1)) {
                    System.out.print(player1.getName() + " choose a move: ");
                    player1Move = scan.nextInt();
                    System.out.println("Error! Out of bounds!");
                }
                move(player1Move, player1);
            }

            checkWin(player1);
            clear();
            printBoard();
            System.out.print(player2.getName() + " choose a move: ");
            int player2Move = scan.nextInt();
//            move(player2Move, player2);
            //issues are when you put in a different value that doesn't fit check, it prints error message twice
            //also when you have error and decide to put in another answer in a different column it doesn't show up
            if (check(player2Move, player2)) {
                move(player2Move, player2);
            } else {
                System.out.println("Error! Out of bounds!");
                while (!check(player2Move, player2)) {
                    System.out.print(player2.getName() + " choose a move: ");
                    player2Move = scan.nextInt();
                    System.out.println("Error! Out of bounds!");
                }
                move(player2Move, player2);
            }
            checkWin(player2);
            clear();
        }
    }

    public void setUpBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = new Space("\uD83D\uDD18");
//                gameBoard[i][j] = new Space("  ");

            }
        }
    }

    public void printBoard() {
        System.out.print("-------------------------------");
        System.out.println();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print("|");
                System.out.print(gameBoard[i][j].getSymbol() + " ");
            }
            System.out.print("|");
            System.out.println();
            System.out.println("-------------------------------");
        }
    }

    public void selectSymbol(String name1, String name2) {
        String[] symbols = {"\uD83D\uDD34", "\uD83C\uDF49", "\uD83C\uDF4A"};
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

    public void move(int playerMove, Player player) {
        //This is going from the bottom row to the top row via the same col.
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            if (gameBoard[i][playerMove-1].getSymbol().equals("\uD83D\uDD18")) {
//            if (gameBoard[i][playerMove-1].getSymbol().equals("  ")) {
                gameBoard[i][playerMove-1] = new Space(player.getSymbol());
                break;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

    public boolean check(int move, Player player) {
        if (move < 1 || move > 7) {
            return false;
        } else if (gameBoard[0][move - 1].getSymbol().equals(player1Symbol) || gameBoard[0][move - 1].getSymbol().equals(player2Symbol)) {
            return false;
        } else {
            return true;
        }


    }

    public void checkWin( Player player) {
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].getSymbol().equals(player.getSymbol()) && gameBoard[i][j + 1].getSymbol().equals(player.getSymbol())) {
                    if (gameBoard[i][j + 2].getSymbol().equals(player.getSymbol())) {
                        if (gameBoard[i][j + 3].getSymbol().equals(player.getSymbol())) {
                            System.out.println(player.getName() + " has won! Congrats!");
                        }
                    }
                }
            }
        }

        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = gameBoard[0].length - 1; j > 2; i--) {
                if (gameBoard[i][j].getSymbol().equals(player.getSymbol()) && gameBoard[i][j - 1].getSymbol().equals(player.getSymbol())) {
                    if (gameBoard[i][j - 2].getSymbol().equals(player.getSymbol())) {
                        if (gameBoard[i][j - 3].getSymbol().equals(player.getSymbol())) {
                            System.out.println(player.getName() + " has won! Congrats!");
                        }
                    }
                }
            }
        }
    }


}
