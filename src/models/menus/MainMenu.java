package menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel implements ActionListener {
    private JButton snakeButton;
    private JButton flappyButton;
    private JButton runnerButton;
    private JButton leaderboardButton;
    private JButton exitButton;
    private ImageIcon animatedBackground;
    private Timer repaintTimer;

    /// ------ COMPONENTES MENU ------

    public MainMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        /// Cargo el gif

        animatedBackground = new ImageIcon(getClass().getResource("/assets/menu/Background_Menu.gif"));

        /// ----- Creacion del menu -----

        add(Box.createVerticalStrut(60));
        JLabel titleLabel = new JLabel("Colección de Juegos");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        add(titleLabel);

        add(Box.createVerticalStrut(30));

        snakeButton = createMenuButton("Snake Game");
        flappyButton = createMenuButton("Flappy Bird");
        runnerButton = createMenuButton("Runner");
        leaderboardButton = createMenuButton("Leaderboards");
        exitButton = createMenuButton("Salir");

        add(snakeButton);
        add(Box.createVerticalStrut(15));
        add(flappyButton);
        add(Box.createVerticalStrut(15));
        add(runnerButton);
        add(Box.createVerticalStrut(15));
        add(leaderboardButton);
        add(Box.createVerticalStrut(15));
        add(exitButton);

        /// ----- Fin creacion del menu -----

        /// Veo si pasan el mouse por arriba de los botones

        snakeButton.addActionListener(this);
        flappyButton.addActionListener(this);
        runnerButton.addActionListener(this);
        leaderboardButton.addActionListener(this);
        exitButton.addActionListener(this);

        /// Timer para repintar el panel

        repaintTimer = new Timer(50, e -> repaint());
        repaintTimer.start();
    }

    /// ------ FIN COMPONENTES MENU ------

    /// ------ BOTONES ------

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 50));
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(new Color(0, 23, 255, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        /// Acciones al pasar el mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 23, 255, 180)); // Más opaco al pasar mouse
                button.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 23, 255, 100)); // Vuelve al original
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
        } else if (e.getSource() == runnerButton) {
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