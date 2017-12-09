package uk.ac.bath.se;

import uk.ac.bath.se.Database.DatabaseHelper;

import java.util.Random;


class DungeonController implements DungeonGamePlayInterface {

    Player player;
    public int[][] gridBounds;
    public int gameWinAmount;
    public boolean nearChest;
    BotPlayer botPlayer;
    private Dungeon dungeon;
    private Gold[] golds;
    private Speed speed;
    private boolean hasMoved;
    private int moves;
    private Chest[] chests;


    public void initialiseDungeonGame() {
        player = new Player();
        botPlayer = new BotPlayer();
        dungeon = new Dungeon();
        speed = new Speed();
        hasMoved = false;
        nearChest = false;
        moves = 0;
        gameWinAmount = 0;
        this.dungeon = Dungeon.getInstance();

        if (Dungeon.difficulty.equals("Hard")) {
            dungeon.setRoomNumber(12);
            dungeon.setHeight(50);
            dungeon.setWidth(50);
            Chest.setChestQuantity(3);
        } else if (Dungeon.difficulty.equals("Easy")) {
            Chest.setChestQuantity(1);
            dungeon.setRoomNumber(4);
            dungeon.setHeight(40);
            dungeon.setWidth(40);
        }

        dungeon.initialiseDungeon();
        chests = new Chest[Chest.getChestQuantity()];
        golds = new Gold[dungeon.getRoomNumber()];
        gridBounds = new int[dungeon.getHeight()][dungeon.getWidth()];

        //Set dungeon bounds
        addDungeonBounds(dungeon);

        //Set up rooms
        addRoomBounds(dungeon, dungeon.getRoomNumber());

        //Set up player, bot and chest location randomly
        addGameItems();

        //Create winAmount
        for (Gold g : golds) {
            gameWinAmount += g.getQuantity();
        }
    }

