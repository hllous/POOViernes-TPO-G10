package models.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int TILE_SIZE = 25;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    private Timer timer;
    private Snake snake;
    private Food food;
    private boolean gameOver;
    private JFrame frame; // reference to the main JFrame

    public GamePanel(JFrame frame) {
        this.frame = frame;
        this.setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        snake = new Snake(WIDTH, HEIGHT);
        food = new Food(WIDTH, HEIGHT, snake);
        timer = new Timer(150, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.dibujar(g, TILE_SIZE);
        food.dibujar(g, TILE_SIZE);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", 60, 200);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            snake.actualizar();

            if (snake.checkCollisionWithWall() || snake.checkCollisionWithItself()) {
                gameOver = true;
                timer.stop();
            }

            if (snake.getHead().equals(food.getPosition())) {
                snake.grow();
                food.respawn(snake);
            }

            repaint();
        }
    }

    public void restartGame() {
        timer.stop();
        snake = new Snake(WIDTH, HEIGHT);
        food = new Food(WIDTH, HEIGHT, snake);
        gameOver = false;
        timer = new Timer(150, this);
        timer.start();
        repaint();
        this.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP -> snake.setDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> snake.setDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> snake.setDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> snake.setDirection(Direction.RIGHT);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}