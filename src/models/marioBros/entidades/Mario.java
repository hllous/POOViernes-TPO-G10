package models.marioBros.entidades;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import models.marioBros.bloques.Bloque;
import models.marioBros.bloques.BloquePregunta;

import javax.swing.*;

public class Mario {

    /// Componentes mario
    private double posicionXMario, poscionYMario;
    private int ancho = 16, alto = 32;
    private double dx = 0, dy = 0;
    private int monedas = 0;
    private ImageIcon gifMario;

    private final double gravedad = 0.5;
    private final double salto = -8.5;
    private final double velocidad = 2.5;
    private final double velocidadCaida = 8.0;

    /// Componentes especiales
    private boolean tocoBloqueVictoria = false;
    private boolean tocoBloquemortal = false;
    private boolean saltando = false;
    private boolean enElAire = false;

    /// Constructor

    public Mario(int x, int y) {
        this.posicionXMario = x;
        this.poscionYMario = y;
        gifMario = new ImageIcon(getClass().getResource("/assets/marioBros/mario.gif"));
    }

    public Rectangle getBounds() {
        return new Rectangle((int) posicionXMario, (int) poscionYMario, ancho, alto);
    }

    /// ----- MOVIMIENTO MARIO -----

    public void mover(List<Bloque> bloques) {
        moverHorizontal(bloques);
        aplicarGravedad();
        moverVertical(bloques);
        verificarSuelo();
    }

    private void moverHorizontal(List<Bloque> bloques) {
        posicionXMario += dx;
        for (Bloque bloque : bloques) {
            if (chocoConBloque(bloque)) {
                posicionXMario -= dx;
                break;
            }
        }
    }

    private void moverVertical(List<Bloque> bloques) {
        poscionYMario += dy;
        for (Bloque bloque : bloques) {
            if (chocoConBloque(bloque)) {
                // MANEJO ESPECIAL PARA BLOQUES PREGUNTA
                if (bloque instanceof BloquePregunta) {
                    BloquePregunta pregunta = (BloquePregunta) bloque;

                    if (dy > 0 && pregunta.puedePararse(this)) {
                        // Mario cayendo sobre el bloque - puede pararse
                        poscionYMario = bloque.getY() - alto;
                        dy = 0;
                        enElAire = false;
                        saltando = false;
                    } else if (dy < 0) {
                        // Mario saltando hacia arriba - golpea desde abajo
                        poscionYMario = bloque.getY() + bloque.getAlto();
                        dy = 0;
                        // La moneda se da en alColisionar automáticamente
                    }
                } else if (dy > 0) {
                    // Bloques normales
                    poscionYMario = bloque.getY() - alto;
                    dy = 0;
                    enElAire = false;
                    saltando = false;
                } else {
                    poscionYMario = bloque.getY() + bloque.getAlto();
                    dy = 0;
                }
                break;
            }
        }
    }

    /// ----- FIN MOVIMIENTO MARIO -----

    /// ----- METODOS EXTRA -----

    private void aplicarGravedad() {
        dy += gravedad;
        if (dy > velocidadCaida) dy = velocidadCaida;
    }

    public void agregarMoneda() {
        monedas++;
    }

    private void verificarSuelo() {
        if (poscionYMario > 400) {
            poscionYMario = 400;
            dy = 0;
            enElAire = false;
            saltando = false;
        }
    }

    private boolean chocoConBloque(Bloque bloque) {
        if (getBounds().intersects(bloque.getBounds())) {
            // VERIFICAR BLOQUES ESPECIALES - SIMPLE
            String tipoBloque = bloque.getClass().getSimpleName();
            if (tipoBloque.equals("BloqueVictoria")) {
                tocoBloqueVictoria = true;
            } else if (tipoBloque.equals("BloqueMortal")) {
                tocoBloquemortal = true;
            }

            bloque.alColisionar(this);
            return bloque.esSolido();
        }
        return false;
    }

    public void dibujar(Graphics g, int camaraX, int camaraY) {

        int drawX = (int) (posicionXMario - camaraX);
        int drawY = (int) (poscionYMario - camaraY);

        // Dibujar GIF de Mario
        gifMario.paintIcon(null, g, drawX, drawY);
    }

    /// ----- COMPONENTES SWING -----

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) dx = -velocidad;
        if (code == KeyEvent.VK_RIGHT) dx = velocidad;
        if (code == KeyEvent.VK_SPACE && !enElAire) {
            saltando = true;
            enElAire = true;
            dy = salto;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT && dx < 0) dx = 0;
        if (code == KeyEvent.VK_RIGHT && dx > 0) dx = 0;
    }

    /// Getters

    public int getPosicionXMario() { return (int) posicionXMario; }
    public int getPoscionYMario() { return (int) poscionYMario; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public boolean isEnElAire() { return enElAire; }
    public int getMonedas() { return monedas; }
    public boolean getTocoBloqueVictoria() { return tocoBloqueVictoria; }
    public boolean getTocoBloqueMortal() { return tocoBloquemortal; }

}