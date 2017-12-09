package uk.ac.bath.se;

public interface DungeonGamePlayInterface {

    void initialiseDungeonGame();

    String movePlayer(PlayerMovement playerMovement);

    String[][] getDungeonMatrix();
}
