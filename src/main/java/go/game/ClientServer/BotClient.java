package go.game.ClientServer;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BotClient implements Runnable {
    private static final int PLAYER2 = 2;
    private static final int boardSize = 19;
    private char[][] board = new char[boardSize][boardSize];

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    private boolean continueToPlay = true;
    private char myColor = ' ';
    private char otherColor = ' ';
    private int rowSelected;
    private int columnSelected;
    private static BotClient bot;

    public BotClient() {
        bot = new BotClient();
        bot.connectToServer();
        myColor = 'W';  // Assuming the bot is always white
        otherColor = 'B';
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 666);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
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

            if (player == PLAYER2) {
                while (continueToPlay) {
                    waitForMove();
                    sendMove();
                    receiveInfoFromServer();
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForMove() throws InterruptedException {
        // Simulate bot's decision (you can replace this with your bot logic)
        // For example, randomly select a move
        rowSelected = (int) (Math.random() * boardSize);
        columnSelected = (int) (Math.random() * boardSize);

        // Simulate a short delay to make it more realistic
        Thread.sleep(500);
    }

    private void sendMove() throws IOException {
        toServer.writeInt(rowSelected);
        toServer.writeInt(columnSelected);
    }

    private void receiveInfoFromServer() throws IOException {
        receiveMove();
        updateMove(rowSelected, columnSelected);
    }

    private void receiveMove() throws IOException {
        int row = fromServer.readInt();
        int column = fromServer.readInt();

        if (row != -1 && column != -1) {
            Color color = (otherColor == 'B') ? Color.BLACK : Color.WHITE;
            System.out.println("Opponent's move: (" + row + ", " + column + ")");
        } else {
            System.out.println("Your turn.");
        }
    }

    public void updateMove(int x, int y) {
        columnSelected = x;
        rowSelected = y;
    }
}
