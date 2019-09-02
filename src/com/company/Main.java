package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        GamePlay gamePlay = new GamePlay();

        jFrame.setBounds(10,10,905,700);
        jFrame.setBackground(Color.DARK_GRAY);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePlay);
    }
}
