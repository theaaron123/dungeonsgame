package uk.ac.bath.se;

import uk.ac.bath.se.Database.DatabaseHelper;

import java.util.Random;

public class DungeonController {

    public Player player;
    public BotPlayer botPlayer;
    public int[][] gridBounds;
    private Dungeon dungeon;
    private Gold[] golds;
    private int gameWinAmount;

    public void initialiseDungeonGame() {
        player = new Player();
        botPlayer = new BotPlayer();
        dungeon = new Dungeon();
        gameWinAmount = 0;
        golds = new Gold[Dungeon.MAXIMUM_ROOMS];
        gridBounds = new int[dungeon.getHeight()][dungeon.getWidth()];

        this.dungeon = Dungeon.getInstance();
        dungeon.initialiseDungeon();

        //Set dungeon bounds
        addDungeonBounds(dungeon);

        //Set up player location
        player.setPlayerX(2); //set X to left of the map
        player.setPlayerY(dungeon.getHeight() - 3); //set Y to the bottom of the map
        //TODO do not collide during initial spawn
        botPlayer.setXCoord(10);
        botPlayer.setYCoord(10);
        dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
        dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = botPlayer.getPLAYER_SYMBOL();

        //Set up rooms
        addRoomBounds(dungeon, Dungeon.MAXIMUM_ROOMS);

        //Create winAmount
        for (Gold g : golds) {
            gameWinAmount += g.getGoldQuantity();
        }
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
                gridBounds[0][i] = 1; //store top

                dungeon.dungeonMatrix[dungeon.getHeight() - 1][i] = "-"; //draw bottom
                gridBounds[dungeon.getHeight() - 1][i] = 1; //store bottom

                dungeon.dungeonMatrix[j][0] = "|"; //draw left
                gridBounds[j][0] = 1; //store left

                dungeon.dungeonMatrix[j][dungeon.getWidth() - 1] = "|";//draw right
                gridBounds[j][dungeon.getWidth() - 1] = 1;//store right

            }
            dungeon.dungeonMatrix[dungeon.getHeight() / 2][i] = "-"; //draw middle horizontal
            gridBounds[dungeon.getHeight() / 2][i] = 1; //store middle horizontal

