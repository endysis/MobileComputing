package com.example.osheadouglas.app;

import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
//import java.util.Base64.Encoder;

/**
 * Created by osheadouglas on 13/11/2016.
 */

public class httpConnect {

    // the below line is for making debugging easier
    final String TAG = "JsonParser.java";

    // where the returned json data from service will be stored when downloaded
    static String json = "";

    //String authorization;

    // your android activity will call this method and pass in the url of the REST service
    public String getJSONFromUrl(String url,String auth, boolean authCheck) {

        try {
            // this code block represents/configures a connection to your REST service
            // it also represents an HTTP 'GET' request to get data from the REST service, not POST!
            URL u = new URL(url);

            HttpURLConnection restConnection = (HttpURLConnection) u.openConnection(); // Gets from url

            if(authCheck){ // If the connection requires a key
                String authorization = auth;
                String encode = Base64.encode(authorization.getBytes(),Base64.DEFAULT).toString();
                String basicAuth = "Basic " + encode; // This may not work
                restConnection.setRequestProperty("Ocp-Apim-Subscription-Key",authorization);
                Log.i("KEY : ", basicAuth);
            }
            restConnection.setRequestMethod("GET");
            restConnection.setRequestProperty("Content-length","0");
            restConnection.setUseCaches(false);
            restConnection.setAllowUserInteraction(false);
            restConnection.setConnectTimeout(10000);
            restConnection.setReadTimeout(10000);


            restConnection.connect();

            int status = restConnection.getResponseCode();

            // switch statement to catch HTTP 200 and 201 errors

            switch(status) {
                case 401: Log.i("KEY ACCESS :"," DENIED");
                case 200:
                case 201:
                    // live connection to your REST service is established here using getInputStream() method
                    BufferedReader br = new BufferedReader(new InputStreamReader(restConnection.getInputStream()));

                    // Create a new string builder to store json data returned from the rest service
                    StringBuilder sb = new StringBuilder();
                    String line;

                    // loop through returned data line by line and append to stringbuilder 'sb' variable
                    while((line = br.readLine()) != null){
                        sb.append(line + "\n");
                    }
                    br.close();

                    try {
                        json = sb.toString();
                    } catch (Exception e) {
                        Log.e(TAG,"Error parsing data " + e.toString());

                    }
                    return json;
            }

        } catch (MalformedURLException ex) {
            Log.e(TAG,"Malformed URL");
        } catch (IOException ex){
            Log.e(TAG,"IO Exception");
        }

        return null;
    }
}
























