package uk.ac.bath.se;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GamePlayLookupTest {
    @Test
    public void getDungeonGamePlayInterface() throws Exception {
        assertTrue(GamePlayLookup.getDungeonGamePlayInterface() != null);
    }
}