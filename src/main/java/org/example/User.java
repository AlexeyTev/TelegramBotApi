package org.example;

public class User {
    private int id;
    private long chatId;

    private String phone;
    private String name;
    private int interactions; // או ליסט של API צריך להחליט
    public User (String phone,String name, int id , long cId){
        this.id = id;
        this.chatId=cId;
        this.name=name;
        this.phone=phone;

    }


}
