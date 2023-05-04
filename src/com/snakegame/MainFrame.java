package com.snakegame;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        GamePlay gamePlay = new GamePlay();

        window.setBounds(200, 100, 905, 700);
        window.setTitle("2D Snake Game");
        window.setBackground(Color.DARK_GRAY);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gamePlay);
    }
}
