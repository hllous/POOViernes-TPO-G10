package models.marioBros.bloques;

import models.marioBros.entidades.Mario;

public class BloqueVictoria extends Bloque {
    public BloqueVictoria(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public boolean esSolido() {
        return false;
    }
}