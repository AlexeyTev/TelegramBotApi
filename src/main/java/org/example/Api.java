package org.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Api extends Thread{
    private int id;
    private static int idCounter = 0;
    private String apiName;
    private User user;
    private Date date;
    private int useCounter;

    public Api (String apiName, User user, int time ){
       final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        this.id = idCounter;
        idCounter++;
        this.user = user;
        this.date = new Date();
        this.useCounter=0;
    }

    //TODO: לטפל בשליחה וקבלת בקשות
    private String sendRequestToApi (String userInput){
        String jsonResult ="";

        return jsonResult;
    }

    @Override
    public String toString() {
        return "Api:" + "(" + this.id + ")"+ " API chosen:" + this.apiName+", User: "+this.user + " at time: "+ this.date;
    }
}