    private void addGameItems() {
        int[] location = randomSpace();
        player.setxCoord(location[0]);
        player.setyCoord(location[1]);
        gridBounds[player.getyCoord()][player.getxCoord()] = Dungeon.BOUNDARY;
        location = randomSpace();
        botPlayer.setxCoord(location[0]);
        botPlayer.setyCoord(location[1]);
        gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord()] = Dungeon.BOUNDARY;
        for (int i = 0; i < chests.length; i++) {
            location = randomSpace();
            chests[i] = new Chest();
            chests[i].setxCoord(location[0]);
            chests[i].setyCoord(location[1]);
            gridBounds[chests[i].getyCoord()][chests[i].getxCoord()] = Dungeon.CHEST;
            dungeon.dungeonMatrix[chests[i].getyCoord()][chests[i].getxCoord()] = Chest.CHEST_SYMBOL;
        }
        gridBounds[player.getyCoord()][player.getxCoord()] = Dungeon.SPACE;
        gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord()] = Dungeon.SPACE;

        //Print player and bot into dungeon
        dungeon.dungeonMatrix[player.getyCoord()][player.getxCoord()] = player.getPlayerSymbol();
        dungeon.dungeonMatrix[botPlayer.getyCoord()][botPlayer.getxCoord()] = botPlayer.getPLAYER_SYMBOL();
    }

    private int[] randomSpace() {
        Random rand = new Random();
        int[] location = new int[2];
        int x = rand.nextInt((dungeon.getWidth() - 1) - 1) + 1;
        int y = rand.nextInt((dungeon.getHeight() - 1) - 1) + 1;
        location[0] = x;
        location[1] = y;
        while (gridBounds[location[1]][location[0]] == Dungeon.BOUNDARY || gridBounds[location[1]][location[0]] == Gold.LOCATION) {
            x = rand.nextInt((dungeon.getWidth() - 1) - 1) + 1;
            y = rand.nextInt((dungeon.getHeight() - 1) - 1) + 1;
            location[0] = x;
            location[1] = y;
        }
        return location;
    }

    private void printDungeonToConsole() {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                if (j == dungeon.getWidth() - 1) {
                    System.out.println(getDungeonMatrix()[i][j]);
                } else {
                    System.out.print(getDungeonMatrix()[i][j]);
                }
            }
        }
    }

    private void addDungeonBounds(Dungeon dungeon) {
        for (int i = 0; i < dungeon.getWidth(); i++) {
            dungeon.dungeonMatrix[0][i] = "-"; //draw top
            gridBounds[0][i] = Dungeon.BOUNDARY; //store top

            dungeon.dungeonMatrix[dungeon.getHeight() - 1][i] = "-"; //draw bottom
            gridBounds[dungeon.getHeight() - 1][i] = Dungeon.BOUNDARY; //store bottom

            dungeon.dungeonMatrix[dungeon.getHeight() / 2][i] = "-"; //draw middle horizontal
            gridBounds[dungeon.getHeight() / 2][i] = Dungeon.BOUNDARY; //store middle horizontal

            dungeon.dungeonMatrix[i][dungeon.getWidth() / 2] = "|"; //draw middle vertical
            gridBounds[i][dungeon.getWidth() / 2] = Dungeon.BOUNDARY; //store middle vertical
        }

        for (int j = 0; j < dungeon.getHeight(); j++) {
            dungeon.dungeonMatrix[j][0] = "|"; //draw left
            gridBounds[j][0] = Dungeon.BOUNDARY; //store left

            dungeon.dungeonMatrix[j][dungeon.getWidth() - 1] = "|";//draw right
            gridBounds[j][dungeon.getWidth() - 1] = Dungeon.BOUNDARY;//store right
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

        for (int room = 1; room <= roomQuantity; room++) {

            switch (room) {
                case 9:
                case 5:
                case 1:
                    do {
                        startHeight = rand.nextInt((dungeon.getHeight() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                        startWidth = rand.nextInt((dungeon.getWidth() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                    } while (roomCollision(startHeight, startWidth));
                    addGold(room, startHeight, startWidth);
                    break;
                // intentional fall through - up to three rooms in top left

                case 10:
                case 6:
                case 2:
                    do {
                        startHeight = rand.nextInt((dungeon.getHeight() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                        startWidth = rand.nextInt((dungeon.getWidth() - UPPER_DISTANCE) - (dungeon.getWidth() / 2 + LOWER_DISTANCE)) + (dungeon.getWidth() / 2 + LOWER_DISTANCE);
                    } while (roomCollision(startHeight, startWidth));
                    addGold(room, startHeight, startWidth);
                    break;
                // intentional fall through - up to three rooms in top right

                case 11:
                case 7:
                case 3:
                    do {
                        startHeight = rand.nextInt((dungeon.getHeight() - UPPER_DISTANCE) - (dungeon.getHeight() / 2 + LOWER_DISTANCE)) + (dungeon.getHeight() / 2 + LOWER_DISTANCE);
                        startWidth = rand.nextInt((dungeon.getWidth() / 2 - UPPER_DISTANCE) - LOWER_DISTANCE) + LOWER_DISTANCE;
                    } while (roomCollision(startHeight, startWidth));
                    addGold(room, startHeight, startWidth);
                    break;
                // intentional fall through - up to three rooms in bottom left

                case 12:
                case 8:
                case 4:
                    do {
                        startHeight = rand.nextInt((dungeon.getHeight() - UPPER_DISTANCE) - (dungeon.getHeight() / 2 + LOWER_DISTANCE)) + (dungeon.getHeight() / 2 + LOWER_DISTANCE);
                        startWidth = rand.nextInt((dungeon.getWidth() - UPPER_DISTANCE) - (dungeon.getWidth() / 2 + LOWER_DISTANCE)) + (dungeon.getWidth() / 2 + LOWER_DISTANCE);
                    } while (roomCollision(startHeight, startWidth));
                    addGold(room, startHeight, startWidth);
                    break;
                // intentional fall through - up to three rooms in bottom right

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

    private boolean roomCollision(int startHeight, int startWidth) {
        int roomSize = 5;
        for (int i = startHeight - 1; i <= startHeight + roomSize; i++) {
            for (int j = startWidth - 1; j <= startWidth + roomSize; j++) {
                if (gridBounds[i][j] == Dungeon.BOUNDARY) {
                    return true;
                }
            }
        }
        return false;
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
        speed.setSpeedBoostRemaining(30);
    }

    private void addGold(int room, int sh, int sw) {
        golds[room - 1] = new Gold();
        golds[room - 1].setxCoord(sw + 2);
        golds[room - 1].setyCoord(sh + 2);

        dungeon.dungeonMatrix[sh + 2][sw + 2] = golds[room - 1].getGoldSymbol();
        gridBounds[sh + 2][sw + 2] = Gold.LOCATION;
    }

    public String assignGold() {
        String goldMessage = "";
        for (Gold gold : golds) {
            if (gold.getxCoord() == player.getxCoord() && gold.getyCoord() == player.getyCoord()) {
                player.setGold(player.getGold() + gold.getQuantity());
                gridBounds[gold.getyCoord()][gold.getxCoord()] = Dungeon.SPACE;
                gold.setxCoord(-1);
                gold.setyCoord(-1);
                goldMessage = "You found " + gold.getQuantity() + " gold!";
                if (gameWinAmount-player.getGold() == 0) {
                    goldMessage += " Find the key to exit.";
                } else {
                    goldMessage += " You have " + Integer.toString(gameWinAmount-player.getGold()) + " left to collect.";
                }
            }
        }
        return goldMessage;
    }

    public boolean checkExit(int y, int x) {
        if (gridBounds[y][x] == Dungeon.EXIT && player.hasKey == false) {
            return false;
        }
        return true;
    }

    public void saveGoldAmount(String playerName, int goldAmount, int score) {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.insertValues(playerName, goldAmount, score);
    }

    public String movePlayer(PlayerMovement move) {
        String message = "";
        if (player.isPlayerTurn()) {
            switch (move) {
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
            message = assignGold();
            assignChest();

            if (!message.equals("")) {
                message += "\n";
            }

            if (speed.getSpeedBoostRemaining() > 0) {
                hasMoved = true;
                speed.setSpeedBoostRemaining(speed.getSpeedBoostRemaining() - 1);
                if(speed.getSpeedBoostRemaining() == 0) {
                    message += "You are out of speed boosts.\n";
                }
            }
            moves++;

            if (moves >= 2 || !hasMoved) {
                player.setPlayerTurn(false);
                player.setScore(player.getScore() + 1);
                moveBot(getBotDirection());
                moves = 0;
            }
            hasMoved = false;
            if (speed.getSpeedBoostRemaining() % 5 == 0 && speed.getSpeedBoostRemaining() > 0) {
                message += "You have " + speed.getSpeedBoostRemaining() + " speed boosts remaining.\n";
            }
        }
        return message;
    }

    @Override
    public String[][] getDungeonMatrix() {
        return dungeon.dungeonMatrix;
    }

    void moveBot(PlayerMovement direction) {
        switch (direction) {
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

    private PlayerMovement getBotDirection() {
        PlayerMovement botMove;
        if (Math.abs(botPlayer.getxCoord() - player.getxCoord()) >= Math.abs(botPlayer.getyCoord() - player.getyCoord())) {
            if (botPlayer.getxCoord() - player.getxCoord() < 0) {
                botMove = PlayerMovement.RIGHT;
            } else if (botPlayer.getxCoord() - player.getxCoord() > 0) {
                botMove = PlayerMovement.LEFT;
            } else {
                botMove = PlayerMovement.NONE;
            }
        } else {
            if (botPlayer.getyCoord() - player.getyCoord() < 0) {
                botMove = PlayerMovement.DOWN;
            } else if (botPlayer.getyCoord() - player.getyCoord() > 0) {
                botMove = PlayerMovement.UP;
            } else {
                botMove = PlayerMovement.NONE;
            }
        }
        botMove = botCollision(botMove);
        return botMove;
    }

    public boolean checkLoss() {
        if (botPlayer.getxCoord() == player.getxCoord() && botPlayer.getyCoord() == player.getyCoord()) {
            return true;
        }
        return false;
    }

    PlayerMovement botCollision(PlayerMovement direction) {
        if (direction == PlayerMovement.UP) {
            if (!checkCollision(botPlayer.getyCoord() - 1, botPlayer.getxCoord())
                    && gridBounds[botPlayer.getyCoord() - 1][botPlayer.getxCoord()] != Gold.LOCATION) {
                return direction;
            } else {
                return findSpace(-1, 0);
            }
        } else if (direction == PlayerMovement.DOWN) {
            if (!checkCollision(botPlayer.getyCoord() + 1, botPlayer.getxCoord())
                    && gridBounds[botPlayer.getyCoord() + 1][botPlayer.getxCoord()] != Gold.LOCATION) {
                return direction;
            } else {
                return findSpace(1,0);
            }
        } else if (direction == PlayerMovement.LEFT) {
            if (!checkCollision(botPlayer.getyCoord(), botPlayer.getxCoord()-1)
                    && gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord()-1] != Gold.LOCATION) {
                return direction;
            } else {
                return findSpace(0, -1);
            }
        } else if (direction == PlayerMovement.RIGHT) {
            if (!checkCollision(botPlayer.getyCoord(), botPlayer.getxCoord()+1)
                    && gridBounds[botPlayer.getyCoord()][botPlayer.getxCoord()+1] != Gold.LOCATION) {
                return direction;
            } else {
                return findSpace(0, 1);
            }
        } else {
            return direction;
        }
    }

    public double distance(int x, int x2, int y, int y2) {
        return Math.hypot(x - x2, y - y2);
    }

    public void assignChest() {
        for (Chest c : chests) {
            if (distance(player.getxCoord(), c.getxCoord(), player.getyCoord(), c.getyCoord())
                    <= 2) {
                nearChest = true;
                break;
            } else {
                nearChest = false;
            }
        }
    }

    public String giveRandomItem() {
        Random rand = new Random();
        String message = "";
        int chance = rand.nextInt(2);
        message += "You open the chest to find ";
        if (player.getGold() >= gameWinAmount && !player.hasKey) {
            giveKey();
            message += "a key! You may now exit.";
        } else if (chance == 0) {
            message += "Nothing.";
        } else if (chance == 0) {
            message += "nothing. Your search continues...";
        } else {
            message += "a ton of speed!";
            assignSpeed();
        }
        chance = rand.nextInt(2);
        if (chance == 0) {
            for (Chest c : chests) {
                if (distance(player.getxCoord(), c.getxCoord(), player.getyCoord(), c.getyCoord())
                        <= 2) {
                    message += "\nThe chest mysteriously vanishes...";
                    int[] newLocation = randomSpace();
                    gridBounds[c.getyCoord()][c.getxCoord()] = Dungeon.SPACE;
                    dungeon.dungeonMatrix[c.getyCoord()][c.getxCoord()] = " ";
                    c.setxCoord(newLocation[0]);
                    c.setyCoord(newLocation[1]);
                    gridBounds[c.getyCoord()][c.getxCoord()] = Dungeon.CHEST;
                    dungeon.dungeonMatrix[c.getyCoord()][c.getxCoord()] = Chest.CHEST_SYMBOL;
                    assignChest();
                }
            }
        }
        return message;
    }

    private void giveKey() {
        player.hasKey = true;
    }

    public boolean checkWin() {
        if (checkExit(player.getyCoord(), player.getxCoord()) &&
                gridBounds[player.getyCoord()][player.getxCoord()] == Dungeon.EXIT) {
            return true;
        }
        return false;
    }

    public boolean checkCollision(int y, int x) {
        if (gridBounds[y][x] == Dungeon.BOUNDARY || !checkExit(y, x) || gridBounds[y][x] == Dungeon.CHEST) {
            return true;
        }
        return false;
    }

    private PlayerMovement findSpace(int yDifference, int xDifference) {
        int leftSpace = 1000;
        int rightSpace = 1000;

        if (Math.abs(yDifference) > 0) {
            for (int i = botPlayer.getxCoord(); i >= 0; i--) {
                if (checkCollision(botPlayer.getyCoord(),i)) {
                    leftSpace = 1000;
                    break;
                }
                else if (!checkCollision(botPlayer.getyCoord() + yDifference,i)) {
                    leftSpace = botPlayer.getxCoord() - i;
                    break;
                }
            }
            for (int i = botPlayer.getxCoord(); i < dungeon.getWidth(); i++) {
                if (checkCollision(botPlayer.getyCoord(),i)) {
                    rightSpace = 1000;
                    break;
                }
                else if (!checkCollision(botPlayer.getyCoord() + yDifference,i)) {
                    rightSpace = i - botPlayer.getxCoord();
                    break;
                }
            }
            if (leftSpace < rightSpace) {
                return PlayerMovement.LEFT;
            } else if (leftSpace >= rightSpace && rightSpace != 1000) {
                return PlayerMovement.RIGHT;
            } else {
                return PlayerMovement.NONE;
            }
        } else {
            for (int i = botPlayer.getyCoord(); i >= 0; i--) {
                if (checkCollision(i,botPlayer.getxCoord())) {
                    leftSpace = 1000;
                    break;
                }
                else if (!checkCollision(i, botPlayer.getxCoord() + xDifference)) {
                    leftSpace = botPlayer.getyCoord() - i;
                    break;
                }
            }
            for (int i = botPlayer.getyCoord(); i < dungeon.getHeight(); i++) {
                if (checkCollision(i,botPlayer.getxCoord())) {
                    rightSpace = 1000;
                    break;
                }
                else if (!checkCollision(i, botPlayer.getxCoord() + xDifference)) {
                    rightSpace = i - botPlayer.getyCoord();
                    break;
                }
            }
            if (leftSpace < rightSpace) {
                return PlayerMovement.UP;
            } else if (leftSpace >= rightSpace && rightSpace != 1000){
                return PlayerMovement.DOWN;
            } else {
                return PlayerMovement.NONE;
            }
        }
    }
}



