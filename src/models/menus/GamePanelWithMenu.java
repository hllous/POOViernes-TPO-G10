package models.menus;

import models.snakegame.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GamePanelWithMenu extends JPanel {
    private JFrame frame;
    private GamePanel gamePanel;

    public GamePanelWithMenu(JFrame frame) {
        super(new BorderLayout());
        this.frame = frame;
        this.gamePanel = new GamePanel(frame);
        add(gamePanel, BorderLayout.CENTER);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem restartItem = new JMenuItem("Reiniciar");
        restartItem.addActionListener(e -> {
            remove(gamePanel);
            gamePanel = new GamePanel(frame);
            add(gamePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
            SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
        });

        JMenuItem mainMenuItem = new JMenuItem("Volver al menú principal");
        mainMenuItem.addActionListener(e -> {
            frame.setJMenuBar(null);
            frame.setContentPane(new MainMenu(frame));
            frame.pack();
            frame.setLocationRelativeTo(null);
        });

        menu.add(restartItem);
        menu.add(mainMenuItem);
        menuBar.add(menu);
        return menuBar;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}