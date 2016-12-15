package com.example.osheadouglas.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;


public class InspireActivity extends AppCompatActivity implements AsyncTaskCompleteListenerXml<ArrayList<String>>,AsyncTaskCompleteListenerJson<ArrayList<String>> {

    // We need to work out how to get image data in same async task


    String locationUrl = "http://musicovery.com/api/V2/artist.php?fct=getfromlocation&resultsnumber=1&location=";
    String moodUrl = "http://musicovery.com/api/V2/playlist.php?&fct=getfrommood&resultsnumber=1"; // &trackvalence=900000&trackarousal=100000
    String urlChoice;
    String selectedURL;
    String selectedParamerter;
    String catchItem;
    private boolean moodBand;
    private ArrayList<String> musicList = new ArrayList<>(); // May have to uses an infromation class to bind items
    private ArrayList<String> imageUrlList = new ArrayList<>(); // May have to uses an infromation class to bind items
    private ArrayList<MusicInformation> completeInfo = new ArrayList<>();
    RecyclerView rV; // The view
    GridLayoutManager gridLayoutManager; // Orders tasks into thier grid
    InfoAdapter adapter; // USed to bind data from the webserver into the recyler view
    int callbackCounter;
    private String location = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire);
        callbackCounter = 0;

        Bundle bundle = getIntent().getExtras();
        selectedParamerter = bundle.getString("urlParameter");
        urlChoice = bundle.getString("SP");
        moodBand = bundle.getBoolean("moodBand");
        location = bundle.getString("location");
        Log.i("TAG1 LOC After",location);   // Problem with location, it is NULL

        // The issuse is here
        if(urlChoice.equals("loc")){
            selectedURL = locationUrl;
            catchItem = "name";
            Log.i("IF CONDITION : "," LOC ");
        } else if(urlChoice.equals("mood")){ // It is == to mood
            selectedURL = moodUrl;
            catchItem = "title";
            Log.i("IF CONDITION : "," MOOD");
        }

        AsyncTaskParseXml a = new AsyncTaskParseXml(this,selectedURL,selectedParamerter,"",false,catchItem);      // this(Callback method is implemented here)
        a.execute();

        rV = (RecyclerView) findViewById(R.id.recyView);
        gridLayoutManager = new GridLayoutManager(this,1);
        rV.setLayoutManager(gridLayoutManager);
    }



    @Override
    public void onTaskCompleteXml(ArrayList<String> result) {
        //Toast.makeText(getApplicationContext(),result.get(0).getMusicDescription(),Toast.LENGTH_LONG).show();
        musicList = result;
        //Log.i("Called : ", Integer.toString(i));
        // If title add song on the end so it can find song covers. Becuase the title xml tag is the song name tag
        if(catchItem.equals("title")){
            for(int i = 0; i < musicList.size(); i++){
                musicList.set(i,musicList.get(i)+" song");
            }
        }

        AsyncTaskParseJson b = new AsyncTaskParseJson(this,musicList,true);
        b.execute();
    }


    @Override
    public void onTaskCompleteJson(ArrayList<String> result) {

        imageUrlList = result;
        completeInfo = arrayBinder(musicList,imageUrlList);
        Log.i("SELECT PARAM", selectedParamerter);

        adapter = new InfoAdapter(this,completeInfo,moodBand,location);
        rV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public ArrayList<MusicInformation> arrayBinder(ArrayList<String> arr1, ArrayList<String> arr2){ // Add ,
        ArrayList<MusicInformation> binded = new ArrayList<>();
        MusicInformation m;

        Log.i("NUMBER : ", Integer.toString(arr1.size()));
        Log.i("NUMBER : ", Integer.toString(arr2.size()));

        for(int i = 0; i < arr1.size(); i++){
            m = new MusicInformation();
            m.setMusicDescription(arr1.get(i));
            m.setImageUrl(arr2.get(i));
            binded.add(m);
        }
        return binded;
    }



}























