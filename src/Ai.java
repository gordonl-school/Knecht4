import java.util.ArrayList;

public class Ai {
    private static Player HUMAN = new Player("\uD83D\uDD34", "HUMAN");;
    private static Player AI = new Player("\uD83C\uDF4A", "AI");;
    private static Space[][] copyOfMain;

    public Ai() {
//        HUMAN = new Player("\uD83D\uDD34", "HUMAN");
//        AI = new Player("\uD83D\uDFE2", "AI");
    }

    public static int minmax(Space[][] gameBoard, int depth, int end, boolean maximizingPlayer) {
        if (depth == 0 || isTerminal(gameBoard)) {
            return evaluateBoard(gameBoard);
        }

        int bestValue;
        int bestMove = -1;

        if (maximizingPlayer) {
            bestValue = Integer.MIN_VALUE; // AI WANTS highest score
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (!isValid(col, gameBoard)) {
                    continue;
                }

                Space[][] newBoard = copyBoard(gameBoard);
                applyMove(newBoard, col, AI);

                int score = minmax(newBoard, depth - 1, end,false);

                if (score > bestValue) {
                    bestValue = score;
                    bestMove = col;
                }
            }
        } else {
            bestValue = Integer.MAX_VALUE; // Human WANTS the lowest score
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (!isValid(col, gameBoard)) {
                    continue;
                }

                Space[][] newBoard = copyBoard(gameBoard);
                applyMove(newBoard, col, HUMAN);

                int score = minmax(newBoard, depth - 1, end, true);

                if (score < bestValue) {
                    bestValue = score;
                }
            }
        }
        if (end == depth) {
            return bestMove;
        } else {
            return bestValue;
        }
    }

    private static boolean isValid(int col, Space[][] gameBoard) {
        if (col < 1 || col > 7) {
            return false;
        } else if (gameBoard[0][col - 1].getSymbol().equals(HUMAN.getSymbol()) || gameBoard[0][col - 1].getSymbol().equals(AI.getSymbol())) {
            return false;
        } else {
            return true;
        }
    }

    private static Space[][] copyBoard(Space[][] gameBoard) {
        Space[][] newBoard = new Space[gameBoard.length][gameBoard[0].length];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                newBoard[i][j] = new Space(gameBoard[i][j].getSymbol());
            }
        }
        return newBoard;
    }

    private static void applyMove(Space[][] board, int col, Player player) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col-1].getSymbol().equals("\uD83D\uDD18")) {
                board[row][col-1] = new Space(player.getSymbol());
                break;
            }
        }
    }

    private static int evaluateBoard(Space[][] gameBoard) {
        int score = 0;

        // Line eval for both (ADDS/SUbTRACTS) based on scenario
        score += evaluateCenter(gameBoard, AI.getSymbol(), HUMAN.getSymbol());

        // Line evals for AI (ADDS)
        score += evaluateLines(gameBoard, AI.getSymbol(), AI);

        // Line evals for Human (SUBTRACTS)
        score -= evaluateLines(gameBoard, HUMAN.getSymbol(), HUMAN);

        return score;
    }

    private static int evaluateCenter(Space[][] gameBoard, String aiSymbol, String humanSymbol) {
        int aiCount = 0;
        int humanCount = 0;

        for (int col = 2; col <= 4; col++) {
            for (int row = 0; row < gameBoard.length; row++) {
                if (gameBoard[row][col].getSymbol().equals(aiSymbol)) {
                    aiCount++;
                } else if (gameBoard[row][col].getSymbol().equals(humanSymbol)) {
                    humanCount++;
                }
            }
        }
        return (aiCount - humanCount) * 3;
    }

    private static int evaluateLines(Space[][] gameBoard, String symbol, Player player) {
        int score = 0;
        // Horizontal/Vertical/Diagonal checker for 4 in a row will be added here (will use the method in GameBoard) -> score += 500 if valid
        if (GameBoard.checkWin(player, gameBoard, true)) {
            score += 1000;
        }
        // Add a horizontal checker here that checks if this (symbol in the param) has 3 in a row -> if so, add score += 50
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[i][j + 1].getSymbol().equals(HUMAN.getSymbol())) {
                    if (gameBoard[i][j + 2].getSymbol().equals(HUMAN.getSymbol())) {
                        if (player.getSymbol().equals(HUMAN.getSymbol())) {
                            score += 120;
                        } else {
                            score += 100;
                        }
//                            player.win();
//                            System.out.println("Horizontal check left to right");
//                            return true;
                    }
                }
            }
        }
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = gameBoard[0].length - 1; j > 3; j--) {
                if (gameBoard[i][j].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[i][j - 1].getSymbol().equals(HUMAN.getSymbol())) {
                    if (gameBoard[i][j - 2].getSymbol().equals(HUMAN.getSymbol())) {
                        if (player.getSymbol().equals(HUMAN.getSymbol())) {
                            score += 120;
                        } else {
                            score += 100;
                        }
//                            player.win();
//                            System.out.println("Horizontal check right to left");
//                            return true;
                    }
                }
            }
        }

        // Add a horizontal checker here that checks if this (symbol in the param) has 2 in a row -> if so, add score += 5
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                if (gameBoard[i][j].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[i][j + 1].getSymbol().equals(HUMAN.getSymbol())) {
                    score += 8;
//                            player.win();
//                            System.out.println("Horizontal check left to right");
//                            return true;
                }
            }
        }
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = gameBoard[0].length - 1; j > 4; j--) {
                if (gameBoard[i][j].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[i][j - 1].getSymbol().equals(HUMAN.getSymbol())) {
                    score += 8;
//                            player.win();
//                            System.out.println("Horizontal check right to left");
//                            return true;
                }
            }
        }

        // Add a vertical checker here that checks if this (symbol in the param) has 3 in a row -> if so, add score += 50
        for (int i = 0; i < gameBoard[0].length - 1; i++) {
            for (int j = gameBoard.length - 1; j > 3; j--) {
                if (gameBoard[j][i].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[j - 1][i].getSymbol().equals(HUMAN.getSymbol())) {
                    if (gameBoard[j - 2][i].getSymbol().equals(HUMAN.getSymbol())) {
                        if (player.getSymbol().equals(HUMAN.getSymbol())) {
                            score += 120;
                        } else {
                            score += 100;
                        }
                    }
                }

            }
        }
        for (int i = gameBoard[0].length - 1; i > 0; i--) {
            for (int j = gameBoard.length - 1; j > 2; j--) {
                if (gameBoard[j][i].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[j - 1][i].getSymbol().equals(HUMAN.getSymbol())) {
                    if (gameBoard[j - 2][i].getSymbol().equals(HUMAN.getSymbol())) {
                        if (player.getSymbol().equals(HUMAN.getSymbol())) {
                            score += 120;
                        } else {
                            score += 100;
                        }
                    }
                }
            }
        }

        // Add a vertical checker here that checks if this (symbol in the param) has 2 in a row -> if so, add score += 5
        for (int i = 0; i < gameBoard[0].length - 1; i++) {
            for (int j = gameBoard.length - 1; j > 4; j--) {
                if (gameBoard[j][i].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[j - 1][i].getSymbol().equals(HUMAN.getSymbol())) {
                    score += 8;
                }

            }
        }
        for (int i = gameBoard[0].length - 1; i > 0; i--) {
            for (int j = gameBoard.length - 1; j > 3; j--) {
                if (gameBoard[j][i].getSymbol().equals(HUMAN.getSymbol()) && gameBoard[j - 1][i].getSymbol().equals(HUMAN.getSymbol())) {
                    score += 8;
                }
            }
        }

        //-------------------- FOR ME TO DO ------------------------
        // Diagonal checker for 3 in a row
        for (int rowSub = 0; rowSub < 2; rowSub++) {
            for (int colAdd = 0; colAdd < 5; colAdd++) {
                boolean win = true;
                int row = gameBoard.length - 1 - rowSub;
                int col = colAdd;
                for (int i = 0; i < 3; i++) {
                    if (!gameBoard[row][col].getSymbol().equals(symbol)) {
                        win = false;
                    }
                    col++;
                    row--;
                }
                if (win) {
                    if (player.getSymbol().equals(HUMAN.getSymbol())) {
                        score += 120;
                    } else {
                        score += 100;
                    }
                }
            }
        }
        for (int rowSub = 0; rowSub < 2; rowSub++) {
            for (int colSub = 0; colSub < 5; colSub++) {
                boolean win = true;
                int row = gameBoard.length - 1 - rowSub;
                int col = gameBoard[0].length - 1 - colSub;
                for (int i = 0; i < 3; i++) {
                    if (!gameBoard[row][col].getSymbol().equals(symbol)) {
                        win = false;
                    }
                    col--;
                    row--;
                }
                if (win) {
                    if (player.getSymbol().equals(HUMAN.getSymbol())) {
                        score += 120;
                    } else {
                        score += 100;
                    }
                }
            }
        }

        // Diagonal checker for 2 in a row
        for (int rowSub = 0; rowSub < gameBoard.length - 1; rowSub++) {
            for (int colAdd = 0; colAdd < gameBoard[0].length - 1; colAdd++) {
                boolean win = true;
                int row = gameBoard.length - 1 - rowSub;
                int col = colAdd;
                for (int i = 0; i < 2; i++) {
                    if (!gameBoard[row][col].getSymbol().equals(symbol)) {
                        win = false;
                    }
                    col++;
                    row--;
                }
                if (win) {
                    score += 8;
                }
            }
        }
        for (int rowSub = 0; rowSub < gameBoard.length - 1; rowSub++) {
            for (int colSub = 0; colSub < gameBoard[0].length - 1; colSub++) {
                boolean win = true;
                int row = gameBoard.length - 1 - rowSub;
                int col = gameBoard[0].length - 1 - colSub;
                for (int i = 0; i < 2; i++) {
                    if (!gameBoard[row][col].getSymbol().equals(symbol)) {
                        win = false;
                    }
                    col--;
                    row--;
                }
                if (win) {
                    score += 8;
                }
            }
        }

        return score;
    }

    private static boolean isTerminal(Space[][] gameBoard) {
        return isFull(gameBoard);
    }

    private static boolean isFull(Space[][] gameBoard) {
        for (int col = 0; col < gameBoard[0].length; col++) {
            if (gameBoard[0][col].getSymbol().equals("\uD83D\uDD18")) {
                return false;
            }
        }
        return true;
    }
}
