package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class AdminPanel extends JPanel {
    public AdminPanel(){
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setLayout(null);
        this.setName("Telegram Bot - Admin Panel");
        this.setVisible(true);
        addChoseApiOpt();
        }

    private void addChoseApiOpt() {
        JButton choseApiOpt = new JButton("Choose API's");
        choseApiOpt.setBounds(Constants.WINDOW_WIDTH/2-Constants.BUTTON_WIDTH/2,50,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        choseApiOpt.setVisible(true);
        this.add(choseApiOpt);
        choseApiOpt.addActionListener(e -> {
            System.out.println(createWindowOfAvailableOpt()
);
        });
    }

    private List<Integer> createWindowOfAvailableOpt() {
        FlowLayout flow = new FlowLayout();
        List<Integer>optionsChoice = new ArrayList<>();
        Window optionWindow = new Window();
        optionWindow.setLayout(flow);
        optionWindow.setLocationRelativeTo(null);
        optionWindow.setSize(Constants.WINDOW_WIDTH/2,Constants.WINDOW_HEIGHT);
        optionWindow.setName("API options window");
        optionWindow.setTitle("API's options window");
        optionWindow.setResizable(false);
        optionWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //TODO: צריך לסדר את זה שכשנפתח חלון לבחירת אופציות מבצבץ כפתור קודם בצד ימין
       // optionWindow.removeAll();
        JButton opt1 = new JButton(Constants.OPT_1);
        AtomicInteger opt1ToggleCounter = new AtomicInteger();
        opt1.setBounds(Constants.WINDOW_WIDTH/4-Constants.BUTTON_WIDTH/2,50,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        optionWindow.add(opt1);
        opt1.setVisible(true);
        opt1.addActionListener(e -> {
            Integer one = 1,two = 2,three = 3,four = 4,five = 5;
            addNumberToToggle(optionsChoice, opt1, opt1ToggleCounter, one,optionWindow);
        });


        JButton opt2 = new JButton(Constants.OPT_2);
        AtomicInteger opt2ToggleCounter = new AtomicInteger();
        opt2.setBounds(Constants.WINDOW_WIDTH/4-Constants.BUTTON_WIDTH/2,50*2,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        optionWindow.add(opt2);
        opt2.setVisible(true);
        opt2.addActionListener(e -> {
            Integer two = 2;
            addNumberToToggle(optionsChoice, opt2, opt2ToggleCounter, two,optionWindow);
        });

        JButton opt3 = new JButton(Constants.OPT_3);
        AtomicInteger opt3ToggleCounter = new AtomicInteger();
        opt3.setBounds(Constants.WINDOW_WIDTH/4-Constants.BUTTON_WIDTH/2,50*3,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        optionWindow.add(opt3);
        opt3.setVisible(true);
        opt3.addActionListener(e -> {
            Integer three = 3;
            addNumberToToggle(optionsChoice, opt3, opt3ToggleCounter, three,optionWindow);
        });
        JButton opt4 = new JButton(Constants.OPT_4);
        AtomicInteger opt4ToggleCounter = new AtomicInteger();
        opt4.setBounds(Constants.WINDOW_WIDTH/4-Constants.BUTTON_WIDTH/2,50*4,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        optionWindow.add(opt4);
        opt4.setVisible(true);
        opt4.addActionListener(e -> {
            Integer four = 4;
            addNumberToToggle(optionsChoice, opt4, opt4ToggleCounter,four,optionWindow );
        });
        JButton opt5 = new JButton(Constants.OPT_5);
        AtomicInteger opt5ToggleCounter = new AtomicInteger();
        opt5.setBounds(Constants.WINDOW_WIDTH/4-Constants.BUTTON_WIDTH/2,50*5,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        optionWindow.add(opt5);
        opt5.setVisible(true);
        opt5.addActionListener(e -> {
            Integer five = 5;
            addNumberToToggle(optionsChoice, opt5, opt5ToggleCounter,five,optionWindow );
        });

        optionWindow.setVisible(true);
        return optionsChoice;
    }

    //TODO: לבדוק איך עושים טוגל בצורה שונה מספירה עם מספרים
    private void addNumberToToggle(List<Integer> optionsChoice, JButton button, AtomicInteger optToggleCounter, Integer num,Window optionWindow
    ) {JLabel errorLabel = new JLabel();
        optToggleCounter.getAndIncrement();
        if (optToggleCounter.intValue()%2==1) {
            if (optionsChoice.size()<Constants.MAX_OPTIONS){
            button.setBackground(Color.BLUE);
            optionsChoice.add(num);
        }else {   errorLabel.setText("ERROR: To Many API choices (ONLY " +Constants.MAX_OPTIONS+ " ALLOWED)");
                System.out.println("ERROR: To Many API choices (ONLY " +Constants.MAX_OPTIONS+ " ALLOWED)");
                errorLabel.setBounds(Constants.WINDOW_WIDTH/4,Constants.WINDOW_HEIGHT-100,200,50);
                errorLabel.setVisible(true);
                optionWindow.add(errorLabel);
                optionWindow.setVisible(true);
        }
            }else {
            button.setBackground(Color.WHITE);
            optionsChoice.remove(num);

        }

        System.out.println(optionsChoice);
    }
}
