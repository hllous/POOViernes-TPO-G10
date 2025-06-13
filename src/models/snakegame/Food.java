package models.snakegame;

import interfaces.Colisionable;
import interfaces.Dibujable;
import models.ElementoDelJuego;

import java.awt.*;
import java.util.Random;

public class Food extends ElementoDelJuego implements Dibujable, Colisionable {

    private int width, height;
    private Random random = new Random();

    public Food(int width, int height, Snake snake) {
        super(0, 0);
        this.width = width;
        this.height = height;
        respawn(snake);
    }

    public void respawn(Snake snake) {
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (snake.estaOcupado(x, y)); // método que verifica si la serpiente está ahí
    }

    @Override
    public void dibujar(Graphics g, int tileSize) {
        g.setColor(Color.RED);
        g.fillOval(x * tileSize, y * tileSize, tileSize, tileSize);
    }

    @Override
    public boolean colisionaCon(ElementoDelJuego otro) {
        return this.x == otro.getX() && this.y == otro.getY();
    }

    @Override
    public void actualizar() {
        // no necesita lógica en cada ciclo
    }

    public Point getPosition() {
        return new Point(x, y);
    }
}
