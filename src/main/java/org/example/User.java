package org.example;

public class User {
    private int id;
    private static int idCounter=0;
    private String phone;
    private String name;
    private int interactions; // או ליסט של API צריך להחליט
    public User (String phone,String name){
        this.id = idCounter;
        idCounter++;
        this.name=name;
        this.phone=phone;

    }


}
