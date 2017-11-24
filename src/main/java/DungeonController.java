import java.util.Random;

public class DungeonController {

    public String[][] dungeonMatrix;
    public Player player;
    public Gold gold;
    public Dungeon dungeon;
    public int[] roomX = new int[100]; //5
    public int[] roomY = new int[100]; //5
    public int[] doorLocations = new int[2];


    public void initialiseRandDungeon() {
        String[][] walls = new String[12][2];
        dungeon = new Dungeon(40, 40, walls);
        player = new Player();
        gold = new Gold();


        dungeonMatrix = new String[dungeon.getHeight()][dungeon.getWidth()];
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                dungeonMatrix[i][j] = " ";
            }
        }
        player.setPlayerX(dungeon.getWidth()/2); //set X to the centre of the map dungeon.getWidth() / 2
        player.setPlayerY(dungeon.getHeight() /2); //set Y to the centre of the map
        System.out.println(player.getPlayerX() + " y: "+player.getPlayerY());
        addRoomBounds(dungeon);

        gold.setGoldX(roomX[0] + 2); //always center
        gold.setGoldY(roomY[0] + 2);

        dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
        dungeonMatrix[gold.getGoldY()][gold.getGoldX()] = gold.getGoldSymbol();
        addDungeonBounds(dungeon);
    }

    private void addRoomBounds(Dungeon dungeon) {
        Random rand = new Random();
        int startHeight = rand.nextInt() & dungeon.getHeight() -7;
        int startWidth = rand.nextInt() & dungeon.getWidth() - 7;

        startHeight +=2;
        startWidth +=2;

        while (player.getPlayerY() >= startHeight && player.getPlayerY() < startHeight+5
                || player.getPlayerX() >= startWidth && player.getPlayerX() < startWidth+5) {
            System.out.println("Moved "+startHeight+" to: ");
            startHeight = rand.nextInt() & dungeon.getHeight()-7;
            startHeight +=2;
            startWidth = rand.nextInt() & dungeon.getWidth()-7;
            startWidth +=2;
        }

        for (int i = 0; i < 5; i++) {
            //y, x
            dungeonMatrix[i+startHeight][startWidth] = "*"; //left
            dungeonMatrix[startHeight][i+startWidth] = "*"; //top

            dungeonMatrix[i+startHeight][startWidth + 4] = "*"; //right
            dungeonMatrix[startHeight + 4][i+startWidth] = "*"; //bottom

            /*
            *    ~ Adding to USABLE Arrays ~ As seen in DungeonView keyboard input - Marc.
            */
            roomX[i] = startWidth; //left
            roomX[i+5] = startWidth + i; //top
            roomX[i+10] = startWidth + 4; // right
            roomX[i+15] = startWidth + i; //bottom

            roomY[i] = startHeight + i; //left
            roomY[i+5] = startHeight; //top
            roomY[i+10] = startHeight + i; // right
            roomY[i+15] = startHeight + 4; //bottom

        }
        removeDoor(dungeon);
    }

    private void removeDoor(Dungeon dungeon) {
        if (roomY[0] > 0 && roomY[0] <= dungeon.getHeight()/2) // remove bottom
        {
            dungeonMatrix[roomY[17]][roomX[17]] = " "; //left
            roomY[17] = 0;
            roomX[17] = 0;
        } else if (roomY[0] > dungeon.getHeight()/2 && roomY[0] < dungeon.getHeight()) { //remove top
            dungeonMatrix[roomY[0]][roomX[7]] = " "; //left
            roomY[15] = 0;
            roomX[7] = 0;
        }

    }


    public void addDungeonBounds(Dungeon dungeon) {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                dungeonMatrix[0][i] = "-"; //top
                dungeonMatrix[dungeon.getHeight() - 1][i] = "-";
                dungeonMatrix[j][0] = "|";
                dungeonMatrix[j][dungeon.getWidth() - 1] = "|";

                dungeonMatrix[i][dungeon.getWidth()/2] = "|";
                dungeonMatrix[dungeon.getHeight()/2][i] = "-";
            }
        }

        addPassage(dungeon);
        addExit(dungeon);
    }

    private void addPassage(Dungeon dungeon) {
        Random rand = new Random();

        doorLocations[0] = rand.nextInt() & dungeon.getHeight() - 4;
        doorLocations[0] +=2;
        dungeonMatrix[doorLocations[0]][0] = " ";
    }

    private void addExit(Dungeon dungeon) {
        Random rand = new Random();
        doorLocations[1] = rand.nextInt() & dungeon.getHeight() - 4;
        doorLocations[1] +=2;

        while (doorLocations[1] == doorLocations[0]) {
            doorLocations[1] = rand.nextInt() & dungeon.getHeight() - 4;
            doorLocations[1] +=2;
        }

        dungeonMatrix[doorLocations[1]][0] = "E";
    }

    public void addScore() {
        if (player.getPlayerX() == gold.getGoldX() &&
                player.getPlayerY() == gold.getGoldY()) {
            removeGold();

            player.setGold(player.getGold() + gold.getGoldQuantity());
            System.out.println(player.getGold());
        }
    }

    private void removeGold() {
        gold.setGoldX(-1);
        gold.setGoldY(-1);
    }

    public void checkExit() {
        if (player.getPlayerX() == 2 && player.getPlayerY() == doorLocations[1]) {
            if (player.getGold() >= Dungeon.GAME_WIN_AMOUNT) {
                System.out.println("Completed");
            }
        }


    }
}
