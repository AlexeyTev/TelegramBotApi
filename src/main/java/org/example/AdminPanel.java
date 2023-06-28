package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class AdminPanel extends JPanel {
    private List<Integer>apiChosen;
    private List<Api>lastActions;
    private final BotStats BOT_STATS;
    private final FlowLayout FLOW = new FlowLayout();

    public AdminPanel(){
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setLayout(FLOW);
        this.setName("Telegram Bot - Admin Panel");
        this.BOT_STATS =new BotStats();
        addStatsButton();
        addLastActionsButton();
        updateActivityGraph();
        addChoseApiOpt();
        this.setBackground(Color.white);
        this.setVisible(true);
    }

    private void updateActivityGraph() {
        JButton activityGraph = new JButton("Activity Graph");
        activityGraph.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        this.add(activityGraph);
        activityGraph.setVisible(true);
    }

    private void addLastActionsButton() {
        JButton lastActions = new JButton("Last Actions");
        lastActions.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        this.add(lastActions);
        lastActions.setVisible(true);
    }

    private void addStatsButton() {
        JButton statsButton = new JButton("Stats");
        statsButton.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        this.add(statsButton);
        statsButton.setVisible(true);
        statsButton.addActionListener(e -> {
            String stats = BOT_STATS.getStats();
            System.out.println(stats);
            Menu statsWindow = new Menu();
            statsWindow.setSize(getWidth(),getHeight());
            statsWindow.setLayout(FLOW);
            statsWindow.setLocationRelativeTo(null);
            statsWindow.setSize(Constants.WINDOW_WIDTH/2,Constants.WINDOW_HEIGHT/2);
            statsWindow.setName("Stats Window");
            statsWindow.setTitle("Telegram Bot Stats");
            statsWindow.setResizable(false);
            statsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JLabel botStats = new JLabel(stats);
            botStats.setVisible(true);
            statsWindow.add(botStats);
            statsWindow.setVisible(true);
        });
    }

    private void addChoseApiOpt() {
        JButton choseApiOpt = new JButton("Choose API's");
        choseApiOpt.setBounds(Constants.WINDOW_WIDTH/2-Constants.BUTTON_WIDTH/2,50,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        this.add(choseApiOpt);
        choseApiOpt.addActionListener(e -> {
            this.apiChosen = createWindowOfAvailableOpt();
            createApiBot(this.apiChosen);
            System.out.println(this.apiChosen);
        });
        choseApiOpt.setVisible(true);
    }

    private void createApiBot(List<Integer> apiChosen) {
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new ApiBot(this.apiChosen));
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private List<Integer> createWindowOfAvailableOpt() {
        List<Integer>optionsChoice = new ArrayList<>();
        Menu optionWindow = new Menu();
        optionWindow.setLayout(FLOW);
        optionWindow.setLocationRelativeTo(null);
        optionWindow.setSize(Constants.WINDOW_WIDTH/2,Constants.WINDOW_HEIGHT/2);
        optionWindow.setName("API options window");
        optionWindow.setTitle("API's options window");
        optionWindow.setResizable(false);
        optionWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JButton opt1 = new JButton(Constants.OPT_1);
        AtomicInteger opt1ToggleCounter = new AtomicInteger();
        opt1.setBounds(Constants.WINDOW_WIDTH/4-Constants.BUTTON_WIDTH/2,50,Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        optionWindow.add(opt1);
        opt1.setVisible(true);
        opt1.addActionListener(e -> {
            Integer one = 1;
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


    private void addNumberToToggle(List<Integer> optionsChoice, JButton button, AtomicInteger optToggleCounter, Integer num, Menu optionWindow) {
        JLabel errorLabel = new JLabel();
        optToggleCounter.getAndIncrement();
        if (optToggleCounter.intValue() % 2 == 1) {
            if (optionsChoice.size() < Constants.MAX_OPTIONS) {
                errorLabel.setText(" ");
                optionWindow.remove(errorLabel);
                optionWindow.add(errorLabel);
                errorLabel.setVisible(true);
                optionWindow.setVisible(true);
                button.setBackground(Color.BLUE);
                optionsChoice.add(num);
            } else {
                String errorMessage = "ERROR: Too Many API choices (ONLY " + Constants.MAX_OPTIONS + " ALLOWED)";
                JOptionPane.showMessageDialog(optionWindow, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                optionWindow.dispose();
            }
        } else {
            button.setBackground(Color.WHITE);
            optionsChoice.remove(num);
        }
        this.apiChosen = optionsChoice;
        System.out.println(optionsChoice);
    }
}

