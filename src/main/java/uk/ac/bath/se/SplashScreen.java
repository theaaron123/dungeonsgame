package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SplashScreen {

    public SplashScreen() {

        //Make a splash screen have three button.
        JFrame demo1 = new JFrame("Dungeon Game");
        demo1.setLocationRelativeTo(null);
        demo1.setVisible(true);
        demo1.setResizable(false);

        demo1.setSize(400, 200);
        demo1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton choiceDifficulty = new JButton("Choose Difficulty Level");
        JButton startGame = new JButton("Start Game");
        JButton viewScore = new JButton("View Historical Scores");

        demo1.getContentPane().setLayout(new GridLayout(3,1));
        demo1.add(choiceDifficulty);
        demo1.add(startGame);
        demo1.add(viewScore);

        choiceDifficulty.addActionListener(new choiceDifficulty());
        startGame.addActionListener(new startGameHandler());
        viewScore.addActionListener(new viewScoreHandler());
    }

    // When player choice button chooseDifficulty, the game shows a new window.
    public class choiceDifficulty implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            win1 win1 = new win1();
        }
    }

    //TODO Those two button still only are button, need to add other function to actually change the difficulty level
    //TODO For instance, maybe Hell Model have five bot for hunting human player.
    // The player can choice the level of difficulty of game.
    public class win1 extends JFrame
    {
        private win1() {

            JFrame demo2 = new JFrame("Choose Difficulty Level");
            demo2.setLocationRelativeTo(null);
            demo2.setVisible(true);
            demo2.setResizable(false);

            demo2.setSize(400, 200);

            JButton easyModel = new JButton("Easy Model");
            JButton hellModel = new JButton("Hell Model");

            demo2.getContentPane().setLayout(new GridLayout(2,1));
            demo2.add(easyModel);
            demo2.add(hellModel);

            easyModel.addActionListener(new easyModel());
            easyModel.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    demo2.dispose();
                }
            });

            hellModel.addActionListener(new hellModel());
            hellModel.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    demo2.dispose();
                }
            });
        }
    }

    // Show a description "You chose Easy Model !" in a new window
    public static class easyModel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame f1;
            f1 =new JFrame("Difficulty Level Chose");
            f1.setLocationRelativeTo(null);
            f1.setVisible(true);
            JOptionPane.showMessageDialog(f1,"You chose Easy Model !");
            f1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    // Show a description "You chose Hell Model !" in a new window
    public static class hellModel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame f2;
            f2 =new JFrame("Difficulty Level Chose");
            f2.setLocationRelativeTo(null);
            f2.setVisible(true);
            JOptionPane.showMessageDialog(f2,"You chose Hell Model !");
            f2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    // When player choice button startGame, the game start.
    public class startGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) { DungeonView d = new DungeonView();
        }
    }

    //When player choice button View Historical Scores, the game shows a new window.
    public class viewScoreHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            win2 win2 = new win2();
        }
    }

    // Show Historical Scores in a new window.
    public class win2 extends JFrame
    {
        public win2() {

            JFrame demo3 = new JFrame("Historical Scores");
            demo3.setLocationRelativeTo(null);
            demo3.setVisible(true);
            demo3.setResizable(false);
            demo3.setSize(400, 400);

            JTextArea scoreArea = new JTextArea();

            demo3.getContentPane().add(BorderLayout.CENTER, scoreArea);

            scoreArea.append("Player Name:" + "Gold:\t" + "Score:\n" + Player.getTopPlayer());

        }
    }

    public static void winScreen() {

        //Make a "You Win !" message.
        JFrame youWin = new JFrame("Dungeon Game");
        youWin.setLocationRelativeTo(null);
        youWin.setVisible(true);
        youWin.setResizable(false);

        youWin.setSize(400, 200);

        JOptionPane.showMessageDialog(youWin,"You Win !");

        // Player can choose go back to Splash Screen or Exit the game
        JFrame winChoice = new JFrame("Dungeon Game");
        winChoice.setLocationRelativeTo(null);
        winChoice.setVisible(true);
        winChoice.setResizable(false);

        winChoice.setSize(400, 200);

        JButton goToSplashScreen = new JButton("Go to Splash Screen");
        JButton stopPlay = new JButton("Exit");

        winChoice.getContentPane().setLayout(new GridLayout(2,1));
        winChoice.add(goToSplashScreen);
        winChoice.add(stopPlay);

        goToSplashScreen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                SplashScreen splashScreen = new SplashScreen();
            }
        });

        goToSplashScreen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                winChoice.dispose();
            }
        });

        stopPlay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


}