package game.main;
/*
 @author Deivis Zolba
  *Vilnius University, Informatics group 2.
  *2D game in pure JAVA
 */

import game.entities.Bullet;
import game.entities.Enemy;
import game.entities.Player;
import game.gfx.Screen;
import game.gfx.SpriteSheet;
import game.level.Level;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{

    private static final long serialVersion = 1;

    public static final int WIDTH = 280;
    public static final int HEIGHT = WIDTH/ 12 * 9;
    public static final int SCALE = 5;
    public static final String NAME = "Game";

    public boolean running = false;
    public int tickCount = 0;

    private final BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private final int[] colours = new int[6 * 6 * 6];

    private Screen screen;
    public InputHandler input;
    public Level level;
    public Player player;
    public Enemy enemy;
    public Bullet bullet;

    public Game() {
        setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));

        JFrame frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {
        int index = 0;
        for(int r = 0; r < 6; r++) {
            for(int g = 0; g < 6; g++) {
                for(int b = 0; b < 6; b++) {
                    int red = (r*255/5);
                    int green = (g*255/5);
                    int blue = (b*255/5);

                    colours[index++] = red << 16 | green << 8 | blue;
                }
            }
        }
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/Sprite_sheet.png"));
        input = new InputHandler(this);
        level = new Level("/levels/level2.png");
        player = new Player(level,60,16,1,input);
        enemy = new Enemy(level, "bob",30,30,4);
        level.addEntity(player);
        level.addEntity(enemy);
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ nsPerTick;
            lastTime = now;

            while(delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                frames++;
                render();
            if(System.currentTimeMillis()-lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println("frames:" + frames + ", ticks:" + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        if(input.shootUp.isPressed()) {
            if(tickCount %30 == 0){
                bullet = new Bullet(level, "bullet", player.x, player.y, 2, 0);
                level.addEntity(bullet);
            }
        }else
        if(input.shootDown.isPressed()) {
            if(tickCount %30 == 0) {
                bullet = new Bullet(level, "bullet", player.x, player.y, 2, 1);
                level.addEntity(bullet);
            }
        }else
        if(input.shootLeft.isPressed()) {
            if(tickCount %30 == 0) {
                bullet = new Bullet(level, "bullet", player.x, player.y, 2, 2);
                level.addEntity(bullet);
            }
        }else
        if(input.shootRight.isPressed()) {
            if(tickCount %30 == 0) {
                bullet = new Bullet(level, "bullet", player.x, player.y, 2, 3);
                level.addEntity(bullet);
            }
        }
        tickCount++;
        level.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        int xOffset = player.x-(screen.width/2);
        int yOffset = player.y-(screen.height/2);
        level.renderTiles(screen, xOffset, yOffset);

        level.renderEntities(screen);

        for(int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colourCode = screen.pixels[x+y * screen.width];
                if(colourCode < 255) pixels[x+y*WIDTH] = colours[colourCode];
            }
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0,getWidth(),getHeight(),null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        System.out.println("Game version:" + serialVersion);
        new Game().start();

    }
}
