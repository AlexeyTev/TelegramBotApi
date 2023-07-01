package org.example;

import java.util.LinkedList;
import java.util.List;

public class BotStats {
   private List<User>uniqueUsers;
   private User mostActiveUser;
  private Api mostPopular;

  public BotStats(){
      this.uniqueUsers= new LinkedList<>();
  }

  public void addUniqueUser (User user){
      if (!this.uniqueUsers.contains(user)){
          this.uniqueUsers.add(user);
      }
  }

    public String getStats (){
      return  " Requests the bot received: "+ ApiBot.countRequest +
              " Unique users: " + ApiBot.sizeUser;
    }
}
