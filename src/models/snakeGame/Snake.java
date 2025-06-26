package models.snakeGame;

import java.awt.*;
import java.util.LinkedList;

public class Snake {

    private LinkedList<Point> body;
    private String direction = "RIGHT";
    private String previousDirection = "RIGHT";
    private int maxWidth, maxHeight;
    private boolean alive = true;

    public Snake(int width, int height) {
        this.maxWidth = width;
        this.maxHeight = height;
        body = new LinkedList<>();
        body.add(new Point(5, 5));
        body.add(new Point(4, 5));
        body.add(new Point(3, 5));
    }

    public void move() {
        if (!alive) return;

        Point head = body.getFirst();
        Point newPoint = new Point(head);

        switch (direction) {
            case "UP": newPoint.y--; break;
            case "DOWN": newPoint.y++; break;
            case "LEFT": newPoint.x--; break;
            case "RIGHT": newPoint.x++; break;
        }

        previousDirection = direction;

        if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= maxWidth || newPoint.y >= maxHeight) {
            alive = false;
            return;
        }

        body.addFirst(newPoint);
        body.removeLast();
    }

    public void grow() {
        if (!alive) return;
        Point tail = body.getLast();
        body.add(new Point(tail));
    }

    public void draw(Graphics g) {
        if (!alive) return;
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x * 25, p.y * 25, 25, 25);
        }
    }

    public void setDirection(String dir) {
        if ((previousDirection.equals("UP") && dir.equals("DOWN")) ||
                (previousDirection.equals("DOWN") && dir.equals("UP")) ||
                (previousDirection.equals("LEFT") && dir.equals("RIGHT")) ||
                (previousDirection.equals("RIGHT") && dir.equals("LEFT"))) {
            return; // evitar dirección opuesta
        }

        this.direction = dir;
    }

    public int getHeadX() {
        if (body.isEmpty()) return -1;
        return body.getFirst().x;
    }

    public int getHeadY() {
        if (body.isEmpty()) return -1;
        return body.getFirst().y;
    }

    public boolean checkCollision() {
        if (!alive || body.isEmpty()) return true;
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlive() {
        return alive;
    }
}