package models.snakegame;

import interfaces.Dibujable;
import models.ElementoDelJuego;

import java.awt.*;
import java.util.LinkedList;

public class Snake extends ElementoDelJuego implements Dibujable {
    private LinkedList<Point> cuerpo;
    private Direction direccion = Direction.RIGHT;
    private int width, height;

    public Snake(int width, int height) {
        super(5, 5);
        this.width = width;
        this.height = height;
        cuerpo = new LinkedList<>();
        cuerpo.add(new Point(x, y));
    }

    public void setDirection(Direction dir) {
        this.direccion = dir;
    }

    public void mover() {
        Point cabeza = cuerpo.getFirst();
        Point nueva = new Point(cabeza);

        switch (direccion) {
            case Direction.UP -> nueva.y--;
            case Direction.DOWN -> nueva.y++;
            case Direction.LEFT -> nueva.x--;
            case Direction.RIGHT -> nueva.x++;
        }

        cuerpo.addFirst(nueva);
        cuerpo.removeLast();

        x = nueva.x;
        y = nueva.y;
    }

    public void grow() {
        cuerpo.addLast(new Point(cuerpo.getLast()));
    }

    public boolean checkCollisionWithWall() {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    public boolean checkCollisionWithItself() {
        Point cabeza = cuerpo.getFirst();
        for (int i = 1; i < cuerpo.size(); i++) {
            if (cabeza.equals(cuerpo.get(i))) return true;
        }
        return false;
    }

    public Point getHead() {
        return cuerpo.getFirst();
    }

    public boolean estaOcupado(int x, int y) {
        for (Point p : cuerpo) {
            if (p.x == x && p.y == y) return true;
        }
        return false;
    }

    @Override
    public void dibujar(Graphics g, int tileSize) {
        g.setColor(Color.GREEN);
        for (Point p : cuerpo) {
            g.fillRect(p.x * tileSize, p.y * tileSize, tileSize, tileSize);
        }
    }

    @Override
    public void actualizar() {
        mover();
    }
    
   
}
