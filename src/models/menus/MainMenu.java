package models.menus;

import interfaces.menu.IMenu;

import javax.sound.sampled.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class MainMenu extends JPanel implements ActionListener, IMenu {

    /// Componentes swing
    private JFrame frame;
    private JButton snakeBoton;
    private JButton flappyBoton;
    private JButton marioBrosBoton;
    private JButton exitBoton;

    /// Componentes extra
    private ImageIcon backgroundGif;
    private Timer repaintTimer;
    private Clip backgroundMusic;

    /// ------ COMPONENTES MENU ------

    /// Menu principal

    public MainMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        /// ----- Cargo el gif -----

        try {
            backgroundGif = new ImageIcon(getClass().getResource("/assets/menu/bg.gif"));
        } catch (Exception e) {
            System.err.println("Error cargando imagen de fondo: " + e.getMessage());
        }

        /// ----- Creacion del menu -----

        /// Titulo

        add(Box.createVerticalStrut(300));
        JLabel titleLabel = new JLabel("Arcade");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(40f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        add(titleLabel);

        /// Opciones Juegos y salir.

        add(Box.createVerticalStrut(30));

        snakeBoton = crearBotonMenu("Snake Game");
        flappyBoton = crearBotonMenu("Flappy Bird");
        marioBrosBoton = crearBotonMenu("Super Mario Bros.");
        exitBoton = crearBotonMenu("Salir");

        add(snakeBoton);
        add(Box.createVerticalStrut(15));
        add(flappyBoton);
        add(Box.createVerticalStrut(15));
        add(marioBrosBoton);
        add(Box.createVerticalStrut(15));
        add(exitBoton);

        /// ----- Fin creacion del menu -----

        /// Veo si pasan el mouse por arriba de los botones

        snakeBoton.addActionListener(this);
        flappyBoton.addActionListener(this);
        marioBrosBoton.addActionListener(this);
        exitBoton.addActionListener(this);

        /// Timer para repintar el panel

        repaintTimer = new Timer(50, e -> repaint());
        repaintTimer.start();

        /// Musica de fondo

        playBackgroundMusic("/assets/menu/MusicaMenu.wav");

    }

    /// Player musica

    private void playBackgroundMusic(String resourcePath) {
        try {
            InputStream musica = getClass().getResourceAsStream(resourcePath);
            if (musica == null) {
                System.err.println("No se encontró el archivo de musica: " + resourcePath);
                return;
            }
            InputStream bufferedIn = new java.io.BufferedInputStream(musica);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /// Font

    public Font loadFont(float size) {
        try {
            /// Uso esto asi funciona de manera portable

            InputStream inputStream = MainMenu.class.getResourceAsStream("/assets/menu/ArcadeFont.ttf");

            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            return font.deriveFont(size);

        } catch (Exception ex) {
            /// Pongo una font de backup en caso de que no funcione

            ex.printStackTrace();
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }

    /// Creacion de botones

    public JButton crearBotonMenu(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(700, 50));
        button.setFont(loadFont(36f));
        button.setBackground(new Color(77, 63, 83, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        /// Acciones al pasar el mouse sobre el boton
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(77, 63, 83, 180)); // Más opaco al pasar mouse
                button.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(77, 63, 83, 100)); // Vuelve al original
                button.repaint();
            }
        });

        return button;
    }

    /// ------ FIN COMPONENTES MENU ------

    /// ----- COMPONENTES SWING -----

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == snakeBoton) {

            JOptionPane.showMessageDialog(this, "Snake Game se implementará próximamente.");
            backgroundMusic.stop();
            backgroundMusic.close();
            repaintTimer.stop();

        } else if (e.getSource() == flappyBoton) {

            JOptionPane.showMessageDialog(this, "Flappy Bird se implementará próximamente.");

        } else if (e.getSource() == marioBrosBoton) {

            backgroundMusic.stop();
            backgroundMusic.close();
            repaintTimer.stop();
            frame.setContentPane(new MarioBrosMenu(frame));
            frame.revalidate();

        } else if (e.getSource() == exitBoton) {

            System.exit(0);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundGif != null) {
            g.drawImage(backgroundGif.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    /// ----- FIN COMPONENTES SWING -----

}