package go.game.frames;

import go.game.ClientServer.NewGame;
import go.game.Database.SQLAddGame;
import go.game.Database.SQLSaveGame;
import go.game.frames.style.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseWinnerFrame extends JFrame {
    private static Color loser;

    public ChooseWinnerFrame() {
        //size
        setSize(500, 400);

        //title
        setTitle("GO - choose winner");

        NewGame.afterSkipFrame.dispose();

        //main components panel
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.PAGE_AXIS));

        JLabel chooseWinnerLabel = new JLabel("Choose winner:");
        chooseWinnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //who wins buttons
        MyButton player1Button = new MyButton("player 1");

        MyButton player2Button = new MyButton("player 2");
        player1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loser = Color.WHITE;
                setWinner(Color.BLACK);
            }
        });
        player2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loser = Color.BLACK;
                setWinner(Color.WHITE);
            }
        });

        //button start
        MyButton endGameButton = new MyButton("end game");

        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameOverFrame(loser);
                //setVisible(false);
                dispose();
            }
        });

        //choose winner label panel
        JPanel chooseWinnerLabelPanel = new JPanel();
        chooseWinnerLabelPanel.setLayout(new BoxLayout(chooseWinnerLabelPanel, BoxLayout.X_AXIS));

        chooseWinnerLabelPanel.add(chooseWinnerLabel);

        //choose player panel
        JPanel choosePlayerPanel = new JPanel();
        choosePlayerPanel.setLayout(new BoxLayout(choosePlayerPanel, BoxLayout.X_AXIS));

        choosePlayerPanel.add(player1Button);
        choosePlayerPanel.add(Box.createHorizontalStrut(10));
        choosePlayerPanel.add(player2Button);


        //end game button panel
        JPanel endGamePanel = new JPanel();
        endGamePanel.setLayout(new BoxLayout(endGamePanel, BoxLayout.X_AXIS));

        endGamePanel.add(endGameButton);


        componentsPanel.add(Box.createVerticalGlue());
        componentsPanel.add(chooseWinnerLabelPanel);
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(choosePlayerPanel);
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(endGamePanel);
        componentsPanel.add(Box.createVerticalGlue());

        // Center the panel on the frame
        setLayout(new BorderLayout());
        add(componentsPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setResizable(false);
    }

    public void setWinner(Color winner) {
        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        sqlSaveGame.setWinner(winner ,sqlSaveGame.getIDGame());
    }
}
