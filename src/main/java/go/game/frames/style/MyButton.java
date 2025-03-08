package go.game.frames.style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MyButton extends JButton {
    public MyButton(String text) {

        setText(text);
        Font boldFont = new Font(getFont().getName(), Font.BOLD, 12);
        //Font boldFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 12);
        setFont(boldFont);

        setColor(new Color(180, 240, 215));
        colorOver = new Color(95, 183, 144);
        colorClick = new Color(32, 110, 76);
        borderColor = new Color(10, 40, 25);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(colorClick);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (over) {
                    setBackground(colorOver);
                } else {
                    setBackground(color);
                }
            }
        });
    }
    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 15;

    /*public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }*/

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    /*public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }*/

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw a rounded rectangle as the background
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor);
        g2d.fillRoundRect(0,0, getWidth(), getHeight(), radius, radius);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
        g2d.setColor(getBackground());
        g2d.fillRoundRect(2,2,getWidth()-4, getHeight()-4, radius,radius);
        super.paintComponent(g2d);

        g2d.dispose();
    }
}
