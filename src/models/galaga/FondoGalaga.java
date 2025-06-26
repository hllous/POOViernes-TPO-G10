package models.galaga;

import java.awt.*;
import java.util.Random;

public class FondoGalaga {
    private static final Random random = new Random();

    public int x, y;
    public int tamaño;
    public Color color;
    public float velocidad;

    public FondoGalaga(int ancho, int alto) {
        reiniciar(ancho);
        /// Posición inicial aleatoria en y
        y = random.nextInt(alto);
    }

    public void reiniciar(int ancho) {
        x = random.nextInt(ancho);
        y = -5;
        tamaño = 2;
        velocidad = 2;
        color = Color.WHITE;
    }

    public void mover(int alto, int ancho) {
        y += velocidad;
        if (y > alto) {
            reiniciar(ancho);
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, tamaño, tamaño);
    }
}