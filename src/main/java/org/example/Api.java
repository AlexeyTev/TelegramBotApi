package org.example;

import java.util.Calendar;
import java.util.Date;

public class Api extends Thread{
    private int id;
    private static int idCounter = 0;
    private String apiName;
    private User user;
    private Date date;
    public static int time;
    private int useCounter;

    //TODO: לבדוק איך תריך להעביר את הזמן שאותו מבקשים
    public Api (String apiName, User user, int time ){
        Calendar calendar = Calendar.getInstance();
        this.id = idCounter;
        idCounter++;
        this.user = user;
        this.date = calendar.getTime();
        this.useCounter=0;
    }



}
