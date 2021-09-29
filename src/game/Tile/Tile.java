package game.Tile;


import game.gfx.Colours;
import game.gfx.Screen;
import game.level.Level;

public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BasicSolidTile(0,0,0, Colours.get(0,-1,-1,-1), 0xFF000000);
    public static final Tile STONE = new BasicSolidTile(1,1,0, Colours.get(-1,432,321,-1),0xFF555555);
    public static final Tile GRASS1 = new BasicTile(2,2,0, Colours.get(-1,131,141,-1), 0xFF00FF00);
    public static final Tile FLOWERS1 = new BasicTile(3,3,0, Colours.get(9,131,141,543), 0xFF00C800);
    public static final Tile FLOWERS2 = new BasicTile(4,4,0, Colours.get(400,131,141,543), 0xFFFF0000);
    public static final Tile BRICKWALL = new BasicSolidTile(5,5,0, Colours.get(-1,321,310,-1), 0xFFF00000);
    public static final Tile BERRYBUSH = new BasicSolidTile(6,6,0, Colours.get(500,131,141,-1), 0xFF00CC00);


    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColour;

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
        this.id = (byte) id;
        if(tiles[id] != null) throw new RuntimeException("Duplicate tile id on" + id);
        this.solid = isSolid;
        this.emitter = isEmitter;
        this.levelColour = levelColour;
        tiles[id] = this;
    }

    public byte getId() {
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isEmitter() {
        return emitter;
    }

    public int getLevelColour() {return levelColour;}

    public abstract void render(Screen screen, Level level, int x, int y);
}
