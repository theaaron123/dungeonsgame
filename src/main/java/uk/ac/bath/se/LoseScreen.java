package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;

public class LoseScreen extends JFrame {
    public LoseScreen(String playerName) {
        //Make a "You Died !" message.
        JFrame youLose = new JFrame("Dungeon Game");
        youLose.setLocationRelativeTo(null);
        youLose.setVisible(false);
        youLose.setResizable(false);
        youLose.setSize(400, 200);
        youLose.dispose();
        JOptionPane.showMessageDialog(youLose, "You have died !");

        // Player can choose Try Again, go back to Splash Screen or Exit the game
        JFrame loseChoice = new JFrame("Dungeon Game");
        loseChoice.setLocationRelativeTo(null);
        loseChoice.setVisible(true);
        loseChoice.setResizable(false);
        loseChoice.setSize(400, 200);

        JButton tryAgain = new JButton("Try Again");
        JButton goToSplashScreen = new JButton("Go to Splash Screen");
        JButton stopPlay = new JButton("Exit");

        loseChoice.getContentPane().setLayout(new GridLayout(3, 1));
        loseChoice.add(tryAgain);
        loseChoice.add(goToSplashScreen);
        loseChoice.add(stopPlay);

        goToSplashScreen.addActionListener(e -> {
            loseChoice.dispose();
            Player.lives += 3;
            new SplashScreen();
        });

        stopPlay.addActionListener(e -> System.exit(0));

        tryAgain.addActionListener(e -> {
            loseChoice.dispose();
            Player.lives += 3;
            new DungeonView(playerName);
        });
    }
}