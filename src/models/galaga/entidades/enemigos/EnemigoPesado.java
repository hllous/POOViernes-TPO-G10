package models.galaga.entidades.enemigos;

import javax.swing.*;
import java.awt.*;

public class EnemigoPesado extends Enemigo {
    // Tamaño aumentado
    private static final int ANCHO = 120;
    private static final int ALTO = 100;

    public EnemigoPesado(int x, int y) {
        super(x, y, 1, "/resources/enemigo3.png");
        this.vida = 2;
    }

    @Override
    public void mover() {
        if (Math.random() < 0.1) {
            x += (Math.random() < 0.5) ? -7 : 7;
            y += (Math.random() < 0.5) ? -7 : 7;
        }
    }

    @Override
    public int getAncho() {
        return ANCHO;
    }

    @Override
    public int getAlto() {
        return ALTO;
    }

    private static Image img;

    static {
        try {
            img = new ImageIcon(EnemigoBasico.class.getResource("/assets/galaga/enemigo2.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar enemigo1.png");
        }
    }

    @Override
    public void dibujar(Graphics g) {
        if (img != null) {
            g.drawImage(img, x, y, ANCHO, ALTO, null);
        } else {
            g.setColor(getColor());
            g.fillRect(x, y, ANCHO, ALTO);
        }
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}