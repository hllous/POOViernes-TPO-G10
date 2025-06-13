import models.MainMenu;
import models.MenuInicial;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /*JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(new MenuInicial(frame)); // ← Asegurate de pasar el frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignored) {}

        // Launch MainMenu frame
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}

