package models.galaga;

import interfaces.menu.IMenu;
import models.galaga.entidades.enemigos.Enemigo;
import models.galaga.entidades.enemigos.EnemigoBasico;
import models.galaga.entidades.enemigos.EnemigoPesado;
import models.galaga.entidades.enemigos.EnemigoRapido;
import models.galaga.entidades.jugador.Jugador;
import models.menus.GalagaMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class GalagaPanel extends JPanel implements Runnable, KeyListener, ActionListener{

    /// Componentes Menu
    private JPanel menuPausa;
    private JPanel menuGameOver;
    private JButton btnReanudar, btnReiniciar, btnSalir;
    private JButton btnReiniciarGameOver, btnSalirGameOver;
    private Timer repaintTimer;
    private Random random = new Random();

    /// Componentes juego
    private JFrame frame;
    private Thread gameThread;
    private boolean running = false;
    private boolean pausado = false;
    private boolean gameOver = false;

    // Elementos del juego
    private Jugador jugador;
    private ArrayList<Disparo> disparos;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<DisparoEnemigo> disparosEnemigos;
    private boolean izquierda, derecha, disparar;
    private int contadorSpawn = 0;
    private int score = 0;
    private int vidas = 3;
    private int delaySpawn = 180;

    // Estrellas del fondo (ahora FondoGalaga)
    private FondoGalaga[] estrellas;
    private final int NUM_ESTRELLAS = 200; // Más estrellas durante el juego

    /// Constructor
    public GalagaPanel(JFrame frame) {
        this.frame = frame;
        Dimension screenSize = frame.getSize();
        setPreferredSize(screenSize);
        setSize(screenSize);
        setLayout(null);
        setBackground(Color.BLACK);

        // Inicializar estrellas
        inicializarEstrellas();

        // Inicializar componentes del juego
        reiniciarJuego();

        // Crear menus
        crearMenuPausa();
        crearMenuGameOver();

        // Configurar panel
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();

        // Timer para repintar y animar estrellas
        repaintTimer = new Timer(40, e -> {
            actualizarEstrellas();
            repaint();
        });
        repaintTimer.start();

        // Iniciar juego
        startGame();
    }

    private void inicializarEstrellas() {
        estrellas = new FondoGalaga[NUM_ESTRELLAS];
        Dimension screenSize = getSize();
        int ancho = screenSize.width > 0 ? screenSize.width : 800;
        int alto = screenSize.height > 0 ? screenSize.height : 600;

        for (int i = 0; i < NUM_ESTRELLAS; i++) {
            estrellas[i] = new FondoGalaga(ancho, alto);
        }
    }


    private void actualizarEstrellas() {
        if (estrellas == null) {
            inicializarEstrellas();
            return;
        }

        int alto = getHeight() > 0 ? getHeight() : 600;
        int ancho = getWidth() > 0 ? getWidth() : 800;

        for (FondoGalaga e : estrellas) {
            e.mover(alto, ancho);
        }
    }
    /// ----- COMPONENTES MENU -----

    /// Menu pausa
    private void crearMenuPausa() {
        menuPausa = new JPanel();
        menuPausa.setLayout(new BoxLayout(menuPausa, BoxLayout.Y_AXIS));
        menuPausa.setOpaque(false);
        menuPausa.setBounds(0, 0, getWidth(), getHeight());
        menuPausa.setVisible(false);

        menuPausa.add(Box.createVerticalStrut(200));
        JLabel titleLabel = new JLabel("PAUSA");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(50f));
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

        btnReanudar.addActionListener(this);
        btnReiniciar.addActionListener(this);
        btnSalir.addActionListener(this);

        add(menuPausa);
    }

    /// Menu Game Over
    private void crearMenuGameOver() {
        menuGameOver = new JPanel();
        menuGameOver.setLayout(new BoxLayout(menuGameOver, BoxLayout.Y_AXIS));
        menuGameOver.setOpaque(false);
        menuGameOver.setBounds(0, 0, getWidth(), getHeight());
        menuGameOver.setVisible(false);

        menuGameOver.add(Box.createVerticalStrut(200));
        JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(50f));
        titleLabel.setForeground(Color.RED);
        titleLabel.setOpaque(false);
        menuGameOver.add(titleLabel);

        // Puntaje
        JLabel scoreLabel = new JLabel("Puntaje: 0");
        scoreLabel.setName("scoreLabel"); // Para actualizarlo después
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setFont(loadFont(30f));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(false);
        menuGameOver.add(scoreLabel);

        menuGameOver.add(Box.createVerticalStrut(50));

        btnReiniciarGameOver = crearBotonMenu("Reiniciar");
        btnSalirGameOver = crearBotonMenu("Salir");

        menuGameOver.add(btnReiniciarGameOver);
        menuGameOver.add(Box.createVerticalStrut(15));
        menuGameOver.add(btnSalirGameOver);

        btnReiniciarGameOver.addActionListener(this);
        btnSalirGameOver.addActionListener(this);

        add(menuGameOver);
    }

    /// Acciones de menú
    public void reanudar() {
        pausado = false;
        menuPausa.setVisible(false);
        requestFocusInWindow();
    }

    public void reiniciar() {
        pausado = false;
        gameOver = false;
        menuPausa.setVisible(false);
        menuGameOver.setVisible(false);
        reiniciarJuego();
        requestFocusInWindow();
    }

    public void salir() {
        repaintTimer.stop();
        running = false;
        frame.setContentPane(new GalagaMenu(frame));
        frame.revalidate();
        frame.repaint();
    }

    /// Creador de botones menu
    public JButton crearBotonMenu(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(500, 60));
        button.setFont(loadFont(36f));
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
                button.setForeground(Color.YELLOW);
                button.setBorderPainted(true);
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setForeground(Color.WHITE);
                button.setBorderPainted(false);
            }
        });

        return button;
    }

    /// Font
    public Font loadFont(float size) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/menu/ArcadeFont.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return font.deriveFont(size);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }

    /// ----- FIN COMPONENTES MENU -----

    /// ----- COMPONENTES JUEGO -----

    public void startGame() {
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void reiniciarJuego() {
        // Calcular posición inicial del jugador y tamaño de pantalla
        Dimension screenSize = getSize();
        int centerX = screenSize.width / 2 - 50; // La mitad del ancho del jugador (100/2)
        int bottomY = screenSize.height - 150; // Un poco más arriba para que sea más visible

        jugador = new Jugador(centerX, bottomY);
        jugador.setLimitePantalla(screenSize.width); // Configurar límite de pantalla para el movimiento

        disparos = new ArrayList<>();
        enemigos = new ArrayList<>();
        disparosEnemigos = new ArrayList<>();
        score = 0;
        vidas = 3;
        delaySpawn = 180;
        contadorSpawn = 0;
    }

    @Override
    public void run() {
        // Bucle principal del juego
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                if (!pausado && !gameOver) {
                    actualizar();
                }
                delta--;
            }

            // El repintado se maneja con el Timer de Swing
            try {
                Thread.sleep(16);  // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // En el método actualizar() de GalagaPanel, necesitamos actualizar las colisiones:

    private void actualizar() {
        // Movimiento del jugador
        if (izquierda) jugador.moverIzquierda();
        if (derecha) jugador.moverDerecha();

        // Disparar (al centro)
        if (disparar) {
            disparos.add(new Disparo(jugador.x + jugador.getAncho()/2 - 4, jugador.y));
            disparar = false;
        }

        // Actualizar y filtrar disparos activos
        actualizarDisparos();

        // Generar enemigos según tiempo
        generarEnemigos();

        // Actualizar enemigos
        actualizarEnemigos();

        // Comprobar colisiones
        comprobarColisiones();
    }

    private void actualizarDisparos() {
        // Actualizar disparos del jugador
        disparos.removeIf(disparo -> {
            disparo.mover();
            return !disparo.estaActivo();
        });

        // Actualizar disparos enemigos
        disparosEnemigos.removeIf(disparo -> {
            disparo.mover();
            if (!disparo.estaActivo()) return true;

            // Comprobar colisiones con el jugador
            if (disparo.getBounds().intersects(jugador.getBounds())) {
                vidas--;
                if (vidas <= 0) {
                    activarGameOver();
                }
                return true;
            }
            return false;
        });
    }

    private void generarEnemigos() {
        contadorSpawn++;
        if (contadorSpawn > delaySpawn) {
            int tipo = (int) (Math.random() * 3);
            int x = (int) (Math.random() * (getWidth() - 120));
            int y = 50 + (int) (Math.random() * 150);

            switch (tipo) {
                case 0 -> enemigos.add(new EnemigoBasico(x, y));
                case 1 -> enemigos.add(new EnemigoRapido(x, y));
                case 2 -> enemigos.add(new EnemigoPesado(x, y));
            }

            contadorSpawn = 0;
            if (delaySpawn > 60) {
                delaySpawn -= 5;
            }
        }

        // Generar disparos enemigos
        for (Enemigo e : enemigos) {
            if (Math.random() < 0.005) {
                disparosEnemigos.add(new DisparoEnemigo(e.getX() + e.getAncho()/2 - 4, e.getY() + e.getAlto()));
            }
        }
    }

    private void actualizarEnemigos() {
        // Filtrar enemigos inactivos y actualizar los activos
        enemigos.removeIf(enemigo -> {
            enemigo.mover();
            return !enemigo.estaActivo();
        });
    }

    private void comprobarColisiones() {
        // Colisiones de disparos con enemigos
        for (Disparo d : disparos) {
            Rectangle rectDisparo = d.getBounds();
            for (Enemigo e : enemigos) {
                if (e.estaActivo() && rectDisparo.intersects(e.getBounds())) {
                    e.recibirDaño(1);
                    d.desactivar(); // Usar método en vez de manipular directamente
                    score += 10;
                }
            }
        }

        // Colisiones del jugador con enemigos (game over)
        for (Enemigo e : enemigos) {
            if (e.getBounds().intersects(jugador.getBounds()) ||
                    e.getY() + e.getAlto() >= getHeight()) {
                activarGameOver();
                return;
            }
        }
    }

    private void activarGameOver() {
        gameOver = true;

        // Actualizar etiqueta de puntuación
        for (Component c : menuGameOver.getComponents()) {
            if (c instanceof JLabel && "scoreLabel".equals(c.getName())) {
                ((JLabel) c).setText("Puntaje: " + score);
                break;
            }
        }

        menuGameOver.setVisible(true);
    }

    /// ----- FIN COMPONENTES JUEGO -----

    /// ----- COMPONENTES SWING -----

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReanudar) {
            reanudar();
        } else if (e.getSource() == btnReiniciar || e.getSource() == btnReiniciarGameOver) {
            reiniciar();
        } else if (e.getSource() == btnSalir || e.getSource() == btnSalirGameOver) {
            salir();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo negro
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar estrellas
        if (estrellas != null) {
            for (FondoGalaga e : estrellas) {
                e.dibujar(g);
            }
        }

        // Dibujar elementos del juego
        if (!gameOver && jugador != null) {
            jugador.dibujar(g);

            // Dibujar disparos
            for (Disparo d : disparos) {
                d.dibujar(g);
            }

            // Dibujar enemigos
            for (Enemigo e : enemigos) {
                e.dibujar(g);
            }

            // Dibujar disparos enemigos
            for (DisparoEnemigo d : disparosEnemigos) {
                d.dibujar(g);
            }
        }

        // Información del jugador
        g.setColor(Color.WHITE);
        g.setFont(loadFont(20f));
        g.drawString("Puntuación: " + score, 20, 30);

        // Vidas
        g.drawString("Vidas: " + vidas, 20, 60);

        // En el método paintComponent
        // Mostrar icono de vidas
        Image iconoVida = Jugador.getTextura();
        if (iconoVida != null) {
            for (int i = 0; i < vidas; i++) {
                g.drawImage(iconoVida, getWidth() - (i + 1) * 60, 10, 50, 50, null); // Iconos más grandes
            }
        }

        // Fondo semi-transparente para los menús
        if (pausado || gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se utiliza
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE && !gameOver) {
            pausado = !pausado;
            menuPausa.setVisible(pausado);
            if (pausado) {
                repaint(); // Asegurar que se pinte el menú
            }
        } else if (!pausado && !gameOver) {
            if (key == KeyEvent.VK_LEFT) {
                izquierda = true;
            } else if (key == KeyEvent.VK_RIGHT) {
                derecha = true;
            } else if (key == KeyEvent.VK_SPACE) {
                disparar = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            izquierda = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            derecha = false;
        }
    }

    /// ----- FIN COMPONENTES SWING -----

    // Para asegurar que las estrellas se inicializan correctamente cuando el panel se muestra
    @Override
    public void addNotify() {
        super.addNotify();

        // Inicializar estrellas cuando el panel se añade a la jerarquía
        SwingUtilities.invokeLater(() -> {
            if (estrellas == null || getWidth() > 0 && estrellas[0].x > getWidth()) {
                inicializarEstrellas();
            }
        });
    }
}