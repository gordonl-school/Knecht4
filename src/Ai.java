import java.util.ArrayList;

public class Ai {
    private static int MAX = 100000;
    private static int MIN = -100000;

    private static Player HUMAN;
    private static Player AI;
    private static ArrayList<Integer> availableMoves;
    private static Space[][] copyOfMain;

    public Ai() {
        HUMAN = new Player("\\uD83D\\uDD34", "Human");
        AI = new Player("\\uD83C\\uDF49", "AI");
        availableMoves = new ArrayList<>();
    }

    public static int minMax(Space[][] gameBoard) {
        copyOfMain = new Space[gameBoard.length][gameBoard[0].length];
        // for loop to copy elements over

        return minmax(copyOfMain, 3, true);  // true == computer
    }

    private static int minmax(Space[][] gameBoard, int depth, boolean maximizingPlayer) {
        int index = 0;
        // Ends once the calculations if done | Recursion
        if (depth == 0 || isTerminal(gameBoard)) {
            // return best eval / position here
            return MAX;
        }
    /*
        1.) Loop through each of the positions
        2.) Check if it is a valid move
        3.) Make the move
        4.) Evaluate the position
        5.) Assign it to a var?
        6.) Call the minmax method again recursively
     */

        // Generate all the moves possible -> Put it into an array/score it on that iteration
        // Score each position -> It depends on the way that you want to do it (different scenarios for connect 4) -> Look up what is a winning position on connect 4
        // Positions and scored based on whether its near any of its friendly pieces -> Have the ai move to where the other pieces are
        //

        //
        // Run minimax on each of the scores (it's going to be like a tree)
        if (maximizingPlayer) {
            int[] availMoves = findAvailableMoves(gameBoard);
//            int bestMove =
//            for col in availMoves {
//                int score = determineMoveScore(col);
//                if score > bestMove, bestMove = score
//
//            }
//            makeMove(gameBoard, bestMove, maximizingPlayer);

            //-------
            int[] availMoves = findAvailableMoves(gameBoard);
            int bestMove = minmax(gameBoard, depth - 1, false);
            makeMove(gameBoard, bestMove, maximizingPlayer);
        }

//        if (maximizingPlayer) {
//            for (int i = 0; i < gameboard.length; i++) {
//                for (int j = 0; j < gameboard[0].length; j++) {
//                    int eval = minmax(copyOfMain, depth - 1, false);
//                    MAX = Math.max(MAX, eval);
//                }
//            }
//        }


        return 1;
    }

//    public static int[] findAvailableMoves(Space[][] gameBoard) {
//        for (int col = 0; col < gameBoard[0].length; col++) {
//            if (GameBoard.isValid(col, gameBoard, AI.getSymbol(), HUMAN.getSymbol())) {
//                availableMoves.add(col);
//            }
//        }
//    }

    // int determineMoveScore(int col) {


    /* Loop through a list and checks if the move is valid, if it is valid then
     Make a method that takes the gameBoard as a param and then plays a move there
     The move then gets placed within the gameBoard and the gameBoard gets returned
     The gameBoard is returned then assigned to a var that will then pass that gameBoard through a method that checks whether the move is good or not (goes through a min-max)
     After running min-max we will compare the scores returned by the method that checks the position of the board
     If the score is higher for the maximizing player (compare it with the maxEval) then if it is higher, then set the col (the move that the ai will make)
     to be equal to that col with the highest evaluation
     **At the very end return that col and then make the ai move in the gameBoard class
     */

    public static void makeMove(Space[][] game, int col, boolean player) {
        // apply move to game, insert move for that player
    }




//    public static Space[][] makeMove(Space[][] board, int col, Player player) {
//        Space[][] newBoard = copyBoard(board);
//        for (int row = board.length - 1; row >= 0; row--) {
//            if (newBoard[row][col] == null) {
//                newBoard[row][col] = new Space(player.getSymbol());
//                break;
//            }
//        }
//        return newBoard;
//    }


    public static int getBestMove(Space[][] gameBoard) {
        int bestMove = -1;
        int bestValue = MIN;

        for (int col = 0; col < gameBoard[0].length; col++) {

        }
    }


    public static boolean isTerminal(Space[][] gameBoard) {
        return GameBoard.checkWin(HUMAN, gameBoard) || GameBoard.checkWin(AI, gameBoard) || GameBoard.boardFull(gameBoard);
    }

    public static int evaluateBoard(Space[][] gameBoard) {

    }
}
