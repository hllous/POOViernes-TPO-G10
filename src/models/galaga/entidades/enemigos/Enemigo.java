package models.galaga.entidades.enemigos;

import models.galaga.entidades.Entidad;
import java.awt.*;

public abstract class Enemigo extends Entidad {
    protected int velocidad;
    protected boolean activo = true;
    protected int vida = 1;
    protected Image textura;

    public Enemigo(int x, int y, int velocidad, String rutaImagen) {
        super(x, y);
        this.velocidad = velocidad;

        if (rutaImagen != null) {
            try {
                this.textura = new javax.swing.ImageIcon(getClass().getResource(rutaImagen)).getImage();
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen: " + rutaImagen);
                this.textura = null;
            }
        } else {
            this.textura = null;
        }
    }

    public abstract void mover();

    public void dibujar(Graphics g) {
        if (textura != null) {
            g.drawImage(textura, x, y, getAncho(), getAlto(), null);
        } else {
            g.setColor(getColor());
            g.fillRect(x, y, getAncho(), getAlto());
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, getAncho(), getAlto());
    }

    public boolean estaActivo() {
        return activo;
    }

    public void recibirDaño(int cantidad) {
        vida -= cantidad;
        if (vida <= 0) {
            activo = false;
        }
    }

    /// Getters y setters

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public abstract int getAncho();
    public abstract int getAlto();
    public abstract Color getColor();
}