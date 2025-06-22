package models.menus;

import interfaces.menu.IMenu;
import models.marioBros.MarioBrosPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class MarioBrosMenu extends JPanel implements IMenu, ActionListener {
    private JFrame frame;
    private BufferedImage fondo;

    private JButton jugarBoton;
    private JButton volverBoton;

    /// Constructor

    public MarioBrosMenu(JFrame frame) {
        this.frame = frame;
        cargarFondo();
        inicializarComponentes();
    }

    /// ----- COMPONENTES MENU -----

    /// Creacion botones y actionListerers

    private void inicializarComponentes() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        jugarBoton = crearBotonMenu(" ");
        volverBoton = crearBotonMenu("Volver");

        jugarBoton.addActionListener(this);
        volverBoton.addActionListener(this);

        add(Box.createVerticalStrut(690));
        add(jugarBoton);
        add(Box.createVerticalStrut(30));
        add(volverBoton);
        add(Box.createVerticalGlue());
    }

    /// Creacion de botones (casi la misma estructura de MainMenu)

    public JButton crearBotonMenu(String texto) {
        JButton button = new JButton(texto);

        button.setAlignmentX(CENTER_ALIGNMENT + 0.03125f);
        if(texto=="Volver"){
            button.setMaximumSize(new Dimension(200, 1000));
        } else {
            button.setMaximumSize(new Dimension(1000, 1000));
        }
        button.setFont(loadFont(28f));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);         // Hace el fondo transparente
        button.setOpaque(false);                    // No pinta fondo por defecto
        button.setFocusPainted(false);              // Quita el borde de focus azul
        button.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),2)); // Sin borde inicial

        // Efecto al pasar mouse: borde negro
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0),2));
            }
        });

        return button;
    }

    /// Font (Misma estructura que MainMenu)

    public Font loadFont(float size) {
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

    /// Cargo fondo estatico, diferente que MainMenu
    // Explicacion: Ya que el fondo es estatico, no hace falta cargarlo cada frame en paintComponent. Solo lo hago una vez.

    private void cargarFondo() {
        try {
            // Cambia la ruta aquí si tu archivo PNG tiene otro nombre o ubicación
            fondo = ImageIO.read(getClass().getResource("/assets/menu/SuperMarioBrosMenu.png"));
        } catch (IOException | IllegalArgumentException e) {
            fondo = null;
            System.err.println("No se pudo cargar el fondo: " + e.getMessage());
        }
    }

    /// ----- FIN COMPONENTES MENU -----

    /// ----- COMPONENTES SWING -----

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarBoton){
            MarioBrosPanel panel = new MarioBrosPanel(frame);
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
            panel.requestFocusInWindow();
        } else if (e.getSource() == volverBoton) {
            frame.setContentPane(new MainMenu(frame));
            frame.revalidate();
        }

    }

    /// ----- FIN COMPONENTES SWING -----

}