package org.example;

import java.util.LinkedList;
import java.util.List;

public class BotStats {
   private List<User>uniqueUsers;
   private User mostActiveUser;
  private Api mostPopular;
  private final String[] API_NAME = {Constants.OPT_1,Constants.OPT_2,Constants.OPT_3,Constants.OPT_4,Constants.OPT_5};

  public BotStats(){
      this.uniqueUsers= new LinkedList<>();

  }


  private String mostActivityApi(){
      String result = "";
      int max = 0;
      int index = 0;
      for (int i = 0; i < 5; i++) {
          if (ApiBot.countApiActivity[i] > max) {
              index = i;
              max = ApiBot.countApiActivity[i];
          }
      }
      result = API_NAME[index];
      return result;
  }

    public String getStats (){
      return  "Requests received: "+ ApiBot.countRequest + "\n" +
              "Unique users: " + ApiBot.sizeUser +"\n" +
              "Most Active Api: " + mostActivityApi() +"\n" +
              "Most Active User: " + ApiBot.userName;
    }
}
