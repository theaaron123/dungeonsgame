
public class Dungeon {

    private int height = 20; //setting default height
    private int width = 20; //setting default width
    private final char exit = 'E';
    public static final int GAME_WIN_AMOUNT = 5;
    public static final int MAXIMUM_ROOMS = 4;

    public Dungeon(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
