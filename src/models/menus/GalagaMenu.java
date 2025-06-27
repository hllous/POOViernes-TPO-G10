package models.menus;

import interfaces.menu.IMenu;
import models.galaga.FondoGalaga;
import models.galaga.GalagaPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Random;

public class GalagaMenu extends JPanel implements IMenu, ActionListener {
    private JFrame frame;
    private JButton jugarBoton;
    private JButton volverBoton;
    private Timer estrellasTimer;
    private Random random = new Random();

    private FondoGalaga[] estrellas;
    private final int NUM_ESTRELLAS = 200;

    /// Constructor
    public GalagaMenu(JFrame frame) {
        this.frame = frame;

        // Configurar el panel
        Dimension screenSize = frame.getSize();
        setPreferredSize(screenSize);
        setSize(screenSize);
        setBackground(Color.BLACK);

        // Inicializar componentes
        inicializarComponentes();
        inicializarEstrellas();

        // Timer para animar las estrellas del fondo
        estrellasTimer = new Timer(40, e -> {
            actualizarEstrellas();
            repaint();
        });
        estrellasTimer.start();
    }

    private void inicializarEstrellas() {
        estrellas = new FondoGalaga[NUM_ESTRELLAS];
        int ancho = getWidth() > 0 ? getWidth() : 800;
        int alto = getHeight() > 0 ? getHeight() : 600;

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

    /// Creacion botones y actionListeners
    private void inicializarComponentes() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Crear titulo
        add(Box.createVerticalStrut(200));
        JLabel titleLabel = new JLabel("GALAGA");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(60f));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setOpaque(false);
        add(titleLabel);

        // Crear botones
        add(Box.createVerticalStrut(100));
        jugarBoton = crearBotonMenu("JUGAR");
        volverBoton = crearBotonMenu("VOLVER");

        // Añadir ActionListeners
        jugarBoton.addActionListener(this);
        volverBoton.addActionListener(this);

        // Añadir botones al panel
        add(jugarBoton);
        add(Box.createVerticalStrut(30));
        add(volverBoton);
        add(Box.createVerticalGlue());
    }

    /// Creacion de botones
    public JButton crearBotonMenu(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 60));
        button.setFont(loadFont(36f));
        button.setBackground(new Color(20, 20, 80, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);

        // Efecto al pasar el mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.YELLOW);
                button.setBorderPainted(true);
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
                button.setBorderPainted(false);
            }
        });

        return button;
    }

    /// Font
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

    /// ----- COMPONENTES SWING -----

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
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarBoton){
            // Detener el timer de estrellas antes de cambiar de panel
            if (estrellasTimer != null) {
                estrellasTimer.stop();
            }

            // Crear y configurar el panel de juego
            GalagaPanel panel = new GalagaPanel(frame);
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
            panel.requestFocusInWindow();
        } else if (e.getSource() == volverBoton) {
            // Detener el timer de estrellas antes de volver al menú principal
            if (estrellasTimer != null) {
                estrellasTimer.stop();
            }

            frame.setContentPane(new MainMenu(frame));
            frame.revalidate();
        }
    }

    /// ----- FIN COMPONENTES SWING -----
}