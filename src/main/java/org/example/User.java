package org.example;

public class User {
    private int id;
    private long chatId;
    private String name;
    private int interactions;
    public User (String name, int id , long cId){
        this.id = id;
        this.chatId=cId;
        this.name=name;

        this.interactions = 0;
    }
    public void addInteraction(){
        this.interactions++;
    }
    public int getInteractions(){
        return this.interactions;
    }

}
