package models.snakeGame;

import java.awt.*;

public class Food extends ItemDelJuego {
    public Food(int maxWidth, int maxHeight) {
        super(maxWidth, maxHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x * 25, y * 25, 25, 25);
    }
}