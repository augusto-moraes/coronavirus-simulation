import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.Panel;

/**
 * Gestionnaire d'affichage pour le jeu de la vie.
 * 
 * @author Brice Chardin <brice.chardin@insa-lyon.fr>
 */
public class Affichage extends Panel {
    private static final int MAX_RESOLUTION = 10;
    private static final int MAX_SIZE = 700;
    private static JFrame frame = null;
    private static Affichage world = null;
    private static BufferedImage image = null;
    private static boolean init = false;
    private static int resolution = 0;
    private static int sleepTime = 500;
    private BufferedImage worldImage;
    //private static long time; // pour le calcul des FPS

    private Affichage() {}

    /**
     * Affiche le contenu d'une cellule.
     * @param g l'abstraction Graphics du composant à dessiner.
     * @param monde le monde auquel appartient la cellule.
     * @param x l'abscisse de la cellule.
     * @param y l'ordonnée de la cellule.
     */
    private static void drawCell(Graphics g, Population monde, int x, int y) {
		int color = monde.population[x][y].etat;
		if (color==1) g.setColor(java.awt.Color.GREEN);
		if (color==2) g.setColor(java.awt.Color.RED);
		if (color==3) g.setColor(java.awt.Color.BLACK);
		if (color==4) g.setColor(java.awt.Color.BLUE);

        if (monde.population[x][y].etat>0) {
            g.fillRect(resolution * y + 1, resolution * x + 1, resolution, resolution);
        }
    }

    /**
     * Affiche un monde. Une fois l'affichage fait, cette méthode force 
     * une attente, permettant d'avoir le temps de voir les choses.
     * La durée d'attente est configurable via setDelay.
     * @param monde le monde à afficher
     */
    public static void affichePop(Population monde) {
        int xSize = monde.taille;
        int ySize = monde.taille;
        if (!init) {
            if (resolution <= 0) {
                /* on adapte la résolution à la hauteur du monde */
                resolution = MAX_SIZE / monde.taille;
                if (resolution <= 0) {
                    resolution = 1;
                } else if (resolution > MAX_RESOLUTION) {
                    resolution = MAX_RESOLUTION;
                }
            }
            frame = new JFrame("Simulation coronavirus by Augusto Moraes");
            frame.setSize(resolution * xSize + 2, resolution * ySize + 2);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            /*frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                }
            );*/
            image = new BufferedImage(resolution * xSize + 2, resolution * ySize + 2, BufferedImage.TYPE_INT_RGB);
            world = new Affichage(image);
            frame.add(world);
            frame.pack();
            frame.setVisible(true);
            //time = System.nanoTime();
            init = true;
        }

        Graphics g = image.getGraphics();
        /* on efface l'image */
        //g.clearRect(0, 0, resolution * xSize + 2, resolution * ySize + 2);
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, resolution * xSize + 2, resolution * ySize + 2);
        /* on dessine la bordure */
        g.setColor(java.awt.Color.GRAY);
        g.drawRect(0, 0, resolution * xSize + 1, resolution * ySize + 1);
        /* et on dessine les cellules vivantes */
        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                drawCell(g, monde, i, j);
            }
        }
        /* affichage des FPS */
        /*
        g.setColor(java.awt.Color.RED);
        long newTime = System.nanoTime();
        g.drawString(Long.toString(1000000000/(newTime - time)), 4, 14);
        time = newTime;
        */

        /* lorsque l'image est calculée, on rafraichit l'affichage */
        world.paint(world.getGraphics());

        /* on limite artificiellement les FPS */
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {}
    }

    /**
     * Modifie le temps d'attente entre deux affichages successifs.
     * @param delay le temps d'attente en millisecondes.
     */
    public static void setDelay(int delay) {
        Affichage.sleepTime = delay;
    }

    /**
     * Modifie la résolution de l'affichage.
     * @param resolution la taille des cellules en pixels.
     */
    public static void setResolution(int resolution) {
        Affichage.resolution = resolution;
    }

    public Affichage(BufferedImage image) {
        this.worldImage = image;
    }

    public void paint(Graphics g) {
        //super.paint();
        g.drawImage(worldImage, 0, 0, this);
    }
 
    public Dimension getPreferredSize() {
        return new Dimension(worldImage.getWidth(), worldImage.getHeight());
    }
}       
