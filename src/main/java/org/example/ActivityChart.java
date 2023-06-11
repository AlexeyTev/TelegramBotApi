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
    public void setActivityAndTime (int data,String label){
        this.activity.add(data);
        this.label.add(label);
    }
    //TODO:לבדוק איך אני מקבל את הרספונס של האיפיאי ולסדר את ההדפסה
    public String generateChart (){
        String jsonResult= "";
        String generate=this.chartUrl;
        for (String label: this.label){
            generate+=label + ",";
        }
        generate+="0],datasets:[{label:%27Activity Over Time%27,data:[";
        for (Integer data:this.activity){
            generate+=data.toString() + ",";
        }
        generate+="0]}]}}";
        System.out.println(generate);

     /*   try{
        GetRequest getRequest = Unirest.get(generate);
        HttpResponse<String> response;

            response = getRequest.asString();

        System.out.println(response.getStatus());
        System.out.println("--------");
        jsonResult = response.getBody();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }*/


        return jsonResult;
    }
}
