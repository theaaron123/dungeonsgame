public class DungeonController {

    public static void initialiseRandDungeon() {
        int[][] walls = new int[12][2];
        walls[0] = new int[]{1, 2};
        Dungeon dungeon = new Dungeon(20,20, walls);

        String[][] dungeonMatrix = new String[dungeon.getHeight()][dungeon.getWidth()];
        for(int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                dungeonMatrix[i][j] = "0";
            }
        }
        for(int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                dungeonMatrix[0][i] = "-";
                dungeonMatrix[dungeon.getHeight() - 1][i] = "-";
                dungeonMatrix[j][0] = "|";
                dungeonMatrix[j][dungeon.getWidth() - 1] = "|";
            }
        }
        drawDungeon(dungeonMatrix);
    }

    private static void drawDungeon(String[][] dungeonMatrix) {
        for (int i = 0; i < dungeonMatrix.length; i++) {
            for (int j = 0; j < dungeonMatrix[i].length; j++) {
                System.out.print(dungeonMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
