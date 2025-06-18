package menus;

import javax.sound.sampled.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class MainMenu extends JPanel implements ActionListener {
    private JButton snakeButton;
    private JButton flappyButton;
    private JButton marioBrosButton;
    private JButton leaderboardButton;
    private JButton exitButton;
    private ImageIcon animatedBackground;
    private Timer repaintTimer;
    private Clip backgroundMusicClip;

    /// ------ COMPONENTES MENU ------

    /// Menu principal

    public MainMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        /// Cargo el gif

        animatedBackground = new ImageIcon(getClass().getResource("/assets/menu/bg.gif"));

        /// ----- Creacion del menu -----

        /// Titulo

        add(Box.createVerticalStrut(300));
        JLabel titleLabel = new JLabel("Arcade");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(loadFont(40f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        add(titleLabel);

        /// Opciones Juegos, leaderboards y salir.

        add(Box.createVerticalStrut(30));

        snakeButton = createMenuButton("Snake Game");
        flappyButton = createMenuButton("Flappy Bird");
        marioBrosButton = createMenuButton("Super Mario Bros.");
        leaderboardButton = createMenuButton("Leaderboards");
        exitButton = createMenuButton("Salir");

        add(snakeButton);
        add(Box.createVerticalStrut(15));
        add(flappyButton);
        add(Box.createVerticalStrut(15));
        add(marioBrosButton);
        add(Box.createVerticalStrut(15));
        add(leaderboardButton);
        add(Box.createVerticalStrut(15));
        add(exitButton);

        /// ----- Fin creacion del menu -----

        /// Veo si pasan el mouse por arriba de los botones

        snakeButton.addActionListener(this);
        flappyButton.addActionListener(this);
        marioBrosButton.addActionListener(this);
        leaderboardButton.addActionListener(this);
        exitButton.addActionListener(this);

        /// Timer para repintar el panel

        repaintTimer = new Timer(50, e -> repaint());
        repaintTimer.start();

        /// Musica de fondo

        playBackgroundMusic("/assets/menu/MusicaMenu.wav");
    }

    private void playBackgroundMusic(String resourcePath) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                System.err.println("No se encontró el archivo de audio: " + resourcePath);
                return;
            }
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /// Font

    public static Font loadFont(float size) {
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

    /// ------ FIN COMPONENTES MENU ------

    /// ------ BOTONES ------

    private JButton createMenuButton(String text) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == snakeButton) {
            JOptionPane.showMessageDialog(this, "Snake Game se implementará próximamente.");
        } else if (e.getSource() == flappyButton) {
            JOptionPane.showMessageDialog(this, "Flappy Bird se implementará próximamente.");
        } else if (e.getSource() == marioBrosButton) {
            JOptionPane.showMessageDialog(this, "Runner se implementará próximamente.");
        } else if (e.getSource() == exitButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    /// ------ FIN BOTONES ------

    /// ------ GRAFICOS ------

    @Override
    protected void paintComponent(Graphics g) {
        // Dibuja primero el fondo animado (GIF) escalado al tamaño del panel
        if (animatedBackground != null) {
            int w = getWidth();
            int h = getHeight();
            animatedBackground.setImageObserver(this); // Para animación fluida
            g.drawImage(animatedBackground.getImage(), 0, 0, w, h, this);
        }
        super.paintComponent(g); // Luego los componentes encima
    }

    /// ------ FIN GRAFICOS ------
}