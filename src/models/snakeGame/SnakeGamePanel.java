package models.snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import models.menus.MainMenu;

public class SnakeGamePanel extends JPanel implements ActionListener, KeyListener {

    private final int TILE_SIZE = 25;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    private final int GAME_WIDTH = WIDTH * TILE_SIZE;
    private final int GAME_HEIGHT = HEIGHT * TILE_SIZE;

    private Timer gameTimer;
    private int delay = 150;
    private Snake snake;

    private Food food;
    private Boost boost;
    private boolean boostVisible = false;
    private boolean boostActive = false;
    private int boostTicks = 0;

    private int score = 0;
    private int topScore = 0;

    private boolean gameOver = false;
    private boolean gameStarted = false;
    private boolean gamePaused = false;

    private JFrame frame;
    private JPanel menuPausa;
    private JButton btnReanudar, btnReiniciar, btnSalir;

    private final File scoreFile = new File("topscore.txt");

    public SnakeGamePanel(JFrame frame) {
        this.frame = frame;

        // Ajustar el tamaño del panel para centrar el area de juego
        Dimension screenSize = frame.getSize();
        setPreferredSize(screenSize);
        setLayout(null);

        setBackground(Color.BLACK);

        // Estos dos metodos son fundamentales para capturar eventos de teclado
        setFocusable(true);
        addKeyListener(this);

        // Crear menú de pausa
        crearMenuPausa();

        loadTopScore();
        initGame();
    }

    private void crearMenuPausa() {
        menuPausa = new JPanel();
        menuPausa.setLayout(new BoxLayout(menuPausa, BoxLayout.Y_AXIS));
        menuPausa.setOpaque(false);

        Dimension frameSize = frame.getSize();
        menuPausa.setBounds(0, 0, frameSize.width, frameSize.height);
        menuPausa.setVisible(false);

        menuPausa.add(Box.createVerticalStrut(200));
        JLabel titleLabel = new JLabel("PAUSA");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        menuPausa.add(titleLabel);

        menuPausa.add(Box.createVerticalStrut(50));

        btnReanudar = crearBotonMenu("Reanudar");
        btnReiniciar = crearBotonMenu("Reiniciar");
        btnSalir = crearBotonMenu("Salir");

        menuPausa.add(btnReanudar);
        menuPausa.add(Box.createVerticalStrut(15));
        menuPausa.add(btnReiniciar);
        menuPausa.add(Box.createVerticalStrut(15));
        menuPausa.add(btnSalir);

        btnReanudar.addActionListener(e -> reanudar());
        btnReiniciar.addActionListener(e -> reiniciar());
        btnSalir.addActionListener(e -> salir());

        add(menuPausa);
    }

