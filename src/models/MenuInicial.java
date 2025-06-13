package models;

import javax.swing.*;
import java.awt.*;

public class MenuInicial extends JPanel {
    public MenuInicial(JFrame frame) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Snake Game", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        add(titulo, BorderLayout.NORTH);

        JButton boton = new JButton("Iniciar Juego");
        boton.setFont(new Font("Arial", Font.PLAIN, 24));

        boton.addActionListener(e -> {
            // Crear e insertar models.GamePanel
            GamePanel gamePanel = new GamePanel(frame);
            frame.setContentPane(gamePanel);
            frame.revalidate();
            frame.pack();

            // ¡Muy importante para que funcione el teclado!
            SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
        });

        JPanel centro = new JPanel();
        centro.add(boton);
        add(centro, BorderLayout.CENTER);
    }
}
