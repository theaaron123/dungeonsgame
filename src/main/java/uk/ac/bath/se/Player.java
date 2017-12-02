package uk.ac.bath.se;

import uk.ac.bath.se.Database.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Player {
    private int xCoord;
    private int yCoord;
    private int score;
    private int gold;
    private String symbol;
    private boolean playerTurn;
    private String playerName;
    public static int lives = 3;
    public boolean hasKey = false;

    public Player() {
        xCoord = 0;
        yCoord = 0;
        gold = 0;
        score = 0;
        symbol = "^";
        playerTurn = true;
    }

    public Player(int score, int gold, String playerName) {
        this.score = score;
        this.gold = gold;
        this.playerName = playerName;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

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
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private static List<Player> getTopPlayers() {
        List<Player> playerList = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper();
        ResultSet topPlayers = databaseHelper.retrieveTopPlayers();
        try {
            while (topPlayers.next()) {
                playerList.add(new Player(topPlayers
                        .getInt("Score"), topPlayers.getInt("Gold"), topPlayers.getString("Player_Name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public static String getTopPlayer() {
        String topPlayers = "";
        List<Player> playerList = getTopPlayers();
        for (Player player : playerList) {
            topPlayers += (player.playerName + "\t " + player.gold + "\t " + player.score + "\n");
        }
        return topPlayers;
    }
}
