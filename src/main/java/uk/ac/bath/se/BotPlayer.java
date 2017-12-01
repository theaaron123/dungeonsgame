package uk.ac.bath.se;

import java.util.Random;

class BotPlayer {
    private int xCoord;
    private int yCoord;
    private int score;
    private int gold;
    private final String PLAYER_SYMBOL = "B";
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
