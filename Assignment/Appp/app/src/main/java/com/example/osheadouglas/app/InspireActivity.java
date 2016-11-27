package com.example.osheadouglas.app;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InterruptedIOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class InspireActivity extends AppCompatActivity implements AsyncTaskCompleteListenerXml<ArrayList<String>>,AsyncTaskCompleteListenerJson<ArrayList<String>> {


    // We need to work out how to get image data in same async task


    private ArrayList<String> musicList = new ArrayList<>(); // May have to uses an infromation class to bind items
    private ArrayList<String> imageUrlList = new ArrayList<>(); // May have to uses an infromation class to bind items
    private ArrayList<MusicInformation> completeInfo = new ArrayList<>();


    RecyclerView rV; // The view
    GridLayoutManager gridLayoutManager; // Orders tasks into thier grid
    InfoAdapter adapter; // USed to bind data from the webserver into the recyler view


    TextView tV;

    int callbackCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire);
        callbackCounter = 0;

        AsyncTaskParseXml a = new AsyncTaskParseXml(this,"http://musicovery.com/api/V2/artist.php?fct=getfromlocation&location=","sheffield","",false);      // this(Callback method is implemented here)
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

        AsyncTaskParseJson b = new AsyncTaskParseJson(this,musicList,true);
        b.execute();
    }

    @Override
    public void onTaskCompleteJson(ArrayList<String> result) {

        imageUrlList = result;
        completeInfo = arrayBinder(musicList,imageUrlList);

        adapter = new InfoAdapter(this,completeInfo);
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
