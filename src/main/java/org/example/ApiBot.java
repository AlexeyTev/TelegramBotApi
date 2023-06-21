package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ApiBot extends TelegramLongPollingBot {
   private List<Integer> apiChosen;

   public ApiBot(List<Integer>apiChosen){
       this.apiChosen=apiChosen;
   }
    @Override
    public String getBotUsername() {
        return "api2106_bot";
    }

    @Override
    public String getBotToken() {
        return "6051729287:AAFBlCWb34R6FAQq6bbiq48Hnkae1582Dhc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String output = "Hi welcome to API bot, here are the available options:";
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        int counter = 0;
        while (counter < Constants.MAX_OPTIONS) {
            switch (apiChosen.get(counter)) {
                case Constants.OPT_1_NUM -> {
                    InlineKeyboardButton button1 = new InlineKeyboardButton();
                    button1.setText("Option 1");
                    button1.setCallbackData(Constants.OPT_1);
                    rowInline.add(button1);
                    counter++;
                }
                case Constants.OPT_2_NUM -> {
                    InlineKeyboardButton button2 = new InlineKeyboardButton();
                    button2.setText("Option 2");
                    button2.setCallbackData(Constants.OPT_2);
                    rowInline.add(button2);
                    counter++;
                }
                case Constants.OPT_3_NUM -> {
                    InlineKeyboardButton button3 = new InlineKeyboardButton();
                    button3.setText("Option 3");
                    button3.setCallbackData(Constants.OPT_3);
                    rowInline.add(button3);
                    counter++;
                }
                case Constants.OPT_4_NUM -> {
                    InlineKeyboardButton button4 = new InlineKeyboardButton();
                    button4.setText("Option 4");
                    button4.setCallbackData(Constants.OPT_4);
                    rowInline.add(button4);
                    counter++;
                }
                case Constants.OPT_5_NUM -> {
                    InlineKeyboardButton button5 = new InlineKeyboardButton();
                    button5.setText("Option 5");
                    button5.setCallbackData(Constants.OPT_5);
                    rowInline.add(button5);
                    counter++;
                }
            }
        }

        rowsInline.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInline);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(output);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        update.getCallbackQuery();
    }
    public void setApiChosen(List<Integer> apiChosen) {
        this.apiChosen = apiChosen;
    }
}
