package org.example;

import javax.swing.*;

public class Window extends JFrame {
    public Window (){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setLayout(null);
        this.setName("Telegram Bot - Admin Panel");
        this.setTitle("Telegram Bot - Admin Panel");
        this.setVisible(true);
        AdminPanel adminPanel = new AdminPanel();
        this.add(adminPanel);
    }
}
