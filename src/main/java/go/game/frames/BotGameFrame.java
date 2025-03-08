package go.game.frames;

import go.game.ClientServer.Client;
import go.game.Database.AddingMoveHandle;
import go.game.Database.Move;
import go.game.Database.SQLSaveGame;
import go.game.logic.DefaultLogicStrategy;
import go.game.logic.Logic;
import go.game.drawing.Board;
import go.game.drawing.Stone;
import go.game.drawing.DrawableElement;
import go.game.frames.style.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BotGameFrame extends JFrame {

    private static Map<Point, DrawableElement> elements;
    private static JTextPane text;
    private static final int boardSize = 19;
    private final int cellSize = 30;
    private static Color playerColor = null;
    private static Color thisColor;
    public static int rowSelected = -1;
    public static int columnSelected = -1;
    private static boolean sendMove = false;
    private static boolean yourTurn;
    public static boolean stop = false;
    private static int gameId;
    static Logic logic = new Logic(new DefaultLogicStrategy());
    private static JLabel captivesLabel;
    private static int captivesCountForBlack = 0;
    private static int captivesCountForWhite = 0;
    private static Point lastMove = null;
    private int[] botMove = new int[2];


    public BotGameFrame(Color color) {

        playerColor = color;
        thisColor = color;

        // size
        setSize(848, 607);

        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        gameId = sqlSaveGame.getIDGame();

        // title
        if(playerColor == Color.BLACK){
            setTitle("GO - game with bot");
            gameId += 1;
            yourTurn = true;
        } else {
            setTitle("GO - player 2");
            yourTurn = false;
        }

        // create elements map
        elements = new HashMap<>();

        // set content pane
        setLayout(new BorderLayout());

        // drawing panel with board
        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);


        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Calculate the position of the click in terms of the grid
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;

                if((x < boardSize) && (y < boardSize) && Logic.ifAlreadyOccupied(x, y) && yourTurn && ifHasBreath(x, y)){
                    Point currentMove = new Point(x, y);
                    if (lastMove != null && lastMove.equals(currentMove)) {
                        return; // Invalid move, exit the method
                    }

                    lastMove = new Point(x, y);

                    addStone(x, y, playerColor);
                    botMove = logic.moveBot();
                    addStone(botMove[0],botMove[1], Color.WHITE);


                    // Odświeżenie widoku
                    drawingPanel.repaint();
                }
            }
        });


        // game id label
        JLabel gameIdLabel = new JLabel("GAME ID: " + gameId);
        gameIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // black won button
        MyButton blackButton = new MyButton("black won");
        blackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameOverFrame(Color.WHITE);
                setWinner(Color.BLACK);
            }
        });

        blackButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        // white won button
        MyButton whiteButton = new MyButton("white won");
        whiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameOverFrame(Color.BLACK);
                setWinner(Color.WHITE);
            }
        });

        whiteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // captives label
        captivesLabel = new JLabel();
        captivesLabel.setText(updateCaptivesCount(0));
        captivesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // text panel
        text = new JTextPane();
        text.setText("GO game started                                               \n");        //do not touch the spaces, they are very important
        text.setEditable(false);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        textPanel.add(text);

        // scroll pane
        JScrollPane scrollPane = new JScrollPane(text);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textPanel.add(scrollPane, BorderLayout.EAST);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(blackButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(whiteButton);

        //captives panel
        JPanel captivesPanel = new JPanel();
        captivesPanel.setLayout(new BoxLayout(captivesPanel, BoxLayout.Y_AXIS));

        captivesPanel.add(Box.createVerticalStrut(5));
        captivesPanel.add(captivesLabel);
        captivesPanel.add(Box.createVerticalStrut(5));

        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        add(mainPanel, BorderLayout.EAST);

        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(gameIdLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(captivesPanel, BorderLayout.CENTER);
        mainPanel.add(textPanel);
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

    public void addStoneToDatabase(int gameId, int x, int y) {
        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        Move move = new Move.Builder(gameId, sqlSaveGame.getIDMove(gameId))
                .typeOfMove("add")
                .x(x)
                .y(y)
                .color((playerColor == Color.BLACK) ? "BLACK" : "WHITE")
                .build();

        AddingMoveHandle addingMoveHandle = new AddingMoveHandle(sqlSaveGame);
        addingMoveHandle.handle(move);
    }

    public static void addDeleteToDatabase(int gameId, int x, int y) {
        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        Move move = new Move.Builder(gameId, sqlSaveGame.getIDMove(gameId))
                .typeOfMove("delete")
                .x(x)
                .y(y)
                .color((playerColor != Color.BLACK) ? "BLACK" : "WHITE")
                .build();

        AddingMoveHandle addingMoveHandle = new AddingMoveHandle(sqlSaveGame);
        addingMoveHandle.handle(move);
    }

    public static void removeStone(int x, int y) {
        char color = logic.getElement(x, y);
        elements.remove(new Point(x, y));
        if (!String.valueOf(color).equals((playerColor == Color.BLACK) ? "B" : "W")) addDeleteToDatabase(gameId ,x ,y);
        if (color == 'B') {
            captivesCountForWhite++;
        }
        else if (color == 'W') {
            captivesCountForBlack++;
        }

        Color playerColor = getPlayerColor();
        if(playerColor == Color.BLACK) {
            captivesLabel.setText(updateCaptivesCount(captivesCountForBlack));
        }
        else if (playerColor == Color.WHITE) {
            captivesLabel.setText(updateCaptivesCount(captivesCountForWhite));
        }
    }
    public static void addStone (int x, int y, Color playerColor) {
        elements.put(new Point(x, y), Stone.addStone(playerColor));
        logic.updateBoard(x, y, playerColor);
        if (playerColor == Color.WHITE) logic.removeStonesWithoutBreath(Color.BLACK);
        else logic.removeStonesWithoutBreath(Color.WHITE);
        logic.updateBoard(x, y, playerColor);

        // Aktualizacja tekstu w JTextPane
        String currentText = text.getText();
        String newText = currentText + String.format("Stone added at coordinates (%d, %d)\nOpponent's turn.\n\n", x, y);
        text.setText(newText);

        // Odświeżenie widoku
        //drawingPanel.repaint();

    }

    private static String updateCaptivesCount(int count) {
        return "captives: " + count;
    }

    public void skipMove(Client client) {
        if (yourTurn) {
            setRowSelected(-1);
            setColumnSelected(-1);
            setMove(true);
            client.updateMove(rowSelected, columnSelected);
            yourTurn = false;
            String currentText = text.getText();
            String newText = currentText + "You skipped move.\nOpponent's turn.\n\n";
            text.setText(newText);
        }
    }

    private static Color getPlayerColor() {
        return thisColor;
    }
    public void setMove(boolean b) {
        sendMove = b;
    }

    public boolean getMove() {
        return sendMove;
    }

    private void setRowSelected(int x) {
        rowSelected = x;
    }

    private void setColumnSelected(int y) {
        columnSelected = y;
    }

    public static void setYourTurn() {
        yourTurn = true;
        String currentText = text.getText();
        String newText = currentText + "Opponent skipped move.\nYour turn\n\n";
        text.setText(newText);
        //skipCount += 1;
    }

    public static void setTurn(boolean b) {
        yourTurn = b;
    }
    public static boolean getTurn() {
        return yourTurn;
    }

    public static void addOpponentsMove(int x, int y, Color playerColor) {
        elements.put(new Point(x, y), Stone.addStone(playerColor));
        logic.updateBoard(x, y, playerColor);
        if (playerColor == Color.WHITE) logic.removeStonesWithoutBreath(Color.BLACK);
        else logic.removeStonesWithoutBreath(Color.WHITE);
        yourTurn = true;
        String currentText = text.getText();
        String newText = currentText + String.format("Stone added at coordinates (%d, %d).\nYour turn. \n\n", x, y);
        text.setText(newText);
        //skipCount = 0;
    }

    public boolean ifHasBreath(int x,int y) {
        Color color = (playerColor == Color.WHITE) ? Color.WHITE : Color.BLACK;
        return (logic.countBreathHypothetical(x, y, playerColor) != 0 || logic.checkRemoveStones(color, x, y));
    }

    public static void setStop(boolean b) {
        stop = b;
    }

    public void setWinner(Color winner) {
        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        sqlSaveGame.setWinner(winner ,sqlSaveGame.getIDGame());
    }
}