public class Ai {
    private static int MAX = 100000;
    private static int MIN = -100000;

    public Ai() {}

    public static int minmax(String[][] gameboard, int depth, boolean maximizingPlayer) {
        int index = 0;
        //Ends once the calculations if done | Recursion
        if (depth == 5) {
            // return best eval / position here
            return 1;
        }

        if (maximizingPlayer) {
            for (int i = 0; i < gameboard.length; i++) {
                for (int j = 0; j < gameboard[0].length; j++) {
                    int eval = minmax(gameboard, depth - 1, false);
                    MAX = Math.max(MAX, eval);
                }
            }
        }


        return 1;
    }
}
