import java.util.Random;

public class DungeonController {

    public String[][] dungeonMatrix;
    public Player player;
    public Gold[] golds;
    public Dungeon dungeon;
    public int[] roomX = new int[100]; //5
    public int[] roomY = new int[100]; //5
    public int[] doorLocations = new int[100];
    public int[][] gridBounds;


    public void initialiseRandDungeon() {
        String[][] walls = new String[12][2];
        dungeon = new Dungeon(40, 40, walls);
        player = new Player();
        gridBounds = new int[dungeon.getHeight()][dungeon.getWidth()];

        golds = new Gold[dungeon.MAXIMUM_ROOMS];

        dungeonMatrix = new String[dungeon.getHeight()][dungeon.getWidth()];
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                dungeonMatrix[i][j] = " ";
            }
        }
        player.setPlayerX(dungeon.getWidth() / 2 + 3); //set X to the centre of the map dungeon.getWidth() / 2
        player.setPlayerY(dungeon.getHeight() / 2 + 3); //set Y to the centre of the map
        player.setPosition(player.getPlayerY(), player.getPlayerX());


        addRoomBounds(dungeon, dungeon.MAXIMUM_ROOMS);

        gridBounds[roomY[0] + 2][roomX[0] + 2] = 2;

        dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
        addDungeonBounds(dungeon);
    }



    private void addRoomBounds(Dungeon dungeon, int rooms) {
        Random rand = new Random();
        int startHeight;
        int startWidth;

        for (int r = 1; r <= rooms; r++) { //1 top left - 2 top right - 3 bottom left - 4 bottom right

            switch(r) {
                case 1:
                    startHeight = rand.nextInt((dungeon.getHeight()/2-6)-2) + 2;
                    startWidth = rand.nextInt((dungeon.getWidth()/2-6)-2) + 2;
                    System.out.println("Room 1: "+startHeight+" "+startWidth);
                    addGold(dungeon, r, startHeight, startWidth);
                    break;

                case 2:
                    startHeight = rand.nextInt((dungeon.getHeight()/2-6)-2) + 2;
                    startWidth = rand.nextInt((dungeon.getWidth()-7)-(dungeon.getWidth()/2 +2)) + (dungeon.getWidth()/2 +2);
                    System.out.println("Room 2: "+startHeight+" "+startWidth);
                    addGold(dungeon, r, startHeight, startWidth);
                    break;

                case 3:
                    startHeight = rand.nextInt((dungeon.getHeight()-7)-(dungeon.getHeight()/2+2)) + (dungeon.getHeight()/2+2);
                    startWidth = rand.nextInt((dungeon.getWidth()/2-6)-2) + 2;
                    System.out.println("Room 3: "+startHeight+" "+startWidth);
                    addGold(dungeon, r, startHeight, startWidth);
                    break;

                case 4:
                    startHeight = rand.nextInt((dungeon.getHeight()-7)-(dungeon.getHeight()/2+2)) + (dungeon.getHeight()/2+2);
                    startWidth = rand.nextInt((dungeon.getWidth()-7)-(dungeon.getWidth()/2 +2)) + (dungeon.getWidth()/2 +2);
                    System.out.println("Room 4: "+startHeight+" "+startWidth);
                    addGold(dungeon, r, startHeight, startWidth);
                    break;

                    default:
                        startHeight = rand.nextInt(1);
                        startWidth = rand.nextInt(1);
            }


            for (int i = 0; i < 5; i++) {
                //y, x
                dungeonMatrix[i + startHeight][startWidth] = "*"; //left
                dungeonMatrix[startHeight][i + startWidth] = "*"; //top
                dungeonMatrix[i + startHeight][startWidth + 4] = "*"; //right
                dungeonMatrix[startHeight + 4][i + startWidth] = "*"; //bottom

                gridBounds[i + startHeight][startWidth] = 1; //left
                gridBounds[startHeight][i + startWidth] = 1; //top
                gridBounds[i + startHeight][startWidth + 4] = 1; //right
                gridBounds[startHeight + 4][i + startWidth] = 1; //bottom

            /*
            *    ~ Adding to USABLE Arrays ~ As seen in DungeonView keyboard input - Marc.
            */
                roomX[i] = startWidth; //left
                roomX[i + 5] = startWidth + i; //top
                roomX[i + 10] = startWidth + 4; // right
                roomX[i + 15] = startWidth + i; //bottom

                roomY[i] = startHeight + i; //left
                roomY[i + 5] = startHeight; //top
                roomY[i + 10] = startHeight + i; // right
                roomY[i + 15] = startHeight + 4; //bottom

            }
            removeDoor(dungeon);
        }
    }

    private void addGold(Dungeon dungeon, int room, int sh, int sw) {
        golds[room-1] = new Gold();
        golds[room-1].setGoldX(sw+2);
        golds[room-1].setGoldY(sh+2);
        System.out.println(golds[room-1].getGoldX() + " "+golds[room-1].getGoldY());

        dungeonMatrix[sh+2][sw+2] = golds[room-1].getGoldSymbol();
        gridBounds[sh+2][sw+2] = 2;
    }

    private void removeDoor(Dungeon dungeon) {

        if (roomY[0] >= 0 && roomY[0] <= dungeon.getHeight() / 2) // remove bottom
        {
            gridBounds[roomY[17]][roomX[17]] = 0;
            dungeonMatrix[roomY[17]][roomX[17]] = " "; //left
            roomY[17] = 0;
            roomX[17] = 0;


        } else if (roomY[0] > dungeon.getHeight() / 2 && roomY[0] < dungeon.getHeight()) { //remove top
            gridBounds[roomY[0]][roomX[7]] = 0;
            dungeonMatrix[roomY[0]][roomX[7]] = " "; //left
            roomY[15] = 0;
            roomX[7] = 0;
        }

    }


    public void addDungeonBounds(Dungeon dungeon) {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                dungeonMatrix[0][i] = "-"; //top

                gridBounds[0][i] = 1;
                dungeonMatrix[dungeon.getHeight() - 1][i] = "-";
                gridBounds[dungeon.getHeight() - 1][i] = 1;

                dungeonMatrix[j][0] = "|";
                gridBounds[j][0] = 1;

                dungeonMatrix[j][dungeon.getWidth() - 1] = "|";
                gridBounds[j][dungeon.getWidth() - 1] = 1;

                dungeonMatrix[i][dungeon.getWidth() / 2] = "|";
                gridBounds[i][dungeon.getWidth() / 2] = 1;

                dungeonMatrix[dungeon.getHeight() / 2][i] = "-";
                gridBounds[dungeon.getHeight() / 2][i] = 1;
            }
            gridBounds[dungeon.getHeight() / 2][i] = 1;
            gridBounds[i][dungeon.getWidth() / 2] = 1;
        }
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (j == 39) {
                    System.out.println(gridBounds[i][j]);
                } else {
                    System.out.print(+gridBounds[i][j]);
                }
            }
        }
        addPassage(dungeon);
        addExit(dungeon);
    }

    private void addPassage(Dungeon dungeon) {
        Random rand = new Random();

        doorLocations[0] = rand.nextInt(dungeon.getHeight() / 2);
        doorLocations[0] += 1;
        gridBounds[doorLocations[0]][dungeon.getWidth() / 2] = 0;
        dungeonMatrix[doorLocations[0]][dungeon.getWidth() / 2] = " "; //top vertical


        doorLocations[0] = rand.nextInt(dungeon.getHeight() - dungeon.getHeight() / 2) + dungeon.getHeight() / 2; //bottom vertical
        doorLocations[0] += 1;
        gridBounds[doorLocations[0]][dungeon.getWidth() / 2] = 0;
        dungeonMatrix[doorLocations[0]][dungeon.getWidth() / 2] = " ";

        doorLocations[0] = rand.nextInt(dungeon.getWidth() / 2); //left horiz
        doorLocations[0] += 1;
        gridBounds[dungeon.getHeight() / 2][doorLocations[0]] = 0;
        dungeonMatrix[dungeon.getHeight() / 2][doorLocations[0]] = " ";

        doorLocations[0] = rand.nextInt(dungeon.getWidth() - dungeon.getWidth() / 2) + dungeon.getWidth() / 2; //left horiz
        doorLocations[0] += 2;
        gridBounds[dungeon.getHeight() / 2][doorLocations[0]] = 0;
        dungeonMatrix[dungeon.getHeight() / 2][doorLocations[0]] = " ";
    }

    private void addExit(Dungeon dungeon) {
        Random rand = new Random();
        doorLocations[1] = rand.nextInt() & dungeon.getHeight() - 4;
        doorLocations[1] += 2;

        while (doorLocations[1] == doorLocations[0]) {
            doorLocations[1] = rand.nextInt() & dungeon.getHeight() - 4;
            doorLocations[1] += 2;
        }

        dungeonMatrix[doorLocations[1]][0] = "E";
    }

    public void addScore() {
        if (gridBounds[player.getPlayerY()][player.getPlayerX()] == 2) {

            gridBounds[player.getPlayerY()][player.getPlayerX()] = 0;
            player.setGold(player.getGold() + golds[0].getGoldQuantity());
            System.out.println(player.getGold());
        }
    }


    public void checkExit() {
        if (player.getPlayerX() == 2 && player.getPlayerY() == doorLocations[1]) {
            if (player.getGold() >= Dungeon.GAME_WIN_AMOUNT) {
                System.out.println("Completed");
            }
        }


    }
}
