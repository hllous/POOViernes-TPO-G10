package models.galaga.entidades;

import interfaces.marioBros.IEntidad;

public abstract class Entidad implements IEntidad {
    protected int x, y;
    protected boolean activa;

    public Entidad(int x, int y) {
        this.x = x;
        this.y = y;
        this.activa = true;
    }

    public abstract void mover();
    public boolean estaActiva() {
        return activa;
    }
}
