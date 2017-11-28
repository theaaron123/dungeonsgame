package uk.ac.bath.se;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DungeonTest {
    @Test
    public void testSingleton() {
        Dungeon instanceOne = Dungeon.getInstance();
        Dungeon instanceTwo = Dungeon.getInstance();
        Assert.assertEquals(true, instanceOne == instanceTwo);
    }
}