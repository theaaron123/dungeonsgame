package uk.ac.bath.se;

import java.util.Random;

class BotPlayer {
    private int xCoord;
    private int yCoord;
    private final String PLAYER_SYMBOL = "B";
    private String playerName = "BOT";

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
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
