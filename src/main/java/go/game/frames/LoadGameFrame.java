package go.game.frames;

import go.game.Database.SQLSaveGame;
import go.game.frames.style.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadGameFrame extends JFrame {
    private int maxGameID;
    private int selectedGameNumber;
    public LoadGameFrame() {
        // size
        setSize(700, 600);

        // title
        setTitle("GO");

        SQLSaveGame sqlSaveGame = new SQLSaveGame();
        maxGameID = sqlSaveGame.getIDGame();

        // main components panel
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.PAGE_AXIS));

        // title label
        JLabel titleLabel = new JLabel("Choose game you want to load:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // title label panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(20));

        // choosing game dropdown list
        String[] gameNumbers = setGames();
        JComboBox<String> gameDropdown = new JComboBox<>(gameNumbers);
        //gameDropdown.setSize(new Dimension(300, 30));
        gameDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGame = (String) gameDropdown.getSelectedItem();
                selectedGameNumber = Integer.parseInt(selectedGame.replaceAll("\\D", ""));
            }
        });


        //game dropdown panel
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
        gamePanel.add(Box.createHorizontalStrut(200));
        gamePanel.add(gameDropdown);
        gamePanel.add(Box.createHorizontalStrut(200));

        // start button
        MyButton startButton = new MyButton("Start loading game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReplayGameFrame(selectedGameNumber);
                dispose();
            }


        });

        // start label panel
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.X_AXIS));
        startPanel.add(startButton);

        componentsPanel.add(Box.createVerticalStrut(240));
        componentsPanel.add(titleLabel);
        componentsPanel.add(Box.createVerticalStrut(20));
        componentsPanel.add(gamePanel);
        componentsPanel.add(Box.createVerticalStrut(20));
        componentsPanel.add(startPanel);
        componentsPanel.add(Box.createVerticalStrut(240));

        // Center the panel on the frame
        setLayout(new BorderLayout());
        add(componentsPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setResizable(false);
    }

    private String[] setGames() {
        String[] games = new String[maxGameID];
        for (int i = 1; i <= maxGameID; i++) {
            games[i-1] = "Game " + i;
        }
        return games;
    }
}
