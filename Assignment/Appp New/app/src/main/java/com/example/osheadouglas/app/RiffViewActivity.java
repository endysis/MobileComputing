package com.example.osheadouglas.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RiffViewActivity extends AppCompatActivity {

    private String finalRID;
    private String finalRName;
    private String finalRMI;
    private String finalRDES;
    private String finalRPATH;
    private String finalRPHOPATH;
    private String finalLoc;



    private TextView txtRiffName;
    private TextView txtRiffMI;
    private TextView txtRiffDES;
    private TextView txtRiffLOC;
    private ImageView riffBanner;
    private ImageView riffPhoto;
    private Button mediaButton;

    private MediaPlayer mediaPlayer = null;

    boolean mediaCheck = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riff_view);

        txtRiffName = (TextView) findViewById(R.id.riffName);
        txtRiffMI = (TextView) findViewById(R.id.riffMood);
        txtRiffDES = (TextView) findViewById(R.id.riffDesc);
        txtRiffLOC = (TextView) findViewById(R.id.riffLocation);
        riffBanner = (ImageView) findViewById(R.id.bannerPic);
        riffPhoto = (ImageView) findViewById(R.id.smallPic);

        mediaButton = (Button) findViewById(R.id.playButton);


        Bundle bundle = getIntent().getExtras();
        finalRID = bundle.getString("rID");
        finalRName = bundle.getString("rName");
        finalRMI = bundle.getString("rMI");
        finalRDES = bundle.getString("rD");
        finalRPATH = bundle.getString("rP");
        finalRPHOPATH = bundle.getString("rPP");
        finalLoc = bundle.getString("rL");



        txtRiffName.setText(finalRName);
        txtRiffMI.setText(finalRMI);
        txtRiffDES.setText(finalRDES);
        txtRiffLOC.setText(finalLoc);

        setImage(riffPhoto,finalRPHOPATH);
        setImage(riffBanner,finalRPHOPATH);



    }


    public void playRiff(View view){

        if(mediaCheck){
            if(mediaPlayer != null){
                mediaPlayer.stop();
            }
            mediaCheck = false;
            mediaButton.setText("PLAY RIFF");
        } else {
            try {
                releaseMediaPlayer();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(finalRPATH);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                Toast.makeText(this, "Media File not found, Please restart application", Toast.LENGTH_LONG).show();
                Log.e("ERROR", "Media File not found, Please restart application");
            }
            mediaCheck = true;
            mediaButton.setText("STOP RIFF");
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {

                mediaCheck = false;
                mediaButton.setText("PLAY RIFF");
            }
        });
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }

    private void setImage(ImageView i, String path){
        File imgFile = new File(path);

        if(imgFile.exists()){
            Bitmap bity = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            i.setImageBitmap(bity);
        }
    }





}
