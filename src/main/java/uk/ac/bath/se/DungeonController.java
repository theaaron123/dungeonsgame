package uk.ac.bath.se;

import uk.ac.bath.se.Database.DatabaseHelper;

import java.util.Random;


class DungeonController implements DungeonGamePlayInterface {

    public Player player;
    private BotPlayer botPlayer;
    public int[][] gridBounds;
    private Dungeon dungeon;
    private Gold[] golds;
    private Speed speed;
    private boolean hasMoved;
    private int moves;
    public int gameWinAmount;
    private Chest chest;
    public boolean nearChest;

    public void initialiseDungeonGame() {
        player = new Player();
        botPlayer = new BotPlayer();
        dungeon = new Dungeon();
        speed = new Speed();
        chest = new Chest();
        golds = new Gold[Dungeon.roomNumber];
        gridBounds = new int[dungeon.getHeight()][dungeon.getWidth()];
        hasMoved = false;
        nearChest = false;
        moves = 0;
        gameWinAmount = 0;

        this.dungeon = Dungeon.getInstance();
        dungeon.initialiseDungeon();

        //Set dungeon bounds
        addDungeonBounds(dungeon);

        //Set up rooms
        addRoomBounds(dungeon, Dungeon.roomNumber);

        //Set up player, bot and chest location randomly
        player.setxCoord(randomSpace()[0]);
        player.setyCoord(randomSpace()[1]);
        gridBounds[player.getyCoord()][player.getxCoord()] = Dungeon.BOUNDARY;
        botPlayer.setxCoord(randomSpace()[0]);
        botPlayer.setyCoord(randomSpace()[1]);
        gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord()] = Dungeon.BOUNDARY;
        chest.setxCoord(randomSpace()[0]);
        chest.setyCoord(randomSpace()[1]);
        gridBounds[chest.getyCoord()][chest.getxCoord()] = Dungeon.CHEST;
        gridBounds[player.getyCoord()][player.getxCoord()] = 0;
        gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord()] = 0;

        //Print player, bot and chest into dungeon
        dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = player.getPlayerSymbol();
        dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = botPlayer.getPLAYER_SYMBOL();
        dungeon.dungeonMatrix[chest.getyCoord()][chest.getxCoord()] = chest.getCHEST_SYMBOL();

        //Create winAmount
        for (Gold g : golds) {
            gameWinAmount += g.getQuantity();
        }
    }

    private int[] randomSpace() {
        Random rand = new Random();
        int[] location = new int [2];
        int x = rand.nextInt((dungeon.getWidth()-1)-1) + 1;
        int y = rand.nextInt((dungeon.getHeight()-1)-1) + 1;
        location[0] = x;
        location[1] = y;
        while (gridBounds[location[1]][location[0]] == Dungeon.BOUNDARY || gridBounds[location[1]][location[0]] == Gold.LOCATION) {
            x = rand.nextInt(dungeon.getWidth()-1) + 1;
            y = rand.nextInt(dungeon.getHeight()-1) + 1;
            location[0] = x;
            location[1] = y;
        }
        return location;
    }

    private void printDungeonToConsole() {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                if (j == dungeon.getWidth() - 1) {
                    System.out.println(gridBounds[i][j]);
                } else {
                    System.out.print(+gridBounds[i][j]);
                }
            }
        }
    }

    private void addDungeonBounds(Dungeon dungeon) {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                dungeon.dungeonMatrix[0][i] = "-"; //draw top
                gridBounds[0][i] = Dungeon.BOUNDARY; //store top

                dungeon.dungeonMatrix[dungeon.getHeight() - 1][i] = "-"; //draw bottom
                gridBounds[dungeon.getHeight() - 1][i] = Dungeon.BOUNDARY; //store bottom

                dungeon.dungeonMatrix[j][0] = "|"; //draw left
                gridBounds[j][0] = Dungeon.BOUNDARY; //store left

                dungeon.dungeonMatrix[j][dungeon.getWidth() - 1] = "|";//draw right
                gridBounds[j][dungeon.getWidth() - 1] = Dungeon.BOUNDARY;//store right

            }
            dungeon.dungeonMatrix[dungeon.getHeight() / 2][i] = "-"; //draw middle horizontal
            gridBounds[dungeon.getHeight() / 2][i] = Dungeon.BOUNDARY; //store middle horizontal

            dungeon.dungeonMatrix[i][dungeon.getWidth() / 2] = "|"; //draw middle vertical
            gridBounds[i][dungeon.getWidth() / 2] = Dungeon.BOUNDARY; //store middle vertical
        }
        addPassage(dungeon);
        addExit(dungeon);
    }

    private void addPassage(Dungeon dungeon) {
        Random rand = new Random();
        int doorLocation;

        //Top vertical door
        doorLocation = rand.nextInt((dungeon.getHeight() / 2 - 1) - 1) + 1;
        gridBounds[doorLocation][dungeon.getWidth() / 2] = Dungeon.SPACE;
        dungeon.dungeonMatrix[doorLocation][dungeon.getWidth() / 2] = " ";

        //Bottom vertical door
        doorLocation = rand.nextInt((dungeon.getHeight() - 1) - (dungeon.getHeight() / 2 + 1)) + (dungeon.getHeight() / 2 + 1);
        gridBounds[doorLocation][dungeon.getWidth() / 2] = Dungeon.SPACE;
        dungeon.dungeonMatrix[doorLocation][dungeon.getWidth() / 2] = " ";

        //Left horizontal door
        doorLocation = rand.nextInt((dungeon.getWidth() / 2 - 1) - 1) + 1;
        gridBounds[dungeon.getHeight() / 2][doorLocation] = Dungeon.SPACE;
        dungeon.dungeonMatrix[dungeon.getHeight() / 2][doorLocation] = " ";

        //Right horizontal door
        doorLocation = rand.nextInt((dungeon.getWidth() - 1) - (dungeon.getWidth() / 2 + 1)) + (dungeon.getWidth() / 2 + 1);
        gridBounds[dungeon.getHeight() / 2][doorLocation] = Dungeon.SPACE;
        dungeon.dungeonMatrix[dungeon.getHeight() / 2][doorLocation] = " ";
    }

    private void addExit(Dungeon dungeon) {
        int exitLocation;
        int wall;
        Random rand = new Random();
        wall = rand.nextInt(4); //Random integer between 0 and 3 inclusive
        //Uses height only to find middle dungeon values, will not work if not a square
        exitLocation = rand.nextInt((dungeon.getHeight() - 1) - 1) + 1;
        while (exitLocation == dungeon.getHeight() / 2) {
            exitLocation = rand.nextInt((dungeon.getHeight() - 1) - 1) + 1;
        }

        switch (wall) {
            case 0: //Top wall
                dungeon.dungeonMatrix[0][exitLocation] = "E";
                gridBounds[0][exitLocation] = Dungeon.EXIT;
                break;
            case 1: //Bottom wall
                dungeon.dungeonMatrix[dungeon.getHeight() - 1][exitLocation] = "E";
                gridBounds[dungeon.getHeight() - 1][exitLocation] = Dungeon.EXIT;
                break;
            case 2: // Left wall
                dungeon.dungeonMatrix[exitLocation][0] = "E";
                gridBounds[exitLocation][0] = Dungeon.EXIT;
                break;
            case 3: //Right wall
                dungeon.dungeonMatrix[exitLocation][dungeon.getWidth() - 1] = "E";
                gridBounds[exitLocation][dungeon.getWidth() - 1] = Dungeon.EXIT;
                break;
        }
    }

    private void addRoomBounds(Dungeon dungeon, int roomQuantity) {
        Random rand = new Random();
        int startHeight;
        int startWidth;
        final int LOWER_DISTANCE = 2;
        final int UPPER_DISTANCE = 6;

        for (int room = 1; room <= roomQuantity; room++) { //1 - top left, 2 - top right, 3 - bottom left, 4 - bottom right

            switch (room) {
                case 1:
                    startHeight = rand.nextInt((dungeon.getHeight() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                    startWidth = rand.nextInt((dungeon.getWidth() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                    addGold(room, startHeight, startWidth);
                    break;

                case 2:
                    startHeight = rand.nextInt((dungeon.getHeight() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                    startWidth = rand.nextInt((dungeon.getWidth() - UPPER_DISTANCE) - (dungeon.getWidth() / 2 + LOWER_DISTANCE)) + (dungeon.getWidth() / 2 + LOWER_DISTANCE);
                    addGold(room, startHeight, startWidth);
                    break;

                case 3:
                    startHeight = rand.nextInt((dungeon.getHeight() - UPPER_DISTANCE) - (dungeon.getHeight() / 2 + LOWER_DISTANCE)) + (dungeon.getHeight() / 2 + LOWER_DISTANCE);
                    startWidth = rand.nextInt((dungeon.getWidth() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                    addGold(room, startHeight, startWidth);
                    break;

                case 4:
                    startHeight = rand.nextInt((dungeon.getHeight() - UPPER_DISTANCE) - (dungeon.getHeight() / 2 + LOWER_DISTANCE)) + (dungeon.getHeight() / 2 + LOWER_DISTANCE);
                    startWidth = rand.nextInt((dungeon.getWidth() - UPPER_DISTANCE) - (dungeon.getWidth() / 2 + LOWER_DISTANCE)) + (dungeon.getWidth() / 2 + LOWER_DISTANCE);
                    addGold(room, startHeight, startWidth);
                    break;

                default:
                    startHeight = rand.nextInt(1);
                    startWidth = rand.nextInt(1);
            }


            for (int i = 0; i < 5; i++) {
                //draw room (y, x)
                dungeon.dungeonMatrix[i + startHeight][startWidth] = "*"; //left
                dungeon.dungeonMatrix[startHeight][i + startWidth] = "*"; //top
                dungeon.dungeonMatrix[i + startHeight][startWidth + 4] = "*"; //right
                dungeon.dungeonMatrix[startHeight + 4][i + startWidth] = "*"; //bottom

                gridBounds[i + startHeight][startWidth] = Dungeon.BOUNDARY; //left
                gridBounds[startHeight][i + startWidth] = Dungeon.BOUNDARY; //top
                gridBounds[i + startHeight][startWidth + 4] = Dungeon.BOUNDARY; //right
                gridBounds[startHeight + 4][i + startWidth] = Dungeon.BOUNDARY; //bottom

            }
            removeDoor(startHeight, startWidth);
            removeDoor(startHeight, startWidth);
        }
    }

    private void removeDoor(int roomStartY, int roomStartX) {
        Random rand = new Random();
        int wall = rand.nextInt(4);

        switch (wall) {
            case 0: //Top wall
                dungeon.dungeonMatrix[roomStartY][roomStartX + 2] = " ";
                gridBounds[roomStartY][roomStartX + 2] = Dungeon.SPACE;
                break;
            case 1: //Bottom Wall
                dungeon.dungeonMatrix[roomStartY + 4][roomStartX + 2] = " ";
                gridBounds[roomStartY + 4][roomStartX + 2] = Dungeon.SPACE;
                break;
            case 2: //Right Wall
                dungeon.dungeonMatrix[roomStartY + 2][roomStartX + 4] = " ";
                gridBounds[roomStartY + 2][roomStartX + 4] = Dungeon.SPACE;
                break;
            case 3: //Left Wall
                dungeon.dungeonMatrix[roomStartY + 2][roomStartX] = " ";
                gridBounds[roomStartY + 2][roomStartX] = Dungeon.SPACE;
                break;
        }
    }

    private void assignSpeed() {
            speed.setSpeedX(-1);
            speed.setSpeedY(-1);
            speed.setSpeedBoostRemaining(20);
    }

    private void addGold(int room, int sh, int sw) {
        golds[room - 1] = new Gold();
        golds[room - 1].setxCoord(sw + 2);
        golds[room - 1].setyCoord(sh + 2);

        dungeon.dungeonMatrix[sh + 2][sw + 2] = golds[room - 1].getGoldSymbol();
        gridBounds[sh + 2][sw + 2] = Gold.LOCATION;
    }

    public void assignGold() {
        for (Gold gold : golds) {
            if (gold.getxCoord() == player.getxCoord() && gold.getyCoord() == player.getyCoord()) {
                player.setGold(player.getGold() + gold.getQuantity());
                gridBounds[gold.getyCoord()][gold.getxCoord()] = Dungeon.SPACE;
                gold.setxCoord(-1);
                gold.setyCoord(-1);
            }
        }
    }

    public boolean checkExit(int y, int x) {
        if (gridBounds[y][x] == Dungeon.EXIT  && player.hasKey == false) {
            return false;
        }
        return true;
    }

    public void saveGoldAmount(String playerName, int goldAmount, int score) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.insertValues(playerName, goldAmount, score);
    }

    public void movePlayer(PlayerMovement move) {
        if (player.isPlayerTurn()) {
            switch(move) {
                case UP:
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = " ";
                    player.setyCoord(player.getyCoord() - 1);
                    player.setSymbol("^");
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = player.getPlayerSymbol();
                    break;

                case DOWN:
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = " ";
                    player.setyCoord(player.getyCoord() + 1);
                    player.setSymbol("v");
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = player.getPlayerSymbol();
                    break;

                case LEFT:
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = " ";
                    player.setxCoord(player.getxCoord() - 1);
                    player.setSymbol("<");
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = player.getPlayerSymbol();
                    break;

                case RIGHT:
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = " ";
                    player.setxCoord(player.getxCoord() + 1);
                    player.setSymbol(">");
                    dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = player.getPlayerSymbol();
                    break;
            }

            if (speed.getSpeedBoostRemaining() > 0) {
                hasMoved = true;
                speed.setSpeedBoostRemaining(speed.getSpeedBoostRemaining() - 1);
            }
            moves ++;

            if (moves >= 2 || !hasMoved) {
                player.setPlayerTurn(false);
                player.setScore(player.getScore()+1);
                moveBot();
                moves = 0;
            }
            hasMoved = false;
        }
    }

    @Override
    public String[][] getDungeonMatrix() {
        return dungeon.dungeonMatrix;
    }

    private void moveBot() {
        PlayerMovement botMove;
        if (Math.abs(botPlayer.getxCoord()-player.getxCoord()) >= Math.abs(botPlayer.getyCoord()-player.getyCoord())) {
            if(botPlayer.getxCoord() - player.getxCoord() < 0) {
                botMove = PlayerMovement.RIGHT;
            } else if (botPlayer.getxCoord() - player.getxCoord() > 0) {
                botMove = PlayerMovement.LEFT;
            } else {
                botMove = PlayerMovement.NONE;
            }
        } else {
            if(botPlayer.getyCoord() - player.getyCoord() < 0) {
                botMove = PlayerMovement.DOWN;
            } else if(botPlayer.getyCoord() - player.getyCoord() > 0){
                botMove = PlayerMovement.UP;
            } else {
                botMove = PlayerMovement.NONE;
            }
        }
        botMove = botCollision(botMove);
        //TODO add method here to prevent bot eating a corner

        switch(botMove) {
            case UP:
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = " ";
                botPlayer.setyCoord(botPlayer.getyCoord() - 1);
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;

            case DOWN:
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = " ";
                botPlayer.setyCoord(botPlayer.getyCoord() + 1);
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;

            case LEFT:
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = " ";
                botPlayer.setxCoord(botPlayer.getxCoord() - 1);
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;

            case RIGHT:
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = " ";
                botPlayer.setxCoord(botPlayer.getxCoord() + 1);
                dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;
        }
        player.setPlayerTurn(true);
    }

    public boolean checkLoss() {
        if (botPlayer.getxCoord() == player.getxCoord() && botPlayer.getyCoord() == player.getyCoord()) {
            return true;
        }
        return false;
    }

    private PlayerMovement botCollision(PlayerMovement direction) {
        //TODO Refactor to minimise code reuse
        if (direction == PlayerMovement.UP) {
            if (gridBounds[botPlayer.getyCoord() - 1][botPlayer.getxCoord()] == 0) {
                return direction;
            } else {
                int leftSpace = 100;
                int rightSpace = 100;
                for (int i = botPlayer.getxCoord(); i >= 0; i--) {
                    if (gridBounds[botPlayer.getyCoord() - 1][i] == 0) {
                        leftSpace = botPlayer.getxCoord() - i;
                        break;
                    }
                }
                for (int i = botPlayer.getxCoord(); i < dungeon.getWidth(); i++) {
                    if (gridBounds[botPlayer.getyCoord() - 1][i] == 0) {
                        rightSpace = i - botPlayer.getxCoord();
                        break;
                    }
                }
                if (leftSpace < rightSpace) {
                    return PlayerMovement.LEFT;
                } else {
                    return PlayerMovement.RIGHT;
                }
            }
        } else if (direction == PlayerMovement.DOWN) {
            if (gridBounds[botPlayer.getyCoord() + 1][botPlayer.getxCoord()] == 0) {
                return direction;
            } else {
                int leftSpace = 100;
                int rightSpace = 100;
                for (int i = botPlayer.getxCoord(); i >= 0; i--) {
                    if (gridBounds[botPlayer.getyCoord() + 1][i] == 0) {
                        leftSpace = botPlayer.getxCoord() - i;
                        break;
                    }
                }
                for (int i = botPlayer.getxCoord(); i < dungeon.getWidth(); i++) {
                    if (gridBounds[botPlayer.getyCoord() + 1][i] == 0) {
                        rightSpace = i - botPlayer.getxCoord();
                        break;
                    }
                }
                if (leftSpace < rightSpace) {
                    return PlayerMovement.LEFT;
                } else {
                    return PlayerMovement.RIGHT;
                }
            }
        } else if (direction == PlayerMovement.LEFT) {
            if (gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord() - 1] == 0) {
                return direction;
            } else {
                int topSpace = 100;
                int bottomSpace = 100;
                for (int i = botPlayer.getyCoord(); i >= 0; i--) {
                    if (gridBounds[i][botPlayer.getxCoord() - 1] == 0) {
                        topSpace = botPlayer.getyCoord() - i;
                        break;
                    }
                }
                for (int i = botPlayer.getyCoord(); i < dungeon.getHeight(); i++) {
                    if (gridBounds[i][botPlayer.getxCoord() - 1] == 0) {
                        bottomSpace = i - botPlayer.getyCoord();
                        break;
                    }
                }
                if (topSpace < bottomSpace) {
                    return PlayerMovement.UP;
                } else {
                    return PlayerMovement.DOWN;
                }
            }
        } else if (direction == PlayerMovement.RIGHT) {
            if (gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord() + 1] == 0) {
                return direction;
            } else {
                int topSpace = 100;
                int bottomSpace = 100;
                for (int i = botPlayer.getyCoord(); i >= 0; i--) {
                    if (gridBounds[i][botPlayer.getxCoord() + 1] == 0) {
                        topSpace = botPlayer.getyCoord() - i;
                        break;
                    }
                }
                for (int i = botPlayer.getyCoord(); i < dungeon.getHeight(); i++) {
                    if (gridBounds[i][botPlayer.getxCoord() + 1] == 0) {
                        bottomSpace = i - botPlayer.getyCoord();
                        break;
                    }
                }
                if (topSpace < bottomSpace) {
                    return PlayerMovement.UP;
                } else {
                    return PlayerMovement.DOWN;
                }
            }
        } else {
            return direction;
        }
    }

    public double distance(int x, int x2, int y, int y2) {
        return Math.hypot(x-x2, y-y2);
    }

    public void assignChest() {
        if (distance(player.getxCoord(),chest.getxCoord(), player.getyCoord(), chest.getyCoord())
                <= 2) {
            nearChest = true;
        } else {
            nearChest = false;
        }
    }

    public void giveRandomItem() {
        Random rand = new Random();
        int a = rand.nextInt(2);
        System.out.println("You open the chest.. to find...");
        if (a == 0) {
            System.out.println("Nothing.");
        } else {
            System.out.println("A ton of speed!");
            assignSpeed();
        }

        if (player.getGold() >= gameWinAmount) {
            giveKey();
        }
    }

    private void giveKey() {
        player.hasKey = true;
        System.out.println("You find the key. \n You may now exit.");
    }
}



