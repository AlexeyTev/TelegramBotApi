package org.example;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    public Menu(){

        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setName("Telegram Bot - Admin Panel");
        this.setTitle("Telegram Bot - Admin Panel");
        ImageIcon icon = new ImageIcon(Constants.ICON_NAME);
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
