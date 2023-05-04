package com.snakegame;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class GamePlay extends JPanel
    implements KeyListener, ActionListener{

    private ImageIcon titleImage;
    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;
    private ImageIcon snakeImage;
    private ImageIcon foodImage;

    private Audio audioPlayer;

    private int snakeLength = 3;
    private int moves = 0;
    private int[] snakeX = new int[750];
    private int[] snakeY = new int[750];
    private int[] foodX = new int[34];
    private int[] foodY = new int[23];

    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean gameOver = false;

    private Timer timer;
    private int delay = 100;

    private Random randomSpawn = new Random();
    private int foodPosX;
    private int foodPosY;
    private int score = 0;

    public GamePlay(){
        audioPlayer = new Audio();
        foodPosX = randomSpawn.nextInt(34);
        foodPosY = randomSpawn.nextInt(23);
        for(int x = 0; x < foodX.length; x++){
            foodX[x] = 25 * (x + 1);
        }
        for(int y = 0; y < foodY.length; y++){
            foodY[y] = 25 * (y + 3);
        }
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics){
        if(moves == 0){
            int nextBody = 0;
            for(int i = 0; i < snakeLength; i++){
                snakeX[i] = 100 - nextBody;
                nextBody += 25;
            }
            for(int i = 0; i < snakeLength; i++){
                snakeY[i] = 100;
            }
        }

        //Draw Image
        graphics.setColor(Color.WHITE);
        graphics.drawRect(24, 10, 851, 55);
        titleImage = new ImageIcon("src/com/snakegame/snaketitle.jpg");
        titleImage.paintIcon(this, graphics, 25, 11);
        //GamePlay Display
        graphics.setColor(Color.WHITE);
        graphics.drawRect(24, 74, 851, 577);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(25, 75, 850, 575);
        //Start Menu
        if(moves == 0 && !gameOver) {
            timer.stop();
            audioPlayer.playSound("src/com/snakegame/menusoundtrack.wav");
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("arial", Font.BOLD, 60));
            graphics.drawString("2D SNAKE", 300, 300);
            graphics.setFont(new Font("arial", Font.BOLD, 60));
            graphics.drawString("Press Enter to Play", 180, 370);
        }
        //Snake
        rightMouth = new ImageIcon("src/com/snakegame/rightmouth.png");
        rightMouth.paintIcon(this, graphics, snakeX[0], snakeY[0]);
        for(int i = 0; i < snakeLength; i++){
            if(i == 0 && right){
                rightMouth = new ImageIcon("src/com/snakegame/rightmouth.png");
                rightMouth.paintIcon(this, graphics, snakeX[i], snakeY[i]);
            }
            else if(i == 0 && left){
                leftMouth = new ImageIcon("src/com/snakegame/leftmouth.png");
                leftMouth.paintIcon(this, graphics, snakeX[i], snakeY[i]);
            }
            else if(i == 0 && up){
                upMouth = new ImageIcon("src/com/snakegame/upmouth.png");
                upMouth.paintIcon(this, graphics, snakeX[i], snakeY[i]);
            }
            else if(i == 0 && down){
                downMouth = new ImageIcon("src/com/snakegame/downmouth.png");
                downMouth.paintIcon(this, graphics, snakeX[i], snakeY[i]);
            }
            else if(i != 0){
                snakeImage = new ImageIcon("src/com/snakegame/snakeimage.png");
                snakeImage.paintIcon(this, graphics, snakeX[i], snakeY[i]);
            }
        }
        //Score
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Score: " + score, 750, 30);
        //Snake Length
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Snake Length: " + snakeLength, 750, 50);
        //Food
        if(moves > 0) {
            foodImage = new ImageIcon("src/com/snakegame/food.png");
            foodImage.paintIcon(this, graphics, foodX[foodPosX], foodY[foodPosY]);
        }
        for(int i = 0; i < snakeLength; i++) {
            if (foodX[foodPosX] == snakeX[i] && foodY[foodPosY] == snakeY[i]) {
                if(i == 0) {
                    audioPlayer.playSound("src/com/snakegame/applebite.wav");
                    snakeLength++;
                    score += 10;
                }
                foodPosX = randomSpawn.nextInt(34);
                foodPosY = randomSpawn.nextInt(23);
            }
        }
        //Body Collision
        for(int b = 1; b < snakeLength; b++){
            if(snakeX[b] == snakeX[0] && snakeY[b] == snakeY[0]){
                gameOver = true;
                if(gameOver){
                    delay = 100;
                }
                right = false;
                left = false;
                up = false;
                down = false;
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Game Over", 300, 300);
                graphics.setFont(new Font("arial", Font.BOLD, 20));
                graphics.drawString("Press Space Button to Restart", 290, 330);
                timer.stop();
            }
        }
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent action){
        if(right){
            for(int r = snakeLength - 1; r >= 0; r--){
                snakeY[r + 1] = snakeY[r];
            }
            for(int r = snakeLength; r >= 0; r--){
                if(r == 0){
                    snakeX[r] += 25;
                } else {
                    snakeX[r] = snakeX[r - 1];
                }
                if(snakeX[r] > 850){
                    snakeX[r] = 25;
                }
            }
            this.repaint();
        }
        else if(left){
            for(int l = snakeLength - 1; l >= 0; l--){
                snakeY[l + 1] = snakeY[l];
            }
            for(int l = snakeLength; l >= 0; l--){
                if(l == 0){
                    snakeX[l] -= 25;
                } else {
                    snakeX[l] = snakeX[l - 1];
                }
                if(snakeX[l] < 25){
                    snakeX[l] = 850;
                }
            }
            this.repaint();
        }
        else if(up){
            for(int u = snakeLength - 1; u >= 0; u--){
                snakeX[u + 1] = snakeX[u];
            }
            for(int u = snakeLength; u >= 0; u--){
                if(u == 0){
                    snakeY[u] -= 25;
                } else {
                    snakeY[u] = snakeY[u - 1];
                }
                if(snakeY[u] < 75){
                    snakeY[u] = 625;
                }
            }
            this.repaint();
        }
        else if(down){
            for(int d = snakeLength - 1; d >= 0; d--){
                snakeX[d + 1] = snakeX[d];
            }
            for(int d = snakeLength; d >= 0; d--){
                if(d == 0){
                    snakeY[d] += 25;
                } else {
                    snakeY[d] = snakeY[d - 1];
                }
                if(snakeY[d] > 625){
                    snakeY[d] = 75;
                }
            }
            this.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent event){}

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int[] directions = {
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN
        };
        int enter = KeyEvent.VK_ENTER;
        int space = KeyEvent.VK_SPACE;
        if(keyCode == enter) {
            if(!gameOver) {
                timer.start();
            }
            moves++;
            right = true;
            left = false;
            up = false;
            down = false;
            this.repaint();
        }
        if(keyCode == space){
            if(gameOver) {
                timer.start();
                gameOver = false;
                moves = 0;
            }
            right = true;
            left = false;
            up = false;
            down = false;
            score = 0;
            snakeLength = 3;
            foodPosX = randomSpawn.nextInt(34);
            foodPosY = randomSpawn.nextInt(23);
            for(int i = 0; i < snakeLength; i++){
                if(foodX[foodPosX] == snakeX[i] && foodY[foodPosY] == snakeY[i]){
                    foodPosX = randomSpawn.nextInt(34);
                    foodPosY = randomSpawn.nextInt(23);
                }
            }
            this.repaint();
        }
        if (keyCode == directions[0]) {
            moves++;
            if(!left){
                right = true;
            } else {
                right = false;
            }
            up = false;
            down = false;
        }
        else if (keyCode == directions[1]) {
            moves++;
            if (!right) {
                left = true;
            } else {
                left = false;
            }
            up = false;
            down = false;
        }
        else if(keyCode == directions[2]){
            moves++;
            up = true;
            if(!down){
                up = true;
            } else {
                up = false;
            }
            left = false;
            right = false;
        }
        else if(keyCode == directions[3]){
            moves++;
            down = true;
            if(!up){
                down = true;
            } else {
                down = false;
            }
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent event){}
}
