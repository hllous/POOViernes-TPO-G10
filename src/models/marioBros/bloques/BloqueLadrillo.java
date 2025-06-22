package models.marioBros.bloques;

import models.marioBros.entidades.Mario;

public class BloqueLadrillo extends Bloque {
    public BloqueLadrillo(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public boolean esSolido() {
        return true;
    }

    @Override
    public void alColisionar(Mario mario) {
    }
}