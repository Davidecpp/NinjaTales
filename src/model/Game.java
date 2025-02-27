package model;

import model.objects.GameObject;
import view.GraphicPanel;

public class Game implements Runnable{
    public  Window window;
    public GraphicPanel gp;
    private Thread gameThread;
    public final int FPS = 120;

    //TILES
    public final static int defaultTileSize = 32;
    public final static double scale = 2.5;
    public final static int tileSize = (int) (defaultTileSize * scale);
    public final static int tileWidth = 18;
    public final static int tileHeight = 12;
    public static int gameWidth = tileSize * tileWidth;
    public static int gameHeight = tileSize * tileHeight;
    //MAP
    public final int mapCols= 50, mapRows = 50;

    public GameObject obj[] = new GameObject[50];
    public Creature enemies[] = new Creature[50];
    public Creature proj[] = new Creature[30];

    public Game(){
        gp = new GraphicPanel(this);
        window = new Window(gp);
        gp.requestFocus();
        start();
    }
    public void start(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double frameInterval = 1000000000/FPS;
        double delta = 0;
        long last = System.nanoTime();
        long current;
        long timer = 0;
        int c = 0;

        while (true) {
            current = System.nanoTime();
            delta += (current - last) / frameInterval;
            timer += (current - last);
            last = current;

            if (delta >= 1) {
                gp.repaint();
                gp.update();
                delta--;
                c++;
            }
            if (timer >= 1000000000) {
                System.out.print("FPS: " + c + "\n");
                c = 0;
                timer = 0;
            }
        }

    }
}
