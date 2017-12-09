package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SplashScreen {

    public SplashScreen() {

        //Make a splash screen have three buttons.
        JFrame menu = new JFrame("Dungeon Game");
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        menu.setResizable(false);

        menu.setSize(400, 200);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton userName = new JButton("Create User Name");
        JButton chooseDifficulty = new JButton("Choose Difficulty Level");
        JButton startGame = new JButton("Start Game");
        JButton viewScore = new JButton("View Historical Scores");

        menu.getContentPane().setLayout(new GridLayout(4, 1));
        menu.add(userName);
        menu.add(chooseDifficulty);
        menu.add(startGame);
        menu.add(viewScore);

        userName.addActionListener(new userNameHandler());
        chooseDifficulty.addActionListener(new choiceDifficulty());
        startGame.addActionListener(new startGameHandler());
        startGame.addActionListener(e -> menu.dispose());
        viewScore.addActionListener(new viewScoreHandler());
    }

    // When player choice button createUserName, the game shows a new window for inputting User Name .
    public class userNameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFrame userNameFrame = new JFrame("Dungeon Game");
            userNameFrame.setSize(300, 100);
            userNameFrame.setLocationRelativeTo(null);
            userNameFrame.setVisible(true);
            userNameFrame.setResizable(false);

            JTextField name = new JTextField(20);

            JLabel nameLabel = new JLabel(" Create User Name");
            JButton confirmButton = new JButton("Confirm");

            userNameFrame.setLayout(new GridLayout(3, 1));
            userNameFrame.add(nameLabel);
            userNameFrame.add(name);
            userNameFrame.add(confirmButton);

            confirmButton.addActionListener(e12 -> {

                //Get User Name
                if (e12.getSource() == confirmButton || e12.getSource() == name) {

                    Player.playerName = name.getText();

                    // Show Welcome Message
                    JFrame welcomeFrame;
                    welcomeFrame = new JFrame("Create User Name");
                    welcomeFrame.setLocationRelativeTo(null);
                    welcomeFrame.setVisible(true);
                    welcomeFrame.dispose();
                    JOptionPane.showMessageDialog(welcomeFrame, "Welcome " + name.getText() + " !");
                    welcomeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
            });

            confirmButton.addActionListener(e1 -> userNameFrame.dispose());
        }
    }

    // When player chooses button chooseDifficulty, the game shows a new window.
    public class choiceDifficulty implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new DifficultyChooser();
        }
    }

    // The player can choose the level of difficulty of game.
    public class DifficultyChooser extends JFrame
    {
        private DifficultyChooser() {

            JFrame difficultyChoice = new JFrame("Choose Difficulty Level");
            difficultyChoice.setLocationRelativeTo(null);
            difficultyChoice.setVisible(true);
            difficultyChoice.setResizable(false);
            difficultyChoice.setSize(400, 200);

            JButton easyModel = new JButton("Easy Model");
            JButton hellModel = new JButton("Hard Model");

            difficultyChoice.getContentPane().setLayout(new GridLayout(2,1));
            difficultyChoice.add(easyModel);
            difficultyChoice.add(hellModel);

            easyModel.addActionListener(new easyModel());
            easyModel.addActionListener(e -> difficultyChoice.dispose());

            hellModel.addActionListener(new hardModel());
            hellModel.addActionListener(e -> difficultyChoice.dispose());
        }
    }

    // Show a description "You chose Easy Model !" in a new window
    public static class easyModel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame easyChoice;
            easyChoice = new JFrame("Difficulty Level Chose");
            easyChoice.setLocationRelativeTo(null);
            easyChoice.setVisible(false);
            easyChoice.dispose();
            Dungeon.difficulty = "Easy";
            JOptionPane.showMessageDialog(easyChoice,"You chose Easy Model !");
        }
    }

    // Show a description "You chose Hard Model !" in a new window
    public static class hardModel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame hardChoice;
            hardChoice = new JFrame("Difficulty Level Chose");
            hardChoice.setLocationRelativeTo(null);
            hardChoice.setVisible(false);
            hardChoice.dispose();
            Dungeon.difficulty = "Hard";
            JOptionPane.showMessageDialog(hardChoice,"You chose Hard Model !");
        }
    }

    // When player chooses button startGame, the game start.
    public class startGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (Player.playerName == null) {
                Player.playerName = "Anonymous";
            }
            new DungeonView();
        }
    }

    //When player chooses button View Historical Scores, the game shows a new window.
    public class viewScoreHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new HighScores();
        }
    }

    // Show Historical Scores in a new window.
    public class HighScores extends JFrame
    {
        public HighScores() {

            JFrame scores = new JFrame("Historical Scores");
            scores.setLocationRelativeTo(null);
            scores.setVisible(true);
            scores.setResizable(false);
            scores.setSize(400, 400);
            scores.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JTextArea scoreArea = new JTextArea();
            scores.getContentPane().add(BorderLayout.CENTER, scoreArea);
            scoreArea.append("Player Name:" + "Gold:\t" + "Score:\n" + Player.getTopPlayer());
        }
    }

    public static void winScreen() {

        //Make a "You Win !" message.
        JFrame youWin = new JFrame("Dungeon Game");
        youWin.setLocationRelativeTo(null);
        youWin.setVisible(false);
        youWin.setResizable(false);
        youWin.setSize(400, 200);
        youWin.dispose();
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

        goToSplashScreen.addActionListener(e -> {
            new SplashScreen();
            winChoice.dispose();
        });

        stopPlay.addActionListener(e -> System.exit(0));
    }

    public static void loseScreen() {

        //Make a "You Died !" message.
        JFrame youLose = new JFrame("Dungeon Game");
        youLose.setLocationRelativeTo(null);
        youLose.setVisible(false);
        youLose.setResizable(false);
        youLose.setSize(400, 200);
        youLose.dispose();
        JOptionPane.showMessageDialog(youLose,"You have died !");

        // Player can choose Try Again, go back to Splash Screen or Exit the game
        JFrame loseChoice = new JFrame("Dungeon Game");
        loseChoice.setLocationRelativeTo(null);
        loseChoice.setVisible(true);
        loseChoice.setResizable(false);
        loseChoice.setSize(400, 200);

        JButton tryAgain = new JButton("Try Again");
        JButton goToSplashScreen = new JButton("Go to Splash Screen");
        JButton stopPlay = new JButton("Exit");

        loseChoice.getContentPane().setLayout(new GridLayout(3,1));
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
            new DungeonView();
        });
    }

    public static void loseOneLifeScreen() {

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
