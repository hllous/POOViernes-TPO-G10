package models.marioBros.bloques;

public class BloqueTuberia extends Bloque {
    public BloqueTuberia(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public boolean esSolido() {
        return true;
    }
}