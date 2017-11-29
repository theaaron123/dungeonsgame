package uk.ac.bath.se;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonView implements KeyListener {
    private final JFrame gameWindow;
    private JPanel gamePanel;
    private JTextArea gameArea;
    private JTextArea scoreArea;
    private DungeonController dungeonController;
    private boolean collided = false;
    Dungeon dungeon;

    public DungeonView() {
        gameWindow = new JFrame();
        gamePanel = new JPanel();

        gameArea = new JTextArea();
        gameArea.setForeground(Color.white);
        gameArea.setFont(new Font("monospaced", Font.PLAIN, 16)); //size depicts the size of the game view.
        gameArea.setVisible(true);

        scoreArea = new JTextArea();
        scoreArea.setText("GOLD: " + 0 + "\n" + "SCORE: " + 0);

        gameWindow.add(gamePanel);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setExtendedState(Frame.MAXIMIZED_BOTH);

        gamePanel.add(gameArea);
        gamePanel.setBackground(Color.BLACK);

        gamePanel.add(scoreArea);

        gameArea.addKeyListener(this);
        gameArea.setBackground(Color.BLACK);

        gameWindow.setVisible(true);

        dungeon = Dungeon.getInstance();
        dungeonController = new DungeonController();
        dungeonController.initialiseDungeonGame();

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

        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                //if player hits boundary.
                if (dungeonController.gridBounds[dungeonController.player.getPlayerY() - 1][dungeonController.player.getPlayerX()] == 1 ||
                        !dungeonController.checkExit(dungeonController.player.getPlayerY() - 1, dungeonController.player.getPlayerX())) {
                    collided = true;
                    break;
                }

                //if player is not moving into a boundary
                if (!collided) {
                    dungeonController.movePlayerUp();
                    drawDungeon();
                }

                collided = false;
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //if player hits boundary
                if (dungeonController.gridBounds[dungeonController.player.getPlayerY() + 1][dungeonController.player.getPlayerX()] == 1 ||
                        !dungeonController.checkExit(dungeonController.player.getPlayerY() + 1, dungeonController.player.getPlayerX())) {
                    collided = true;
                    break;
                }


                //if player is not moving into a boundary
                if (!collided) {
                    dungeonController.movePlayerDown();
                    drawDungeon();
                }
                collided = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                //if player hits boundary
                if (dungeonController.gridBounds[dungeonController.player.getPlayerY()][dungeonController.player.getPlayerX() - 1] == 1 ||
                        !dungeonController.checkExit(dungeonController.player.getPlayerY(), dungeonController.player.getPlayerX() - 1)) {
                    collided = true;
                    break;
                }

                //if player is not moving into a boundary
                if (!collided) {
                    dungeonController.movePlayerLeft();
                    drawDungeon();
                }
                collided = false;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                //if player hits boundary
                if (dungeonController.gridBounds[dungeonController.player.getPlayerY()][dungeonController.player.getPlayerX() + 1] == 1 ||
                        !dungeonController.checkExit(dungeonController.player.getPlayerY(), dungeonController.player.getPlayerX() + 1)) {
                    collided = true;
                    break;
                }

                //if player is not moving into a boundary
                if (!collided) {
                    dungeonController.movePlayerRight();
                    drawDungeon();
                }
                collided = false;
                break;
        }
        dungeonController.assignGold(); //Check if player has moved onto gold
        dungeonController.assignTorch();
        dungeonController.player.setScore(dungeonController.player.getScore()+1);
        //Reset at completion
        if (dungeonController.checkExit(dungeonController.player.getPlayerY(), dungeonController.player.getPlayerX()) &&
                dungeonController.gridBounds[dungeonController.player.getPlayerY()][dungeonController.player.getPlayerX()] == 5) {

            dungeonController.saveGoldAmount("Aaron",
                    dungeonController.player.getGold(),
                    dungeonController.player.getScore()
            );

            dungeonController.initialiseDungeonGame();
            drawDungeon();
        }
        scoreArea.setText("GOLD: " +
                dungeonController.player.getGold() +
                "\n" +
                "SCORE: " +
                dungeonController.player.getScore() +
                "\n\n"
        );
        //TODO don't call this every time the player moves..
        scoreArea.append("Player Name:" + " Gold:\t" + "Score:\n" + Player.getTopPlayer());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
