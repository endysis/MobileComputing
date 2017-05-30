package com.example.osheadouglas.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InspireDecide extends AppCompatActivity implements musicCallBack<String> {

    String urlText;
    String musicText;
    String location;
    private boolean moodBand;
    ImageView i;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire_decide);

        i = (ImageView) findViewById(R.id.imageIn);
        Bundle bundle = getIntent().getExtras();
        musicText = bundle.getString("inspText");
        urlText = bundle.getString("image");
        location = bundle.getString("location");
        moodBand = bundle.getBoolean("iM");
        Picasso.with(getApplicationContext()).load(urlText).into(i);
    }

    public void recClick(View view){
        if(moodBand == true){
            musicText = "Inspired by the song " + musicText;
        } else {
            musicText = "Inspired by " + musicText;
        }
        Intent i = new Intent(this,RecordActivity.class);
        i.putExtra("inspire",musicText);
        i.putExtra("location",location);
        startActivity(i);
    }

    public void playClick(View view){
        musicAsync a = new musicAsync(this,musicText);
        a.execute();
    }

    @Override
    public void onTaskCompleteJson(String result) {
        urlText = result;
        openBrowser(urlText);
    }


    public void openBrowser(String url){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }



}
