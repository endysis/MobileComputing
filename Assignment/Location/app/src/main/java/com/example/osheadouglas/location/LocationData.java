package com.example.osheadouglas.location;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class LocationData extends AppCompatActivity {



    static String xml = "";

    private String latReceive;
    private String longReceive;
    private String apiKey = "AIzaSyBtboWfA9FAdUcLH3ULpqDKOJeKzOXU-aI";
    private String radius = "1000";
    private String locFind;
    TextView address1;
    TextView location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_data);

        location = (TextView) findViewById(R.id.locationID);
        address1 = (TextView) findViewById(R.id.addressTV);

        // Getting Location Data from the other intent
        Bundle bundle = getIntent().getExtras();
        latReceive = bundle.getString("lat");
        longReceive = bundle.getString("long");


        new AsyncTaskParseXml().execute();
    }




    public class AsyncTaskParseXml extends AsyncTask<String,String,String> {

        String yourXmlServiceUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location="+latReceive+","+longReceive+"&"+radius+"&key="+apiKey;

        @Override
        // this method is used for......................
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

                            if(name.equals("vicinity")) {
                                locFind =(text);
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
            location.setText(locFind);
        }
    }



    public void prefSearch(View view){
         String loc = locFind.toLowerCase();
         Intent intent = new Intent(this,Music.class);
         intent.putExtra("Location",loc);
         this.startActivity(intent);
    }





}









