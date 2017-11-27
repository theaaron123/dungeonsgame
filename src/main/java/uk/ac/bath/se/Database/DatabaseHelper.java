package uk.ac.bath.se.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost/db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    void connect() {
        try {
            MySQLJDBC.createConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    void close() {
        try {
            MySQLJDBC.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertValues(String playerName, int goldAmount, int score) {
        connect();
        PreparedStatement ps;
        try {
            ps = MySQLJDBC.getConnection()
                    .prepareStatement("INSERT INTO `Dungeon_Scores`(`Player_Name`, `Gold`, `Score`) VALUES (?, ?, ?)");

            ps.setString(1, playerName);
            ps.setInt(2, goldAmount);
            ps.setInt(3, score);

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    public ResultSet retrieveTopPlayers() {
        connect();
        PreparedStatement statement;
        try {
            statement = MySQLJDBC.getConnection()
                    .prepareStatement("SELECT * FROM `Dungeon_Scores` ORDER BY `Score` DESC LIMIT 10");
            ResultSet rs = statement.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return null;
    }

}

