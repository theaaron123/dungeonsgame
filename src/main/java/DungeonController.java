import java.util.Random;

public class DungeonController {

    public String[][] dungeonMatrix;
    public Player player;

    public int[] roomX = new int [100]; //5
    public int[] roomY = new int [100]; //5
    public int loops = 0;
    public void initialiseRandDungeon() {
        String[][] walls = new String[12][2];
        Dungeon dungeon = new Dungeon(20, 20, walls);
        player = new Player();


        dungeonMatrix = new String[dungeon.getHeight()][dungeon.getWidth()];
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                dungeonMatrix[i][j] = " ";
            }
        }

        addRoomBounds(dungeon);
        //addRoomBounds(dungeon);
       // addRoomBounds(dungeon);

        player.setPlayerX(dungeon.getWidth() / 2); //set X to the centre of the map
        player.setPlayerY(dungeon.getHeight() / 2); //set X to the centre of the map

        dungeonMatrix[player.getPlayerX()][player.getPlayerY()] = player.getPlayerSymbol();
        addDungeonBounds(dungeon);
    }

    private void addRoomBounds(Dungeon dungeon) {
        Random rand = new Random();
        int startHeight = rand.nextInt(dungeon.getHeight() - 1);

        if (startHeight >= dungeon.getHeight() - 5) {
            startHeight = dungeon.getHeight() -6;
        } else if (startHeight < 1) {
            startHeight = 2;
        }

        for (int i = startHeight; i < startHeight + 5; i++) {

            int y = i;
            int x = startHeight;

            //y, x
            dungeonMatrix[i][startHeight] = "*"; //left
            dungeonMatrix[startHeight][i] = "*"; //top
            dungeonMatrix[i][startHeight + 4] = "*"; //right
            dungeonMatrix[startHeight + 4][i] = "*"; //bottom

            /*
            *    ~ Adding to USABLE Arrays ~ As seen in DungeonView keyboard input - Marc.
            */
            roomX[loops] = i;
            roomY[loops] = startHeight;

            roomX[loops + 5] = startHeight;
            roomY[loops + 5] = i;

            roomX[loops + 10] = i;
            roomY[loops + 10] = startHeight + 4;

            roomX[loops + 15] = startHeight + 4;
            roomY[loops + 15] = i;


            loops++;
        }
        System.out.println("Loops:"+loops);
        removeDoor();
        loops += 16;
       /* for (int i = 0; i < 100; i++) {
            System.out.println(i + ": X: " + roomX[i] + " y:" + roomY[i]);

        }*/

    }

    private void removeDoor() {
        if (roomY[0] > 0 && roomY[0] < 8) // remove bottom
        {
            System.out.println(roomY[loops+15]);
            dungeonMatrix[roomY[loops+7]][roomX[loops+7]] = " "; //left
            roomY[loops+7] = 0;
            roomX[loops+7] = 0;
            System.out.println(roomY[loops+15]);
            System.out.println("done");
        }
        else if (roomY[0] > 7 && roomY[0] < 20) { //remove top
            System.out.println(roomY[loops]);
            dungeonMatrix[roomY[loops-3]][roomX[loops-3]] = " "; //left
            roomY[loops-3] = 0;
            roomX[loops-3] = 0;
            System.out.println(roomY[loops]);
            System.out.println("done2");
        }

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
