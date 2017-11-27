package uk.ac.bath.se;

import java.util.Random;

public class BotPlayer {
    private int xCoord;
    private int yCoord;
    private int score;
    private int gold;
    private final String PLAYER_SYMBOL = "B";
    private boolean playerTurn;
    private String playerName = "BOT";

    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getPLAYER_SYMBOL() {
        return PLAYER_SYMBOL;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public static int[] randomWalk(Dungeon dungeon) {
        Random rand = new Random();
        int xRand;
        int yRand;
        xRand = rand.nextInt(dungeon.getWidth());
        yRand = rand.nextInt(dungeon.getHeight());
        int[] xyArray = {xRand, yRand};
        return xyArray;
    }
}
