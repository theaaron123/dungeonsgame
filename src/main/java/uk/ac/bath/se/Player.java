package uk.ac.bath.se;

import uk.ac.bath.se.Database.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerX;
    private int playerY;
    private int score;
    private int gold;
    private final String PLAYER_SYMBOL = "@";
    private boolean playerTurn;
    private String playerName;

    public Player() {
        playerX = 0;
        playerY = 0;
        gold = 0;
        score = 0;
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