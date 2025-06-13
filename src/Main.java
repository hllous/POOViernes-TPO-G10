import models.menus.MainMenu;

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

        JFrame frame = new JFrame("Mini Arcade");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(new MainMenu(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}

