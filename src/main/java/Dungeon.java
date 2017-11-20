
public class Dungeon {

    private int height = 20; //setting default height
    private int width = 20; //setting default width
    private String[][] walls;
    private final char exit = 'E';

    public Dungeon(int height, int width, String[][] walls) {
        this.height = height;
        this.width = width;
        this.walls = walls;
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

    public String[][] getWalls() {
        return walls;
    }

    public void setWalls(String[][] walls) {
        this.walls = walls;
    }
}
