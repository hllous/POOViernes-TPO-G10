package models.galaga.entidades.jugador;

import javax.swing.*;
import java.awt.*;

public class Jugador {
    public int x, y;
    private int velocidad = 10;
    private int limitePantalla = 800; // Valor predeterminado
    private static Image textura;

    // Tamaño aumentado del jugador
    private static final int ANCHO = 100;
    private static final int ALTO = 100;

    static {
        try {
            textura = new ImageIcon(Jugador.class.getResource("/assets/galaga/jugador.png"))
                    .getImage()
                    .getScaledInstance(ANCHO, ALTO, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del jugador.");
        }
    }

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Método para establecer el límite de la pantalla (ancho)
    public void setLimitePantalla(int ancho) {
        this.limitePantalla = ancho;
    }

    public void moverIzquierda() {
        x -= velocidad;
        if (x < 0) x = 0;
    }

    public void moverDerecha() {
        x += velocidad;
        if (x > limitePantalla - ANCHO) x = limitePantalla - ANCHO;
    }

    public static Image getIcono() {
        return textura;
    }

    public static Image getTextura() {
        return textura;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ANCHO, ALTO);
    }

    public int getAncho() {
        return ANCHO;
    }

    public int getAlto() {
        return ALTO;
    }

    public void dibujar(Graphics g) {
        if (textura != null) {
            g.drawImage(textura, x, y, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, ANCHO, ALTO);
        }
    }
}