package tests;

import models.marioBros.MarioBrosPanel;

import javax.swing.*;

public class TestMarioBros {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Mario Bros");
        MarioBrosPanel panel = new MarioBrosPanel(frame);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.startGame();
    }
}
