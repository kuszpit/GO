package go.game.logic;

import java.awt.*;

public interface LogicStrategy {
    int countBreath(int[][] board, int column, int row);
    int countBreathHypothetical(int[][] board, int column, int row, Color color);
    boolean ifAlreadyOccupied(int[][] board, int x, int y);
    void updateBoard(int[][] board, int x, int y, Color color);
    char getElement(int[][] board, int x, int y);
    void removeStonesWithoutBreath(int[][] board, Color color);
    boolean checkRemoveStones(int[][] board, Color color, int x, int y);
    int[] moveBot(int[][] board);
}
