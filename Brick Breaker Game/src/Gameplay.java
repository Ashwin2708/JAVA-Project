import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score=0;

    private int totalBricks=21;

    private Timer time;

    private int delay = 0;

    private int PlayerX=310;

    private int ballPosX=120;
    private int ballPosY=350;

    private int ballXdir=-1;
    private int ballYdir=-2;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1, 1, 692, 592);

        //borders
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //paddle shifter
        g.setColor(Color.yellow);
        g.fillRoundRect(PlayerX, 550, 140, 15, 10, 10);

        //balls
        g.setColor(Color.cyan);
        g.fillOval(ballPosX, ballPosY, 22, 22);

        //drawing map bricks
        map.draw((Graphics2D)g);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("Montserrat", Font.BOLD , 25));
        g.drawString(" " +score , 590, 30);

        //for game completion
        if(totalBricks <=0) {
            play = false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Montserrat", Font.BOLD , 27));
            g.drawString("Congratulations You've Won!" , 250, 300);

            g.setFont(new Font("Montserrat", Font.BOLD , 30));
            g.drawString("Press Enter to Restart" , 184, 350);
        }

        //setting for GameOver
        if(ballPosY > 570){
            play = false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Montserrat", Font.BOLD , 25));
            g.drawString("!!Game Over!!" , 250, 300);

            g.setFont(new Font("Montserrat", Font.BOLD , 30));
            g.drawString("Press Enter to Restart!" , 184, 350);
        }



    }



    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if(play) {

            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(PlayerX, 550, 100, 8))) {
                ballYdir= -ballYdir;
            }

            A:for(int i = 0; i < map.map.length; i++) {
                for(int j =0; j< map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickwidth + 80;
                        int brickY = i * map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickwidth , brickheight);
                        Rectangle ballrect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickrect= rect;

                        if(ballrect.intersects(brickrect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=5;

                            if(ballPosX + 19 <= brickrect.x || ballPosX + 1 >= brickrect.x + brickrect.width) {
                                ballXdir = - ballXdir;
                            }else {
                                ballYdir = - ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if(ballPosX<0) {
                ballXdir= -ballXdir;
            }
            if(ballPosY<0) {
                ballYdir= -ballYdir;
            }
            if(ballPosX>670) {
                ballXdir= -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
            if(PlayerX >= 600) {
                PlayerX = 600;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
            if(PlayerX <= 10) {
                PlayerX = 10;
            }
            else {
                moveLeft();
            }
        }

        //for showing scores and restarting the game.
        //set everything to default and repaint
        if (e.getKeyCode()== KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPosX=120;
                ballPosY=350;
                ballXdir=-1;
                ballYdir=-2;
                score=0;
                PlayerX=310;
                totalBricks=21;

                map= new MapGenerator(3,7);
                repaint();

            }

        }

    }

    public void moveLeft() {
        play = true;
        PlayerX-=20;
    }
    public void moveRight() {
        play = true;
        PlayerX+=20;
    }

}
