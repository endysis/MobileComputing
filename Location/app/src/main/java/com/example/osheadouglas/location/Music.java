package com.example.osheadouglas.location;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Music extends AppCompatActivity {


    // global vsariable to store returned xml data from service
    static String xml = "";

    // global variable to bitmap for current number 1 single (we aren't returning
    static Bitmap bitmap;

    private String locationReceive;

    // array list to store song names from  service
    ArrayList<String> songs = new ArrayList<String>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Bundle bundle = getIntent().getExtras();
        locationReceive = bundle.getString("Location");

        // start the  AsyncTask for calling the REST service using httpConnect class
        new AsyncTaskParseXml().execute();
    }



    public class AsyncTaskParseXml extends AsyncTask<String,String,String> {

       // String loc = locationReceive.toLowerCase();

        String yourXmlServiceUrl = "http://musicovery.com/api/V2/artist.php?fct=getfromlocation&location="+locationReceive;


        @Override
        protected void onPreExecute() {}


        // Background
        @Override
        protected String doInBackground(String... arg0) {

            try {

                String text = null;

                httpConnect xmlParser = new httpConnect();

                // get xml string from service url
                xml = xmlParser.getJSONFromUrl(yourXmlServiceUrl);


                // Create new instance of XMLPullParser to parse xml
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                // XPP
                XmlPullParser xpp = factory.newPullParser();


                // set input to xml parser as xml string from service
                xpp.setInput(new StringReader(xml));


                // variable for XML parse event
                int event = xpp.getEventType();


                // variable for setting chart position of each song (the xml doesnt return this!)
                int chartPosition = 1;


                // while statement to loop through xml tags to end of xml document
                while (event != XmlPullParser.END_DOCUMENT) {

                    // Stores the tag name
                    String name = xpp.getName();



                    switch (event){ // If at the start of the tag do nothing
                        case XmlPullParser.START_TAG:
                            break;



                        case XmlPullParser.TEXT:
                                text = xpp.getText(); // Stores the text when needed in the next case.
                            break;



                        case XmlPullParser.END_TAG: // We are at the end of a given tag

                            // when xml parser matches the <img> tag, get the 'alt' attribute value which contains the song name

                            if(name.equals("name")) {
                                songs.add(chartPosition + ".  " + text);
                                // increment chart position for next song!
                                chartPosition++;
                                break;
                            }
                    }
                    event = xpp.next(); // Moves on to the next event type
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // below method will run when service HTTP request is complete
        protected void onPostExecute(String strFromDoInBg) {
            // bind bitmap parsed from image url in xml for current number 1 single
            ImageView singleArt = (ImageView) findViewById(R.id.singleCoverArt);
            singleArt.setImageBitmap(bitmap);

            // bind the song names parsed from xml to the ListView
            ListView list = (ListView)findViewById(R.id.songList);
            ArrayAdapter<String> songArrayAdapter = new ArrayAdapter<String>(Music.this, android.R.layout.simple_expandable_list_item_1, songs);
            list.setAdapter(songArrayAdapter);
        }

        }

    }





