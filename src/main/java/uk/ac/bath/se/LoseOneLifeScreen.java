package uk.ac.bath.se;

import javax.swing.*;

public class LoseOneLifeScreen extends JFrame {
    public LoseOneLifeScreen() {
        //Make a "You Died !" message.
        JFrame youLose = new JFrame("Dungeon Game");
        youLose.setLocationRelativeTo(null);
        youLose.setVisible(false);
        youLose.setResizable(false);
        youLose.setSize(400, 200);
        youLose.dispose();
        JOptionPane.showMessageDialog(youLose, "You have lost one life !");
    }
}