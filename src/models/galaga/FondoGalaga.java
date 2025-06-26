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
        reiniciar(ancho, alto);
        // Posición inicial aleatoria en y
        y = random.nextInt(alto);
    }

    public void reiniciar(int ancho, int alto) {
        x = random.nextInt(ancho > 0 ? ancho : 800);
        y = -5;
        tamaño = random.nextInt(3) + 1;
        velocidad = tamaño; // Simplificado: el tamaño determina la velocidad directamente

        // Simplificación de colores: blanco o azul claro
        if (random.nextBoolean()) {
            color = Color.WHITE;
        } else {
            color = new Color(200, 200, 255); // Azul claro simplificado
        }
    }

    public void mover(int alto, int ancho) {
        y += velocidad;
        if (y > alto) {
            reiniciar(ancho, alto);
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, tamaño, tamaño);
    }
}