package game.entities;

import game.gfx.Colours;
import game.gfx.Screen;
import game.level.Level;

public class Enemy extends Mob{

    private final int colour = Colours.get(-1, 400, 500,543);

    public Enemy(Level level, String name, int x, int y, int speed) {
        super(level, name, x, y, speed);
    }

    public void tick() {
    }

    public void walkTowardsPlayer() {

    }

    public void render(Screen screen) {
        int xTile =0;
        int yTile =28;
        screen.render(0,0,xTile+yTile*31,colour,0x00,scale);
    }


    public boolean hasCollided(int xa, int ya) {
        return false;
    }
}
