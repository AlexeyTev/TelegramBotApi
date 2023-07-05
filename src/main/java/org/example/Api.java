package org.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Api {
    private int id;
    private static int idCounter = 0;
    private String apiName;
    private User user;
    private Date date;
    private int useCounter;

    public Api (String apiName){
        this.id = idCounter;
        idCounter++;
        this.date = new Date();
        this.useCounter=0;
        this.apiName=apiName;
    }

    private String sendRequestToApi (String userInput){
        String jsonResult ="";

        return jsonResult;
    }


    public String toString() {
        return "Api:" + "(" + this.id + ")"+ " API chosen:" + this.apiName+" at time: "+ this.date;
    }
}
