package interfaces.marioBros;

import java.awt.Graphics;

public interface IEntidad {
    void mover();
    void dibujar(Graphics g);

    int getX();
    int getY();
    int getAncho();
    int getAlto();
}