package models.galaga.entidades.jugador;

import javax.swing.*;
import java.awt.*;

public class Jugador {
    public int x, y;
    private int velocidad = 10;
    private int limitePantalla = 800; // Valor predeterminado
    private Image textura;

    /// Tamaño del jugador
    private final int ancho = 100;
    private final int alto = 100;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
        cargarTextura();
    }

    private void cargarTextura() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/assets/galaga/jugador.png"));
            textura = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del jugador.");
        }
    }

    public void setLimitePantalla(int ancho) {
        this.limitePantalla = ancho;
    }

    public void moverIzquierda() {
        x -= velocidad;
        if (x < 0) x = 0;
    }

    public void moverDerecha() {
        x += velocidad;
        if (x > limitePantalla - ancho) x = limitePantalla - ancho;
    }

    public Image getTextura() {
        return textura;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void dibujar(Graphics g) {
        if (textura != null) {
            g.drawImage(textura, x, y, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, ancho, alto);
        }
    }
}