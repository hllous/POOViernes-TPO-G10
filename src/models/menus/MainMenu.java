package models.menus;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    public MainMenu(JFrame frame) {
        setPreferredSize(new Dimension(600, 400));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Select a Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel);

        /// BOTON SNAKE GAME

        JButton snakeButton = new JButton("Snake game");
        snakeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        snakeButton.setMaximumSize(new Dimension(200, 40));
        snakeButton.addActionListener(e -> {
            GamePanelWithMenu gamePanelWithMenu = new GamePanelWithMenu(frame);
            frame.setContentPane(gamePanelWithMenu);
            frame.setJMenuBar(gamePanelWithMenu.createMenuBar());
            frame.pack();
            frame.setLocationRelativeTo(null);
            SwingUtilities.invokeLater(() -> gamePanelWithMenu.getGamePanel().requestFocusInWindow());
        });
        add(snakeButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        /// BOTON FLAPPY BIRD

        JButton flappyButton = new JButton("Flappy Bird");
        flappyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        flappyButton.setMaximumSize(new Dimension(200, 40));
        flappyButton.setEnabled(false); // Not implemented yet
        add(flappyButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        /// BOTON RUNNER

        JButton runnerButton = new JButton("Runner");
        runnerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        runnerButton.setMaximumSize(new Dimension(200, 40));
        runnerButton.setEnabled(false); // Not implemented yet
        add(runnerButton);
    }
}