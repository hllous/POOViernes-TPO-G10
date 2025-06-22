package models.marioBros.bloques;

import models.marioBros.entidades.Mario;
import java.awt.*;

public abstract class Bloque {
    protected int x, y, ancho, alto;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bloque(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    public void alColisionar(Mario mario){};

    public boolean esSolido(){
        return true;
    };

    public void dibujar(Graphics g, int camaraX, int camaraY){};
}