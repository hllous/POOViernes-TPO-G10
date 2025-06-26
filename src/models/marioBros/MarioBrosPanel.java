package models.marioBros;

import interfaces.menu.IMenu;
import models.marioBros.niveles.Nivel;
import models.marioBros.niveles.Nivel1_1;
import models.menus.MainMenu;
import models.menus.MarioBrosMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class MarioBrosPanel extends JPanel implements Runnable, KeyListener, ActionListener, IMenu {

    /// Componentes Menu
    private JPanel menuPausa;
    private JPanel menuVictoria; // NUEVO: menú de victoria
    private JButton btnReanudar, btnReiniciar, btnSalir;
    private JButton btnVolverMenu; // NUEVO: botón para volver al menú
    private Timer repaintTimer;

    /// Componentes extra
    private JFrame frame;
    private Nivel nivelActual;
    private Thread gameThread;
    private boolean running = false;
    private boolean pausado = false;
    private boolean ganado = false;

    /// Constructor

    public MarioBrosPanel(JFrame frame) {
        this.frame = frame;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        setSize(screenSize);
        setLayout(null);

        nivelActual = new Nivel1_1();
        crearMenuPausa();
        crearMenuVictoria();

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();

        repaintTimer = new Timer(50, e -> repaint());
        repaintTimer.start();

        startGame();
    }

    /// ----- COMPONENTES MENU -----

    /// Menu pausa

    private void crearMenuPausa() {
        menuPausa = new JPanel();
        menuPausa.setLayout(new BoxLayout(menuPausa, BoxLayout.Y_AXIS));
        menuPausa.setOpaque(false);
        menuPausa.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
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

    public void reanudar() {
        pausado = false;
        menuPausa.setVisible(false);
        requestFocusInWindow();
    }

    public void reiniciar() {
        pausado = false;
        ganado = false;
        menuPausa.setVisible(false);
        menuVictoria.setVisible(false);
        nivelActual.reiniciarNivel();
        requestFocusInWindow();
    }

    public void salir() {
        repaintTimer.stop();
        nivelActual.stopBackgroundMusic();
        running = false;
        frame.setContentPane(new MarioBrosMenu(frame));
        frame.revalidate();
    }

    /// Menu win

    private void crearMenuVictoria() {
        // MENU DE VICTORIA - SIMPLE
        menuVictoria = new JPanel();
        menuVictoria.setLayout(new BoxLayout(menuVictoria, BoxLayout.Y_AXIS));
        menuVictoria.setOpaque(false);
        menuVictoria.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        menuVictoria.setVisible(false);

        // Título "GANASTE"
        menuVictoria.add(Box.createVerticalStrut(200));
        JLabel titleLabel = new JLabel("¡GANASTE!");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(60f));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setOpaque(false);
        menuVictoria.add(titleLabel);

        // Botón volver al menú
        menuVictoria.add(Box.createVerticalStrut(50));
        btnVolverMenu = crearBotonMenu("Volver al Menu");
        btnVolverMenu.addActionListener(this);
        menuVictoria.add(btnVolverMenu);

        add(menuVictoria);
    }

    /// Creador de botones menu (casi la misma estructura de MainMenu)

    public JButton crearBotonMenu(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(600, 50));
        button.setFont(loadFont(30f));
        button.setBackground(new Color(77, 63, 83, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(77, 63, 83, 180));
                button.repaint();
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(77, 63, 83, 100));
                button.repaint();
            }
        });

        return button;
    }

    /// Font (Misma estructura que MainMenu)

    public Font loadFont(float size) {
        try {
            InputStream inputStream = MainMenu.class.getResourceAsStream("/assets/menu/ArcadeFont.ttf");
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

    @Override
    public void run() {
        while (running) {
            if (!pausado && !ganado) {
                nivelActual.actualizar();

                // VERIFICAR ESTADOS ESPECIALES - SIMPLE
                if (nivelActual.getMario().getTocoBloqueMortal()) {
                    nivelActual.reiniciarNivel(); // Reiniciar por bloque mortal
                }

                if (nivelActual.getMario().getTocoBloqueVictoria()) {
                    ganado = true;
                    menuVictoria.setVisible(true); // Mostrar menú de victoria
                }
            }
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /// ----- FIN COMPONENTES JUEGO -----

    /// ----- COMPONENTES SWING -----

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReanudar) {
            reanudar();
        } else if (e.getSource() == btnReiniciar) {
            reiniciar();
        } else if (e.getSource() == btnSalir || e.getSource() == btnVolverMenu) {
            salir();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nivelActual != null) {
            nivelActual.dibujar(g, getWidth(), getHeight());
        }

        if (pausado || ganado) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !ganado) {
            pausado = !pausado;
            menuPausa.setVisible(pausado);
        } else if (!pausado && !ganado) {
            nivelActual.getMario().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!pausado && !ganado) {
            nivelActual.getMario().keyReleased(e);
        }
    }

    /// ----- FIN COMPONENTES SWING -----

}