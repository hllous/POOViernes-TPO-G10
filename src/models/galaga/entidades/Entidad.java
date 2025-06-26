package models.galaga.entidades;

import interfaces.galaga.IEntidad;

public abstract class Entidad implements IEntidad {
    protected int x, y;
    protected boolean activo;

    public Entidad(int x, int y) {
        this.x = x;
        this.y = y;
        this.activo = true;
    }

    public abstract void mover();
    public boolean estaActiva() {
        return activo;
    }
}
