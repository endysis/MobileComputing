package com.example.osheadouglas.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by osheadouglas on 23/11/2016.
 */

// 79a57862ebb742fea38bceff8dc324e8


class AsyncTaskParseXml extends AsyncTask<String,String,ArrayList<String>> {


    private AsyncTaskCompleteListenerXml<ArrayList<String>> callBack; // Call back

    private static final String TAG = "LIST CHECK";

    static String xml;
    String xmlService;
    String xmlSpecifier;

    String authKey;  // Auth key is passed to async task, and then past to the httpconnect method.
    boolean isAuth;

    String xmlCatch;


    String mE; // Music List Element    Text or Picture
    ArrayList<String> mL; // Music LIst


    // Default contructor takes Callback Listener, takes the http Url , takes the URl specifier, Takes an auth key,
    public AsyncTaskParseXml(AsyncTaskCompleteListenerXml<ArrayList<String>> cb, String xmlServiceI ,String xmlSpecifierI,String authKeyI, boolean isAuthI,String xmlCatchI){ // I == Input
        super();

        xmlService = xmlServiceI;
        xmlSpecifier = xmlSpecifierI;
        authKey = authKeyI;
        isAuth = isAuthI;
        xmlCatch = xmlCatchI;

        this.callBack = cb;
    }


    @Override
    protected void onPreExecute() {

    }

    // Background
    @Override
    protected ArrayList<String> doInBackground(String... arg0) {

        mE = new String();
        mL = new ArrayList<>(); // Music LIst
        try {

            String text = null;

            httpConnect xmlParser = new httpConnect();

            // get xml string from service url
            xml = xmlParser.getJSONFromUrl(xmlService+xmlSpecifier,authKey,isAuth);

            // Create new instance of XMLPullParser to parse xml
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);

            // XPP
            XmlPullParser xpp = factory.newPullParser();

            // set input to xml parser as xml string from service
            xpp.setInput(new StringReader(xml));

            // variable for XML parse event
            int event = xpp.getEventType();

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

                        if(name.equals(xmlCatch)) {  // was name
                            mE = text;
                            mL.add(mE); // Add it to the list
                            break;
                        }
                }
                event = xpp.next(); // Moves on to the next event type
            }
        }
        catch (Exception e) {
            Log.i(TAG,"XML Not found please run application");
            e.printStackTrace();
        }
        return mL;
    }




    @Override
    // below method will run when service HTTP request is complete
    protected void onPostExecute(ArrayList<String> listOutput) {
        //Log.i(TAG,listOutput.get(0).getMusicDescription());
        callBack.onTaskCompleteXml(listOutput);
        Log.i(TAG,"On Post Execute Called");
    }
}

































