import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonView implements KeyListener {
    private JPanel panel1;
    public DungeonController dg;
    Dungeon dungeon;
    JTextArea label1;

    //TODO refactor to adhere to MVC

    public DungeonView() {
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        label1 = new JTextArea();
        label1.setVisible(true);

        label1.setForeground(Color.white);
        label1.setFont(new Font("monospaced", Font.PLAIN, 22)); //size depicts the size of the game view.


        frame.add(panel);
        panel.add(label1);
        frame.setVisible(true);
        frame.setSize(800, 800);
        panel.setBackground(Color.BLACK);
        label1.setBackground(Color.BLACK);

        label1.addKeyListener(this);

        dg = new DungeonController();
        dg.initialiseRandDungeon();

        drawDungeon();
    }

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
            case KeyEvent.VK_UP:
                if (dg.player.getPlayerX() > 1) {
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = " ";
                    dg.player.setPlayerX(dg.player.getPlayerX() - 1);
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_DOWN:
                if (dg.player.getPlayerX() < 18) {
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = " ";
                    dg.player.setPlayerX(dg.player.getPlayerX() + 1);
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_LEFT:
                if (dg.player.getPlayerY() > 1) {
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = " ";
                    dg.player.setPlayerY(dg.player.getPlayerY() - 1);
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (dg.player.getPlayerY() < 18) {
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = " ";
                    dg.player.setPlayerY(dg.player.getPlayerY() + 1);
                    dg.dungeonMatrix[dg.player.getPlayerX()][dg.player.getPlayerY()] = dg.player.getPlayerSymbol();
                    drawDungeon();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