            dungeon.dungeonMatrix[i][dungeon.getWidth() / 2] = "|"; //draw middle vertical
            gridBounds[i][dungeon.getWidth() / 2] = 1; //store middle vertical
        }

        addPassage(dungeon);
        addExit(dungeon);
    }

    private void addPassage(Dungeon dungeon) {
        Random rand = new Random();
        int doorLocation;

        //Top vertical door
        doorLocation = rand.nextInt((dungeon.getHeight() / 2 - 1) - 1) + 1;
        gridBounds[doorLocation][dungeon.getWidth() / 2] = 0;
        dungeon.dungeonMatrix[doorLocation][dungeon.getWidth() / 2] = " ";

        //Bottom vertical door
        doorLocation = rand.nextInt((dungeon.getHeight() - 1) - (dungeon.getHeight() / 2 + 1)) + (dungeon.getHeight() / 2 + 1);
        gridBounds[doorLocation][dungeon.getWidth() / 2] = 0;
        dungeon.dungeonMatrix[doorLocation][dungeon.getWidth() / 2] = " ";

        //Left horizontal door
        doorLocation = rand.nextInt((dungeon.getWidth() / 2 - 1) - 1) + 1;
        gridBounds[dungeon.getHeight() / 2][doorLocation] = 0;
        dungeon.dungeonMatrix[dungeon.getHeight() / 2][doorLocation] = " ";

        //Right horizontal door
        doorLocation = rand.nextInt((dungeon.getWidth() - 1) - (dungeon.getWidth() / 2 + 1)) + (dungeon.getWidth() / 2 + 1);
        gridBounds[dungeon.getHeight() / 2][doorLocation] = 0;
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
                gridBounds[0][exitLocation] = 5;
                break;
            case 1: //Bottom wall
                dungeon.dungeonMatrix[dungeon.getHeight() - 1][exitLocation] = "E";
                gridBounds[dungeon.getHeight() - 1][exitLocation] = 5;
                break;
            case 2: // Left wall
                dungeon.dungeonMatrix[exitLocation][0] = "E";
                gridBounds[exitLocation][0] = 5;
                break;
            case 3: //Right wall
                dungeon.dungeonMatrix[exitLocation][dungeon.getWidth() - 1] = "E";
                gridBounds[exitLocation][dungeon.getWidth() - 1] = 5;
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

                gridBounds[i + startHeight][startWidth] = 1; //left
                gridBounds[startHeight][i + startWidth] = 1; //top
                gridBounds[i + startHeight][startWidth + 4] = 1; //right
                gridBounds[startHeight + 4][i + startWidth] = 1; //bottom

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
                gridBounds[roomStartY][roomStartX + 2] = 0;
                break;
            case 1: //Bottom Wall
                dungeon.dungeonMatrix[roomStartY + 4][roomStartX + 2] = " ";
                gridBounds[roomStartY + 4][roomStartX + 2] = 0;
                break;
            case 2: //Right Wall
                dungeon.dungeonMatrix[roomStartY + 2][roomStartX + 4] = " ";
                gridBounds[roomStartY + 2][roomStartX + 4] = 0;
                break;
            case 3: //Left Wall
                dungeon.dungeonMatrix[roomStartY + 2][roomStartX] = " ";
                gridBounds[roomStartY + 2][roomStartX] = 0;
                break;
        }
    }

    private void addGold(int room, int sh, int sw) {
        golds[room - 1] = new Gold();
        golds[room - 1].setGoldX(sw + 2);
        golds[room - 1].setGoldY(sh + 2);

        dungeon.dungeonMatrix[sh + 2][sw + 2] = golds[room - 1].getGoldSymbol();
        gridBounds[sh + 2][sw + 2] = 2;
    }

    public void assignGold() {
        for (Gold gold : golds) {
            if (gold.getGoldX() == player.getPlayerX() && gold.getGoldY() == player.getPlayerY()) {
                player.setGold(player.getGold() + gold.getGoldQuantity());
                gridBounds[gold.getGoldY()][gold.getGoldX()] = 0;
                gold.setGoldX(-1);
                gold.setGoldY(-1);
            }
        }
    }

    public boolean checkExit(int y, int x) {
        if (gridBounds[y][x] == 5 && player.getGold() < gameWinAmount) {
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
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = " ";
                    player.setPlayerY(player.getPlayerY() - 1);
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
                    break;

                case DOWN:
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = " ";
                    player.setPlayerY(player.getPlayerY() + 1);
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
                    break;

                case LEFT:
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = " ";
                    player.setPlayerX(player.getPlayerX() - 1);
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
                    break;

                case RIGHT:
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = " ";
                    player.setPlayerX(player.getPlayerX() + 1);
                    dungeon.dungeonMatrix[player.getPlayerY()][player.getPlayerX()] = player.getPlayerSymbol();
                    break;
            }
            player.setPlayerTurn(false);
            moveBot();
        }
    }

    private void moveBot() {
        PlayerMovement botMove;
        if (Math.abs(botPlayer.getXCoord()-player.getPlayerX()) >= Math.abs(botPlayer.getYCoord()-player.getPlayerY())) {
            if(botPlayer.getXCoord() - player.getPlayerX() < 0) {
                botMove = PlayerMovement.RIGHT;
            } else if (botPlayer.getXCoord() - player.getPlayerX() > 0) {
                botMove = PlayerMovement.LEFT;
            } else {
                botMove = PlayerMovement.NONE;
            }
        } else {
            if(botPlayer.getYCoord() - player.getPlayerY() < 0) {
                botMove = PlayerMovement.DOWN;
            } else if(botPlayer.getYCoord() - player.getPlayerY() > 0){
                botMove = PlayerMovement.UP;
            } else {
                botMove = PlayerMovement.NONE;
            }
        }
        botMove = botCollision(botMove);

        switch(botMove) {
            case UP:
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = " ";
                botPlayer.setYCoord(botPlayer.getYCoord() - 1);
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;

            case DOWN:
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = " ";
                botPlayer.setYCoord(botPlayer.getYCoord() + 1);
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;

            case LEFT:
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = " ";
                botPlayer.setXCoord(botPlayer.getXCoord() - 1);
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;

            case RIGHT:
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = " ";
                botPlayer.setXCoord(botPlayer.getXCoord() + 1);
                dungeon.dungeonMatrix[botPlayer.getYCoord()][botPlayer.getXCoord()] = botPlayer.getPLAYER_SYMBOL();
                break;
        }
        if (checkLoss()) {
            Player.lives -= 1;
            if (Player.lives == 0) {
                //TODO return player to splash screen
                //End game
            } else {
                initialiseDungeonGame();
            }
        }
        player.setPlayerTurn(true);
    }
    private boolean checkLoss() {
        if (botPlayer.getXCoord() == player.getPlayerX() && botPlayer.getYCoord() == player.getPlayerY()) {
            return true;
        }
        return false;
    }

    private PlayerMovement botCollision(PlayerMovement direction) {
        //TODO Refactor to minimise code reuse
        if (direction == PlayerMovement.UP) {
            if (gridBounds[botPlayer.getYCoord()-1][botPlayer.getXCoord()] == 0) {
                return direction;
            } else {
                int leftSpace = 100;
                int rightSpace = 100;
                for (int i = botPlayer.getXCoord(); i >= 0; i--) {
                    if (gridBounds[botPlayer.getYCoord()-1][i] == 0) {
                        leftSpace = botPlayer.getXCoord()-i;
                        break;
                    }
                }
                for (int i = botPlayer.getXCoord(); i < dungeon.getWidth(); i++) {
                    if (gridBounds[botPlayer.getYCoord()-1][i] == 0) {
                        rightSpace = i-botPlayer.getXCoord();
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
            if (gridBounds[botPlayer.getYCoord()+1][botPlayer.getXCoord()] == 0) {
                return direction;
            } else {
                int leftSpace = 100;
                int rightSpace = 100;
                for (int i = botPlayer.getXCoord(); i >= 0; i--) {
                    if (gridBounds[botPlayer.getYCoord()+1][i] == 0) {
                        leftSpace = botPlayer.getXCoord()-i;
                        break;
                    }
                }
                for (int i = botPlayer.getXCoord(); i < dungeon.getWidth(); i++) {
                    if (gridBounds[botPlayer.getYCoord()+1][i] == 0) {
                        rightSpace = i-botPlayer.getXCoord();
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
            if (gridBounds[botPlayer.getYCoord()][botPlayer.getXCoord()-1] == 0) {
                return direction;
            } else {
                int topSpace = 100;
                int bottomSpace = 100;
                for (int i = botPlayer.getYCoord(); i >= 0; i--) {
                    if (gridBounds[i][botPlayer.getXCoord()-1] == 0) {
                        topSpace = botPlayer.getYCoord()-i;
                        break;
                    }
                }
                for (int i = botPlayer.getYCoord(); i < dungeon.getHeight(); i++) {
                    if (gridBounds[i][botPlayer.getXCoord()-1] == 0) {
                        bottomSpace = i-botPlayer.getYCoord();
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
            if (gridBounds[botPlayer.getYCoord()][botPlayer.getXCoord()+1] == 0) {
                return direction;
            } else {
                int topSpace = 100;
                int bottomSpace = 100;
                for (int i = botPlayer.getYCoord(); i >= 0; i--) {
                    if (gridBounds[i][botPlayer.getXCoord()+1] == 0) {
                        topSpace = botPlayer.getYCoord()-i;
                        break;
                    }
                }
                for (int i = botPlayer.getYCoord(); i < dungeon.getHeight(); i++) {
                    if (gridBounds[i][botPlayer.getXCoord()+1] == 0) {
                        bottomSpace = i-botPlayer.getYCoord();
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
}
