package uk.ac.bath.se;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GamePlayLookupTest {
    @Test
    public void testGetDungeonGamePlayInterfaceTest() throws Exception {
        assertTrue(GamePlayLookup.getDungeonGamePlayInterface() != null);
    }

    @Test
    public void testPlayerMovement() throws Exception {
        DungeonGamePlayInterface dungeonGamePlayInterface = GamePlayLookup.getDungeonGamePlayInterface();
        dungeonGamePlayInterface.initialiseDungeonGame();
        String[][] initialDungeonMatrix =
                Arrays.copyOf(dungeonGamePlayInterface.getDungeonMatrix(),
                        dungeonGamePlayInterface.getDungeonMatrix().length
                );
        dungeonGamePlayInterface.movePlayer(PlayerMovement.RIGHT);
        dungeonGamePlayInterface.movePlayer(PlayerMovement.UP);
        assertTrue(initialDungeonMatrix != dungeonGamePlayInterface.getDungeonMatrix());
    }
}