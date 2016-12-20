package com.example.osheadouglas.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.R.attr.id;
import static com.example.osheadouglas.app.DatabaseHelper.TABLE_NAME;

public class RiffViewActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
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
    private TextView riffLength;
    private ImageView riffBanner;
    private ImageView riffPhoto;
    private Button mediaButton;
    private MediaPlayer mediaPlayer = null;
    boolean mediaCheck = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riff_view);
        myDb = new DatabaseHelper(this);
        txtRiffName = (TextView) findViewById(R.id.riffName);
        txtRiffMI = (TextView) findViewById(R.id.riffMood);
        txtRiffDES = (TextView) findViewById(R.id.riffDesc);
        txtRiffLOC = (TextView) findViewById(R.id.riffLocation);
        riffBanner = (ImageView) findViewById(R.id.bannerPic);
        riffPhoto = (ImageView) findViewById(R.id.smallPic);
        riffLength = (TextView) findViewById(R.id.riffLength);
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

        riffLength.setText(getAudioFileLength(finalRPATH));

    }

    public String getAudioFileLength(String PATH){
        int i = 1;
        releaseMediaPlayer();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(PATH);
            mediaPlayer.prepare();
        } catch(Exception e) {
         Log.i("Error","File Not found");
        }
         i = mediaPlayer.getDuration();
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(i),
                TimeUnit.MILLISECONDS.toSeconds(i) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(i)));
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

    public void deleteClick(View view){
        File file1 = new File(finalRPATH);
        File file2 = new File(finalRPHOPATH);
        boolean deleted = file1.delete();
        boolean deleted2 = file2.delete();
        Intent i = new Intent(this,RiffSelectActivity.class);
        try {
            SQLiteDatabase db = myDb.getWritableDatabase();
            db.delete(TABLE_NAME, "NAME = ?", new String[]{finalRName}); // Return number of deleted data

            if(deleted == true) {
                Toast.makeText(this, "Riff Deleted", Toast.LENGTH_SHORT).show();
                startActivity(i);
            } else {
                Toast.makeText(this, "Riff not seleted unable to locate path", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        } catch(Exception e){
            Toast.makeText(this, "Database connection failed, Please reload app", Toast.LENGTH_SHORT).show();
            }
        }

}













