package uk.ac.bath.se;

public interface DungeonGamePlayInterface {

    void initialiseDungeonGame();

    void movePlayer(PlayerMovement playerMovement);

    String[][] getDungeonMatrix();
}
