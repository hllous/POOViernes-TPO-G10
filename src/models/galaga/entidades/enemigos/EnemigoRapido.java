package models.galaga.entidades.enemigos;

import javax.swing.*;
import java.awt.*;

public class EnemigoRapido extends Enemigo {
    // Tamaño aumentado
    private static final int ANCHO = 80;
    private static final int ALTO = 65;

    public EnemigoRapido(int x, int y) {
        super(x, y, 4, null); // no usa imagen
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
            img = new ImageIcon(EnemigoBasico.class.getResource("/assets/galaga/enemigo3.png")).getImage();
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
        return Color.RED;
    }
}