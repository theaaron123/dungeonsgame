import java.util.Random;

public class DungeonController {

    public Player player;
    public Dungeon dungeon;
    public Gold[] golds;
    public String[][] dungeonMatrix;
    public int[][] gridBounds;

    public void initialiseRandDungeon() {
        player = new Player();
        dungeon = new Dungeon(40, 40);
        golds = new Gold[dungeon.MAXIMUM_ROOMS];
        gridBounds = new int[dungeon.getHeight()][dungeon.getWidth()];

        //initialise dungeon with blanks
        dungeonMatrix = new String[dungeon.getHeight()][dungeon.getWidth()];
        for (int i = 0; i < dungeon.getHeight(); i++) {
            for (int j = 0; j < dungeon.getWidth(); j++) {
                dungeonMatrix[i][j] = " ";
            }
        }

        //Set dungeon bounds
        addDungeonBounds(dungeon);

        //Set up player location
        player.setPlayerX(2); //set X to left of the map
        player.setPlayerY(dungeon.getHeight() - 3); //set Y to the bottom of the map
        dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();

        //Set up rooms
        addRoomBounds(dungeon, dungeon.MAXIMUM_ROOMS);

    }

    public void addDungeonBounds(Dungeon dungeon) {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                dungeonMatrix[0][i] = "-"; //draw top
                gridBounds[0][i] = 1; //store top

                dungeonMatrix[dungeon.getHeight() - 1][i] = "-"; //draw bottom
                gridBounds[dungeon.getHeight() - 1][i] = 1; //store bottom

                dungeonMatrix[j][0] = "|"; //draw left
                gridBounds[j][0] = 1; //store left

                dungeonMatrix[j][dungeon.getWidth() - 1] = "|";//draw right
                gridBounds[j][dungeon.getWidth() - 1] = 1;//store right

            }
            dungeonMatrix[dungeon.getHeight() / 2][i] = "-"; //draw middle horizontal
            gridBounds[dungeon.getHeight() / 2][i] = 1; //store middle horizontal

