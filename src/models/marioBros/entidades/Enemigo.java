package models.marioBros.entidades;

import interfaces.marioBros.IEntidad;
import java.awt.*;
import java.util.List;
import models.marioBros.bloques.Bloque;

import javax.swing.*;

public class Enemigo implements IEntidad {
    private double x, y;
    private int ancho = 16, alto = 16;
    private double velocidad = 1.0;
    private boolean moviendoIzquierda = true;
    private double dy = 0;
    private final double GRAVEDAD = 0.5;
    private final double MAX_FALL_SPEED = 8.0;
    private List<Bloque> bloques;
    private ImageIcon gifGoomba;

    // NUEVO: Estado del enemigo
    private boolean eliminado = false;

    public Enemigo(int x, int y) {
        this.x = x;
        this.y = y;
        moviendoIzquierda = true;

        // Cargar GIF del Goomba
        gifGoomba = new ImageIcon(getClass().getResource("/assets/marioBros/goomba.gif"));
    }
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, ancho, alto);
    }

    // NUEVO: Método para verificar si Mario lo pisó
    public boolean verificarColisionConMario(Rectangle marioBounds, double marioVelocidadY) {
        if (eliminado) return true;

        if (getBounds().intersects(marioBounds)) {
            // LÓGICA SÚPER SIMPLE: ¿Mario está arriba del enemigo?
            boolean marioArriba = marioBounds.y < y; // Mario está arriba si su Y es menor
            boolean marioCayendo = marioVelocidadY > 1; // Mario está cayendo

            if (marioArriba && marioCayendo) {
                // Mario viene desde arriba = eliminar enemigo
                eliminar();
                return true;
            } else {
                // Mario toca por cualquier otro lado = Mario muere
                return false;
            }
        }
        return true;
    }

    // ELIMINAR ENEMIGO
    public void eliminar() {
        eliminado = true;
    }

    // VERIFICAR SI ESTÁ ELIMINADO
    public boolean estaEliminado() {
        return eliminado;
    }

    // IMPLEMENTACIÓN DE LA INTERFAZ IEntidad
    @Override
    public double getX() { return (int) x; }

    @Override
    public double getY() { return (int) y; }

    @Override
    public int getAncho() { return ancho; }

    @Override
    public int getAlto() { return alto; }

    @Override
    public void mover() {
        if (eliminado) return;
        if (bloques == null) return;

        // MOVIMIENTO HORIZONTAL - SIMPLE
        if (moviendoIzquierda) {
            x -= velocidad;
        } else {
            x += velocidad;
        }

        // Verificar colisión con bloques
        for (Bloque bloque : bloques) {
            if (bloque.esSolido() && getBounds().intersects(bloque.getBounds())) {
                // Retroceder y cambiar dirección
                if (moviendoIzquierda) {
                    x += velocidad; // Volver atrás
                } else {
                    x -= velocidad; // Volver atrás
                }
                moviendoIzquierda = !moviendoIzquierda; // Cambiar dirección
                break;
            }
        }

        // GRAVEDAD - SIMPLE
        dy += GRAVEDAD;
        if (dy > MAX_FALL_SPEED) dy = MAX_FALL_SPEED;

        y += dy;

        // Colisión vertical
        for (Bloque bloque : bloques) {
            if (bloque.esSolido() && getBounds().intersects(bloque.getBounds())) {
                if (dy > 0) { // Cayendo
                    y = bloque.getY() - alto;
                    dy = 0;
                }
                break;
            }
        }

        // Suelo por defecto
        if (y > 400) {
            y = 400;
            dy = 0;
        }
    }

    public void dibujar(Graphics g) {
        dibujar(g, 0, 0);
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public void dibujar(Graphics g, int camaraX, int camaraY) {
        int drawX = (int) x - camaraX;
        int drawY = (int) y - camaraY;

        if (!eliminado) {
            gifGoomba.paintIcon(null, g, drawX, drawY);
        }
    }
}