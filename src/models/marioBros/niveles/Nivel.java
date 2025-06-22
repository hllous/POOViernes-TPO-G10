package models.marioBros.niveles;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import models.marioBros.bloques.*;
import models.marioBros.entidades.Enemigo;
import models.marioBros.entidades.Mario;
import models.menus.MainMenu;

public abstract class Nivel {
    /// Componentes nivel
    protected BufferedImage fondo;
    private Clip backgroundMusicClip;
    protected int camaraX = 0;

    /// Entidades y bloques nivel
    protected Mario mario;
    protected List<Enemigo> enemigos;
    protected List<Bloque> bloques;
    protected int piso=208;

    public int bloque = 16;
    public int ventana_alto = (int)(14.5 * bloque);
    public int ventala_ancho = 20 * bloque;

    /// Constructor

    public Nivel(String fondoPath, String musicaDeFondoPath) {

        crearFondoNivel(fondoPath);
        playBackgroundMusic(musicaDeFondoPath);
        enemigos = new ArrayList<>();
        bloques = new ArrayList<>();
        mario = new Mario(200, 209);
        inicializarEntidades();
    }

    ///  Creacion de fondo

    private void crearFondoNivel(String fondoPath) {
        try {
            var url = getClass().getResource(fondoPath);
            if (url != null) {
                fondo = ImageIO.read(url);
            } else {
                System.err.println("No se encontró el fondo del nivel en: " + fondoPath);
                fondo = null;
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            fondo = null;
        }
    }

    /// ----- MUSICA -----

    public void playBackgroundMusic(String musicaDeFondoPath) {
        try {
            InputStream musica = getClass().getResourceAsStream(musicaDeFondoPath);
            if (musica == null) {
                System.err.println("No se encontró el archivo de musica: " + musicaDeFondoPath);
                return;
            }
            InputStream bufferedIn = new java.io.BufferedInputStream(musica);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
    }

    /// ----- FIN MUSICA -----

    /// ----- LOGICA NIVEL -----

    protected abstract void inicializarEntidades();

    public void actualizar() {
        mario.mover(bloques);

        // Configurar bloques para enemigos y moverlos
        for (Enemigo enemigo : enemigos) {
            enemigo.setBloques(bloques);
            enemigo.mover();
        }

        // NUEVA LÓGICA DE COLISIÓN CON ENEMIGOS
        Rectangle marioBounds = mario.getBounds();
        List<Enemigo> enemigosAEliminar = new ArrayList<>();

        for (Enemigo enemigo : enemigos) {
            if (!enemigo.estaEliminado()) {
                boolean resultado = enemigo.verificarColisionConMario(marioBounds, mario.getDy());

                if (!resultado) {
                    // Mario tocó por el lado = reiniciar
                    reiniciarNivel();
                    return;
                } else if (enemigo.estaEliminado()) {
                    enemigosAEliminar.add(enemigo);
                }
            }
        }

        // Remover enemigos eliminados
        enemigos.removeAll(enemigosAEliminar);

        // Código de cámara...
        int centroVentana = ventala_ancho / 2;
        if (mario.getPosicionXMario() > centroVentana) {
            camaraX = mario.getPosicionXMario() - centroVentana;
            if (fondo != null && camaraX > fondo.getWidth() - ventala_ancho)
                camaraX = fondo.getWidth() - ventala_ancho;
        } else {
            camaraX = 0;
        }
        if (camaraX < 0) camaraX = 0;
    }

    public void reiniciarNivel() {
        mario = new Mario(200, 208);
        enemigos.clear();
        bloques.clear();
        inicializarEntidades();
    }

    /// ----- FIN LOGICA NIVEL -----

    /// Creacion de sprites

    public void dibujar(Graphics g, int panelWidth, int panelHeight) {
        // Fondo
        g.drawImage(
                fondo,
                0, 0, panelWidth, panelHeight,
                camaraX, 0, camaraX + ventala_ancho, ventana_alto,
                null
        );
        /*if (fondo != null) {

        } else {
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, panelWidth, panelHeight);
        }*/

        // Escalar gráficos
        Graphics2D g2 = (Graphics2D) g.create();
        double escalaX = panelWidth / (double) ventala_ancho;
        double escalaY = panelHeight / (double) ventana_alto;
        g2.scale(escalaX, escalaY);

        // Dibujar
        for (Bloque b : bloques) b.dibujar(g2, camaraX, 0);
        for (Enemigo e : enemigos) e.dibujar(g2, camaraX, 0);
        mario.dibujar(g2, camaraX, 0);

        g2.dispose();

        g.setColor(Color.YELLOW);
        g.setFont(loadFont(24f));
        g.drawString("Monedas: " + mario.getMonedas(), panelWidth/2, 30);
    }

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

    /// Getters y creacion de bloques

    public Mario getMario() { return mario; }
    public List<Enemigo> getEnemigos() { return enemigos; }
    public List<Bloque> getBloques() { return bloques; }

    public void crearBloqueTuberia(int posicion, int ancho, int alto) {
        bloques.add(new BloqueLadrillo(posicion*16,piso-(alto*16),ancho*16,alto*16));
    }
    public void crearBloqueMortal(int posicion, int altura,int ancho, int alto) {
        bloques.add(new BloqueMortal(posicion*16,piso-(altura*16),ancho*16,alto*16));
    }
    public void crearBloquePregunta(int posicion,int altura, int ancho, int alto) {
        bloques.add(new BloquePregunta(posicion*16,piso-(altura*16),ancho*16,alto*16));
    }
    public void crearBloqueVictoria(int posicion,int altura, int ancho, int alto) {
        bloques.add(new BloqueVictoria(posicion*16,piso-(altura*16),ancho*16,alto*16));
    }
    public void crearBloqueLadrillo(int posicion,int altura, int ancho, int alto) {
        bloques.add(new BloqueLadrillo(posicion*16,piso-(altura*16),ancho*16,alto*16));
    }
    public void crearEnemigo(int posicion,int altura) {
        enemigos.add(new Enemigo(posicion*16,piso-(altura*16)));
    }


}