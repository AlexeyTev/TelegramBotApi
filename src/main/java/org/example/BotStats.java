package org.example;

import java.util.LinkedList;
import java.util.List;

public class BotStats {
    private int totalRequests ;
   private List<User>uniqueUsers;
   private User mostActiveUser;
  private Api mostPopular;

  public BotStats(){
      this.totalRequests=0;
      this.uniqueUsers= new LinkedList<>();
  }
  public void addRequest (){
      this.totalRequests++;
  }
  public void addUniqueUser (User user){
      if (!this.uniqueUsers.contains(user)){
          this.uniqueUsers.add(user);
      }
  }

    public String getStats (){
      return ApiBot.countRequest + " requests, " + this.uniqueUsers.size() + " unique users, ";
    }
}
