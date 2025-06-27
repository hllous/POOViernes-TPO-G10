package interfaces.marioBros;

import java.awt.*;

public interface IEntidad {
    void mover();
    Rectangle getBounds();
    double getX();
    double getY();
    int getAncho();
    int getAlto();
}