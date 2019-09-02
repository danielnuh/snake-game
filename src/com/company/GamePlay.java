package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private ImageIcon titleImage, snakeImage, rightMouth, leftmouth, upMouth, downMouth, enemyImage;

    private int[] snakexLength = new int[750];
    private int[] snakeyLength = new int[750];
    private boolean isGameOver = false;

    private Random random = new Random();
    private int posXEnemy, posYEnemy;

    private boolean isLeft = false, isRight = false, isUp = false, isDown = false;

    private int lengthOfSnake = 3, moves = 0, score = 0, highScore = 0, level = 1, highLevel = 1;

    private Timer timer;
    private int delay = 100, tempDelay = 100;

    public GamePlay() {

        File file = new File("highScore.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();

                PrintWriter printWriter = new PrintWriter(file);
                printWriter.print("1;0");
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String[]temp = scanner.next().split(";");
                    highLevel = Integer.parseInt(temp[0]);
                    highScore = Integer.parseInt(temp[1]);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        addKeyListener(this);

        setFocusable(true);

        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (moves == 0) {
            snakexLength[2] = 50;
            snakexLength[1] = 75;
            snakexLength[0] = 100;

            snakeyLength[2] = 100;
            snakeyLength[1] = 100;
            snakeyLength[0] = 100;

        }

        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);

        titleImage = new ImageIcon("snaketitle.jpg");
        titleImage.paintIcon(this, g, 25, 11);

        g.setColor(Color.black);
        g.drawRect(24, 74, 851, 577);

        g.setColor(Color.black);
        g.drawRect(25, 75, 850, 575);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Delay : " + delay+" ms", 550, 30);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("High Score : " + highScore, 750, 30);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("High Level : " + highLevel, 750, 50);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Level : " + level, 660, 50);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score : " + score, 660, 30);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("W A S D dan panah untuk menggerakan ular", 50, 30);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("SPACE untuk menambah kecepatan", 50, 50);

        rightMouth = new ImageIcon("rightmouth.png");
        rightMouth.paintIcon(this, g, snakexLength[0], snakeyLength[0]);

        for (int i = 0; i < lengthOfSnake; i++) {
            if (i == 0 && isRight) {
                rightMouth = new ImageIcon("rightmouth.png");
                rightMouth.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
            if (i == 0 && isLeft) {
                leftmouth = new ImageIcon("leftmouth.png");
                leftmouth.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
            if (i == 0 && isDown) {
                downMouth = new ImageIcon("downmouth.png");
                downMouth.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
            if (i == 0 && isUp) {
                upMouth = new ImageIcon("upmouth.png");
                upMouth.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
            if (i != 0) {
                snakeImage = new ImageIcon("snakeimage.png");
                snakeImage.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
        }

        enemyImage = new ImageIcon("enemy.png");

        if ((snakexLength[0] == posXEnemy && snakeyLength[0] == posYEnemy) || posYEnemy == 0 || posXEnemy == 0) {

            if (posXEnemy != 0 && posXEnemy != 0) {
                lengthOfSnake++;

                score++;
                if (score > highScore) highScore = score;

                if (score == level * 10) {
                    level++;
                    if (delay > 40) {
                        delay -= 5;
                        tempDelay = delay;
                        timer.setDelay(tempDelay);
                    }else if (delay > 1){
                        delay -= 1;
                        tempDelay = delay;
                        timer.setDelay(tempDelay);
                    }
                }

                if (level > highLevel) highLevel = level;

            }

            boolean flag = true;

            while (flag) {
                posXEnemy = (1 + random.nextInt(34)) * 25;
                posYEnemy = (3 + random.nextInt(22)) * 25;

                for (int i = 0; i < snakeyLength.length; i++) {
                    if (posXEnemy == snakexLength[i] && posYEnemy == snakeyLength[i]) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
            }
        }

        enemyImage.paintIcon(this, g, posXEnemy, posYEnemy);


        for (int i = 1; i < lengthOfSnake; i++) {
            if (snakexLength[i] == snakexLength[0] && snakeyLength[i] == snakeyLength[0]) {
                isRight = isLeft = isUp = isDown = false;
                g.setColor(Color.black);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("GAME OVER", 300, 300);

                g.setColor(Color.black);
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Space to RESTART", 350, 340);

                File file = new File("highScore.txt");
                try {
                    Formatter formatter = new Formatter(file);
                    formatter.format(highLevel+";" + highScore);
                    formatter.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                isGameOver = true;
            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (isRight) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeyLength[i + 1] = snakeyLength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakexLength[i] = snakexLength[i] + 25;
                } else {
                    snakexLength[i] = snakexLength[i - 1];
                }

                if (snakexLength[i] > 850) {
                    snakexLength[i] = 25;
                }
            }
            repaint();
        }
        if (isLeft) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeyLength[i + 1] = snakeyLength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) snakexLength[i] = snakexLength[i] - 25;
                else snakexLength[i] = snakexLength[i - 1];

                if (snakexLength[i] < 25) snakexLength[i] = 850;
            }
            repaint();
        }
        if (isUp) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakexLength[i + 1] = snakexLength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) snakeyLength[i] = snakeyLength[i] - 25;
                else snakeyLength[i] = snakeyLength[i - 1];

                if (snakeyLength[i] < 75) snakeyLength[i] = 625;
            }
            repaint();
        }
        if (isDown) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakexLength[i + 1] = snakexLength[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) snakeyLength[i] = snakeyLength[i] + 25;
                else snakeyLength[i] = snakeyLength[i - 1];

                if (snakeyLength[i] > 625) snakeyLength[i] = 75;
            }
            repaint();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameOver) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                moves++;
                isRight = true;
                if (!isLeft) {
                    isRight = true;
                } else {
                    isRight = false;
                    isLeft = true;
                }
                isUp = false;
                isDown = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                moves++;
                isLeft = true;
                if (!isRight) {
                    isLeft = true;
                } else {
                    isLeft = false;
                    isRight = true;
                }
                isUp = false;
                isDown = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                moves++;
                isUp = true;
                if (!isDown) {
                    isUp = true;
                } else {
                    isUp = false;
                    isDown = true;
                }
                isLeft = false;
                isRight = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                moves++;
                isDown = true;
                if (!isUp) {
                    isDown = true;
                } else {
                    isUp = true;
                    isDown = false;
                }
                isLeft = false;
                isRight = false;
            }
        }


        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            timer.setDelay(tempDelay / 2);

            if (isGameOver) {
                score = 0;
                lengthOfSnake = 3;
                moves = 0;
                level = 1;
                tempDelay = delay = 100;
                isGameOver = false;
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            delay = tempDelay;
            timer.setDelay(delay);
        }
    }
}
