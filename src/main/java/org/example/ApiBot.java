package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiBot extends TelegramLongPollingBot {

    private List<Integer> apiChosen;

    public ApiBot(List<Integer> apiChosen) {
        this.apiChosen = apiChosen;
    }

    public String getBotUsername() {
        return Constants.BOT_USER_NAME;
    }

    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }

    public void onUpdateReceived(Update update) {
        String output = "Hi welcome to API bot, here are the available options: ";
        SendMessage sendMessage = new SendMessage();


        if(update.hasCallbackQuery()){
            sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
            String buttonPressed = update.getCallbackQuery().getData();
            switch (buttonPressed){
                case Constants.OPT_1 -> {sendMessage.setText(jokesCreator());                }
                case Constants.OPT_2 -> {sendMessage.setText(numberCreator());}
                case Constants.OPT_3 -> {sendMessage.setText(quotesCreator());}
                case Constants.OPT_4 -> {}
                case Constants.OPT_5 -> {}
            }
        }else {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            int counter = 0;
            while (counter < Constants.MAX_OPTIONS) {
                switch (apiChosen.get(counter)) {
                    case Constants.OPT_1_NUM -> {
                        InlineKeyboardButton button1 = new InlineKeyboardButton();
                        button1.setText(Constants.OPT_1);
                        button1.setCallbackData(Constants.OPT_1);
                        rowInline.add(button1);
                        counter++;
                    }
                    case Constants.OPT_2_NUM -> {
                        InlineKeyboardButton button2 = new InlineKeyboardButton();
                        button2.setText(Constants.OPT_2);
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
                        button5.setText(Constants.OPT_5);
                        button5.setCallbackData(Constants.OPT_5);
                        rowInline.add(button5);
                        counter++;
                    }
                }
            }

            rowsInline.add(rowInline);
            inlineKeyboardMarkup.setKeyboard(rowsInline);

            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(output);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setApiChosen(List<Integer> apiChosen) {
        this.apiChosen = apiChosen;
    }

    public String getJson(String url) {
        String json = "";
        try {
            GetRequest getRequest = Unirest.get(url);
            HttpResponse<String> response = getRequest.asString();
            json = response.getBody();

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String jokesCreator() {
        String output = "Error";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
           JokesApi jokesApi = objectMapper.readValue( new URL(Constants.JOKES_API_URL), JokesApi.class);
            output=jokesApi.getJoke();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return output;
    }

    public String numberCreator(){
        GetRequest getRequest = Unirest.get(Constants.NUMBERS_API_URL);
        HttpResponse<String> response = null;
        try {
            response = getRequest.asString();
            String json = response.getBody();
            System.out.println(json);
        } catch (UnirestException e) {
            e.printStackTrace();
        }


    }

    public String quotesCreator(){
        String output = "Error";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            QuotesApi quotesApi = objectMapper.readValue( new URL(Constants.QUOTES_API_URL), QuotesApi.class);
            output=quotesApi.toString();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

    }
    }



