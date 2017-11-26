package uk.ac.bath.se;

public class Player {
    private int playerX;
    private int playerY;
    private int score;
    private int gold;
    private final String PLAYER_SYMBOL = "@";
    private boolean playerTurn;

    public Player() {
        playerX = 0;
        playerY = 0;
        gold = 0;
        score = 0;
        playerTurn = true;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getPlayerSymbol() {
        return PLAYER_SYMBOL;
    }

}
