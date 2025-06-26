package models.galaga.entidades.enemigos;

import java.awt.*;

public class EnemigoBasico extends Enemigo {
    private static final int ANCHO = 90;
    private static final int ALTO = 70;
    private static Image texturaCacheada;

    static {
        try {
            texturaCacheada = new javax.swing.ImageIcon(EnemigoBasico.class.getResource("/assets/galaga/enemigo1.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar enemigo1.png");
        }
    }

    public EnemigoBasico(int x, int y) {
        super(x, y, 2, null);
        this.textura = texturaCacheada; // Usar la textura cacheada
    }

    @Override
    public void mover() {
        // Simplificar el movimiento: 10% de probabilidad de cambiar dirección
        if (Math.random() < 0.1) {
            x += (Math.random() < 0.5) ? -7 : 7;
            y += (Math.random() < 0.5) ? -3 : 5;
        }
    }

    @Override
    public int getAncho() { return ANCHO; }

    @Override
    public int getAlto() { return ALTO; }

    @Override
    public Color getColor() { return Color.CYAN; }
}