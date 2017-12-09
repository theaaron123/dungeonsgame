package uk.ac.bath.se;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DungeonControllerTest {

    @Test
    public void testMovePlayerRight() throws Exception {
        DungeonController dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();
        int xCoordInitial = dungeonController.player.getxCoord();
        dungeonController.movePlayer(PlayerMovement.RIGHT);
        int xCoord = dungeonController.player.getxCoord();
        assertTrue(xCoord == (xCoordInitial + 1));
    }

    @Test
    public void testMovePlayerLeft() throws Exception {
        DungeonController dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();
        int xCoordInitial = dungeonController.player.getxCoord();
        dungeonController.movePlayer(PlayerMovement.LEFT);
        int xCoord = dungeonController.player.getxCoord();
        assertTrue(xCoord == (xCoordInitial - 1));
    }

    @Test
    public void testMovePlayerUp() throws Exception {
        DungeonController dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();
        int yCoordInitial = dungeonController.player.getyCoord();
        dungeonController.movePlayer(PlayerMovement.UP);
        int yCoord = dungeonController.player.getyCoord();
        assertTrue(yCoord == (yCoordInitial - 1));
    }

    @Test
    public void testMovePlayerDown() throws Exception {
        DungeonController dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();
        int yCoordInitial = dungeonController.player.getyCoord();
        dungeonController.movePlayer(PlayerMovement.DOWN);
        int yCoord = dungeonController.player.getyCoord();
        assertTrue(yCoord == (yCoordInitial + 1));
    }

    @Test
    public void testAddDungeonBounds() throws Exception {
        DungeonController dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();
        Dungeon dungeon = Dungeon.getInstance();

        for (int i = 0; i < dungeon.getWidth(); i++) {
            assertTrue(dungeon.dungeonMatrix[0][i] != " ");
            assertTrue(dungeon.dungeonMatrix[dungeon.getHeight() - 1][i] != " ");
        }
        for (int j = 0; j < dungeon.getHeight(); j++) {
            assertTrue(dungeon.dungeonMatrix[j][0] != " ");
            assertTrue(dungeon.dungeonMatrix[j][dungeon.getWidth() - 1] != " ");
        }
    }

    @Test
    public void drawDungeon() throws Exception {
    }

    @Test
    public void initialiseRandDungeon() throws Exception {
    }
}