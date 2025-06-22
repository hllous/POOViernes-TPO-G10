package models.marioBros.bloques;

import models.marioBros.entidades.Mario;

public class BloqueMortal extends Bloque {
    public BloqueMortal(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public boolean esSolido() {
        return true; // Es sólido pero mortal
    }
}