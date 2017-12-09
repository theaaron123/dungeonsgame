package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class DungeonView implements KeyListener {
    private final JFrame gameWindow;
    private JPanel gamePanel;
    private JPanel dialoguePanel;
    private JTextArea gameArea;
    private JTextArea scoreArea;
    private JTextArea actionBox;
    private DungeonController dungeonController;
    Dungeon dungeon;

    public DungeonView() {
        gameWindow = new JFrame();
        gamePanel = new JPanel();
        dialoguePanel = new JPanel();

        gameArea = new JTextArea();
        gameArea.setForeground(Color.white);
        if (Dungeon.difficulty.equals("Easy")) {
            gameArea.setFont(new Font("monospaced", Font.PLAIN, 16));
        } else if (Dungeon.difficulty.equals("Hard")) {
            gameArea.setFont(new Font("monospaced", Font.PLAIN, 13));
        }
        gameArea.setVisible(true);
        gameArea.setEditable(false);

        scoreArea = new JTextArea();
        scoreArea.setBackground(Color.BLACK);
        scoreArea.setForeground(Color.white);
        scoreArea.setFont(new Font("monospaced", Font.PLAIN, 16));
        scoreArea.setVisible(true);
        scoreArea.setEnabled(false);

        actionBox = new JTextArea();
        actionBox.setBackground(Color.BLACK);
        actionBox.setForeground(Color.white);
        actionBox.setFont(new Font("monospaced", Font.PLAIN, 16));
        actionBox.setVisible(true);
        actionBox.setEnabled(false);
        actionBox.setText("The game has started.... Good Luck!\n");

        gameWindow.setLayout(new GridLayout(1,2));
        gameWindow.add(gamePanel);
        gameWindow.add(dialoguePanel);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setExtendedState(Frame.MAXIMIZED_BOTH);

        gamePanel.add(gameArea);
        gamePanel.setBackground(Color.BLACK);

        dialoguePanel.setLayout(new BorderLayout());
        dialoguePanel.add(scoreArea, BorderLayout.PAGE_START);
        dialoguePanel.add(actionBox, BorderLayout.PAGE_END);
        dialoguePanel.setBackground(Color.BLACK);
        dialoguePanel.add(new JScrollPane(actionBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        gameArea.addKeyListener(this);
        gameArea.setBackground(Color.BLACK);

        gameWindow.setVisible(true);

        dungeon = Dungeon.getInstance();
        dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();

        scoreArea.setText("Player Name: "
                + dungeonController.player.getPlayerName() +
                "\n" +
                "Lives: " +
                Player.lives +
                "\n" +
                "Score: " +
                dungeonController.player.getScore() +
                "\n" +
                "Gold: " +
                dungeonController.player.getGold() +
                "\n" +
                "Total gold required: " +
                dungeonController.gameWinAmount
        );

        drawDungeon();
    }

    //Draws dungeon with coordinates (y, x)
    private void drawDungeon() {
        gameArea.setText("");
        for (int i = 0; i < dungeon.dungeonMatrix.length; i++) {
            for (int j = 0; j < dungeon.dungeonMatrix[i].length; j++) {
                gameArea.append(dungeon.dungeonMatrix[i][j] + " ");
            }
            gameArea.append("\n");
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        String moveMessage = "";

        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                //if player hits boundary.
                if (!dungeonController.checkCollision(dungeonController.player.getyCoord() - 1, dungeonController.player.getxCoord())) {
                    moveMessage = dungeonController.movePlayer(PlayerMovement.UP);
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //if player hits boundary
                if (!dungeonController.checkCollision(dungeonController.player.getyCoord() + 1, dungeonController.player.getxCoord())) {
                    moveMessage = dungeonController.movePlayer(PlayerMovement.DOWN);
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                //if player hits boundary
                if (!dungeonController.checkCollision(dungeonController.player.getyCoord(), dungeonController.player.getxCoord() - 1)) {
                    moveMessage = dungeonController.movePlayer(PlayerMovement.LEFT);
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                //if player hits boundary
                if (!dungeonController.checkCollision(dungeonController.player.getyCoord(), dungeonController.player.getxCoord() + 1)) {
                    moveMessage = dungeonController.movePlayer(PlayerMovement.RIGHT);
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_SPACE:
                if (dungeonController.nearChest) {
                    String message = dungeonController.giveRandomItem();
                    actionBox.append(message + "\n");
                    drawDungeon();
                } else {
                    actionBox.append("No nearby items were found... \n");
                }
                break;
        }
        if (!moveMessage.equals("")) {
            actionBox.append(moveMessage);
        }

        if (dungeonController.checkLoss()) {
            Player.lives -= 1;
            if (Player.lives == 0) {
                gameWindow.dispose();
                SplashScreen.loseScreen();
            } else {
                int playerScore = dungeonController.player.getScore();
                actionBox.setText("You were caught by the bot. Try again.\n");
                actionBox.append("You have " + Player.lives);
                if(Player.lives > 1) {
                    actionBox.append(" lives remaining...\n");
                } else {
                    actionBox.append(" life remaining...\n");
                }
                dungeonController.initialiseDungeonGame();
                SplashScreen.loseOneLifeScreen();
                dungeonController.player.setScore(playerScore);
                drawDungeon();
            }
        }

        if (dungeonController.checkWin()) {
            //TODO input username to use
            dungeonController.saveGoldAmount(dungeonController.player.getPlayerName(),
                    dungeonController.player.getGold(),
                    dungeonController.player.getScore()
            );
            gameWindow.dispose();
            SplashScreen.winScreen();
        }
        scoreArea.setText("Player Name: "
                + dungeonController.player.getPlayerName() +
                "\n" +
                "Lives: " +
                Player.lives +
                "\n" +
                "Score: " +
                dungeonController.player.getScore() +
                "\n" +
                "Gold: " +
                dungeonController.player.getGold() +
                "\n" +
                "Total gold required: " +
                dungeonController.gameWinAmount
        );
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
