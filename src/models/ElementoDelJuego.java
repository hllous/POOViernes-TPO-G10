package models;

public abstract class ElementoDelJuego {
    protected int x, y;

    public ElementoDelJuego(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void actualizar(); // o mover(), si lo usás

    public int getX() { return x; }
    public int getY() { return y; }
}
