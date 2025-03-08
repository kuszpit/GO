package go.game.logic;


import java.awt.*;
import java.util.Objects;

public class Logic {
    private static final int boardSize = 19;
    public static int[][] board = new int[boardSize][boardSize];
    private static LogicStrategy logicStrategy;

    public Logic(LogicStrategy logicStrategy) {
        this.logicStrategy = logicStrategy;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++){
                board[i][j] = ' ';
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int countBreathHypothetical(int column, int row, Color color) {
        return logicStrategy.countBreathHypothetical(board, column, row, color);
    }

    public int countBreath(int x, int y){ return logicStrategy.countBreath(board, x, y); }

    public void removeStonesWithoutBreath(Color color) {
        logicStrategy.removeStonesWithoutBreath(board, color);
    }

    public static boolean ifAlreadyOccupied(int x, int y) {
        return logicStrategy.ifAlreadyOccupied(board, x, y);
    }

    public static void updateBoard(int x, int y, Color color) {
        logicStrategy.updateBoard(board, x, y, color);
    }

    public char getElement(int x, int y) {
        return (char) logicStrategy.getElement(board, x, y);
    }

    public boolean checkRemoveStones(Color color, int x, int y) { return logicStrategy.checkRemoveStones(board, color, x, y);}

    public int[] moveBot() { return logicStrategy.moveBot(board); }
}
