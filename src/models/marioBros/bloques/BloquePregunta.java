package models.marioBros.bloques;

import models.marioBros.entidades.Mario;
import java.awt.*;

public class BloquePregunta extends Bloque {
    private boolean yaUsado = false;

    public BloquePregunta(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public boolean esSolido() {
        return true;
    }

    @Override
    public void alColisionar(Mario mario) {
        // Verificar si Mario viene desde ABAJO
        if (esColisionDesdeAbajo(mario) && !yaUsado) {
            mario.agregarMoneda();
            yaUsado = true;
        }
    }

    // MÉTODO PARA DETECTAR COLISIÓN DESDE ABAJO - SIMPLE
    private boolean esColisionDesdeAbajo(Mario mario) {
        // Mario viene desde abajo si:
        // 1. Está saltando (dy negativa)
        // 2. Su parte superior toca la parte inferior del bloque

        Rectangle marioBounds = mario.getBounds();
        Rectangle bloqueBounds = getBounds();

        // Mario está debajo del bloque y subiendo
        boolean marioDebajo = marioBounds.y > bloqueBounds.y;
        boolean marioSubiendo = mario.getY() < 0; // Velocidad hacia arriba

        // La cabeza de Mario toca la parte inferior del bloque
        boolean tocaDesdeAbajo = marioBounds.y <= bloqueBounds.y + bloqueBounds.height;

        return marioDebajo && marioSubiendo && tocaDesdeAbajo;
    }

    // MÉTODO ESPECIAL: Verificar si Mario puede pararse encima
    public boolean puedePararse(Mario mario) {
        if (yaUsado) return true; // Si ya está usado, actúa como bloque normal

        Rectangle marioBounds = mario.getBounds();
        Rectangle bloqueBounds = getBounds();

        // Mario puede pararse si viene desde arriba y está cayendo
        boolean marioArriba = marioBounds.y < bloqueBounds.y;
        boolean marioCayendo = mario.getY() >= 0;

        return marioArriba && marioCayendo;
    }

    public boolean estaUsado() {
        return yaUsado;
    }

    public void dibujar(Graphics g, int camaraX, int camaraY) {
        if (yaUsado) {
            // Bloque usado - marrón como bloque normal
            g.setColor(new Color(139, 69, 19));
            g.fillRect(x - camaraX, y - camaraY, ancho, alto);
            g.setColor(Color.BLACK);
            g.drawRect(x - camaraX, y - camaraY, ancho, alto);
        } else {
        }


    }
}