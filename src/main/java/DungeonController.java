import java.util.Random;

public class DungeonController {

    public String[][] dungeonMatrix;
    public int[] playerPos = new int[2];

    public void initialiseRandDungeon() {
        String[][] walls = new String[12][2];
        Dungeon dungeon = new Dungeon(20, 20, walls);

        dungeonMatrix = new String[dungeon.getHeight()][dungeon.getWidth()];
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                dungeonMatrix[i][j] = " ";
            }
        }

        Random rand = new Random();
        int startHeight = rand.nextInt(dungeon.getHeight() - 1);

        for (int i = startHeight; i < startHeight + 5; i++) {
            if (i + startHeight >= dungeon.getHeight() && startHeight + i >= dungeon.getWidth()) {
                break;
            }
            dungeonMatrix[i][startHeight] = "+"; //left
            dungeonMatrix[startHeight][i] = "+"; //top
            dungeonMatrix[i][startHeight + 4] = "*"; //right
            dungeonMatrix[startHeight + 4][i] = "*"; //bottom
        }
        playerPos = new int[]{10, 10};
        dungeonMatrix[playerPos[0]][playerPos[1]] = "@";
        addDungeonBounds(dungeon);
    }

    public void addDungeonBounds(Dungeon dungeon) {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                dungeonMatrix[0][i] = "-";
                dungeonMatrix[dungeon.getHeight() - 1][i] = "-";
                dungeonMatrix[j][0] = "|";
                dungeonMatrix[j][dungeon.getWidth() - 1] = "|";
            }
        }
    }

    public void drawDungeonToConsole() {
        for (int i = 0; i < dungeonMatrix.length; i++) {
            for (int j = 0; j < dungeonMatrix[i].length; j++) {
                System.out.print(dungeonMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
