package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class AdminPanel extends JPanel {
    private ApiBot theActiveBot;
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
        activityGraph.addActionListener(e -> {
            this.theActiveBot.getAc().generateChart();
            Menu statsWindow = new Menu();
            statsWindow.setSize(getWidth(),getHeight());
            statsWindow.setLayout(FLOW);
            statsWindow.setLocationRelativeTo(null);
            statsWindow.setSize(Constants.WINDOW_WIDTH/2,Constants.WINDOW_HEIGHT/2);
            statsWindow.setName("Stats Window");
            statsWindow.setTitle("Telegram Bot Stats");
            statsWindow.setResizable(false);
            statsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            String chartUrl = "https://quickchart.io/sandbox/#%7B%22chart%22%3A%22%7B%5Cn%20%20type%3A%20'bar'%2C%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%2F%2F%20Show%20a%20bar%20chart%5Cn%20%20data%3A%20%7B%5Cn%20%20%20%20labels%3A%20%5B2012%2C%202013%2C%202014%2C%202015%2C%202016%5D%2C%20%20%20%2F%2F%20Set%20X-axis%20labels%5Cn%20%20%20%20datasets%3A%20%5B%7B%5Cn%20%20%20%20%20%20label%3A%20'Users'%2C%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%2F%2F%20Create%20the%20'Users'%20dataset%5Cn%20%20%20%20%20%20data%3A%20%5B50%2C%20100%2C%2050%2C%20180%2C%20120%5D%20%20%20%20%20%20%20%20%20%20%20%2F%2F%20Add%20data%20to%20the%20chart%5Cn%20%20%20%20%7D%5D%5Cn%20%20%7D%5Cn%7D%22%2C%22width%22%3A800%2C%22height%22%3A400%2C%22version%22%3A%222.9.4%22%2C%22backgroundColor%22%3A%22%23fff%22%7D"; // הקישור לתמונת הגרף
            URL url = null;
            try {
                url = new URL(chartUrl);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
            ImageIcon chartImage = new ImageIcon(url);
            JLabel chartLabel = new JLabel(chartImage);
            statsWindow.getContentPane().add(chartLabel);
            statsWindow.setVisible(true);
        });
    }

    private void addLastActionsButton() {
        JButton lastActions = new JButton("Last Actions");
        lastActions.setSize(Constants.BUTTON_WIDTH,Constants.BUTTON_HEIGHT);
        this.add(lastActions);
        lastActions.setVisible(true);
        lastActions.addActionListener(e->{
            if (this.theActiveBot!=null) {
                Menu lastActionsWindow = new Menu();
                lastActionsWindow.setSize(getWidth(), getHeight());
                lastActionsWindow.setLayout(FLOW);
                lastActionsWindow.setLocationRelativeTo(null);
                lastActionsWindow.setSize(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2);
                lastActionsWindow.setName("Last Actions Window");
                lastActionsWindow.setTitle("Last Actions Telegram Bot");
                lastActionsWindow.setResizable(false);
                lastActionsWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JTextArea lastActionsLabel = new JTextArea(this.theActiveBot.getLastActions());
                lastActionsLabel.setVisible(true);
                lastActionsWindow.add(lastActionsLabel);
                lastActionsWindow.setVisible(true);
            }
        });
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
            ApiBot apiBot = new ApiBot(this.apiChosen);
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(apiBot);
            this.theActiveBot=apiBot;
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

