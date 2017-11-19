import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonView implements KeyListener {
    private JPanel panel1;
    public DungeonController dg;
    JTextArea label1;

    //TODO refactor to adhere to MVC

    public DungeonView() {
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        label1 = new JTextArea();
        label1.setVisible(true);
        label1.setFont(new Font("monospaced", Font.PLAIN, 12));
        frame.add(panel);
        panel.add(label1);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setSize(600, 400);
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
                if(dg.playerPos[0] > 1) {
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                    dg.playerPos[0] = dg.playerPos[0] - 1;
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_DOWN:
                if(dg.playerPos[0] < 18) {
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                    dg.playerPos[0] = dg.playerPos[0] + 1;
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                    drawDungeon();
                }
                break;

            case KeyEvent.VK_LEFT:
                if(dg.playerPos[1] > 1) {
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                    dg.playerPos[1] = dg.playerPos[1] - 1;
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                    drawDungeon();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (dg.playerPos[1] <18) {
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                    dg.playerPos[1] = dg.playerPos[1] + 1;
                    dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                    drawDungeon();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
