package models.snakeGame;

import java.awt.*;

public abstract class ItemDelJuego {
    protected int x, y;

    public ItemDelJuego(int maxWidth, int maxHeight) {
        respawn(maxWidth, maxHeight);
    }

    public abstract void draw(Graphics g);

    public void respawn(int maxWidth, int maxHeight) {
        this.x = (int)(Math.random() * maxWidth);
        this.y = (int)(Math.random() * maxHeight);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
