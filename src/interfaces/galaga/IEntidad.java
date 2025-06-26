package interfaces.galaga;

import java.awt.*;

public interface IEntidad {
    void mover();
    void dibujar(Graphics g);

    int getX();
    int getY();
    int getAncho();
    int getAlto();
}