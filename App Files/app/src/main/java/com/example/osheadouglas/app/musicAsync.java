package com.example.osheadouglas.app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by osheadouglas on 16/12/2016.
 */

public class musicAsync extends AsyncTask<String,String,String> {

    private String musicName;
    private musicCallBack<String> callBack;
    private String jsonUrl = "https://itunes.apple.com/search?term=";
    private String jsonParameters = "&limit=1";
    private String soundFileAddress = "";

    public musicAsync(musicCallBack<String> cb,String mN){
        super();
        musicName = mN;

        if(musicName.contains(" ")){
            musicName = musicName.replaceAll(" ","+");
            Log.i("SPACE FOUND",musicName);
        }

        this.callBack = cb;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {

        soundFileAddress = new String();

        try {
            Log.i("Tag", jsonUrl+musicName+jsonParameters);
            // create new instance of the httpConnect class
            httpConnect jParser = new httpConnect();
                // get json string from service url
                String json = jParser.getJSONFromUrl(jsonUrl+musicName+jsonParameters,"",false);
                JSONObject root = new JSONObject(json);
                JSONArray valueArray = root.getJSONArray("results");
                JSONObject imageResult = valueArray.getJSONObject(0);
                soundFileAddress = imageResult.getString("previewUrl");
                Log.i("Value :: ", soundFileAddress);
            } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return soundFileAddress;
    }

    @Override
    // below method will run when service HTTP request is complete
    protected void onPostExecute(String musicAddress) {
        callBack.onTaskCompleteJson(musicAddress);
    }

}




















