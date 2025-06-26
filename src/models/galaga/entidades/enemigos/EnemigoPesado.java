package models.galaga.entidades.enemigos;

import javax.swing.*;
import java.awt.*;

public class EnemigoPesado extends Enemigo {
    private int ancho = 120;
    private int alto = 100;
    private Image textura;

    public EnemigoPesado(int x, int y) {
        super(x, y, 1, "/resources/enemigo3.png");
        this.vida = 2;
        cargarTextura();
    }

    private void cargarTextura() {
        try {
            textura = new ImageIcon(getClass().getResource("/assets/galaga/enemigo2.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar enemigo2.png");
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
        return Color.GREEN;
    }
}