package uk.ac.bath.se;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DungeonTest {
    @Test
    public void testInitialiseDungeon() throws Exception {
        Dungeon dungeon = Dungeon.getInstance();
        dungeon.initialiseDungeon();
        String[][] dungeonMatrix = dungeon.getDungeonMatrix();
        assertTrue(dungeonMatrix != null);
        assertTrue(dungeon.getHeight() == dungeonMatrix.length);
        assertTrue(dungeon.getWidth() == dungeonMatrix[0].length);
    }

    @Test
    public void testSingleton() {
        Dungeon instanceOne = Dungeon.getInstance();
        Dungeon instanceTwo = Dungeon.getInstance();
        assertEquals(true, instanceOne == instanceTwo);
    }
}