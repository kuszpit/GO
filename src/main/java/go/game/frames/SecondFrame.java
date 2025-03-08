package go.game.frames;

import go.game.frames.style.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecondFrame extends JFrame {
    public static int gameMode = -1; // 0 - 2 player game, 1 - game with bot
    public static int boardSize = 19;
    public boolean setGameMode = false;
    public static boolean startGame = false;
    private boolean gameWithBot = false;

    public SecondFrame() {
        //size
        setSize(700, 600);

        //title
        setTitle("GO - choose game mode");



        //main components panel
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.PAGE_AXIS));

        //game mode buttons
        MyButton button2PlayerGame = new MyButton("2 PLAYER GAME");

        MyButton buttonGameWithBot = new MyButton("GAME WITH BOT");
        button2PlayerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameMode(0);
                setGameMode = true;
            }
        });
        buttonGameWithBot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWithBot = true;
                setGameMode(1);
                setGameMode = true;
            }
        });

        //game mode buttons panel
        JPanel modeButtonsPanel = new JPanel();
        modeButtonsPanel.setLayout(new BoxLayout(modeButtonsPanel, BoxLayout.X_AXIS));

        //adding game mode buttons to panel
        modeButtonsPanel.add(button2PlayerGame);
        modeButtonsPanel.add(Box.createHorizontalStrut(10));
        modeButtonsPanel.add(buttonGameWithBot);

        boardSize = 19;

        //button start
        MyButton startButton = new MyButton("start game");

        JLabel errorLabel = new JLabel(" ");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (setGameMode) {
                    setStartGame(true);
                    errorLabel.setText("Waiting for players to join.");
                } else {
                    errorLabel.setText("You have to choose game mode!");
                }
                if (gameWithBot) {
                    new BotGameFrame(Color.BLACK);
                }
            }
        });

        //start button panel
        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new BoxLayout(startButtonPanel, BoxLayout.X_AXIS));

        //adding start button to panel
        startButtonPanel.add(startButton);

        //error label panel
        JPanel errorLabelPanel = new JPanel();
        errorLabelPanel.setLayout(new BoxLayout(errorLabelPanel, BoxLayout.X_AXIS));

        //adding start button to panel
        errorLabelPanel.add(errorLabel);

        componentsPanel.add(Box.createVerticalGlue());
        componentsPanel.add(modeButtonsPanel);
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(startButtonPanel);
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(errorLabelPanel);
        componentsPanel.add(Box.createVerticalGlue());

        // Center the panel on the frame
        setLayout(new BorderLayout());
        add(componentsPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setResizable(false);
    }

    public static int getGameMode() {
        return gameMode;
    }

    public static void setStartGame(boolean value) {
        startGame = value;
    }

    public static boolean getStartGame() {
        return startGame;
    }

    public static void setGameMode(int value) {
        gameMode = value;
    }
}