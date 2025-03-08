package go.game.ClientServer;

import go.game.frames.GameFrame;

import java.awt.*;
import java.net.*;
import java.io.*;

public class Client implements Runnable {
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    private boolean myTurn = false;
    private static final int boardSize = 19;
    public static char[][] board = new char[boardSize][boardSize];

    private int rowSelected;
    private int columnSelected;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    private boolean continueToPlay = true;
    public char myColor = ' ';
    public char otherColor = ' ';
    private static Client client;
    public GameFrame gameFrame;
    private static boolean skip = false;


    public static void main(String[] args) {
        client = new Client();
        client.connectToServer();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++){
                board[i][j] = ' ';
            }
        }
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 666);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            int player = fromServer.readInt();
            System.out.println(player);

            if (player == PLAYER1) {
                myColor = 'B';
                otherColor = 'W';
                gameFrame = new GameFrame(Color.BLACK, client);

                myTurn = true;
            } else if (player == PLAYER2) {
                myColor = 'W';
                otherColor = 'B';
                gameFrame = new GameFrame(Color.WHITE, client);
            }

            while (continueToPlay) {
                if (myTurn) {
                    waitForMove();
                    sendMove();
                    receiveInfoFromServer();
                } else {
                    receiveInfoFromServer();
                    waitForMove();
                    sendMove();
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForMove() throws InterruptedException {
        while (!(gameFrame.getMove() || skip)) {
            Thread.sleep(100);
        }
        if (gameFrame.getMove()) {
            gameFrame.setMove(false);
            myTurn = false;
        } else {
            gameFrame.setMove(false);
            GameFrame.setTurn(false);
            updateMove(-1, -1);
            myTurn = false;
        }
    }


    private void sendMove() throws IOException {
        toServer.writeInt(rowSelected);
        toServer.writeInt(columnSelected);
    }

    private void receiveMove() throws IOException {
        int row = fromServer.readInt();
        int column = fromServer.readInt();

        if (row != -1 && column != -1) {
            Color color = (otherColor == 'B') ? Color.BLACK : Color.WHITE;
            GameFrame.addOpponentsMove(column, row, color);
            gameFrame.repaint();
        }
        else GameFrame.setYourTurn();
    }

    private void receiveInfoFromServer() throws IOException {
        receiveMove();
        myTurn = true;
        updateMove(rowSelected, columnSelected);
    }

    public void updateMove(int x, int y) {
        columnSelected = x;
        rowSelected = y;
    }

    public void surrenderPlayer(Color color) {
        continueToPlay = false;
        System.out.println(color);
    }
}
