package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen {

    public SplashScreen() {

        JFrame demo = new JFrame();
        demo.setSize(400, 150);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton choiceDifficulty = new JButton("Choice Difficulty");
        JButton startGame = new JButton("Start Game");
        JButton viewScore = new JButton("View Score");

        demo.getContentPane().add(BorderLayout.NORTH, choiceDifficulty);
        demo.getContentPane().add(BorderLayout.CENTER, startGame);
        demo.getContentPane().add(BorderLayout.SOUTH, viewScore);

        demo.setVisible(true);

        startGame.addActionListener(new startGameHandler());
    }
    // choice button-startGame
    private static class startGameHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            DungeonView d = new DungeonView();
        }
    }
}
