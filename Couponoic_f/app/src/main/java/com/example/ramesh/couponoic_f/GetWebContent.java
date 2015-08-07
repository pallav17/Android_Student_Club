package com.example.ramesh.couponoic_f;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ramesh on 4/11/2015.
 */
public class GetWebContent {

    public static String getResponseFromWeb(String url){
        StringBuffer res= new StringBuffer("");
        try{
            URL u=new URL(url);
            URLConnection ul=u.openConnection();
            BufferedReader br=new BufferedReader(new InputStreamReader(ul.getInputStream()));
            String temp;
            while((temp=br.readLine())!=null){
                res.append(temp);
            }
            return res.toString();
        }
        catch(Exception e){
            return e.getMessage();
        }

    }

}