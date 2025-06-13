package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        // Title label
        JLabel titleLabel = new JLabel("Select a Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        panel.add(titleLabel);

        // Button for "Snake game"
        JButton snakeButton = new JButton("Snake game");
        snakeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        snakeButton.setMaximumSize(new Dimension(200, 40));
        snakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder action
                JOptionPane.showMessageDialog(MainMenu.this, "Snake game coming soon!");
            }
        });
        panel.add(snakeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Button for "Flappy Bird"
        JButton flappyButton = new JButton("Flappy Bird");
        flappyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        flappyButton.setMaximumSize(new Dimension(200, 40));
        flappyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder action
                JOptionPane.showMessageDialog(MainMenu.this, "Flappy Bird coming soon!");
            }
        });
        panel.add(flappyButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Button for "Runner"
        JButton runnerButton = new JButton("Runner");
        runnerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        runnerButton.setMaximumSize(new Dimension(200, 40));
        runnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder action
                JOptionPane.showMessageDialog(MainMenu.this, "Runner coming soon!");
            }
        });
        panel.add(runnerButton);

        add(panel);
    }
}