package com.example.osheadouglas.app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by osheadouglas on 26/11/2016.
 */

//79a57862ebb742fea38bceff8dc324e8
//9bc27df31bc543e393253f511a3afd94

public class AsyncTaskParseJson extends AsyncTask<String, String, ArrayList<String>> {


    private AsyncTaskCompleteListenerJson<ArrayList<String>> callBack; // Call back


    private String auth = "9bc27df31bc543e393253f511a3afd94";
    private String jsonString = "";
    private String imageContentUrl;
    private String jsonService = "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q=";
    private String jsonParameters = "&mkt=en-us%20HTTP/1.1&count=1&size=medium";
    private ArrayList<String> musicNameList;
    private ArrayList<String> imageUrlList;


    boolean isAuth;

    public AsyncTaskParseJson(AsyncTaskCompleteListenerJson<ArrayList<String>> cb, ArrayList<String> musicNameListI, boolean isAuthI){ // I == Input
        super();
        musicNameList = musicNameListI;
        isAuth = isAuthI;
        this.callBack = cb;
    }


    @Override
    // this method is used for......................
    protected void onPreExecute(){
    }


    @Override
    // this method is used for...................
    protected ArrayList<String> doInBackground(String... arg0) {
        imageUrlList = new ArrayList<>();

        try {
            // create new instance of the httpConnect class
            httpConnect jParser = new httpConnect();

            for(int i = 0; i < musicNameList.size(); i++) {
                // get json string from service url
                String valuePass = URLEncoder.encode(musicNameList.get(i),"UTF-8");
                String json = jParser.getJSONFromUrl(jsonService+valuePass+jsonParameters, auth, isAuth);    // Loops through the musicNameList and queries each music list string
                JSONObject root = new JSONObject(json);
                JSONArray valueArray = root.getJSONArray("value");
                JSONObject imageResult = valueArray.getJSONObject(0);
                imageContentUrl = imageResult.getString("contentUrl");
                imageUrlList.add(imageContentUrl);
                Log.i("Value :: ", imageContentUrl);
            }

        } catch (Exception e) {
            Log.i("ITS HERE", " THE ERROR IS HERE");
            Log.i("FIRST MUSIC ", musicNameList.get(0));
            e.printStackTrace();
        }
        return imageUrlList;
    }


    @Override
    // below method will run when service HTTP request is complete, will then bind tweet text in arrayList to ListView
    protected void onPostExecute(ArrayList<String> urlListOutput) {
        callBack.onTaskCompleteJson(urlListOutput);
        Log.i("Test Json", imageContentUrl);
    }

}



















