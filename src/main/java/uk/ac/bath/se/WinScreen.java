package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;

public class WinScreen extends JFrame {
    public WinScreen() {
        //Make a "You Win !" message.
        JFrame youWin = new JFrame("Dungeon Game");
        youWin.setLocationRelativeTo(null);
        youWin.setVisible(false);
        youWin.setResizable(false);
        youWin.setSize(400, 200);
        youWin.dispose();
        JOptionPane.showMessageDialog(youWin, "You Win !");

        // Player can choose go back to Splash Screen or Exit the game
        JFrame winChoice = new JFrame("Dungeon Game");
        winChoice.setLocationRelativeTo(null);
        winChoice.setVisible(true);
        winChoice.setResizable(false);
        winChoice.setSize(400, 200);

        JButton goToSplashScreen = new JButton("Go to Splash Screen");
        JButton stopPlay = new JButton("Exit");

        winChoice.getContentPane().setLayout(new GridLayout(2, 1));
        winChoice.add(goToSplashScreen);
        winChoice.add(stopPlay);

        goToSplashScreen.addActionListener(e -> {
            new SplashScreen();
            winChoice.dispose();
        });

        stopPlay.addActionListener(e -> System.exit(0));
    }
}