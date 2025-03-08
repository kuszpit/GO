package go.game.frames;

import go.game.Database.Move;
import go.game.Database.SQLLoadGame;
import go.game.Database.SQLSaveGame;
import go.game.drawing.Board;
import go.game.drawing.Stone;
import go.game.drawing.DrawableElement;
import go.game.frames.style.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReplayGameFrame extends JFrame {

    private static Map<Point, DrawableElement> elements;
    private static final int boardSize = 19;
    private final int cellSize = 30;
    private static int gameId;
    private int maxMoveId;
    private int iterator = 0;


    public ReplayGameFrame(int gameId) {
        this.gameId = gameId;

        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        SQLLoadGame sqlLoadGame = new SQLLoadGame();
        Move move;
        maxMoveId = sqlSaveGame.getIDMove(gameId);
        String[] typeOfMove = new String[maxMoveId-1];
        int[] xOfMove = new int[maxMoveId-1];
        int[] yOfMove = new int[maxMoveId-1];
        String[] colorOfMove = new String[maxMoveId-1];
        String winner = sqlLoadGame.getWinner(gameId);
        Color loserColor;
        int x;
        int y;
        String colorString;
        Color color;
        ArrayList<Integer> xToDelete = new ArrayList<Integer>();
        ArrayList<Integer> yToDelete = new ArrayList<Integer>();

        if(Objects.equals(winner, "BLACK")) {
            loserColor = Color.WHITE;
        }
        else {
            loserColor = Color.BLACK;
        }



        for (int i = 1; i < maxMoveId; i++) {
            move = sqlLoadGame.getMove(gameId, i);
            typeOfMove[i-1] = move.getTypeOfMove();
            xOfMove[i-1] = move.getX();
            yOfMove[i-1] = move.getY();
            colorOfMove[i-1] = move.getColor();
        }

        // size
        setSize(700, 607);

        // title
        setTitle("GO - replay game " + gameId);

        // create elements map
        elements = new HashMap<>();

        // set content pane
        setLayout(new BorderLayout());

        // drawing panel with board
        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        // game id label
        JLabel gameIdLabel = new JLabel("GAME ID: " + gameId);
        gameIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // next move button
        MyButton nextButton = new MyButton("next move");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(iterator < maxMoveId-1){
                    int x = xOfMove[iterator];
                    int y = yOfMove[iterator];

                    String colorString = colorOfMove[iterator];
                    Color color = null;
                    if (Objects.equals(colorString, "WHITE")) {
                        color = Color.WHITE;
                    }
                    else if (Objects.equals(colorString, "BLACK")) {
                        color = Color.BLACK;
                    }

                    if(Objects.equals(typeOfMove[iterator], "add")) {
                        addStone(x, y, color);
                        for(int i = 0; i < xToDelete.size(); i++){
                            removeStone(xToDelete.get(i), yToDelete.get(i));
                        }
                        xToDelete.clear();
                        yToDelete.clear();
                        drawingPanel.repaint();
                    }
                    else if (Objects.equals(typeOfMove[iterator], "delete")) {
                        xToDelete.add(x);
                        yToDelete.add(y);
                    }
                    drawingPanel.repaint();
                    iterator++;
                }
                else if (iterator == maxMoveId-1) {
                    new GameOverFrame(loserColor);
                }
            }
        });

        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createVerticalStrut(10));


        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        add(mainPanel, BorderLayout.EAST);

        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(gameIdLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(Box.createVerticalStrut(5));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // draw the board
            Board board = new Board();
            board.draw(g);

            // draw stones
            for (Map.Entry<Point, DrawableElement> entry : elements.entrySet()) {
                Point position = entry.getKey();
                DrawableElement element = entry.getValue();

                int x = position.x * 30; // assuming cell size is 30
                int y = position.y * 30;

                g.translate(x, y);
                element.draw(g);
                g.translate(-x, -y);
            }
        }
    }

    private void addStone(int x, int y, Color color) {
        elements.put(new Point(x, y), Stone.addStone(color));
    }
    private void removeStone(int x, int y) {
        elements.remove(new Point(x, y));
    }

}
