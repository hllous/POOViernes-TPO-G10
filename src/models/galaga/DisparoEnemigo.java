package models.galaga;

import java.awt.*;

public class DisparoEnemigo{
    public int x, y;
    private int velocidad = 5;
    private boolean activo = true;

    // Tamaño aumentado
    private static final int ANCHO = 8;
    private static final int ALTO = 20;

    public DisparoEnemigo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        y += velocidad;
        if (y > 1200) activo = false; // Cambié por un valor fijo más grande para pantallas grandes
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, ANCHO, ALTO);
    }

    public boolean estaActivo() {
        return activo;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }
}