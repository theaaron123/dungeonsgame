import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonView implements KeyListener {
    private JPanel panel1;
    public DungeonController dg;
    Dungeon dungeon;
    JTextArea label1;
    public boolean collided = false;

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
//player pos 1 = x
//player pos 0 = y..
            case KeyEvent.VK_UP:
                //if player hits room.
                if(dg.playerPos[0] > 1) {
                    for (int i=0; i < dg.roomX.length; i++) {
                        if (dg.playerPos[0]-1 == dg.roomY[i] &&
                                dg.playerPos[1] == dg.roomX[i]) {
                            System.out.println("hit");
                            collided = true;
                            break;
                        }
                    }
                    //if player is not in room
                    if (!collided) {
                        dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                        dg.playerPos[0] = dg.playerPos[0] - 1;
                        dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                        drawDungeon();
                    }
                    collided = false;
                }
                break;

            case KeyEvent.VK_DOWN:
                if(dg.playerPos[0] < 18) {

                    //if player hits room.
                        for (int i = 0; i < dg.roomX.length; i++) {
                            if (dg.playerPos[0] + 1 == dg.roomY[i] &&
                                    dg.playerPos[1] == dg.roomX[i]) {
                                System.out.println("hit");
                                collided = true;
                                break;
                            }
                        }
                        //if player is not in room
                        if (!collided) {
                            dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                            dg.playerPos[0] = dg.playerPos[0] + 1;
                            dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                            drawDungeon();
                        }
                        collided = false;

                    }
                    break;

            case KeyEvent.VK_LEFT:
                if(dg.playerPos[1] > 1) {
                    //if player hits room.
                        for (int i = 0; i < dg.roomX.length; i++) {
                            if (dg.playerPos[0] == dg.roomY[i] &&
                                    dg.playerPos[1] -1 == dg.roomX[i]) {
                                System.out.println("hit");
                                collided = true;
                                break;
                            }
                        }
                        //if player is not in room
                        if (!collided) {
                            dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                            dg.playerPos[1] = dg.playerPos[1] - 1;
                            dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                            drawDungeon();
                        }
                        collided = false;
                    }
                    break;

            case KeyEvent.VK_RIGHT:
                if (dg.playerPos[1] < 18) {
                    //if player hits room.
                    for (int i = 0; i < dg.roomX.length; i++) {
                        if (dg.playerPos[0] == dg.roomY[i] &&
                                dg.playerPos[1] +1 == dg.roomX[i]) {
                            System.out.println("hit");
                            collided = true;
                            break;
                        }
                    }
                    //if player is not in room
                    if (!collided) {
                        System.out.println("X: " + dg.playerPos[1]);
                        dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = " ";
                        dg.playerPos[1] = dg.playerPos[1] + 1;
                        dg.dungeonMatrix[dg.playerPos[0]][dg.playerPos[1]] = "@";
                        drawDungeon();
                    }
                    collided = false;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
