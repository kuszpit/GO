package go.game.logic;

import go.game.frames.GameFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class DefaultLogicStrategy implements  LogicStrategy{
    @Override
    public int countBreath(int[][] board, int column, int row) {
        char element = getElement(board, column, row);
        int breath = 0;
        boolean[][] visited = new boolean[board.length][board[0].length]; // tablica do śledzenia odwiedzonych pól
        Stack<int[]> stack = new Stack<>(); // śledzenie współrzędnych do odwiedzenia
        stack.push(new int[]{column, row}); // dodanie na stos początkowych współrzędnych

        while (!stack.isEmpty()) {
            int[] currentPosition = stack.pop();
            int currentColumn = currentPosition[0];
            int currentRow = currentPosition[1];

            if (currentColumn < 0 || currentColumn >= 19 || currentRow < 0 || currentRow >= 19 || visited[currentColumn][currentRow]) {
                continue;
            }

            visited[currentColumn][currentRow] = true;

            char checkedElement = getElement(board, currentColumn, currentRow);

            if (checkedElement == ' ') {
                breath++;
            } else if (checkedElement == element) {
                stack.push(new int[]{currentColumn, currentRow - 1});
                stack.push(new int[]{currentColumn, currentRow + 1});
                stack.push(new int[]{currentColumn - 1, currentRow});
                stack.push(new int[]{currentColumn + 1, currentRow});
            }
        }

        return breath;
    }

    @Override
    public int countBreathHypothetical(int[][] board, int column, int row, Color color) {
        int breath = 0;
        if (color == Color.BLACK) board[column][row] = 'B';
        else board[column][row] = 'W';
        breath = countBreath(board, column, row);
        board[column][row] = ' ';
        return breath;
    }

    @Override
    public boolean ifAlreadyOccupied(int[][] board, int x, int y) {
        System.out.println(board[x][y]);
        return !(board[x][y] == 'W' || board[x][y] == 'B');
    }

    @Override
    public void updateBoard(int[][] board, int x, int y, Color color) {
        if (color == Color.BLACK) board[x][y] = 'B';
        else if (color == Color.WHITE) board[x][y] = 'W';
        else board[x][y] = ' ';
    }

    @Override
    public char getElement(int[][] board, int x, int y) {
        return (char) board[x][y];
    }

    @Override
    public void removeStonesWithoutBreath(int[][] board, Color color) {
        ArrayList<Integer> coordinatesToDelete = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if(countBreath(board, i, j) == 0) {
                    char colorPlayer = (color == Color.BLACK) ? 'B' : 'W';
                    if (colorPlayer == getElement(board ,i, j)) {
                        coordinatesToDelete.add(i);
                        coordinatesToDelete.add(j);
                    }
                }
            }
        }

        for (int i = 0; i < coordinatesToDelete.size(); i=i+2){
            int xToDelete = coordinatesToDelete.get(i);
            int yToDelete = coordinatesToDelete.get(i+1);
            GameFrame.removeStone(xToDelete, yToDelete);
            updateBoard(board, xToDelete, yToDelete, null);
        }
    }

    @Override
    public boolean checkRemoveStones(int[][] board, Color color, int x, int y) {
        if (color == Color.BLACK) board[x][y] = 'B';
        else board[x][y] = 'W';
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if(countBreath(board, i, j) == 0) {
                    char colorPlayer = (color == Color.BLACK) ? 'W' : 'B';
                    if (colorPlayer == getElement(board ,i, j)) {
                        board[x][y] = ' ';
                        return true;
                    }
                }
            }
        }
        board[x][y] = ' ';
        return false;
    }

    @Override
    public int[] moveBot(int[][] board) {
        int[] move = new int[2];
        char colorPlayer = 'B';

        for (int i = 1; i < 18; i++) {
            for (int j = 1; j < 18; j++) {
                if(countBreath(board, i, j) == 1) {
                    if (colorPlayer == getElement(board ,i, j)) {
                        move[0] = i;
                        move[1] = j;
                        if (getElement(board,move[0] + 1, move[1]) == ' ') move[0] += 1;
                        else if (getElement(board,move[0] - 1, move[1]) == ' ')  move[0] -= 1;
                        else if (getElement(board,move[0], move[1] + 1) == ' ')  move[1] += 1;
                        else if ((getElement(board,move[0], move[1] - 1) == ' '))  move[1] -= 1;
                        return move;
                    }
                }
            }
        }

        for (int i = 1; i < 18; i++) {
            for (int j = 1; j < 18; j++) {
                if (colorPlayer == getElement(board ,i, j)) {
                    move[0] = i;
                    move[1] = j;
                    if (getElement(board,move[0] + 1, move[1]) == ' ') move[0] += 1;
                    else if (getElement(board,move[0] - 1, move[1]) == ' ')  move[0] -= 1;
                    else if (getElement(board,move[0], move[1] + 1) == ' ')  move[1] += 1;
                    else if ((getElement(board,move[0], move[1] - 1) == ' '))  move[1] -= 1;
                    return move;
                }

            }
        }
        return null;
    }

}
