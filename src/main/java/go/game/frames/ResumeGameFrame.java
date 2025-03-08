package go.game.frames;

import go.game.ClientServer.NewGame;
import go.game.frames.style.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResumeGameFrame extends JFrame {
    public static boolean resume = false;
    private static int playerWhoStarts;

    public ResumeGameFrame() {
        NewGame.afterSkipFrame.dispose();
        //size
        setSize(500, 400);

        //title
        setTitle("GO - resume game");

        //main components panel
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.PAGE_AXIS));

        JLabel choosePlayerLabel = new JLabel("Choose player who starts:");
        choosePlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //game mode buttons
        MyButton player1Button = new MyButton("player 1");

        MyButton player2Button = new MyButton("player 2");
        player1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerWhoStarts = 1;
            }
        });
        player2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerWhoStarts = 2;
            }
        });

        //button start
        MyButton resumeGameButton = new MyButton("resume game");

        resumeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resume = true;
                dispose();
            }
        });

        //choose player label panel
        JPanel choosePlayerLabelPanel = new JPanel();
        choosePlayerLabelPanel.setLayout(new BoxLayout(choosePlayerLabelPanel, BoxLayout.X_AXIS));

        choosePlayerLabelPanel.add(choosePlayerLabel);

        //choose player panel
        JPanel choosePlayerPanel = new JPanel();
        choosePlayerPanel.setLayout(new BoxLayout(choosePlayerPanel, BoxLayout.X_AXIS));

        choosePlayerPanel.add(player1Button);
        choosePlayerPanel.add(Box.createHorizontalStrut(10));
        choosePlayerPanel.add(player2Button);


        //resume game button panel
        JPanel resumeGamePanel = new JPanel();
        resumeGamePanel.setLayout(new BoxLayout(resumeGamePanel, BoxLayout.X_AXIS));

        resumeGamePanel.add(resumeGameButton);


        componentsPanel.add(Box.createVerticalGlue());
        componentsPanel.add(choosePlayerLabelPanel);
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(choosePlayerPanel);
        componentsPanel.add(Box.createVerticalStrut(10));
        componentsPanel.add(resumeGamePanel);
        componentsPanel.add(Box.createVerticalGlue());

        // Center the panel on the frame
        setLayout(new BorderLayout());
        add(componentsPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setResizable(false);
    }

    public static int getPlayerWhoStarts() {
        return playerWhoStarts;
    }

    public static boolean getResume() { return resume; }
}
