import models.menus.MainMenu;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Arcade");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);     // Ventana sin bordes

            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);      // Fullscreen

            frame.setContentPane(new MainMenu(frame));      // Setteo la ventana a MainMenu
            frame.setVisible(true);
        });
    }
}