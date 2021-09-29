package game.entities;

import game.gfx.Colours;
import game.gfx.Screen;
import game.level.Level;

public class Bullet extends Mob {

    private final int colour = Colours.get(-1, 543, -1,-1);

    int movingDir;

    boolean colided = false;

    public Bullet(Level level, String name, int x, int y, int speed,int movingDir) {
        super(level, name, x, y, speed);
        this.movingDir = movingDir;
    }

    public void tick() {
        if (movingDir == 0) {
            move(0, -1);
        } else if (movingDir == 1) {
            move(0, 1);
        } else if (movingDir == 2) {
            move(-1, 0);
        } else {
            move(1, 0);
        }
    }

    public void render(Screen screen) {
        if(!colided) {
            int xTile = 0;
            int yTile = 27;

            int modifier = 8 * scale;
            int xOffset = x - modifier / 2;
            int yOffset = y - modifier / 2 - 4;
            screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, colour, 0, scale);
        }
    }

    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 4;
        int yMin = 0;
        int yMax = 4;
        for(int x = xMin; x < xMax; x++) {
            if(isSolidTile(xa+8,ya-8,x,yMin)){ colided = true; return true; }
            if(isSolidTile(xa+8,ya-8,x,yMax)){ colided = true; return true; }
        }
        for(int y = yMin; y < yMax; y++) {
            if(isSolidTile(xa+8,ya-8,xMin,y)) { colided = true; return true; }
            if(isSolidTile(xa+8,ya-8,xMax,y)) { colided = true; return true; }
        }
        return false;
    }
}
