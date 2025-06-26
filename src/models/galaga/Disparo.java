package models.galaga;

import java.awt.*;

public class Disparo {
    public int x, y;
    private final int velocidad = 10;
    private boolean activo = true;
    private static final int ANCHO = 8;
    private static final int ALTO = 20;

    public Disparo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        y -= velocidad;
        if (y < 0) activo = false;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, ANCHO, ALTO);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }

    public boolean estaActivo() {
        return activo;
    }

    public void desactivar() {
        activo = false;
    }
}