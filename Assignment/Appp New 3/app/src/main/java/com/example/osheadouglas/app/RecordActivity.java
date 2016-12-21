package com.example.osheadouglas.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Debug;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer = null;
    private MediaRecorder recorder = null;
    private String OUTPUT_FILE;
    private String moodInps = "";
    private String location = "";
    private String inspireString = "";

    private EditText userInput;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Bundle bundle = getIntent().getExtras();
        inspireString = bundle.getString("inspire");
        location = bundle.getString("location");
        Date now = new Date();
        String time = now.toString().replace(":","_");
        // Pass this the the next activity, in that activity ask the user fro details then save all in the database. Encrpt the info before storingin the database.
        // ENcrypt using this http://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat maybeeee
        OUTPUT_FILE = Environment.getExternalStorageDirectory()+ "/ " + time  + "riff.3gpp"; // Specifies the output
    }


    public void saveRecBtn(View view){
        Intent i = new Intent(this,SaveActivity.class);
        i.putExtra("filePath",OUTPUT_FILE);
        i.putExtra("mood",moodInps);
        i.putExtra("location",location);
        i.putExtra("inspire", inspireString);
        startActivity(i);
    }


    public void startRec(View view){
        beginRecording();
    }


    public void endRec(View view){
        stopRecording();
    }

    public void restartClick(View view){
        try {
            File file = new File(OUTPUT_FILE);
            boolean deleted = file.delete();
        } catch (Exception e){
            Log.e("ERROR", "File name cannot be found");
        }

        Date now = new Date();
        String time = now.toString().replace(":","_");
        OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/ " + time + "riff.3gpp";
        beginRecording();
    }


    public void startPlay(View view) throws IOException {
        startPlayBack();
    }


    public void endPlay(View view){
        endPlayBack();
    }


    private void beginRecording() {
       // releaseMediaRecording();
        File outFile = new File(OUTPUT_FILE);
        if(outFile.exists()){ outFile.delete();}
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // change to AAC if does not work
                recorder.setOutputFile(OUTPUT_FILE);
        try {
            recorder.prepare();
            //recorder.start();
        } catch (IOException e){
            Log.e("ERROR ", "Prep failed");
        }
        recorder.start();
    }


    private void stopRecording() {
        if(recorder != null){
            recorder.stop();
        }
    }





    private void startPlayBack() throws IOException {

       try {
           releaseMediaPlayer();
           mediaPlayer = new MediaPlayer();
           mediaPlayer.setDataSource(OUTPUT_FILE);
           mediaPlayer.prepare();
           mediaPlayer.start();
       } catch(Exception e){
           Toast.makeText(this,"Media File not found, Please restart application",Toast.LENGTH_LONG).show();
           Log.e("ERROR", "Media File not found, Please restart application");
       }
    }



    private void endPlayBack() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }




    private void releaseMediaRecording(){ // Gues its lets go of a recoring that is already in the buffer
        if(recorder != null){
            recorder.release();
        }
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }






































}






