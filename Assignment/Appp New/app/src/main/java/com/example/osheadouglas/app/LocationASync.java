package com.example.osheadouglas.app;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by osheadouglas on 27/11/2016.
 */

public class LocationASync extends AsyncTask<String,String,String> {

    private locationCallBack<String> callBack; // Call back

    static String xml = "";
    private String latReceive;
    private String longReceive;
    private String apiKey = "AIzaSyBtboWfA9FAdUcLH3ULpqDKOJeKzOXU-aI";
    private String radius = "1000";
    private String locFind;


    public LocationASync(locationCallBack<String> cb,String longI, String latI){
        super();
        longReceive = longI;
        latReceive = latI;
        callBack = cb;
    }


    @Override
    // this method is used for......................
    protected void onPreExecute() {}

    // Background
    @Override
    protected String doInBackground(String... arg0) {
        String locationUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location="+latReceive+","+longReceive+"&"+radius+"&key="+apiKey;
        locFind = new String();
        try {
            String text = null;
            httpConnect xmlParser = new httpConnect();
            // get xml string from service url
            xml = xmlParser.getJSONFromUrl(locationUrl,"",false);
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
                            locFind = (text);
                            return locFind;
                        }
                }
                event = xpp.next(); // Moves on to the next event type
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return locFind;
    }

    @Override
    // below method will run when service HTTP request is complete
    protected void onPostExecute(String locFind) {
        callBack.onTaskCompleteLocation(locFind);
    }


}
































