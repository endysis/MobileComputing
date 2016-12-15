package com.example.osheadouglas.app;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    // https://www.bing.com/images/search?view=detailv2&FORM=OIIRPO&q=sailing+dinghies&id=343498995D41C02DDC1E7DF0B7196F7A86E34593&simid=608048528616131155





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AsyncTaskParseJson j = new AsyncTaskParseJson();
       // j.execute();

        //ImageView i = (ImageView) findViewById(R.id.imageView);

        //Picasso.with(getApplicationContext()).load("http://www.bing.com/cr?IG=619AAE9F9C1F4CBC9FC7820B4DFF1226&CID=23404499F65868F701544D40F73E699A&rd=1&h=k4UDjLQvESCjqWJWRUV0E1QtWi46JX5rHSsebLNRg7o&v=1&r=http%3a%2f%2fimages5.fanpop.com%2fimage%2fphotos%2f27500000%2fRadiohead-radiohead-27519232-1920-1080.jpg&p=DevEx,5008.1").into(i);
    }


    public void recordClick(View view){
        Intent intent = new Intent(this,RecordActivity.class);
        startActivity(intent);
    }


    public void inspireClick(View view){
        Intent intent = new Intent(this,InspireOptions.class);
        startActivity(intent);
    }

    public void viewClick(View view){
        Intent intent = new Intent(this,RiffSelectActivity.class);
        startActivity(intent);
    }





}




// http://www.bing.com/cr?IG=619AAE9F9C1F4CBC9FC7820B4DFF1226&CID=23404499F65868F701544D40F73E699A&rd=1&h=k4UDjLQvESCjqWJWRUV0E1QtWi46JX5rHSsebLNRg7o&v=1&r=http%3a%2f%2fimages5.fanpop.com%2fimage%2fphotos%2f27500000%2fRadiohead-radiohead-27519232-1920-1080.jpg&p=DevEx,5008.1