import java.util.*;

public class GameBoard {
    private Space[][] gameBoard;
    private Player player1;
    private Player player2;
    private String player1Symbol;
    private String player2Symbol;
    private Scanner scan;

    public GameBoard() {
        gameBoard = new Space[6][7];
        scan = new Scanner(System.in);
    }

    public void start() {
        System.out.println("------Starting------");
        System.out.println("1. Player VS Player");
        System.out.println("2. Player VS AI");
        System.out.print("Choose your pick: ");
        int userPick = scan.nextInt();

        if (userPick == 1) {
            scan.nextLine();
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
        } else if (userPick == 2) {
            scan.nextLine();
            System.out.print("Enter Player Name: ");
            String name1 = scan.nextLine();
            System.out.println("User symbol: " + "\uD83D\uDD34"); // This is a red circle
            System.out.println("AI symbol: " + "\uD83C\uDF4A"); // This is a green circle
            player1 = new Player("\uD83D\uDD34", name1);
            player2 = new Player("\uD83C\uDF4A", "AI");
            setUpBoard();
        }

        System.out.println("Directions:\nTo choose a column, reply with 1-7. With 1 being the outermost left while 7 is the outermost right.");
        printBoard();
        while (!checkWin(player1, gameBoard, false) && !checkWin(player2, gameBoard, false) && !boardFull()) {
            while (true) {
                try {
                    System.out.print(player1.getName() + " choose a move: ");
                    int player1Move = scan.nextInt();

                    if (check(player1Move, player1)) {
                        move(player1Move, player1);
                        break;
                    } else {
                        while (!check(player1Move, player1)) {
                            System.out.println("Error! Out of bounds!");
                            System.out.print(player1.getName() + " choose a move: ");
                            player1Move = scan.nextInt();
                        }
                        move(player1Move, player1);
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid answer");
                    scan.next();
                }
            }

            clear();
            printBoard();
            if (checkWin(player1, gameBoard, false)) {
                //break;
                System.exit(0);
            }

            if (userPick == 1) {
                while (true) {
                    try {
                        System.out.print(player2.getName() + " choose a move: ");
                        int player2Move = scan.nextInt();
                        if (check(player2Move, player2)) {
                            move(player2Move, player2);
                            break;
                        } else {
                            while (!check(player2Move, player2)) {
                                System.out.println("Error! Out of bounds!");
                                System.out.print(player2.getName() + " choose a move: ");
                                player2Move = scan.nextInt();
                            }
                            move(player2Move, player2);
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid answer");
                        scan.next();
                    }
                }
            } else {
                int aiChosenCol = Ai.minmax(gameBoard, 2, 2, true);
                move(aiChosenCol, player2);
            }
            clear();
            printBoard();
            if (checkWin(player2, gameBoard, false)) {
                System.exit(0);
            }
        }
        Space loser = new Space("\uD83D\uDC4E");
        loser.win();
    }

    public void setUpBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = new Space("\uD83D\uDD18");

            }
        }
    }

    public void printBoard() {
//        System.out.print("-------------------------------");
        System.out.print("———————————————————————————————");
        System.out.println();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print("|");
                System.out.print(gameBoard[i][j].getSymbol() + " ");
            }
            System.out.print("|");
            System.out.println();
            System.out.println("———————————————————————————————");
        }
    }

    public void selectSymbol(String name1, String name2) {
        String[] symbols = {"\uD83D\uDD34", "\uD83C\uDF49", "\uD83C\uDF4A"};
        System.out.println("List of Symbols: ");
        for (int i = 1; i <= symbols.length; i++) {
            System.out.println(i + ". " + symbols[i - 1]);
        }
        System.out.print(name1 + ", select a symbol: ");
        int symbol1 = scan.nextInt();
        player1Symbol = symbols[symbol1 - 1];
        while (true) {
            System.out.print(name2 + ", select a symbol: ");
            int symbol2 = scan.nextInt();
            player2Symbol = symbols[symbol2 - 1];
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
            if (gameBoard[i][playerMove - 1].getSymbol().equals("\uD83D\uDD18")) {
//            if (gameBoard[i][playerMove-1].getSymbol().equals("  ")) {
                gameBoard[i][playerMove - 1] = new Space(player.getSymbol());
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

    public static boolean checkWin(Player player, Space[][] gameBoard, boolean fromAIClass) {
        //————————————————————————————————————————Horizontal Checker———————————————————--————————————————————————————————
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j].getSymbol().equals(player.getSymbol()) && gameBoard[i][j + 1].getSymbol().equals(player.getSymbol())) {
                    if (gameBoard[i][j + 2].getSymbol().equals(player.getSymbol())) {
                        if (gameBoard[i][j + 3].getSymbol().equals(player.getSymbol())) {
                            if (!fromAIClass) {
                                player.win();
                            }
                            return true;
                        }
                    }
                }
            }
        }
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = gameBoard[0].length - 1; j > 2; j--) {
                if (gameBoard[i][j].getSymbol().equals(player.getSymbol()) && gameBoard[i][j - 1].getSymbol().equals(player.getSymbol())) {
                    if (gameBoard[i][j - 2].getSymbol().equals(player.getSymbol())) {
                        if (gameBoard[i][j - 3].getSymbol().equals(player.getSymbol())) {
                            if (!fromAIClass) {
                                player.win();
                            }
                            return true;
                        }
                    }
                }
            }
        }

        //————————————————————————————————————————Vertical Checker———————————————————--————————————————————————————————
        ArrayList<Space> verticalCheck = new ArrayList<>();
        for (int i = 0; i < gameBoard[0].length - 1; i++) {
            for (int j = gameBoard.length - 1; j >= 3; j--) {
                if (gameBoard[j][i].getSymbol().equals(player.getSymbol()) && gameBoard[j - 1][i].getSymbol().equals(player.getSymbol())) {
                    verticalCheck.add(gameBoard[j][i]);
                    verticalCheck.add(gameBoard[j - 1][i]);
                    if (gameBoard[j - 2][i].getSymbol().equals(player.getSymbol())) {
                        verticalCheck.add(gameBoard[j - 2][i]);
                        if (gameBoard[j - 3][i].getSymbol().equals(player.getSymbol())) {
                            verticalCheck.add(gameBoard[j - 3][i]);
                        } else {
                            verticalCheck.clear();
                        }
                    } else {
                        verticalCheck.clear();
                    }
                }
                if (verticalCheck.size() == 4) {
//                    System.out.println(Colors.GREEN + player.getName() + "(" + player.getSymbol() + ")" + " has won! Congrats!");
                    if (!fromAIClass) {
                        player.win();
                    }
                    return true;
                }
            }
        }

        for (int i = gameBoard[0].length - 1; i > 0; i--) {
            for (int j = gameBoard.length - 1; j > 2; j--) {
                if (gameBoard[j][i].getSymbol().equals(player.getSymbol()) && gameBoard[j - 1][i].getSymbol().equals(player.getSymbol())) {
                    if (gameBoard[j - 2][i].getSymbol().equals(player.getSymbol())) {
                        if (gameBoard[j - 3][i].getSymbol().equals(player.getSymbol())) {
//                            System.out.println(Colors.GREEN + player.getName() + "(" + player.getSymbol() + ")" + " has won! Congrats!");
                            if (!fromAIClass) {
                                player.win();
                            }
                            return true;
                        }
                    }
                }
            }
        }

        //————————————————————————————————————————Diagonal Checker———————————————————--————————————————————————————————
        //Diagonal Checker from the Bottom Right
        for (int rowSub = 0; rowSub < 3; rowSub++) {
            for (int colAdd = 0; colAdd < 4; colAdd++) {
                //CHECKER FOR THE INITIAL ROWS
                boolean win = true;
                int row = gameBoard.length - 1 - rowSub;
                int col = colAdd;
                for (int i = 0; i < 4; i++) {
                    if (!gameBoard[row][col].getSymbol().equals(player.getSymbol())) {
                        win = false;
                    }
                    col++;
                    row--;
                }
                if (win) {
//                    System.out.println(Colors.GREEN + player.getName() + "(" + player.getSymbol() + ")" + " has won! Congrats!");
                    if (!fromAIClass) {
                        player.win();
                    }
                    return true;
                }
            }
        }

        //Diagonal Checker for the Bottom left
        for (int rowSub = 0; rowSub < 3; rowSub++) {
            for (int colSub = 0; colSub < 4; colSub++) {
                //CHECKER FOR THE INITIAL ROWS
                boolean win = true;
                int row = gameBoard.length - 1 - rowSub;
                int col = gameBoard[0].length - 1 - colSub;
                for (int i = 0; i < 4; i++) {
                    if (!gameBoard[row][col].getSymbol().equals(player.getSymbol())) {
                        win = false;
                    }
                    col--;
                    row--;
                }
                if (win) {
//                    System.out.println(Colors.GREEN + player.getName() + "(" + player.getSymbol() + ")" + " has won! Congrats!");
                    if (!fromAIClass) {
                        player.win();
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public boolean boardFull() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j].getSymbol().equals("\uD83D\uDD18")) {
                    return false;
                }
            }
        }
        return true;
    }
}



      //Alternate solution to horizontal
//    public boolean check1(Player player) {
//        for (int subRow = 0; subRow < 6; subRow++) {
//            for (int add = 0; add < 4; add++) {
//                boolean win = true;
//                for (int col = 0 + add; col < 4 + add; col++) {
//                    if (!gameBoard[gameBoard.length - 1 - subRow][col].getSymbol().equals(player.getSymbol())) {
//
//                        win = false;
//                    }
//                }
//                if (win) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }







