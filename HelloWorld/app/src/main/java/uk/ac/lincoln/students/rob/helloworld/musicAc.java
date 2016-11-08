package uk.ac.lincoln.students.rob.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
import android.widget.*;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class musicAc extends Activity {



    // global vsariable to store returned xml data from service
    static String xml = "";

    // global variable to bitmap for current number 1 single (we aren't returning the other 39 songs!)
    static Bitmap bitmap;

    // array list to store song names from  service
    ArrayList<String> songs = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        // start the  AsyncTask for calling the REST service using httpConnect class
        new AsyncTaskParseXml().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
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

    public void getMusicData(View view){
        Intent intent = new Intent(this,musicAc.class);
        startActivity(intent);
    }

    public class AsyncTaskParseXml extends AsyncTask<String,String,String>{
        // set the url of the web service to call
        // the url is encoded below, unencoded it is: https://query.yahooapis.com/v1/public/yql?q=SELECT content, src, alt FROM html WHERE url="http://www.bbc.co.uk/radio1/chart/singles/" and xpath="//img[@class='cht-entry-image']|//div[@class='cht-entry-artist']/a" LIMIT 80
        String yourXmlServiceUrl = "https://query.yahooapis.com/v1/public/yql?q=SELECT%20content%2C%20src%2C%20alt%20FROM%20html%20WHERE%0Aurl%3D%22http%3A%2F%2Fwww.bbc.co.uk%2Fradio1%2Fchart%2Fsingles%2F%22%20and%20xpath%3D%22%2F%2Fimg%5B%40class%3D'cht-entry-image'%5D%7C%2F%2Fdiv%5B%40class%3D'cht-entry-artist'%5D%2Fa%22%20LIMIT%2080";

        @Override
        // this method is used for......................
        protected void onPreExecute() {}

        @Override
        // this method is used for...................
        protected String doInBackground(String... arg0)  {

            try {
                // create new instance of the httpConnect class
                httpConnect xmlParser = new httpConnect();

                // get xml string from service url
                xml = xmlParser.getJSONFromUrl(yourXmlServiceUrl);

            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        // below method will run when service HTTP request is complete
        protected void onPostExecute(String strFromDoInBg) {
            // bind the xml from service to the textview
            TextView tv1 = (TextView)findViewById(R.id.xmlText);
            tv1.setText(xml);
        }



    }




}
