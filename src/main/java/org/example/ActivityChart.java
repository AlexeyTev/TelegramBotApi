package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.util.LinkedList;
import java.util.List;

//https://quickchart.io/chart?c={type:%27line%27,
// data:{labels:[2012,2013,2014,2015,%202016],
// datasets:[{label:%27Users%27,data:[120,60,50,180,120]}]}}

public class ActivityChart {
    private List<String> label;
    private List<Integer>activity;
    private String chartUrl;

    public ActivityChart(){ //בלינקד ליסט בגלל שהמספר יכול לגדול מאוד בעת ההפעלה וכך נמנע בעיות בזיכרון
        this.label =new LinkedList<>();
        this.activity = new LinkedList<>();
        this.chartUrl = Constants.CHART_URL;


    }
    public void setActivityAndTime (Api api,int n){
        this.activity.add(n);
        String day ="";
        switch (api.getDate().toString().substring(0, api.getDate().toString().indexOf(" "))){
            case "Sun" -> day = "1";
            case "Mon" -> day ="2";
            case "Tue" -> day ="3";
            case "Wed" -> day ="4";
            case "Thu" -> day ="5";
            case "Fri" -> day="6";
            case "Sat" -> day="7";
        }
        this.label.add(day);
    }
    //TODO:לבדוק איך אני מקבל את הרספונס של האיפיאי ולסדר את ההדפסה
    public String generateChart () {
        String jsonResult = "";
        if (this.activity != null && this.label != null || this.activity.size()>0 && this.label.size()>0) {

            String generate = this.chartUrl;
            for (String label : this.label) {
                generate += label + ",";
            }
            generate = generate.substring(0, generate.length() - 1);
            generate += "],datasets:[{label:%27Activity Over Time%27,data:[";
            for (Integer data : this.activity) {
                generate += data.toString() + ",";
            }
            generate = generate.substring(0, generate.length() - 1);
            generate += "]}]}}";
            System.out.println(generate);

//            try {
//                GetRequest getRequest = Unirest.get(generate);
//                HttpResponse<String> response;
//
//                response = getRequest.asString();
//
//                System.out.println(response.getStatus());
//                System.out.println("--------");
//                jsonResult = response.getBody();
//            } catch (UnirestException e) {
//                throw new RuntimeException(e);
//            }


        }
        return jsonResult;
    }

}
