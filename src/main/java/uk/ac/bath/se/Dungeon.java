package uk.ac.bath.se;

public class Dungeon {

    public static final int MAXIMUM_ROOMS = 4;
    private static Dungeon instance = null;
    private final char exit = 'E';
    public String[][] dungeonMatrix;
    private int height = 40; //setting default height
    private int width = 40; //setting default width
    private int[][] gridBounds;

    protected Dungeon() {
    }

    public static Dungeon getInstance() {
        if (instance == null) {
            instance = new Dungeon();
        }
        return instance;
    }

    public void initialiseDungeon() {
        //initialise dungeon with blanks
        dungeonMatrix = new String[this.height][this.width];
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                dungeonMatrix[i][j] = " ";
            }
        }
    }

    public String[][] getDungeonMatrix() {
        return dungeonMatrix;
    }

    public void setDungeonMatrix(String[][] dungeonMatrix) {
        this.dungeonMatrix = dungeonMatrix;
    }

    public int[][] getGridBounds() {
        return gridBounds;
    }

    public void setGridBounds(int[][] gridBounds) {
        this.gridBounds = gridBounds;
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
