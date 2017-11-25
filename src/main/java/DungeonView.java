import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonView implements KeyListener {
    private JPanel panel1;
    JTextArea label1;
    public DungeonController dg;
    public boolean collided = false;

    public DungeonView() {
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        label1 = new JTextArea();
        label1.setForeground(Color.white);
        label1.setFont(new Font("monospaced", Font.PLAIN, 16)); //size depicts the size of the game view.
        label1.setVisible(true);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        panel.add(label1);
        panel.setBackground(Color.BLACK);

        label1.addKeyListener(this);
        label1.setBackground(Color.BLACK);

        frame.setVisible(true);

        dg = new DungeonController();
        dg.initialiseRandDungeon();
        drawDungeon();
    }

    //Draws dungeon with coordinates (y, x)
    private void drawDungeon() {
        label1.setText("");
        for (int i = 0; i < dg.dungeonMatrix.length; i++) {
            for (int j = 0; j < dg.dungeonMatrix[i].length; j++) {
                label1.append(dg.dungeonMatrix[i][j] + " ");
            }
            label1.append("\n");
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                //if player hits boundary.
                if (dg.gridBounds[dg.player.getPlayerY()-1][dg.player.getPlayerX()] == 1) {
                    collided = true;
                    break;
                }

                //if player is not moving into a boundary
                if (!collided) {
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = " ";
                    dg.player.setPlayerY(dg.player.getPlayerY() - 1);
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }

                collided = false;
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //if player hits boundary
                if (dg.gridBounds[dg.player.getPlayerY()+1][dg.player.getPlayerX()] == 1) {
                    collided = true;
                    break;
                }

                //if player is not moving into a boundary
                if (!collided) {
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = " ";
                    dg.player.setPlayerY(dg.player.getPlayerY() + 1);
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                collided = false;
                break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                //if player hits boundary
                if (dg.gridBounds[dg.player.getPlayerY()][dg.player.getPlayerX() -1] == 1) {
                    collided = true;
                    break;
                }

                //if player is not moving into a boundary
                if (!collided) {
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = " ";
                    dg.player.setPlayerX(dg.player.getPlayerX() - 1);
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                collided = false;
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                //if player hits boundary
                if (dg.gridBounds[dg.player.getPlayerY()][dg.player.getPlayerX()+1] == 1) {
                    collided = true;
                    break;
                }
                //if player is not moving into a boundary
                if (!collided) {
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = " ";
                    dg.player.setPlayerX(dg.player.getPlayerX() + 1);
                    dg.dungeonMatrix[dg.player.getPlayerY()][dg.player.getPlayerX()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                collided = false;
                break;
        }
        dg.addScore(); //Check if player has moved onto gold
        dg.checkExit(); //Check if player has moved to the exit
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
