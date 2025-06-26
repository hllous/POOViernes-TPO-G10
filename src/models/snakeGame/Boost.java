package models.snakeGame;

import java.awt.*;

public class Boost extends ItemDelJuego {
    public Boost(int maxWidth, int maxHeight) {
        super(maxWidth, maxHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(x * 25, y * 25, 25, 25);
    }
}