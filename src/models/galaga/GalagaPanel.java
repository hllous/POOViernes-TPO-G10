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

public class GalagaPanel extends JPanel implements Runnable, KeyListener, ActionListener, IMenu {

    /// Componentes Menu
    private JPanel menuPausa;
    private JPanel menuGameOver;
    private JButton btnReanudar, btnReiniciar, btnSalir;
    private JButton btnReiniciarGameOver, btnSalirGameOver;
    private JLabel scoreLabel; // Referencia directa al label del puntaje
    private Timer repaintTimer;

    /// Componentes juego
    private JFrame frame;
    private Thread gameThread;
    private boolean running = false;
    private boolean pausado = false;
    private boolean gameOver = false;

    /// Componentes del entidades
    private Jugador jugador;
    private ArrayList<Disparo> disparos;
    private ArrayList<Enemigo> enemigos;
    private ArrayList<DisparoEnemigo> disparosEnemigos;
    private boolean izquierda, derecha, disparar;
    private int contadorSpawn = 0;
    private int delaySpawn = 180;
    private int score = 0;
    private int vidas = 3;

    /// Componentes para el fondo, en menu y en juego
    private FondoGalaga[] estrellas;
    private final int cantidadEstrellas = 200;

    /// Constructor
    public GalagaPanel(JFrame frame) {
        this.frame = frame;
        configurarPanel();
        inicializarJuego();
        iniciarTimers();
    }

    private void configurarPanel() {
        Dimension screenSize = frame.getSize();
        setPreferredSize(screenSize);
        setSize(screenSize);
        setLayout(null);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }

    private void inicializarJuego() {
        /// Inicializar componentes del juego
        inicializarEstrellas();
        reiniciarJuego();

        /// Creo menus
        crearMenuPausa();
        crearMenuGameOver();
    }

    private void iniciarTimers() {
        /// Timer para repaint y animación de fondo
        repaintTimer = new Timer(40, e -> {
            actualizarEstrellas();
            repaint();
        });
        repaintTimer.start();

        startGame();
    }

    /// ----- Métodos para crear el fondo -----

    private void inicializarEstrellas() {
        estrellas = new FondoGalaga[cantidadEstrellas];
        int ancho = getWidth();
        int alto = getHeight();

        for (int i = 0; i < cantidadEstrellas; i++) {
            estrellas[i] = new FondoGalaga(ancho, alto);
        }
    }

    private void actualizarEstrellas() {
        if (estrellas == null) {
            inicializarEstrellas();
            return;
        }

        int alto = getHeight();
        int ancho = getWidth();

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

        // Título
        JLabel titleLabel = new JLabel("PAUSA");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(50f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);

        // Botones
        btnReanudar = crearBotonMenu("Reanudar");
        btnReiniciar = crearBotonMenu("Reiniciar");
        btnSalir = crearBotonMenu("Salir");

        btnReanudar.addActionListener(this);
        btnReiniciar.addActionListener(this);
        btnSalir.addActionListener(this);

        // Añadir componentes al panel
        menuPausa.add(Box.createVerticalStrut(200));
        menuPausa.add(titleLabel);
        menuPausa.add(Box.createVerticalStrut(50));
        menuPausa.add(btnReanudar);
        menuPausa.add(Box.createVerticalStrut(15));
        menuPausa.add(btnReiniciar);
        menuPausa.add(Box.createVerticalStrut(15));
        menuPausa.add(btnSalir);

        add(menuPausa);
    }

    /// Menu Game Over
    private void crearMenuGameOver() {
        menuGameOver = new JPanel();
        menuGameOver.setLayout(new BoxLayout(menuGameOver, BoxLayout.Y_AXIS));
        menuGameOver.setOpaque(false);
        menuGameOver.setBounds(0, 0, getWidth(), getHeight());
        menuGameOver.setVisible(false);

        // Título Game Over
        JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(50f));
        titleLabel.setForeground(Color.RED);
        titleLabel.setOpaque(false);

        // Label de puntuación - guardamos referencia para actualizar fácilmente
        scoreLabel = new JLabel("Puntaje: 0");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setFont(loadFont(30f));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(false);

        // Botones
        btnReiniciarGameOver = crearBotonMenu("Reiniciar");
        btnSalirGameOver = crearBotonMenu("Salir");

        btnReiniciarGameOver.addActionListener(this);
        btnSalirGameOver.addActionListener(this);

        // Añadir componentes al panel
        menuGameOver.add(Box.createVerticalStrut(200));
        menuGameOver.add(titleLabel);
        menuGameOver.add(scoreLabel);
        menuGameOver.add(Box.createVerticalStrut(50));
        menuGameOver.add(btnReiniciarGameOver);
        menuGameOver.add(Box.createVerticalStrut(15));
        menuGameOver.add(btnSalirGameOver);

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

        /// Efecto al pasar el mouse
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

    /// ----- COMPONENTES JUEGO -----