    public JButton crearBotonMenu(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(500, 60));
        button.setFont(new Font("Arial", Font.BOLD, 36));
        button.setBackground(new Color(20, 20, 80, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);

        // Efecto al pasar el mouse
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setForeground(Color.GREEN);
                button.setBorderPainted(true);
                button.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setForeground(Color.WHITE);
                button.setBorderPainted(false);
            }
        });

        return button;
    }

    public void reanudar() {
        gamePaused = false;
        menuPausa.setVisible(false);
        gameTimer.start();
        requestFocusInWindow();
    }

    public void reiniciar() {
        initGame();
        gameStarted = true;
        gamePaused = false;
        menuPausa.setVisible(false);
        requestFocusInWindow();
    }

    public void salir() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        MainMenu mainMenu = new MainMenu(frame);
        frame.setContentPane(mainMenu);
        frame.revalidate();
        mainMenu.requestFocusInWindow();
    }

    private void initGame() {
        snake = new Snake(WIDTH, HEIGHT);
        food = new Food(WIDTH, HEIGHT);
        boost = new Boost(WIDTH, HEIGHT);
        boostVisible = false;

        if (gameTimer != null) {
            gameTimer.stop();
        }

        gameTimer = new Timer(delay, this);
        gameTimer.start();
        score = 0;
        gameOver = false;
        boostActive = false;
        boostTicks = 0;
    }

    private void loadTopScore() {
        try {
            if (scoreFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(scoreFile));
                topScore = Integer.parseInt(reader.readLine());
                reader.close();
            }
        } catch (Exception e) {
            topScore = 0;
        }
    }

    private void saveTopScore() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile));
            writer.write(String.valueOf(topScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calcular posicion para centrar el area de juego
        int offsetX = (getWidth() - GAME_WIDTH) / 2;
        int offsetY = (getHeight() - GAME_HEIGHT) / 2;

        // Dibujar fondo del area de juego
        g.setColor(Color.DARK_GRAY);
        g.fillRect(offsetX, offsetY, GAME_WIDTH, GAME_HEIGHT);

        // Dibujar borde blanco alrededor del area de juego
        g.setColor(Color.WHITE);
        g.drawRect(offsetX - 1, offsetY - 1, GAME_WIDTH + 1, GAME_HEIGHT + 1);

        if (!gameStarted) {
            drawStartMenu(g, offsetX, offsetY);
            return;
        }

        // Configurar el area
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(offsetX, offsetY);

        // Dibujar elementos del juego
        snake.draw(g2d);
        food.draw(g2d);
        if (boostVisible) {
            boost.draw(g2d);
        }
        g2d.dispose();

        // Dibujar puntajes en la esquina superior izquierda del área de juego
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Puntaje: " + score, offsetX + 10, offsetY + 20);
        g.drawString("Top Score: " + topScore, offsetX + 10, offsetY + 40);

        if (gameOver) {
            drawGameOver(g, offsetX, offsetY);
        }

        // Dibujar fondo semi-transparente para menú de pausa
        if (gamePaused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void drawStartMenu(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String startMessage = "Presioná ENTER para iniciar";
        FontMetrics metrics = g.getFontMetrics();
        int textX = offsetX + (GAME_WIDTH - metrics.stringWidth(startMessage)) / 2;
        int textY = offsetY + GAME_HEIGHT / 2;
        g.drawString(startMessage, textX, textY);
    }

    private void drawGameOver(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        String gameOverMessage = "GAME OVER";
        FontMetrics metrics = g.getFontMetrics();
        int textX = offsetX + (GAME_WIDTH - metrics.stringWidth(gameOverMessage)) / 2;
        int textY = offsetY + GAME_HEIGHT / 2;
        g.drawString(gameOverMessage, textX, textY);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String restartMessage = "Presioná R para reiniciar";
        metrics = g.getFontMetrics();
        textX = offsetX + (GAME_WIDTH - metrics.stringWidth(restartMessage)) / 2;
        textY = offsetY + GAME_HEIGHT / 2 + 40;
        g.drawString(restartMessage, textX, textY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && gameStarted && !gamePaused) {
            snake.move();

            // Ajustar coordenadas para el sistema centrado
            if (snake.getHeadX() == food.getX() && snake.getHeadY() == food.getY()) {
                snake.grow();
                food.respawn(WIDTH, HEIGHT);
                score += 100;

                // 20% de chance de mostrar boost
                if (!boostVisible && Math.random() < 0.2) {
                    boost.respawn(WIDTH, HEIGHT);
                    boostVisible = true;
                }
            }

            if (boostVisible && snake.getHeadX() == boost.getX() && snake.getHeadY() == boost.getY()) {
                boostVisible = false;
                score += 50;
                boostActive = true;
                boostTicks = 30;
                gameTimer.setDelay(75);
            }

            if (boostActive) {
                boostTicks--;
                if (boostTicks <= 0) {
                    boostActive = false;
                    gameTimer.setDelay(delay);
                }
            }

            if (snake.checkCollision()) {
                gameOver = true;
                gameTimer.stop();
                if (score > topScore) {
                    topScore = score;
                    saveTopScore();
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (!gameStarted && code == KeyEvent.VK_ENTER) {
            gameStarted = true;
            repaint();
            return;
        }

        if (gameOver && code == KeyEvent.VK_R) {
            initGame();
            gameStarted = true;
            repaint();
            return;
        }

        if (gameStarted && !gameOver && code == KeyEvent.VK_ESCAPE) {
            gamePaused = !gamePaused;
            if (gamePaused) {
                gameTimer.stop();
                menuPausa.setVisible(true);
            } else {
                gameTimer.start();
                menuPausa.setVisible(false);
            }
            repaint();
            return;
        }

        if (!gamePaused && gameStarted && !gameOver) {
            switch (code) {
                case KeyEvent.VK_UP:    snake.setDirection("UP"); break;
                case KeyEvent.VK_DOWN:  snake.setDirection("DOWN"); break;
                case KeyEvent.VK_LEFT:  snake.setDirection("LEFT"); break;
                case KeyEvent.VK_RIGHT: snake.setDirection("RIGHT"); break;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}