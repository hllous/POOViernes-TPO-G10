package models.galaga.entidades.enemigos;

import javax.swing.*;
import java.awt.*;

public class EnemigoRapido extends Enemigo {
    private int ancho = 80;
    private int alto = 65;
    private Image textura;

    public EnemigoRapido(int x, int y) {
        super(x, y, 4, null); // no usa imagen
        cargarTextura();
    }

    private void cargarTextura() {
        try {
            textura = new ImageIcon(getClass().getResource("/assets/galaga/enemigo3.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar enemigo3.png");
        }
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
        return ancho;
    }

    @Override
    public int getAlto() {
        return alto;
    }

    @Override
    public void dibujar(Graphics g) {
        if (textura != null) {
            g.drawImage(textura, x, y, ancho, alto, null);
        } else {
            g.setColor(getColor());
            g.fillRect(x, y, ancho, alto);
        }
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}