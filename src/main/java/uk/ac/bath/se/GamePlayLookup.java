package uk.ac.bath.se;

public class GamePlayLookup {
    private static final DungeonGamePlayInterface dungeonGamePlay = new DungeonController();

    public static DungeonGamePlayInterface getDungeonGamePlayInterface() {
        return dungeonGamePlay;
    }
}