            dungeonMatrix[i][dungeon.getWidth() / 2] = "|"; //draw middle vertical
            gridBounds[i][dungeon.getWidth() / 2] = 1; //store middle vertical
        }

        addPassage(dungeon);
        addExit(dungeon);

        //Print dungeon array to console
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                if (j == dungeon.getWidth()-1) {
                    System.out.println(gridBounds[i][j]);
                } else {
                    System.out.print(+gridBounds[i][j]);
                }
            }
        }
    }

    private void addPassage(Dungeon dungeon) {
        Random rand = new Random();
        int doorLocation;

        //Top vertical door
        doorLocation = rand.nextInt((dungeon.getHeight()/2-1)-1)+1;
        gridBounds[doorLocation][dungeon.getWidth() / 2] = 0;
        dungeonMatrix[doorLocation][dungeon.getWidth() / 2] = " ";

        //Bottom vertical door
        doorLocation = rand.nextInt((dungeon.getHeight()-1) - (dungeon.getHeight()/2+1)) + (dungeon.getHeight()/2+1);
        gridBounds[doorLocation][dungeon.getWidth() / 2] = 0;
        dungeonMatrix[doorLocation][dungeon.getWidth() / 2] = " ";

        //Left horizontal door
        doorLocation = rand.nextInt((dungeon.getWidth()/2-1)-1)+1;
        gridBounds[dungeon.getHeight() / 2][doorLocation] = 0;
        dungeonMatrix[dungeon.getHeight() / 2][doorLocation] = " ";

        //Right horizontal door
        doorLocation = rand.nextInt((dungeon.getWidth()-1) - (dungeon.getWidth()/2+1)) + (dungeon.getWidth()/2+1);
        gridBounds[dungeon.getHeight() / 2][doorLocation] = 0;
        dungeonMatrix[dungeon.getHeight() / 2][doorLocation] = " ";
    }

    private void addExit(Dungeon dungeon) {
        int exitLocation;
        int wall;
        Random rand = new Random();
        wall = rand.nextInt(4); //Random integer between 1 and 4
        //Uses height only to find middle dungeon values, will not work if not a square
        exitLocation = rand.nextInt((dungeon.getHeight()-1) - 1) + 1;
        while (exitLocation == dungeon.getHeight()/2) {
            exitLocation = rand.nextInt((dungeon.getHeight()-1) - 1) + 1;
            System.out.println("Moved Exit");
        }

        switch (wall) {
            case 0: //Top wall
                dungeonMatrix[0][exitLocation] = "E";
                gridBounds[0][exitLocation] = 5;
                break;
            case 1: //Bottom wall
                dungeonMatrix[dungeon.getHeight()-1][exitLocation] = "E";
                gridBounds[dungeon.getHeight()-1][exitLocation] = 5;
                break;
            case 2: // Left wall
                dungeonMatrix[exitLocation][0] = "E";
                gridBounds[exitLocation][0] = 5;
                break;
            case 3: //Right wall
                dungeonMatrix[exitLocation][dungeon.getWidth()-1] = "E";
                gridBounds[exitLocation][dungeon.getWidth()-1] = 5;
                break;
        }
    }

    private void addRoomBounds(Dungeon dungeon, int rooms) {
        Random rand = new Random();
        int startHeight;
        int startWidth;

        for (int r = 1; r <= rooms; r++) { //1 - top left, 2 - top right, 3 - bottom left, 4 - bottom right

            switch(r) {
                case 1:
                    startHeight = rand.nextInt((dungeon.getHeight()/2-6)-2) + 2;
                    startWidth = rand.nextInt((dungeon.getWidth()/2-6)-2) + 2;
                    System.out.println("Room 1: "+startHeight+", "+startWidth);
                    addGold(r, startHeight, startWidth);
                    break;

                case 2:
                    startHeight = rand.nextInt((dungeon.getHeight()/2-6)-2) + 2;
                    startWidth = rand.nextInt((dungeon.getWidth()-7)-(dungeon.getWidth()/2 +2)) + (dungeon.getWidth()/2 +2);
                    System.out.println("Room 2: "+startHeight+", "+startWidth);
                    addGold(r, startHeight, startWidth);
                    break;

                case 3:
                    startHeight = rand.nextInt((dungeon.getHeight()-7)-(dungeon.getHeight()/2+2)) + (dungeon.getHeight()/2+2);
                    startWidth = rand.nextInt((dungeon.getWidth()/2-6)-2) + 2;
                    System.out.println("Room 3: "+startHeight+", "+startWidth);
                    addGold(r, startHeight, startWidth);
                    break;

                case 4:
                    startHeight = rand.nextInt((dungeon.getHeight()-7)-(dungeon.getHeight()/2+2)) + (dungeon.getHeight()/2+2);
                    startWidth = rand.nextInt((dungeon.getWidth()-7)-(dungeon.getWidth()/2 +2)) + (dungeon.getWidth()/2 +2);
                    System.out.println("Room 4: "+startHeight+", "+startWidth);
                    addGold(r, startHeight, startWidth);
                    break;

                    default:
                        startHeight = rand.nextInt(1);
                        startWidth = rand.nextInt(1);
            }


            for (int i = 0; i < 5; i++) {
                //draw room (y, x)
                dungeonMatrix[i + startHeight][startWidth] = "*"; //left
                dungeonMatrix[startHeight][i + startWidth] = "*"; //top
                dungeonMatrix[i + startHeight][startWidth + 4] = "*"; //right
                dungeonMatrix[startHeight + 4][i + startWidth] = "*"; //bottom

                gridBounds[i + startHeight][startWidth] = 1; //left
                gridBounds[startHeight][i + startWidth] = 1; //top
                gridBounds[i + startHeight][startWidth + 4] = 1; //right
                gridBounds[startHeight + 4][i + startWidth] = 1; //bottom

            }
            removeDoor(startHeight, startWidth);
        }
    }

    private void addGold(int room, int sh, int sw) {
        golds[room-1] = new Gold();
        golds[room-1].setGoldX(sw+2);
        golds[room-1].setGoldY(sh+2);

        dungeonMatrix[sh+2][sw+2] = golds[room-1].getGoldSymbol();
        gridBounds[sh+2][sw+2] = 2;
    }

    private void removeDoor(int sh, int sw) {
        System.out.println(sh + " " + sw);
        Random rand = new Random();
        int wall = rand.nextInt(4);

        switch (wall) {
            case 0: //Top wall
                dungeonMatrix[sh][sw+2] = " ";
                gridBounds[sh][sw+2] = 0;
                break;
            case 1: //Bottom Wall
                dungeonMatrix[sh+4][sw+2] = " ";
                gridBounds[sh+4][sw+2] = 0;
                break;
            case 2: //Right Wall
                dungeonMatrix[sh+2][sw+4] = " ";
                gridBounds[sh+2][sw+4] = 0;
                break;
            case 3: //Left Wall
                dungeonMatrix[sh+2][sw] = " ";
                gridBounds[sh+2][sw] = 0;
                break;
        }
    }

    public void addScore() {
        if (gridBounds[player.getPlayerY()][player.getPlayerX()] == 2) {
            gridBounds[player.getPlayerY()][player.getPlayerX()] = 0;
            player.setGold(player.getGold() + golds[0].getGoldQuantity());
            System.out.println(player.getGold());
        }
    }

    public void checkExit() {
        if (gridBounds[player.getPlayerY()][player.getPlayerX()] == 5) {
            if (player.getGold() >= Dungeon.GAME_WIN_AMOUNT) {
                System.out.println("Completed");
            }
            else {
                System.out.println("More gold needed");
            }
        }
    }
}
