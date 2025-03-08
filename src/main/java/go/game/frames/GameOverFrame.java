package go.game.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverFrame extends JFrame {
    public GameOverFrame(Color color) {
        String whoWon;

        if(color == Color.BLACK){
            whoWon = "White won!";
        }
        else {
            whoWon = "Black won!";
        }

        //size
        setSize(500, 400);

        //title
        setTitle("GO");

        //main components panel
        JPanel componentsPanel = new JPanel();
        componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.PAGE_AXIS));

        //label
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font(gameOverLabel.getFont().getName(), Font.BOLD, 50));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //label
        JLabel whoWonLabel = new JLabel(whoWon);
        whoWonLabel.setFont(new Font(gameOverLabel.getFont().getName(), Font.ITALIC, 40));
        whoWonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        componentsPanel.add(Box.createVerticalGlue());
        componentsPanel.add(gameOverLabel);
        componentsPanel.add(Box.createVerticalStrut(20));
        componentsPanel.add(whoWonLabel);
        componentsPanel.add(Box.createVerticalGlue());

        // Center the panel on the frame
        setLayout(new BorderLayout());
        add(componentsPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setResizable(false);
    }

}
