package com.example.computing.intenttests;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.ContentProvider;


public class MainActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_"; // File name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // For dis
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "File: " + image.getAbsolutePath();
        return image;
    }

    // Task 1
    public void cameraIntent(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);



        /*
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex){
            // Its not working
        }

        if(photoFile != null){
            Uri photoURI = FileProvider.getUriFile(this, "com.example.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);

        }
        */
    }

    // Task 2
    public void browserIntent(View view){
        String url = "http://www.imdb.com/title/";
        String movieID = "tt0816692";
        Uri webpage = Uri.parse(url + movieID);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(intent);
    }

    // Task 3
    public void eventIntent(View view){
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 12, 25, 00, 00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, 12, 25, 00, 01);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Xmas!");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "North Pole");
        startActivity(calendarIntent);
    }




    @Override
    // Returning from the intent
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            // Gets the name of the id.
            ImageView img = (ImageView) findViewById(R.id.picView);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }




}