    public void startGame() {
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void reiniciarJuego() {
        // Posición inicial del jugador y tamaño de pantalla
        Dimension screenSize = getSize();
        int centerX = screenSize.width / 2 - 50;
        int bottomY = screenSize.height - 150;

        jugador = new Jugador(centerX, bottomY);
        jugador.setLimitePantalla(screenSize.width);

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
        while (running) {
            if (!pausado && !gameOver) {
                actualizar();
            }

            try {
                Thread.sleep(16); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /// Actualizo los movimientos y las colisiones:
    private void actualizar() {
        actualizarJugador();
        actualizarDisparos();
        generarEnemigos();
        actualizarEnemigos();
        comprobarColisiones();
    }

    private void actualizarJugador() {
        /// Movimiento del jugador
        if (izquierda) jugador.moverIzquierda();
        if (derecha) jugador.moverDerecha();

        /// Disparo
        if (disparar) {
            disparos.add(new Disparo(jugador.x + jugador.getAncho()/2 - 4, jugador.y));
            disparar = false;
        }
    }

    private void actualizarDisparos() {
        /// Actualizo disparos del jugador

        disparos.removeIf(disparo -> {
            disparo.mover();
            return !disparo.estaActivo();
        });

        /// Actualizo disparos enemigos
        disparosEnemigos.removeIf(disparo -> {
            disparo.mover();
            if (!disparo.estaActivo()) return true;

            /// Comprobar colisiones con el jugador
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
            spawnNewEnemigo();
            contadorSpawn = 0;
        }

        /// Genero disparos de los enemigos
        for (Enemigo e : enemigos) {
            if (Math.random() < 0.005) {
                disparosEnemigos.add(new DisparoEnemigo(e.getX() + e.getAncho()/2 - 4, e.getY() + e.getAlto()));
            }
        }
    }

    private void spawnNewEnemigo() {
        int tipo = (int) (Math.random() * 3);
        int x = (int) (Math.random() * (getWidth() - 120));
        int y = 50 + (int) (Math.random() * 150);

        switch (tipo) {
            case 0 -> enemigos.add(new EnemigoBasico(x, y));
            case 1 -> enemigos.add(new EnemigoRapido(x, y));
            case 2 -> enemigos.add(new EnemigoPesado(x, y));
        }
    }

    private void actualizarEnemigos() {
        /// Actualizo los enemigos que no están eliminados

        enemigos.removeIf(enemigo -> {
            enemigo.mover();
            return !enemigo.estaActivo();
        });
    }

    private void comprobarColisiones() {
        /// Colisiones de disparos con enemigos
        for (Disparo d : disparos) {
            Rectangle rectDisparo = d.getBounds();
            for (Enemigo e : enemigos) {
                if (e.estaActivo() && rectDisparo.intersects(e.getBounds())) {
                    e.recibirDaño(1);
                    d.desactivar();
                    score += 10;
                }
            }
        }
    }

    private void activarGameOver() {
        gameOver = true;
        scoreLabel.setText("Puntaje: " + score);
        menuGameOver.setVisible(true);
    }

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
        dibujarFondo(g);
        dibujarElementosJuego(g);
        dibujarInterfazUsuario(g);
    }

    private void dibujarFondo(Graphics g) {

        /// Fondo negro
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        /// Dibujo las estrellas
        if (estrellas != null) {
            for (FondoGalaga e : estrellas) {
                e.dibujar(g);
            }
        }
    }

    private void dibujarElementosJuego(Graphics g) {
        if (gameOver || jugador == null) return;

        /// Dibujo jugador
        jugador.dibujar(g);

        /// Dibujo disparos
        for (Disparo d : disparos) {
            d.dibujar(g);
        }

        /// Dibujo enemigos
        for (Enemigo e : enemigos) {
            e.dibujar(g);
        }

        /// Dibujo disparos enemigos
        for (DisparoEnemigo d : disparosEnemigos) {
            d.dibujar(g);
        }
    }

    private void dibujarInterfazUsuario(Graphics g) {
        /// Información del jugador
        g.setColor(Color.WHITE);
        g.setFont(loadFont(20f));
        g.drawString("Puntuación: " + score, 20, 30);
        g.drawString("Vidas: " + vidas, 20, 60);

        /// Mostrar vidas
        Image iconoVida = new ImageIcon(getClass().getResource("/assets/galaga/jugador.png")).getImage();
        if (iconoVida != null) {
            for (int i = 0; i < vidas; i++) {
                g.drawImage(iconoVida, getWidth() - (i + 1) * 60, 10, 50, 50, null);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        /// Chequeo si esta pausado

        if (key == KeyEvent.VK_ESCAPE && !gameOver) {
            pausado = !pausado;
            menuPausa.setVisible(pausado);

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


    /// Tengo que agregar este metodo, aunque no lo uso, por la interfaz
    @Override
    public void keyTyped(KeyEvent e) {}
}