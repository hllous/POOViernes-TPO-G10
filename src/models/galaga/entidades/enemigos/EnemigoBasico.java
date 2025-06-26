package models.galaga.entidades.enemigos;

import java.awt.*;
import javax.swing.ImageIcon;

public class EnemigoBasico extends Enemigo {
    private int ancho = 90;
    private int alto = 70;
    private Image textura;

    public EnemigoBasico(int x, int y) {
        super(x, y, 2, null);
        cargarTextura();
    }

    private void cargarTextura() {
        try {
            textura = new ImageIcon(getClass().getResource("/assets/galaga/enemigo1.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar enemigo1.png");
        }
    }

    @Override
    public void mover() {
        // Movimiento randomizado
        if (Math.random() < 0.1) {
            x += (Math.random() < 0.5) ? -7 : 7;
            y += (Math.random() < 0.5) ? -3 : 5;
        }
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
    public int getAncho() { return ancho; }

    @Override
    public int getAlto() { return alto; }

    @Override
    public Color getColor() { return Color.CYAN; }
}