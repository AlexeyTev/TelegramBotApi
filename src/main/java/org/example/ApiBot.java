package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

public class ApiBot extends TelegramLongPollingBot {
    ActivityChart ac;

    private List<Integer> apiChosen;
    public static int countRequest = -1;
    private Map<Long,Integer> uniqueUser;
    public static int sizeUser = 0;
    public static String userName = "";
    private ArrayList<Api>lastActions;
    public static int[] countApiActivity;

    public ApiBot(List<Integer> apiChosen) {
        this.apiChosen = apiChosen;
        uniqueUser = new HashMap<>();
        lastActions = new ArrayList<>();
        this.countApiActivity = new int[5];
        this.ac = new ActivityChart();
    }

    public String getBotUsername() {
        return Constants.BOT_USER_NAME;
    }

    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }

    public void onUpdateReceived(Update update) {
        sizeUser = uniqueUser.size();
        countRequest++;
        Long chatId = getChatId(update);
        if (!this.uniqueUser.containsKey(chatId)){
           this.uniqueUser.put(chatId,1);
        }else {
            int counter = this.uniqueUser.get(chatId)+1;
           this.uniqueUser.put(chatId,counter);
        }
        Long maxKey = Collections.max(uniqueUser.entrySet(), Map.Entry.comparingByValue()).getKey();
        String idUser = String.valueOf(maxKey);
        userName = getUserUsername(idUser);
        System.out.println(this.uniqueUser);
        String output = "Hi welcome to API bot, here are the available options: ";
        SendMessage sendMessage = new SendMessage();

        if(update.hasCallbackQuery()){
            sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
            String buttonPressed = update.getCallbackQuery().getData();
            switch (buttonPressed){
                case Constants.OPT_1 -> sendMessage.setText(jokesCreator());
                case Constants.OPT_2 -> sendMessage.setText(numberCreator());
                case Constants.OPT_3 -> sendMessage.setText(quotesCreator());
                case Constants.OPT_4 -> sendMessage.setText(catsCreator());
                case Constants.OPT_5 -> sendMessage.setText(factsCreator());
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
                        button3.setText(Constants.OPT_3);
                        button3.setCallbackData(Constants.OPT_3);
                        rowInline.add(button3);
                        counter++;
                    }
                    case Constants.OPT_4_NUM -> {
                        InlineKeyboardButton button4 = new InlineKeyboardButton();
                        button4.setText(Constants.OPT_4);
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

    public String getUserUsername(String userId) {
        GetChat getChat = new GetChat();
        getChat.setChatId(userId);
        String name = "";
        try {
            Chat chat = execute(getChat);
             name = chat.getFirstName();
            if (chat.getLastName() != null)
                name+="" +chat.getLastName();
            System.out.println("שם המשתמש: " + name);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return name;
    }
    private Long getChatId(Update update) {
        Long a=null;
        if (update.getMessage()!=null){
          a = update.getMessage().getChatId();
        }else {
            a = update.getCallbackQuery().getMessage().getChatId();
        }
        return a;
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
            this.countApiActivity[Constants.OPT_1_NUM-1]++;
            Api api = new Api(Constants.OPT_1);
            if (lastActions.size()<Constants.TEN_LAST_ACTIONS) {
                lastActions.add(api);
            }else {lastActions.remove(0);
                lastActions.add(api);
            }
            ac.setActivityAndTime(api,countRequest);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return output;
    }

    public String factsCreator() {
        String output = "Error";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FactApi factApi = objectMapper.readValue( new URL(Constants.FACT_API_URL), FactApi.class);
            output=factApi.toString();
            this.countApiActivity[Constants.OPT_5_NUM-1]++;
            Api api = new Api(Constants.OPT_5);
            if (lastActions.size()<Constants.TEN_LAST_ACTIONS) {
                lastActions.add(api);
            }else {lastActions.remove(0);
                lastActions.add(api);
            }
            ac.setActivityAndTime(api,countRequest);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return output;
    }

    public String catsCreator() {
        String output = "Error";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CatApi catApi = objectMapper.readValue( new URL(Constants.CAT_API_URL), CatApi.class);
            output=catApi.toString();
            this.countApiActivity[Constants.OPT_4_NUM-1]++;
            Api api = new Api(Constants.OPT_4);
            if (lastActions.size()<Constants.TEN_LAST_ACTIONS) {
                lastActions.add(api);
            }else {lastActions.remove(0);
                lastActions.add(api);
            }
            ac.setActivityAndTime(api,countRequest);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return output;
    }

    public String numberCreator(){
        GetRequest getRequest = Unirest.get(Constants.NUMBERS_API_URL);
        HttpResponse<String> response = null;
        String json = "Error";
        try {
            response = getRequest.asString();
             json = response.getBody();
            System.out.println(json);
            this.countApiActivity[Constants.OPT_2_NUM-1]++;
            Api api = new Api(Constants.OPT_2);
            ac.setActivityAndTime(api,countRequest);
            if (lastActions.size()<Constants.TEN_LAST_ACTIONS) {
                lastActions.add(api);
            }else {lastActions.remove(0);
                lastActions.add(api);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String quotesCreator(){
        String output = "Error";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<QuotesApi> quotesApi = objectMapper.readValue(new URL(Constants.QUOTES_API_URL), new TypeReference<List<QuotesApi>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            output=quotesApi.toString();
            this.countApiActivity[Constants.OPT_3_NUM-1]++;
            Api api = new Api(Constants.OPT_3);
            if (lastActions.size()<Constants.TEN_LAST_ACTIONS) {
                lastActions.add(api);
            }else {lastActions.remove(0);
                lastActions.add(api);
            }
            ac.setActivityAndTime(api,countRequest);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return output;
    }

    public String  getLastActions() {
        String output="";
        Api api;
        for (int i = 0 ;i < lastActions.size();i++){
            api = lastActions.get(i);
           output+=lastActions.get(i).toString() + "\n";
            System.out.println(api);
        }
        return output;
    }

    public ActivityChart getAc() {
        return ac;
    }
}



