package go.game.drawing;

import java.awt.*;

public class Stone implements DrawableElement {
    private Color color;

    private Stone(Color color) {
        this.color = color;
    }

    public static Stone addStone(Color color) {
        return new Stone(color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(0, 0, 30, 30); // assuming cell size is 30
    }
}
